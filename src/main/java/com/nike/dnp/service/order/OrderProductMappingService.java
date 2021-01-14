package com.nike.dnp.service.order;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.email.OrderProductDTO;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.dto.order.OrderProductFileSaveDTO;
import com.nike.dnp.dto.order.OrderProductMappingSaveDTO;
import com.nike.dnp.dto.order.OrderProductResultDTO;
import com.nike.dnp.dto.user.UserDTO;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.entity.order.OrderProductFile;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.repository.order.OrderProductFileRepository;
import com.nike.dnp.repository.order.OrderProductMapperRepository;
import com.nike.dnp.util.CloudFrontUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class Order product mapping service.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 1. 오전 11:51:28
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderProductMappingService {

	/**
	 * The Order product mapper repository
	 *
	 * @author [윤태호]
	 */
	private final OrderProductMapperRepository orderProductMapperRepository;

	private final OrderProductFileRepository orderProductFileRepository;

	/**
	 * The Mail service
	 *
	 * @author [윤태호]
	 */
	private final MailService mailService;

	/**
	 * The Editor url
	 */
	@Value("${nike.url.pc.domain:}")
	private String cdnUrl;


	/**
	 * 주문 제품 맴핑 저장
	 *
	 * @param orderProductMappingSaveDTO the order product mapping save dto
	 * @return the order product mapping
	 * @author [윤태호]
	 * @implNote 주문 제품 맴핑 저장
	 * @since 2020. 7. 2. 오후 3:07:14
	 */
	@Transactional
	public OrderProductMapping saveOrderProductMapping(final OrderProductMappingSaveDTO orderProductMappingSaveDTO) {
		log.info("OrderProductMappingService.saveOrderProductMapping");
		final OrderProductMapping orderProductMapping = new OrderProductMapping();
		orderProductMapping.setGoodsSeq(orderProductMappingSaveDTO.getGoodsSeq());
		orderProductMapping.setOrderSeq(orderProductMappingSaveDTO.getOrderSeq());
		orderProductMapping.setOrderQuantity(orderProductMappingSaveDTO.getOrderQuantity());
		orderProductMapping.setProductDescription(orderProductMappingSaveDTO.getProductDescription());
		return orderProductMapperRepository.save(orderProductMapping);


	}

	/**
	 * 주문내역 이메일 발송
	 *
	 * @param orderEntity   the order
	 * @param recipientList the recipient list
	 * @return the boolean
	 * @author [이소정]
	 * @implNote 주문내역 이메일 발송
	 * @since 2021. 1. 5. 오후 6:45:37
	 */
	public void orderSheetSend(final OrderEntity orderEntity, final List<UserDTO> recipientList) {
		log.info("OrderProductMappingService.orderSheetSend");
		final List<OrderProductResultDTO> emailList = orderProductMapperRepository.findSearchEmailValue(orderEntity.getOrderSeq());

		final List<Long> agencyList = new ArrayList<>();
		Long compareAgencySeq = null;
		for(final OrderProductResultDTO orderProductResultDTO : emailList){
			final Long agencySeq = Long.parseLong(String.valueOf(orderProductResultDTO.getAgencySeq()));
			if(ObjectUtils.isEmpty(compareAgencySeq) || !compareAgencySeq.equals(agencySeq)){
				agencyList.add(agencySeq);
				compareAgencySeq = agencySeq;
			}
		}

		final List<SendDTO> sendDTOList = new ArrayList<>();
		for(final Long tempAgencySeq : agencyList) {
			final SendDTO sendDTO = new SendDTO();
			final List<OrderProductDTO> productList = new ArrayList<>();
			for(final OrderProductResultDTO orderProductResultDTO : emailList){
				final Long agencySeq = Long.parseLong(String.valueOf(orderProductResultDTO.getAgencySeq()));
				if(tempAgencySeq.equals(agencySeq)){
					sendDTO.setNickname(SecurityUtil.currentUser().getNickname());
					sendDTO.setEmail(String.valueOf( orderProductResultDTO.getEmail()));
					sendDTO.setAgencyName(String.valueOf(orderProductResultDTO.getAgencyName()));
					sendDTO.setOrderDt(orderProductResultDTO.getRegistrationDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
					sendDTO.setOrderComment(StringUtils.defaultIfBlank(orderProductResultDTO.getOrderDescription(), ""));
					final OrderProductDTO orderProductDTO = new OrderProductDTO();
					orderProductDTO.setAmount(Integer.parseInt(String.valueOf(orderProductResultDTO.getOrderQuantity())));
					orderProductDTO.setProductName(String.valueOf(orderProductResultDTO.getGoodsName()));
					orderProductDTO.setProductDesc(String.valueOf(orderProductResultDTO.getGoodsDescription()));
					orderProductDTO.setImageFilePhysicalName(orderProductResultDTO.getImageFilePhysicalName());
					orderProductDTO.setProductComment(orderProductResultDTO.getProductDescription());

					// 상품별 파일 목록 조회
					orderProductDTO.setProductFileList(
							orderProductFileRepository.findByOrderGoodsSeq(orderProductResultDTO.getOrderGoodsSeq())
					);

					productList.add(orderProductDTO);
				}
			}
			sendDTO.setProductList(productList);
			sendDTOList.add(sendDTO);
		}

		// 선택한 수신자 목록 메일 발송
		if (!recipientList.isEmpty()) {
			for (UserDTO userDTO : recipientList) {
				final SendDTO sendDTO = new SendDTO();
				final List<OrderProductDTO> productList = new ArrayList<>();
				for(final OrderProductResultDTO orderProductResultDTO : emailList){
					sendDTO.setNickname(SecurityUtil.currentUser().getNickname());
					sendDTO.setEmail(String.valueOf(userDTO.getUserId()));
					sendDTO.setAgencyName(String.valueOf(userDTO.getNickname()));
					sendDTO.setOrderDt(orderProductResultDTO.getRegistrationDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
					sendDTO.setOrderComment(StringUtils.defaultIfBlank(orderProductResultDTO.getOrderDescription(), ""));
					final OrderProductDTO orderProductDTO = new OrderProductDTO();
					orderProductDTO.setAmount(Integer.parseInt(String.valueOf(orderProductResultDTO.getOrderQuantity())));
					orderProductDTO.setProductName(String.valueOf(orderProductResultDTO.getGoodsName()));
					orderProductDTO.setProductDesc(String.valueOf(orderProductResultDTO.getGoodsDescription()));
					orderProductDTO.setImageFilePhysicalName(orderProductResultDTO.getImageFilePhysicalName());
					orderProductDTO.setProductComment(orderProductResultDTO.getProductDescription());

					// 상품별 파일 목록 조회
					orderProductDTO.setProductFileList(
							orderProductFileRepository.findByOrderGoodsSeq(orderProductResultDTO.getOrderGoodsSeq())
					);
					productList.add(orderProductDTO);

				}
				sendDTO.setProductList(productList);
				sendDTOList.add(sendDTO);
			}

		}

		for (final SendDTO sendDTO : sendDTOList){
			final DecimalFormat format = new DecimalFormat("###,###");
			final StringBuilder builder = new StringBuilder();
			for(final OrderProductDTO dto : sendDTO.getProductList()){
builder.append("<tr>");
builder.append("	<td style=\"margin:0; padding:30px; border-bottom:1px solid #ddd;\">");
builder.append("		<table border=\"0\" style=\"width:100%; border-collapse:collapse; table-layout:fixed; font-family:'Noto Sans KR', 'Malgun Gothic', Arial, Sans-serif;\">");
builder.append("			<colgroup>");
builder.append("				<col style=\"width:65px;\">");
builder.append("				<col style=\"width:425px;\">");
builder.append("			</colgroup>");
builder.append("			<tbody>");
builder.append("			<tr>");
builder.append("				<td style=\"width:65px; margin:0; vertical-align:top\">");
builder.append("					<div style=\"width:50px; height:50px; overflow:hidden; margin:0; padding:0; line-height:50px; background:#f7f7f7; \" >");
builder.append("			<img width=\"50\" style=\"width:50px; vertical-align:middle;\" src=\"");
builder.append(dto.getImageFilePhysicalName());
builder.append("\">");
builder.append("					</div>");
builder.append("				</td>");
builder.append("				<td style=\"width:425px;\">");
builder.append("					<p style=\"margin:0; width:300px; font-size:12px; color:#333; line-height:18px;\">");
builder.append(dto.getProductName());
//builder.append(dto.getProductName()+"<br><br>");
builder.append("					</p>");
builder.append("					<p style=\"margin:5px 0 0 0; width:300px; font-size:11px; color:#555; line-height:17px;\">");
builder.append(dto.getProductDesc());
builder.append("					</p>");
builder.append("				</td>");
builder.append("			</tr>");
builder.append("			<tr>");
builder.append("				<td style=\"width:65px; margin:0; padding-top:15px; vertical-align:top;\">");
builder.append("					<span style=\"line-height:18px; font-size:12px; color:#888;\">");
builder.append("					주문수량");
builder.append("					</span>");
builder.append("				</td>");
builder.append("				<td style=\"width:425px;  padding-top:15px; vertical-align:top;\">");
builder.append("					<span style=\"line-height:18px; font-size:12px; color:#000;\"strong>");
builder.append(format.format(dto.getAmount()));
builder.append("					</strong> 개</span>");
builder.append("				</td>");
builder.append("			</tr>");
builder.append("			<tr>");
builder.append("				<td style=\"width:65px; margin:0; padding-top:5px; vertical-align:top;\">");
builder.append("					<span style=\"line-height:18px; font-size:12px; color:#888;\">");
builder.append("					첨부파일");
builder.append("					</span>");
builder.append("				</td>");
builder.append("				<td style=\"width:425px;  padding-top:5px; vertical-align:top;\">");
builder.append("					<div style=\"overflow:hidden\">");
List<OrderProductFile> productFileList = dto.getProductFileList();
for (OrderProductFile orderProductFileSaveDTO : productFileList) {
	builder.append("						<a href=\"");
	builder.append(cdnUrl+orderProductFileSaveDTO.getFilePhysicalName());
	builder.append("						\" style=\"float:left; display:block; margin-right:5px;\">");
	builder.append("							<span style=\"display:inline-block;\">");
	builder.append("								<img style=\"width:40px; height:40px; vertical-align:middle;\" src=\"");

	builder.append(cdnUrl+orderProductFileSaveDTO.getThumbnailFilePhysicalName());

	builder.append("								\" alt=\"\">");
	builder.append("							</span>");
	builder.append("						</a>");
}

builder.append("					</div>");
builder.append("				</td>");
builder.append("			</tr>");
builder.append("			<tr>");
builder.append("				<td style=\"width:65px; margin:0; padding-top:15px; vertical-align:top;\">");
builder.append("					<span style=\"line-height:18px; font-size:12px; color:#888;\">");
builder.append("					요청사항");
builder.append("					</span>");
builder.append("				</td>");
builder.append("				<td style=\"width:425px;  padding-top:10px; vertical-align:top;\">");
builder.append("					<span style=\"line-height:18px; font-size:12px; color:#888;\">");
builder.append(dto.getProductComment());
builder.append("					</span>");
builder.append("				</td>");
builder.append("			</tr>");
builder.append("			</tbody>");
builder.append("		</table>");
builder.append("	</td>");
builder.append("</tr>");

//				builder.append("<tr>");
//				builder.append("	<td style=\"width:90px; margin:0; padding:15px 0 15px 30px; border-bottom:1px solid #ddd;\">");
//				builder.append("		<div style=\"width:50px; height:50px; overflow:hidden; margin:0; padding:0; line-height:50px; background:#f7f7f7; \">");
//				builder.append("			<img width=\"50\" style=\"width:50px; vertical-align:middle;\" src=\"");
//				builder.append(CloudFrontUtil.getCustomSignedUrl(dto.getImageFilePhysicalName(), 259200));
//				builder.append("\" alt=\"\" >");
//				builder.append("		</div>");
//				builder.append("	</td>");
//				builder.append("	<td style=\"width:365px; margin:0; padding:15px; border-bottom:1px solid #ddd;\">");
//				builder.append("		<p style=\"margin:0; font-size:12px; color:#333; line-height:18px;\">");
//				builder.append(dto.getProductName());
//				builder.append("		</p>");
//				builder.append("		<p style=\"margin:5px 0 0 0; font-size:11px; color:#555; line-height:17px;\">");
//				builder.append(dto.getProductDesc());
//				builder.append("		</p>");
//				builder.append("	</td>");
//				builder.append("	<td align=\"center\" style=\"margin:0; padding:0; width:103px; border-bottom:1px solid #ddd; font-size:12px; color:#000;\">");
//				builder.append("		<strong>");
//				builder.append(format.format(dto.getAmount()));
//				builder.append("		</strong>");
//				builder.append("		개</p>");
//				builder.append("	</td>");
//				builder.append("</tr>");
			}
			sendDTO.setOrderArea(builder.toString());
			mailService.sendMail(
					ServiceCode.EmailTypeEnumCode.ORDER.toString(),
					ServiceCode.EmailTypeEnumCode.ORDER.getMessage(),
					sendDTO);
		}
	}
}
