package com.mockuai.tradecenter.core.base;

/**
 * 
 * @author hzmk
 *
 */
public interface ClientExecutorFactory {
   
	/**
	 * 根据支付方式和获取不同的执行器
	 * @param paymentId
	 * @return
	 */
    public ClientExecutor getExecutor(String paymentId);
    
}

