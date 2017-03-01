package com.mockuai.marketingcenter.core.filter;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.service.RequestContext;

/**
 * Filter的超类， 实现它的子类必须提供一个无参的构造函数
 * filter在配置文件中支持可配置的属性， 不过现在只支持这几种类型：
 * int, long, char, byte, boolean, String
 */
public interface Filter {

    /*
     * before和after的前置判断条件， 只有返回true， before和after才会被执行。
     */
    public boolean isAccept(RequestContext ctx);

    /*
    * 组合在chain的before中调用，如果isAccept返回true并且filter业务执行成功，则返回true
    * 否则返回false
    */
    public MarketingResponse before(RequestContext ctx) throws MarketingException;

    /*
    * 组合在chain的after中调用，如果isAccept返回true并且filter业务执行成功，则返回true
    * 否则返回false
    */
    public MarketingResponse after(RequestContext ctx) throws MarketingException;
}