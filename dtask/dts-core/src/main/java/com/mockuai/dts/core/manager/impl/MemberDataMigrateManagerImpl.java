//package com.mockuai.dts.core.manager.impl;
//
//import com.mockuai.customer.client.CustomerClient;
//import com.mockuai.customer.common.api.CustomerResponse;
//import com.mockuai.dts.common.TaskStatusEnum;
//import com.mockuai.dts.common.constant.ResponseCode;
//import com.mockuai.dts.common.domain.MemberDataMigrateDTO;
//import com.mockuai.dts.core.dao.ExportTaskDAO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.MemberDataMigrateManager;
//import com.mockuai.usercenter.client.UserClient;
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.usercenter.common.qto.UserQTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * Created by duke on 15/12/30.
// */
//@Service
//public class MemberDataMigrateManagerImpl implements MemberDataMigrateManager {
//    private static final Logger log = LoggerFactory.getLogger(MemberDataMigrateManagerImpl.class);
//
//    @Resource
//    private UserClient userClient;
//
//    @Resource
//    private CustomerClient customerClient;
//
//    @Resource
//    private ExportTaskDAO exportTaskDAO;
//
//    @Override
//    public boolean dataMigrate(MemberDataMigrateDTO memberDateMigrateDTO, ExportTaskDO exportTaskDO)
//            throws DtsException {
//        try {
//            log.info("start migrate member data");
//            long offset = 0L;
//            int count = 500;
//            while (true) {
//                UserQTO userQTO = new UserQTO();
//                userQTO.setOffset(offset);
//                userQTO.setCount(count);
//                Response<List<UserDTO>> response = userClient.queryUser(userQTO, exportTaskDO.getAppKey());
//                if (!response.isSuccess()) {
//                    throw new DtsException(response.getCode(), response.getMessage());
//                }
//                List<UserDTO> userDTOs = response.getModule();
//                CustomerResponse<Boolean> resp = customerClient.batchAddMember(userDTOs, exportTaskDO.getAppKey());
//                if (!resp.isSuccess()) {
//                    throw new DtsException(response.getCode(), resp.getMessage());
//                }
//
//                long total = response.getTotalCount();
//                int process;
//                if (total == 0) {
//                    process = 0;
//                } else {
//                    process = (int) (offset / total) * 100;
//                }
//
//                exportTaskDO.setTaskProcess(process);
//                exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
//                exportTaskDAO.updateTask(exportTaskDO);
//
//                offset += userDTOs.size();
//                if (userDTOs.size() < count) {
//                    // 最后一页，退出
//                    break;
//                }
//            }
//            log.info("end migrate member data");
//        } catch (Exception e) {
//            log.error("data migrate error", e);
//            throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
//        }
//
//        exportTaskDO.setTaskProcess(100);
//        exportTaskDO.setTaskStatus(TaskStatusEnum.COMPLETE_TASK.getStatus());
//        exportTaskDO.setFileLink("");
//        exportTaskDAO.updateTask(exportTaskDO);
//        return true;
//    }
//}
