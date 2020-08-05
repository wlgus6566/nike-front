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
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	 * @implNote
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
	 * @implNote
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
					sendDTO.setNickname(SecurityUtil.currentUser()
													.getNickname());
					sendDTO.setEmail(String.valueOf( orderProductResultDTO.getEmail()));
					sendDTO.setAgencyName(String.valueOf(orderProductResultDTO.getAgencyName()));
					sendDTO.setOrderDt(String.valueOf(orderProductResultDTO.getRegistrationDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))));
					sendDTO.setOrderComment(String.valueOf(orderProductResultDTO.getOrderDescription()));
					final OrderProductDTO orderProductDTO = new OrderProductDTO();
					orderProductDTO.setAmount(Integer.parseInt(String.valueOf(orderProductResultDTO.getOrderQuantity())));
					orderProductDTO.setProductName(String.valueOf(orderProductResultDTO.getGoodsName()));
					orderProductDTO.setProductDesc(String.valueOf(orderProductResultDTO.getGoodsDescription()));
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
				builder.append("<tr style=\"border-top:1px solid #ddd\"><td style=\"padding:15px; border-right:1px solid #ddd;\"><p style=\"margin:0\">");
				builder.append(dto.getProductName());
				builder.append("</p><p style=\"margin:2px 0 0 0; font-size:12px; color:#888;\">");
				builder.append(dto.getProductDesc());
				builder.append("</p></td><td align=\"center\"><p>");
				builder.append(format.format(dto.getAmount()));
				builder.append("개</p></td></tr>");
			}
			sendDTO.setOrderArea(builder.toString());
			mailService.sendMail(
					ServiceCode.EmailTypeEnumCode.ORDER.toString(),
					ServiceCode.EmailTypeEnumCode.ORDER.getMessage(),
					sendDTO);

		}
	}

}


