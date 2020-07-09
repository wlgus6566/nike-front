package com.nike.dnp.service.order;

import com.nike.dnp.common.mail.MailService;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.email.OrderProductDTO;
import com.nike.dnp.dto.email.SendDTO;
import com.nike.dnp.dto.order.OrderProductMappingSaveDTO;
import com.nike.dnp.dto.order.OrderSearchDTO;
import com.nike.dnp.entity.order.Order;
import com.nike.dnp.entity.order.OrderProductMapping;
import com.nike.dnp.repository.order.OrderProductMapperRepository;
import com.nike.dnp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class Order product mapping service.
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 1. 오전 11:51:28
 * @Description
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
	 * Save order product mapping order product mapping.
	 *
	 * @param orderProductMappingSaveDTO the order product mapping save dto
	 * @return the order product mapping
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오후 3:07:14
	 * @Description
	 */
	@Transactional
	public OrderProductMapping saveOrderProductMapping(final OrderProductMappingSaveDTO orderProductMappingSaveDTO) {
		OrderProductMapping orderProductMapping = new OrderProductMapping();
		orderProductMapping.setGoodsSeq(orderProductMappingSaveDTO.getGoodsSeq());
		orderProductMapping.setOrderSeq(orderProductMappingSaveDTO.getOrderSeq());
		orderProductMapping.setOrderQuantity(orderProductMappingSaveDTO.getOrderQuantity());
		return orderProductMapperRepository.save(orderProductMapping);
	}


	/**
	 * Order sheet send boolean.
	 *
	 * @param order the order
	 * @return the boolean
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오후 3:07:14
	 * @Description
	 */
	public void orderSheetSend(final Order order) {
		List<HashMap<String, Object>> emailList = orderProductMapperRepository.findSearchEmailValue(order.getOrderSeq());

		List<Long> agencyList = new ArrayList<>();
		Long compareAgencySeq = null;
		for(HashMap<String, Object> map : emailList){
			Long agencySeq = Long.parseLong(String.valueOf(map.get("agencySeq")));
			if(ObjectUtils.isEmpty(compareAgencySeq) || !compareAgencySeq.equals(agencySeq)){
				agencyList.add(agencySeq);
				compareAgencySeq = agencySeq;
			}
		}

		List<SendDTO> sendDTOList = new ArrayList<>();
		for(Long tempAgencySeq : agencyList){
			SendDTO sendDTO = new SendDTO();
			List<OrderProductDTO> productList = new ArrayList<>();
			for(HashMap<String, Object> map : emailList){
				Long agencySeq = Long.parseLong(String.valueOf(map.get("agencySeq")));
				if(tempAgencySeq.equals(agencySeq)){
					sendDTO.setNickname(SecurityUtil.currentUser()
													.getNickname());
					sendDTO.setEmail(String.valueOf(map.get("email")));
					sendDTO.setAgencyName(String.valueOf(map.get("agencyName")));
					sendDTO.setOrderDt(String.valueOf(map.get("registrationDt")));
					sendDTO.setOrderComment(String.valueOf(map.get("orderDescription")));
					OrderProductDTO orderProductDTO = new OrderProductDTO();
					orderProductDTO.setAmount(Integer.parseInt(String.valueOf(map.get("orderQuantity"))));
					orderProductDTO.setProductName(String.valueOf(map.get("goodsName")));
					orderProductDTO.setProductDesc(String.valueOf(map.get("goodsDescription")));
					productList.add(orderProductDTO);
				}
			}
			sendDTO.setProductList(productList);
			sendDTOList.add(sendDTO);
		}


		for(SendDTO sendDTO : sendDTOList){
			DecimalFormat format = new DecimalFormat("###,###");
			StringBuilder sb = new StringBuilder();
			for(OrderProductDTO dto : sendDTO.getProductList()){
				sb.append("<tr style=\"border-top:1px solid #ddd\">");
				sb.append("    <td style=\"padding:15px; border-right:1px solid #ddd;\">");
				sb.append("        <p style=\"margin:0\">" + dto.getProductName() + "</p>");
				sb.append("        <p style=\"margin:2px 0 0 0; font-size:12px; color:#888;\">" + dto.getProductDesc() + "</p>");
				sb.append("    </td>");
				sb.append("    <td align=\"center\">");
				sb.append("        <p>" + format.format(dto.getAmount()) + "개</p>");
				sb.append("    </td>");
				sb.append("</tr>");
			}
			sendDTO.setOrderArea(sb.toString());
			mailService.sendMail(
					ServiceEnumCode.EmailTypeEnumCode.ORDER.toString(),
					ServiceEnumCode.EmailTypeEnumCode.ORDER.getMessage(),
					sendDTO);

		}
	}

	/**
	 * Find page order page.
	 *
	 * @param orderSearchDTO the order search dto
	 * @return the page
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오후 2:44:07
	 * @Description
	 */
	public Page<OrderProductMapping> findPageOrder(final OrderSearchDTO orderSearchDTO) {
		orderSearchDTO.setUserSeq(SecurityUtil.currentUser()
											  .getUserSeq());

		return orderProductMapperRepository.findPagesOrder(
				orderSearchDTO,
				PageRequest.of(
						orderSearchDTO.getPage(),
						orderSearchDTO.getSize(),
						Sort.by("registrationDt").descending()));

	}

	/**
	 * Find by id order product mapping.
	 *
	 * @param orderGoodsSeq the order goods seq
	 * @return the order product mapping
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 7. 오후 2:44:07
	 * @Description
	 */
	public OrderProductMapping findById(final Long orderGoodsSeq) {
		return orderProductMapperRepository.findById(orderGoodsSeq).orElse(new OrderProductMapping());
	}
}
