/* 
 * InsteadPayListener.java  
 * 
 * version TODO
 *
 * 2016年10月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.cnaps.consumer.listener;

import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.google.common.base.Charsets;
import com.zcbspay.platform.cnaps.application.PaymentByAgency;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.application.bean.enums.CNAPSPaymentTagsEnum;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月19日 下午2:31:41
 * @since 
 */
@Service("cnapsPaymentListener")
public class CNAPSPaymentListener implements MessageListenerConcurrently{
	private static final Logger log = LoggerFactory.getLogger(CNAPSPaymentListener.class);
	private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("consumer_cnaps");
	private static final String KEY = "CNAPSPAYMENT:";
	
	@Autowired
	private PaymentByAgency paymentByAgency;
	/**
	 *
	 * @param msgs
	 * @param context
	 * @return
	 */
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		for (MessageExt msg : msgs) {
			if (msg.getTopic().equals(RESOURCE.getString("cmbc.insteadpay.subscribe"))) {
				CNAPSPaymentTagsEnum cnapsPaymentTagsEnum = CNAPSPaymentTagsEnum.fromValue(msg.getTags());
				if (cnapsPaymentTagsEnum==CNAPSPaymentTagsEnum.BATCHPAYMENT) {
					
						String json = new String(msg.getBody(), Charsets.UTF_8);
						log.info("接收到的MSG:" + json);
						log.info("接收到的MSGID:" + msg.getMsgId());
						TradeBean tradeBean = JSON.parseObject(json,TradeBean.class);
						if (tradeBean == null) {
							log.warn("MSGID:{}JSON转换后为NULL,无法生成订单数据,原始消息数据为{}",msg.getMsgId(), json);
							break;
						}
						com.zcbspay.platform.cnaps.common.bean.ResultBean resultBean = null;
						try {
							resultBean = paymentByAgency.batchPaymentByAgency(tradeBean.getBatchNo());
						} catch (Throwable e) {
							// TODO: handle exception
							resultBean = new ResultBean("T000", e.getMessage());
						}
						//insteadPayCacheResultService.saveInsteadPayResult(KEY + msg.getMsgId(), JSON.toJSONString(resultBean));
				}else if(cnapsPaymentTagsEnum==CNAPSPaymentTagsEnum.REALTIMEPAYMENT){
					String json = new String(msg.getBody(), Charsets.UTF_8);
					log.info("接收到的MSG:" + json);
					log.info("接收到的MSGID:" + msg.getMsgId());
					TradeBean tradeBean = JSON.parseObject(json,TradeBean.class);
					if (tradeBean == null) {
						log.warn("MSGID:{}JSON转换后为NULL,无法生成订单数据,原始消息数据为{}",msg.getMsgId(), json);
						break;
					}
					com.zcbspay.platform.cnaps.common.bean.ResultBean resultBean = null;
					try {
						resultBean = paymentByAgency.realTimePaymentByAgency(tradeBean);
					}catch (Throwable e) {
						// TODO: handle exception
					}
				}

			}
			log.info(Thread.currentThread().getName()+ " Receive New Messages: " + msgs);
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	
}
