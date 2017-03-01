package com.mockuai.itemcenter.common.api;

/**
 * Created by yindingyu on 16/6/8.
 */
public class WrappedResponse<Module> {

    private int  code;

    private Module module;

    private String message;

    public int getCode() {
        return code;
    }

    public Module getModule() {
        return module;
    }

    public String getMessage() {
        return message;
    }

    private WrappedResponse(){

    }

    public static  <Module> WrappedResponse<Module>  wrap(int code,Module module,String message){
        WrappedResponse<Module> response = new WrappedResponse<Module>();
        response.code = code;
        response.module = module;
        response.message =  message;
        return response;
    }
}
