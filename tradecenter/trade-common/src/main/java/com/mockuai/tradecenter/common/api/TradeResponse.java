package com.mockuai.tradecenter.common.api;

import java.io.Serializable;

import com.mockuai.tradecenter.common.constant.ResponseCode;


public class TradeResponse<T> implements Response<T>,Serializable{
	private static final long serialVersionUID = 8295766534182699773L;
	
	private T module;
    private int code;
    private String message;
    private long totalCount = 0;
    
    public TradeResponse(ResponseCode responseCode,String message){
    	this.code = responseCode.getCode();
    	this.message = message;
    }
    
    public TradeResponse(ResponseCode responseCode){
    	this.code = responseCode.getCode();
    	this.message = responseCode.getComment();
    }
    
    public TradeResponse(int code, String message){
    	this.code =code;
    	this.message = message;
    }
    
    /**
     * 只有当module是集合类型时候才需要将集合大小赋值给totalCount字段
     * @param module
     */
    public TradeResponse(T module){
    	this.module = module;
    	/*if(module != null){
    		if(module instanceof Collection){
    			totalCount = ((Collection)module).size();
    		}else{
    			totalCount = 0;
    		}
    	}*/
		this.code = ResponseCode.RESPONSE_SUCCESS.getCode();
		this.message = ResponseCode.RESPONSE_SUCCESS.getComment();
    }
    
	public T getModule() {
		return module;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCode(int code){
		this.code = code;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString(){
		return "code : " + this.code + " message: " + this.message + " totalCount: " + this.totalCount + "module : " + this.module ;
	}


	public boolean isSuccess() {
		return this.code == ResponseCode.RESPONSE_SUCCESS.getCode();
	}


	public int getCode() {
		return this.code;
	}
}
