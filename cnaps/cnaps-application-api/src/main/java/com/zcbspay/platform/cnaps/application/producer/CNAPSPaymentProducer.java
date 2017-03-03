/* 
 * InsteadPayProducer.java  
 * 
 * version TODO
 *
 * 2016年10月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.cnaps.application.producer;

import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.google.common.base.Charsets;
import com.zcbspay.platform.cnaps.application.bean.ResultBean;
import com.zcbspay.platform.cnaps.application.bean.enums.CNAPSPaymentTagsEnum;
import com.zcbspay.platform.cnaps.application.interfaces.Producer;
import com.zcbspay.platform.cnaps.application.redis.RedisFactory;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月19日 下午1:49:33
 * @since 
 */
public class CNAPSPaymentProducer implements Producer {
	private final static Logger logger = LoggerFactory.getLogger(CNAPSPaymentProducer.class);
	private static final String KEY = "CNAPSPAYMENT:";
	private static final  ResourceBundle RESOURCE = ResourceBundle.getBundle("producer_cnaps");
	//RocketMQ消费者客户端
	private DefaultMQProducer producer;
	//主题
	private String topic;
	
	public CNAPSPaymentProducer(String namesrvAddr,CNAPSPaymentTagsEnum tags) throws MQClientException{
		logger.info("【初始化CNAPSPaymentProducer】");
		logger.info("【namesrvAddr】"+namesrvAddr);
		producer = new DefaultMQProducer(RESOURCE.getString("cnaps.payment.producer.group"));
		producer.setNamesrvAddr(namesrvAddr);
		Random random = new Random();
        producer.setInstanceName(RESOURCE.getString("cnaps.payment.instancename")+random.nextInt(9999));
        topic = RESOURCE.getString("cnaps.payment.subscribe");
        logger.info("【初始化CNAPSPaymentProducer结束】");
	}
	
	/**
	 *
	 * @param message
	 * @param tags
	 * @return
	 * @throws MQClientException
	 * @throws RemotingException
	 * @throws InterruptedException
	 * @throws MQBrokerException
	 */
	@Override
	public SendResult sendJsonMessage(String message,Object tags)
			throws MQClientException, RemotingException, InterruptedException,
			MQBrokerException {
		if(producer==null){
			throw new MQClientException(-1,"SimpleOrderProducer为空");
		}
		producer.start();
		CNAPSPaymentTagsEnum insteadPayTagsEnum =(CNAPSPaymentTagsEnum) tags;
		Message msg = new Message(topic, insteadPayTagsEnum.getCode(), message.getBytes(Charsets.UTF_8));
		SendResult sendResult = producer.send(msg);
		return sendResult;
	}

	/**
	 *
	 */
	@Override
	public void closeProducer() {
		// TODO Auto-generated method stub
		producer.shutdown();
		producer = null;
	}

	/**
	 *
	 * @param sendResult
	 * @return
	 */
	@Override
	public ResultBean queryReturnResult(SendResult sendResult) {
		// TODO Auto-generated method stub
		logger.info("【InsteadPayProducer receive Result message】{}",JSON.toJSONString(sendResult));
		logger.info("msgID:{}",sendResult.getMsgId());
		
		for (int i = 0;i<100;i++) {
			String json = getJsonByCycle(sendResult.getMsgId());
			logger.info("从redis中取得key【{}】值为{}",KEY+sendResult.getMsgId(),json);
			if(StringUtils.isNotEmpty(json)){
				ResultBean resultBean = JSON.parseObject(json, ResultBean.class);
				
				logger.info("msgID:{},结果数据:{}",sendResult.getMsgId(),JSON.toJSONString(resultBean));
				return resultBean;
			}else{
				try {
					TimeUnit.MILLISECONDS.sleep(900);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		logger.info("end time {}",System.currentTimeMillis());
		return null;
	}
	private String getJsonByCycle(String msgId){
		Jedis jedis = RedisFactory.getInstance().getRedis();
		String json = jedis.get(KEY+msgId);
		jedis.close();
		if(StringUtils.isNotEmpty(json)){
			return json;
		}
		return null;
	}
}
