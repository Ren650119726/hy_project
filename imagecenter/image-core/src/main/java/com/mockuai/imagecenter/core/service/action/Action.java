package com.mockuai.imagecenter.core.service.action;

import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.core.exception.ImageException;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 * 
 * @author luliang
 *
 */
@Service
public interface Action {

	public ImageResponse execute(RequestContext context) throws ImageException;

	public String getName();
}
