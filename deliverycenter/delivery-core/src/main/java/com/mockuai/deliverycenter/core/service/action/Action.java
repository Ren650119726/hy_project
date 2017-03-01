package com.mockuai.deliverycenter.core.service.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.service.RequestContext;

/**
 * 操作对像基类
 * 
 * @author wujin.zzq
 *
 */
@Service
public interface Action {
	static final Log log = LogFactory.getLog(Action.class);

	@SuppressWarnings("rawtypes")
	public DeliveryResponse execute(RequestContext context)
			throws DeliveryException;

	public String getName();
}
