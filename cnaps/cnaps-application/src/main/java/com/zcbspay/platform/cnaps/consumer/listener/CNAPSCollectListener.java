/* 
 * WithholdingListener.java  
 * 
 * version TODO
 *
 * 2016年10月14日 
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
import com.zcbspay.platform.cnaps.application.CollectionCharges;
import com.zcbspay.platform.cnaps.application.bean.enums.CNAPSCollectTagsEnum;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;

/**
 * 民生代扣监听器
 *
 * @author guojia
 * @version
 * @date 2016年10月14日 上午10:52:26
 * @since 
 */
@Service("cnapsCollectListener")
public class CNAPSCollectListener implements MessageListenerConcurrently{

	private static final Logger log = LoggerFactory.getLogger(CNAPSCollectListener.class);
	private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("consumer_cnaps");
	private static final String KEY = "CNAPSCOLLECT:";
	
	@Autowired
	private CollectionCharges collectionCharges;
	/**
	 *
	 * @param msgs
	 * @param context
	 * @return
	 */
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		String json = null;
		for (MessageExt msg : msgs) {
			if (msg.getTopic().equals(RESOURCE.getString("cmbc.withholding.subscribe"))) {
				CNAPSCollectTagsEnum cnapsCollectTagsEnum = CNAPSCollectTagsEnum.fromValue(msg.getTags());
				if(cnapsCollectTagsEnum == CNAPSCollectTagsEnum.BATCHCOLLECT){//代扣
					json = new String(msg.getBody(), Charsets.UTF_8);
					log.info("接收到的MSG:" + json);
					log.info("接收到的MSGID:" + msg.getMsgId());
					TradeBean tradeBean = JSON.parseObject(json,TradeBean.class);
					if (tradeBean == null) {
						log.warn("MSGID:{}JSON转换后为NULL,无法生成订单数据,原始消息数据为{}",msg.getMsgId(), json);
						break;
					}
					ResultBean resultBean = null;
					try {
						resultBean = collectionCharges.batchCollectionCharges(tradeBean.getBatchNo());
						//withholdingCacheResultService.saveWithholdingResult(KEY + msg.getMsgId(), JSON.toJSONString(resultBean));
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						resultBean = new ResultBean("T000", e.getLocalizedMessage());
					}
				}else if(cnapsCollectTagsEnum == CNAPSCollectTagsEnum.REALTIMECOLLECT){//实名认证
					json = new String(msg.getBody(), Charsets.UTF_8);
					log.info("接收到的MSG:" + json);
					log.info("接收到的MSGID:" + msg.getMsgId());
					TradeBean tradeBean = JSON.parseObject(json,TradeBean.class);
					if (tradeBean == null) {
						log.warn("MSGID:{}JSON转换后为NULL,无法生成订单数据,原始消息数据为{}",msg.getMsgId(), json);
						break;
					}
					ResultBean resultBean = null;
					try {
						resultBean = collectionCharges.realTimeCollectionCharges(tradeBean);
						//withholdingCacheResultService.saveWithholdingResult(KEY + msg.getMsgId(), JSON.toJSONString(resultBean));
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						resultBean = new ResultBean("T000", e.getLocalizedMessage());
					}
				}
				

			}
			log.info(Thread.currentThread().getName()+ " Receive New Messages: " + msgs);
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
