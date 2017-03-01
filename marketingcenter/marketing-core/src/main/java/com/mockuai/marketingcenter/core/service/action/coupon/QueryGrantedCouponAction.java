package com.mockuai.marketingcenter.core.service.action.coupon;

import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.EmployeeManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.UserManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Created by edgar.zr on 7/15/2016.
 */
@Controller
public class QueryGrantedCouponAction extends TransAction {

	public static final Logger LOGGER = LoggerFactory.getLogger(QueryGrantedCouponAction.class);

	@Autowired
	private GrantedCouponManager grantedCouponManager;
	@Autowired
	private MarketActivityManager marketActivityManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private EmployeeManager employeeManager;
	@Autowired
	private PropertyManager propertyManager;

	@Override
	protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
		GrantedCouponQTO grantedCouponQTO = (GrantedCouponQTO) context.getRequest().getParam("grantedCouponQTO");
		String appKey = (String) context.get("appKey");
		String bizCode = (String) context.get("bizCode");

		grantedCouponQTO.setBizCode(bizCode);
		List<GrantedCouponDTO> grantedCouponDTOs = new ArrayList<>();
		// receiverId
		if (StringUtils.isNotBlank(grantedCouponQTO.getUserName())) {
			UserDTO userDTO = userManager.getUserByMobile(grantedCouponQTO.getUserName(), appKey);
			LOGGER.info("get user by mobile : {}, result : {}", grantedCouponQTO.getUserName(), JsonUtil.toJson(userDTO));
			if (userDTO != null) {
				grantedCouponQTO.setReceiverId(userDTO.getId());
			} else {
				return MarketingUtils.getSuccessResponse(grantedCouponDTOs, 0);
			}
		}
		// granterId
		if (StringUtils.isNotBlank(grantedCouponQTO.getOperatorName())) {
			EmployeeDTO employeeDTO = employeeManager.getEmployeeByUserName(grantedCouponQTO.getOperatorName(), appKey);
			LOGGER.info("the user queried by operatorName : {}, result : {}", grantedCouponQTO.getOperatorName(), JsonUtil.toJson(employeeDTO));
			if (employeeDTO != null) {
				grantedCouponQTO.setGranterId(employeeDTO.getId());
			} else {
				return MarketingUtils.getSuccessResponse(grantedCouponDTOs, 0);
			}
		}
		LOGGER.info("queryGrantedCoupon, qto : {}", JsonUtil.toJson(grantedCouponQTO));
		List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);

		if (grantedCouponDOs.isEmpty()) {
			return MarketingUtils.getSuccessResponse(grantedCouponDTOs, grantedCouponQTO.getTotalCount());
		}

		Map<Long, List<GrantedCouponDO>> marketIdKey = new HashMap<>();
		Set<Long> userIds = new HashSet<>();
		Set<Long> granterIds = new HashSet<>();
		for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
			if (!marketIdKey.containsKey(grantedCouponDO.getActivityId())) {
				marketIdKey.put(grantedCouponDO.getActivityId(), new ArrayList<GrantedCouponDO>());
			}
			marketIdKey.get(grantedCouponDO.getActivityId()).add(grantedCouponDO);
			userIds.add(grantedCouponDO.getReceiverId());
			if (grantedCouponDO.getGranterId() != null && grantedCouponDO.getGranterId() != 0) {
				granterIds.add(grantedCouponDO.getGranterId());
			}
		}
		LOGGER.info("get receiverId, list : {}", Arrays.deepToString(userIds.toArray()));
		List<UserDTO> userDTOs = userManager.queryByUserIdList(new ArrayList<>(userIds), appKey);
		if (userDTOs == null) {
			userDTOs = new ArrayList<>();
		}
		LOGGER.info("receiverId , {}", JsonUtil.toJson(userDTOs));

		EmployeeQTO employeeQTO = new EmployeeQTO();
		//employeeQTO.setIdList(new ArrayList<Long>(granterIds));注释现有不知道什么用处魔筷提供的代码
		LOGGER.info("get granterId, list : {}", Arrays.deepToString(granterIds.toArray()));
		List<EmployeeDTO> employeeDTOs = employeeManager.queryEmployee(employeeQTO, appKey);
		if (employeeDTOs == null) {
			employeeDTOs = new ArrayList<>();
		}
		LOGGER.info("granterId, {}", JsonUtil.toJson(employeeDTOs));

		Map<Long, UserDTO> userIdKey = new HashMap<>();
		for (UserDTO userDTO : userDTOs) {
			userIdKey.put(userDTO.getId(), userDTO);
		}

		Map<Long, EmployeeDTO> employeeIdKey = new HashMap<>();
		for (EmployeeDTO employeeDTO : employeeDTOs) {
			employeeIdKey.put(employeeDTO.getId(), employeeDTO);
		}

		MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
		marketActivityQTO.setIdList(new ArrayList<>(marketIdKey.keySet()));
		marketActivityQTO.setBizCode(bizCode);
		List<MarketActivityDO> marketActivityDOs = marketActivityManager.queryActivity(marketActivityQTO);
		List<MarketActivityDTO> marketActivityDTOs = ModelUtil.genMarketActivityDTOList(marketActivityDOs);
		propertyManager.fillUpMarketWithProperty(marketActivityDTOs, bizCode);
		for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
			grantedCouponDTOs.addAll(
					ModelUtil.genGrantedCouponDTOList(marketIdKey.get(marketActivityDTO.getId()), marketActivityDTO));
		}
		UserDTO userDTO;
		EmployeeDTO employeeDTO;
		Date now = new Date();
		for (GrantedCouponDTO grantedCouponDTO : grantedCouponDTOs) {
			userDTO = userIdKey.get(grantedCouponDTO.getReceiverId());
			if (userDTO != null) {
				grantedCouponDTO.setReceiverName(userDTO.getMobile());
			}
			employeeDTO = employeeIdKey.get(grantedCouponDTO.getGranterId());
			if (employeeDTO != null) {
				grantedCouponDTO.setGranterName(employeeDTO.getName());
			}
			if (grantedCouponDTO.getStatus() == UserCouponStatus.USED.getValue()
					    || grantedCouponDTO.getStatus() == UserCouponStatus.PRE_USE.getValue()) {
				grantedCouponDTO.setStatus(2);
				continue;
			}
			if (grantedCouponDTO.getInvalidTime() != null && grantedCouponDTO.getInvalidTime().before(now)
					    || grantedCouponDTO.getInvalidTime() == null && grantedCouponDTO.getEndTime().before(now)) {
				grantedCouponDTO.setStatus(3);
			} else if (grantedCouponDTO.getStatus() == UserCouponStatus.UN_USE.getValue()) {
				grantedCouponDTO.setStatus(1);
			} else {
				grantedCouponDTO.setStatus(3);
			}
		}
		Collections.sort(grantedCouponDTOs, new GrantedCouponComparator());
		LOGGER.info("grantedCouponDTOs : {}", JsonUtil.toJson(grantedCouponDTOs));
		return MarketingUtils.getSuccessResponse(grantedCouponDTOs, grantedCouponQTO.getTotalCount());
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_GRANTED_COUPON.getActionName();
	}

	private class GrantedCouponComparator implements Comparator<GrantedCouponDTO> {

		@Override
		public int compare(GrantedCouponDTO o1, GrantedCouponDTO o2) {
			if (o1.getId() > o2.getId().longValue()) {
				return -1;
			}
			if (o1.getId() < o2.getId().longValue()) {
				return 1;
			}
			return 0;
		}
	}
}