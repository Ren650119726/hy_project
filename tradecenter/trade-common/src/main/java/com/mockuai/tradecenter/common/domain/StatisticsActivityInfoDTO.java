package com.mockuai.tradecenter.common.domain; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

/**
 * Created by hy on 2016/7/20.
 */
public class StatisticsActivityInfoDTO extends  BaseDTO {

    private Integer orderCount ;
    private Integer cancelOrderCount;
    private Integer waitPaidOrderCount;
    private Integer paidOrderCount;
    private Integer refundOrderCount;
    private Long totalAmount;
    private Long cancelOrderAmount;
    private Long paidOrderAmount;
    private Long refundOrderAmount;
    private Long averageAmount;//客单价
    private Long discountAmount ;
    /**参与人数**/
    private Integer participants;

    private Long waitPaidOrderAmount;


    public Long getWaitPaidOrderAmount() {
        return waitPaidOrderAmount;
    }

    public void setWaitPaidOrderAmount(Long waitPaidOrderAmount) {
        this.waitPaidOrderAmount = waitPaidOrderAmount;
    }

    public Integer getWaitPaidOrderCount() {
        return waitPaidOrderCount;
    }

    public void setWaitPaidOrderCount(Integer waitPaidOrderCount) {
        this.waitPaidOrderCount = waitPaidOrderCount;
    }

    public void setAverageAmount(Long averageAmount) {
        this.averageAmount = averageAmount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getCancelOrderCount() {
        return cancelOrderCount;
    }

    public void setCancelOrderCount(Integer cancelOrderCount) {
        this.cancelOrderCount = cancelOrderCount;
    }

    public Integer getPaidOrderCount() {
        return paidOrderCount;
    }

    public void setPaidOrderCount(Integer paidOrderCount) {
        this.paidOrderCount = paidOrderCount;
    }

    public Integer getRefundOrderCount() {
        return refundOrderCount;
    }

    public void setRefundOrderCount(Integer refundOrderCount) {
        this.refundOrderCount = refundOrderCount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getCancelOrderAmount() {
        return cancelOrderAmount;
    }

    public void setCancelOrderAmount(Long cancelOrderAmount) {
        this.cancelOrderAmount = cancelOrderAmount;
    }

    public Long getPaidOrderAmount() {
        return paidOrderAmount;
    }

    public void setPaidOrderAmount(Long paidOrderAmount) {
        this.paidOrderAmount = paidOrderAmount;
    }

    public Long getRefundOrderAmount() {
        return refundOrderAmount;
    }

    public void setRefundOrderAmount(Long refundOrderAmount) {
        this.refundOrderAmount = refundOrderAmount;
    }

    public Long getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(Double averageAmount) {
        if(averageAmount != null){
            this.averageAmount =  Math.round( averageAmount);
        }
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
}
