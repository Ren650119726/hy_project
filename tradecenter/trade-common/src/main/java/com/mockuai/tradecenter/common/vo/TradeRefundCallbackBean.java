package com.mockuai.tradecenter.common.vo;


/**
 * 银行卡支付类
 * @author duzl
 *
 */
public class TradeRefundCallbackBean extends TranBaseBean{

    private static final long serialVersionUID = 1L;
    private String            oid_partner;          // 商户编号
    private String            no_refund;            //商户退款流水号
    private String            money_refund;         //退款金额
    private String            oid_refundno;         //连连银通退款流水号
    private String            sta_refund;         //退款状态   退款申请: 0 退款处理中：1 退款成功：2  退款失败：3 
    private String            settle_date;         //清算日期
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getNo_refund() {
		return no_refund;
	}
	public void setNo_refund(String no_refund) {
		this.no_refund = no_refund;
	}
	public String getMoney_refund() {
		return money_refund;
	}
	public void setMoney_refund(String money_refund) {
		this.money_refund = money_refund;
	}
	public String getOid_refundno() {
		return oid_refundno;
	}
	public void setOid_refundno(String oid_refundno) {
		this.oid_refundno = oid_refundno;
	}
	public String getSta_refund() {
		return sta_refund;
	}
	public void setSta_refund(String sta_refund) {
		this.sta_refund = sta_refund;
	}
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
    
    

    

}
