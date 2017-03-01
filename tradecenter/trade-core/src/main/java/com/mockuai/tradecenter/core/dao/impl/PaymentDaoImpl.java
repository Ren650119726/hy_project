//package com.mockuai.tradecenter.core.dao.impl;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//
//import com.mockuai.tradecenter.core.dao.PaymentDao;
//import com.mockuai.tradecenter.core.domain.PaymentDO;
//
//public class PaymentDaoImpl extends SqlMapClientDaoSupport  implements PaymentDao{
//
//	@Override
//	public Long addPayment(PaymentDO payment) {
//		return (Long)this.getSqlMapClientTemplate().insert("payment.addPayment",payment);
//	}
//
//	@Override
//	public int deletePayment(Integer paymentId) {
//		return this.getSqlMapClientTemplate().update("payment.deletePayment",paymentId);
//	}
//
//	@Override
//	public PaymentDO getPayment(Integer paymentId){
//		return (PaymentDO)this.getSqlMapClientTemplate().queryForObject("payment.getPayment",paymentId);
//	}
//
//	@Override
//	public int updatePayment(PaymentDO payment) {
//		return this.getSqlMapClientTemplate().update("payment.updatePayment",payment);
//	}
//
//	@Override
//	public PaymentDO getPaymentByClass(PaymentDO paymentDO){
//		return (PaymentDO)this.getSqlMapClientTemplate().queryForObject("payment.getPaymentByClass",paymentDO);
//	}
//}
