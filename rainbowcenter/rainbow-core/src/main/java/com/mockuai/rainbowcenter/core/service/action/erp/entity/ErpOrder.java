package com.mockuai.rainbowcenter.core.service.action.erp.entity;
import java.util.Date;
import java.util.List;

/**
 * Created by lizg on 2016/6/13.
 */
public class ErpOrder extends ErpCommon{
    private  String order_type_code; //订单类型code

    private Integer refund;    //退款状态 0-非退款 ，1--退款（退款中）；

    private Boolean cod;     //货到付款

    private String platform_code; //平台单号

    private String shop_code; //店铺code

    private String express_code;  //物流公司code

    private String warehouse_code; //仓库code

    private String vip_code;      //会员code

    private String vip_name;     //会员名称

    private String receiver_name; //收货人

    private String receiver_address; //收货地址

    private String receiver_zip;  //收货邮编

    private String receiver_mobile; //收货人手机

    private String receiver_phone; //收货人电话

    private String receiver_province; //收货人省份

    private String receiver_city;  //收货人城市

    private String receiver_district; //收货人区域

    private String tag_code;    //标记code

    private String deal_datetime; //拍单时间

    private String pay_datetime; //付款时间

    private String business_man_code; //业务员code

    private String post_fee; //物流费用

    private String cod_fee; //到付服务费

    private String discount_fee; //让利金额

    private Date plan_delivery_date; //预计发货日期

    private String buyer_memo; //买家留言

    private String seller_memo; //卖家备注

    private String seller_memo_late; //二次备注

    private String vipIdCard; //身份证号

    private String vipRealName; //真实姓名

    private String vipEmail;  //电子邮箱

    private List<ErpPayments> payments;   //支付信息数组

    private List<ErpItemAttribute> details;  //商品信息数组

    private List<ErpInvoices> invoices;   //发票信息数组

    public String getBusiness_man_code() {
        return business_man_code;
    }

    public void setBusiness_man_code(String business_man_code) {
        this.business_man_code = business_man_code;
    }

    public String getBuyer_memo() {
        return buyer_memo;
    }

    public void setBuyer_memo(String buyer_memo) {
        this.buyer_memo = buyer_memo;
    }

    public Boolean getCod() {
        return cod;
    }

    public void setCod(Boolean cod) {
        this.cod = cod;
    }

    public String getDeal_datetime() {
        return deal_datetime;
    }

    public void setDeal_datetime(String deal_datetime) {
        this.deal_datetime = deal_datetime;
    }



    public List<ErpItemAttribute> getDetails() {
        return details;
    }

    public void setDetails(List<ErpItemAttribute> details) {
        this.details = details;
    }



    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public List<ErpInvoices> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<ErpInvoices> invoices) {
        this.invoices = invoices;
    }

    public String getOrder_type_code() {
        return order_type_code;
    }

    public void setOrder_type_code(String order_type_code) {
        this.order_type_code = order_type_code;
    }

    public List<ErpPayments> getPayments() {
        return payments;
    }

    public void setPayments(List<ErpPayments> payments) {
        this.payments = payments;
    }

    public String getPay_datetime() {
        return pay_datetime;
    }

    public void setPay_datetime(String pay_datetime) {
        this.pay_datetime = pay_datetime;
    }

    public Date getPlan_delivery_date() {
        return plan_delivery_date;
    }

    public void setPlan_delivery_date(Date plan_delivery_date) {
        this.plan_delivery_date = plan_delivery_date;
    }

    public String getPlatform_code() {
        return platform_code;
    }

    public void setPlatform_code(String platform_code) {
        this.platform_code = platform_code;
    }

    public String getCod_fee() {
        return cod_fee;
    }

    public void setCod_fee(String cod_fee) {
        this.cod_fee = cod_fee;
    }

    public String getDiscount_fee() {
        return discount_fee;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }

    public String getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public String getSeller_memo() {
        return seller_memo;
    }

    public void setSeller_memo(String seller_memo) {
        this.seller_memo = seller_memo;
    }

    public String getSeller_memo_late() {
        return seller_memo_late;
    }

    public void setSeller_memo_late(String seller_memo_late) {
        this.seller_memo_late = seller_memo_late;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public String getTag_code() {
        return tag_code;
    }

    public void setTag_code(String tag_code) {
        this.tag_code = tag_code;
    }

    public String getVip_code() {
        return vip_code;
    }

    public void setVip_code(String vip_code) {
        this.vip_code = vip_code;
    }

    public String getVip_name() {
        return vip_name;
    }

    public void setVip_name(String vip_name) {
        this.vip_name = vip_name;
    }

    public String getVipEmail() {
        return vipEmail;
    }

    public void setVipEmail(String vipEmail) {
        this.vipEmail = vipEmail;
    }

    public String getVipIdCard() {
        return vipIdCard;
    }

    public void setVipIdCard(String vipIdCard) {
        this.vipIdCard = vipIdCard;
    }

    public String getVipRealName() {
        return vipRealName;
    }

    public void setVipRealName(String vipRealName) {
        this.vipRealName = vipRealName;
    }

    public String getWarehouse_code() {
        return warehouse_code;
    }

    public void setWarehouse_code(String warehouse_code) {
        this.warehouse_code = warehouse_code;
    }
}
