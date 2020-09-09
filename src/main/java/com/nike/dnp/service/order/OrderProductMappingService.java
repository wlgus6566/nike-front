package com.nike.dnp.service.order;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.email.OrderProductDTO;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.dto.order.OrderProductMappingSaveDTO;
import com.nike.dnp.dto.order.OrderProductResultDTO;
import com.nike.dnp.entity.order.OrderEntity;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.repository.order.OrderProductMapperRepository;
import com.nike.dnp.util.CloudFrontUtil;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Order product mapping service.
 *
 * @author [윤태호]
 * @since 2020. 7. 1. 오전 11:51:28
 * @implNote
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

	/**
	 * The Mail service
	 *
	 * @author [윤태호]
	 */
	private final MailService mailService;


	/**
	 * 주문 제품 맴핑 저장
	 *
	 * @param orderProductMappingSaveDTO the order product mapping save dto
	 * @return the order product mapping
	 * @author [윤태호]
	 * @since 2020. 7. 2. 오후 3:07:14
	 * @implNote 주문 제품 맴핑 저장
	 */
	@Transactional
	public OrderProductMapping saveOrderProductMapping(final OrderProductMappingSaveDTO orderProductMappingSaveDTO) {
		log.info("OrderProductMappingService.saveOrderProductMapping");
		final OrderProductMapping orderProductMapping = new OrderProductMapping();
		orderProductMapping.setGoodsSeq(orderProductMappingSaveDTO.getGoodsSeq());
		orderProductMapping.setOrderSeq(orderProductMappingSaveDTO.getOrderSeq());
		orderProductMapping.setOrderQuantity(orderProductMappingSaveDTO.getOrderQuantity());
		return orderProductMapperRepository.save(orderProductMapping);
	}

	/**
	 * 주문내역 이메일 발송
	 *
	 * @param orderEntity the order
	 * @return the boolean
	 * @author [윤태호]
	 * @since 2020. 7. 2. 오후 3:07:14
	 * @implNote 주문내역 이메일 발송
	 */
	public void orderSheetSend(final OrderEntity orderEntity) {
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
		for(final Long tempAgencySeq : agencyList){
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
					productList.add(orderProductDTO);
				}
			}
			sendDTO.setProductList(productList);
			sendDTOList.add(sendDTO);
		}

		for(final SendDTO sendDTO : sendDTOList){
			final DecimalFormat format = new DecimalFormat("###,###");
			final StringBuilder builder = new StringBuilder();
			for(final OrderProductDTO dto : sendDTO.getProductList()){
				builder.append("<tr>");
				builder.append("	<td style=\"width:50px; margin:0; padding:15px; border-bottom:1px solid #ddd;\">");
				builder.append("		<div style=\"width:50px; height:50px; overflow:hidden; margin:0; padding:0; line-height:50px; background:#f7f7f7; \">");
				builder.append("			<img width=\"50\" style=\"width:50px; vertical-align:middle;\" src=\"");
				builder.append(CloudFrontUtil.getCustomSignedUrl(dto.getImageFilePhysicalName(), 259200));
				builder.append("\" alt=\"\" >");
				builder.append("		</div>");
				builder.append("	</td>");
				builder.append("	<td style=\"margin:0; padding:15px; border-bottom:1px solid #ddd;\">");
				builder.append("		<p style=\"margin:0; font-size:12px; color:#333; line-height:18px;\">");
				builder.append(dto.getProductName());
				builder.append("		</p>");
				builder.append("		<p style=\"margin:5px 0 0 0; font-size:11px; color:#555; line-height:17px;\">");
				builder.append(dto.getProductDesc());
				builder.append("		</p>");
				builder.append("	</td>");
				builder.append("	<td align=\"center\" style=\"margin:0; padding:0; width:103px; border-bottom:1px solid #ddd; font-size:12px; color:#000;\">");
				builder.append("		<strong>");
				builder.append(format.format(dto.getAmount()));
				builder.append("		</strong>");
				builder.append("		개</p>");
				builder.append("	</td>");
				builder.append("</tr>");
			}
			sendDTO.setOrderArea(builder.toString());
			mailService.sendMail(
					ServiceCode.EmailTypeEnumCode.ORDER.toString(),
					ServiceCode.EmailTypeEnumCode.ORDER.getMessage(),
					sendDTO);
		}
	}
}