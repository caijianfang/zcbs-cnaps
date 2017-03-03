package com.zcbspay.platform.cnaps.fe.receive.impl;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.zcbspay.platform.cnaps.fe.receive.MessageReceive;
import com.zcbspay.platform.cnaps.fe.receive.bean.MessageCodeEnum;

@Service("messageReceiveCCMS")
public class MessageReceiveCCMSImpl implements MessageReceive {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiveCCMSImpl.class);
    private static ResourceBundle RESOURCE = ResourceBundle.getBundle("ibmmq_params");
    private static MQQueueConnectionFactory mqcf;
    private QueueConnection conn;
    private QueueSession session;
    private Queue queue;
    private QueueReceiver receiver;
    @Resource
    private MessageListener messageListenerCCMS;

    @Override
    @PostConstruct
    public void receiveMsg(String message) {
        logger.info("messageReceiveCNAPS receiveMsg begin~");

        try {
            init(MessageCodeEnum.CCMS.getCode());
            conn.start();
            receiver.setMessageListener(messageListenerCCMS);
        }
        catch (JMSException e) {
            logger.error("messageReceiveCNAPS receiveMsg exception", e);
        }
        finally {
            closeMQObjects();
        }
    }

    private void init(String qName) throws JMSException {
        mqcf = new MQQueueConnectionFactory();
        mqcf.setTransportType(0);
        mqcf.setHostName(RESOURCE.getString("hostname"));
        mqcf.setPort(Integer.parseInt(RESOURCE.getString("port")));
        mqcf.setChannel(RESOURCE.getString("channel"));
        mqcf.setQueueManager(RESOURCE.getString("queueManager"));
        mqcf.setCCSID(Integer.parseInt(RESOURCE.getString("ccid")));
        conn = mqcf.createQueueConnection();
        session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(qName);
        receiver = session.createReceiver(queue);
    }

    @PreDestroy
    public void closeMQObjects() {
        try {
            if (conn != null) {
                conn.stop();
            }
            if (conn != null) {
                conn.close();
            }
            logger.info("Close MQ objects succeed.");
        }
        catch (JMSException e) {
            logger.error("Close MQ objects failed: ", e);
        }
    }

}
