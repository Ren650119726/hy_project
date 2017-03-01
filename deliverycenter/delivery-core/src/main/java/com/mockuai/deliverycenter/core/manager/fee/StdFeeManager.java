//package com.mockuai.deliverycenter.core.manager.fee;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
//import com.mockuai.deliverycenter.common.qto.fee.StdFeeQTO;
//import com.mockuai.deliverycenter.core.domain.fee.StdFee;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//
//@Service
//public interface StdFeeManager {
//
//	public StdFeeDTO addStdFee(StdFeeDTO StdFeeDTO) throws DeliveryException;
//
//	public int updateStdFee(StdFeeDTO StdFeeDTO) throws DeliveryException;
//
//	public int deleteStdFee(Integer id) throws DeliveryException;
//
//	public List<StdFeeDTO> queryStdFee(StdFeeQTO stdFeeQTO)
//			throws DeliveryException;
//
//	public StdFee getStdFee(Integer feeId) throws DeliveryException;
//
//}
