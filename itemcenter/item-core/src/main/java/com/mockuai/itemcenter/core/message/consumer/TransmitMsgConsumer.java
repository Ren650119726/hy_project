package com.mockuai.itemcenter.core.message.consumer;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.mockuai.itemcenter.core.message.adapter.TopicAdapter;
import com.mockuai.itemcenter.core.message.listener.CrushOrderSkuListener;
import com.mockuai.itemcenter.core.message.listener.ItemSalesCountMsgListener;
import com.mockuai.itemcenter.core.message.listener.TradeMsgListener;

/**
 * Created by duke on 15/9/25.
 */
public class TransmitMsgConsumer implements InitializingBean{

    private static final Logger log = LoggerFactory.getLogger(TransmitMsgConsumer.class);

    private String accessKey;

    private String secretKey;

    private String consumerId;

    private TopicAdapter topicAdapter;

    private TradeMsgListener tradeMsgListener;
    
    private ItemSalesCountMsgListener itemSalesCountMsgListener;   

	private CrushOrderSkuListener crushOrderSkuListener;

    private Integer consumeThreadNums;

    @Override
    public void afterPropertiesSet() throws Exception {
        consumer();
    }

    public void consumer(){
        try {

            Properties properties = new Properties();

            properties.put(PropertyKeyConst.ConsumerId, consumerId);// ConsumerId需要设置您自己的
            properties.put(PropertyKeyConst.AccessKey, accessKey);// AccessKey 需要设置您自己的
            properties.put(PropertyKeyConst.SecretKey, secretKey);// SecretKey 需要设置您自己的
            properties.put(PropertyKeyConst.ConsumeThreadNums, consumeThreadNums);

            Consumer consumer = ONSFactory.createConsumer(properties);

            consumer.subscribe(topicAdapter.adapt("haiyn_trade_msg"),"paySuccessNotify || orderUnpaid",tradeMsgListener);
            //consumer.subscribe(topicAdapter.adapt("haiyn_trade_msg"),"orderUnpaid",crushOrderSkuListener);

            consumer.start();
            System.out.println("Consumer Started");
        } catch (ExceptionInInitializerError e) {
            log.error("mq consumer start error", e);
        }
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public TopicAdapter getTopicAdapter() {
        return topicAdapter;
    }

    public void setTopicAdapter(TopicAdapter topicAdapter) {
        this.topicAdapter = topicAdapter;
    }

    public TradeMsgListener getTradeMsgListener() {
        return tradeMsgListener;
    }

    public void setTradeMsgListener(TradeMsgListener tradeMsgListener) {
        this.tradeMsgListener = tradeMsgListener;
    }

    public Integer getConsumeThreadNums() {
        return consumeThreadNums;
    }

    public void setConsumeThreadNums(Integer consumeThreadNums) {
        this.consumeThreadNums = consumeThreadNums;
    }

    public CrushOrderSkuListener getCrushOrderSkuListener() {
        return crushOrderSkuListener;
    }

    public void setCrushOrderSkuListener(CrushOrderSkuListener crushOrderSkuListener) {
        this.crushOrderSkuListener = crushOrderSkuListener;
    }

	public ItemSalesCountMsgListener getItemSalesCountMsgListener() {
		return itemSalesCountMsgListener;
	}

	public void setItemSalesCountMsgListener(ItemSalesCountMsgListener itemSalesCountMsgListener) {
		this.itemSalesCountMsgListener = itemSalesCountMsgListener;
	}
}
