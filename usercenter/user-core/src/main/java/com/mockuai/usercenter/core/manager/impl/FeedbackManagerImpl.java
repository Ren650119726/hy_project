package com.mockuai.usercenter.core.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.qto.FeedbackQTO;
import com.mockuai.usercenter.core.dao.UserDAO;
import com.mockuai.usercenter.core.domain.UserDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.core.dao.FeedbackDAO;
import com.mockuai.usercenter.core.domain.FeedbackDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.FeedbackManager;

/**
 * Created by duanyytop on 15/5/13.
 */
@Service
public class FeedbackManagerImpl implements FeedbackManager {

    @Resource
    private FeedbackDAO feedbackDAO;

    @Resource
    private UserDAO userDAO;

    @Override
    public Long addFeedback(FeedbackDTO feedbackDTO) throws UserException {

        if(feedbackDTO == null){
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "feedbackDTO is null");
        }

        if(feedbackDTO.getContent() == null || "".equals(feedbackDTO.getContent())){
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "content is null");
        }

        // 将dto转换为do
        FeedbackDO feedbackDO = new FeedbackDO();
        BeanUtils.copyProperties(feedbackDTO, feedbackDO);
        Long id = feedbackDAO.addFeedback(feedbackDO);

        return id;
    }


    @Override
    public List<FeedbackDTO> getFeedbackByDate(Date startDate, Date endDate, Long offset, Integer count, String bizCode) throws UserException {
        if (null == offset) {
            offset = 0L;
        }
        if(null == count){
            count  = 100;
        }
        List<FeedbackDO> feedbackDOList = feedbackDAO.getFeedbackByDate(startDate, endDate, offset, count, bizCode);
        List<FeedbackDTO> feedbackDTOList = null;
        if (null != feedbackDOList) {
            feedbackDTOList = new ArrayList<FeedbackDTO>();
            for(FeedbackDO feedbackDO : feedbackDOList){
                FeedbackDTO feedbackDTO = new FeedbackDTO();
                BeanUtils.copyProperties(feedbackDO, feedbackDTO);

                //get name by id
                UserDO userDO= userDAO.getUserById(feedbackDTO.getUserId());
                if(userDO!=null&&userDO.getName()!=null){
                    feedbackDTO.setSenderName(userDO.getName());
                }else{
               //     feedbackDTO.setSenderName("name is null");
                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date=formatter.format(feedbackDO.getGmtCreated());
                feedbackDTO.setTime(date);

                feedbackDTOList.add(feedbackDTO);
            }
        }
        return feedbackDTOList;
    }

    @Override
    public FeedbackDTO getFeedbackById(Long id) throws UserException {
        if (null == id) {
            throw new UserException(ResponseCode.P_PARAM_NULL,
                    "id is null");
        }
        FeedbackDO feedbackDO = feedbackDAO.getFeedbackById(id);
        FeedbackDTO feedbackDTO = null;
        if (null != feedbackDO) {
            feedbackDTO = new FeedbackDTO();
            BeanUtils.copyProperties(feedbackDO, feedbackDTO);

            //get name by id
            UserDO userDO= userDAO.getUserById(feedbackDTO.getId());
            if(userDO!=null&&userDO.getName()!=null){
                feedbackDTO.setSenderName(userDO.getName());
            }else{
                feedbackDTO.setSenderName("name is null");
            }

        }
        return feedbackDTO;
    }
    
    
    public Integer getTotalCount(FeedbackQTO feedbackQTO)throws UserException{
    	Integer result = this.feedbackDAO.getTotalCount(feedbackQTO);
    	return result;
    }

	@Override
	public List<FeedbackDTO> queryFeeback(FeedbackQTO feedbackQTO)
			throws UserException {
		if(feedbackQTO ==null){
   		 throw new UserException(ResponseCode.P_PARAM_NULL,
                    "feedbackQTO is null");
		}
       List<FeedbackDO> list = feedbackDAO.queryFeedback(feedbackQTO);
       List<FeedbackDTO> result =new ArrayList<FeedbackDTO>();
       if(list != null){
       	 for(FeedbackDO feedbackDO : list){
                FeedbackDTO feedbackDTO = new FeedbackDTO();
                BeanUtils.copyProperties(feedbackDO, feedbackDTO);
             //get name by id
             UserDO userDO= userDAO.getUserById(feedbackDTO.getId());
             if(userDO!=null&&userDO.getName()!=null){
                 feedbackDTO.setSenderName(userDO.getName());
             }else{
                 feedbackDTO.setSenderName("name is null");
             }
                result.add(feedbackDTO);
            }
       }
       return result;
	}
    
}
