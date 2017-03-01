//package com.mockuai.usercenter.core.service.action.suppliercompany;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.dto.SupplierCompanyDTO;
//import com.mockuai.usercenter.common.qto.SupplierCompanyQTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.CompanyManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
//@Service
//public class QueryCompanyAction implements Action {
//
//	@Resource
//	private CompanyManager companyManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		List<SupplierCompanyDTO> supplierCompanyDtos = null;
//
//		UserRequest userRequest = context.getRequest();
//		SupplierCompanyQTO companyQto = (SupplierCompanyQTO) userRequest
//				.getParam("companyQTO");
//
//		try {
//			supplierCompanyDtos = companyManager.queryCompany(companyQto);
//		} catch (UserException e) {
//			log.error(e.getMessage(), e);
//			return new UserResponse(e.getResponseCode(),
//					e.getMessage());
//		}
//		return new UserResponse(supplierCompanyDtos);
//	}
//
//	@Override
//	public String getName() {
//
//		return ActionEnum.QUERY_SUPPLIER_COMPANY.getActionName();
//	}
//
//}
