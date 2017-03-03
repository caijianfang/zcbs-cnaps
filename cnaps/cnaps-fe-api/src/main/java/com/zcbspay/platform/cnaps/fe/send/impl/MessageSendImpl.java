package com.zcbspay.platform.cnaps.fe.send.impl;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.zcbspay.platform.cnaps.fe.enums.ErrorCodeFE;
import com.zcbspay.platform.cnaps.fe.redis.RedisFactory;
import com.zcbspay.platform.cnaps.fe.send.MessageSend;
import com.zcbspay.platform.cnaps.fe.send.bean.MessageCodeEnum;
import com.zcbspay.platform.cnaps.fe.send.bean.ResultBean;
import com.zcbspay.platform.cnaps.fe.send.bean.SendResult;

@Service("messageSendCNAPS")
public class MessageSendImpl implements MessageSend {

    private static final Logger logger = LoggerFactory.getLogger(MessageSendImpl.class);
    private static final String KEY = "CNAPSFE:";
    private static ResourceBundle RESOURCE = ResourceBundle.getBundle("ibmmq_params");

    @Override
    public SendResult send(String message, MessageCodeEnum messageCodeEnum) {
        SendResult result = null;
        MQQueueManager qMgr;
        try {
            qMgr = getConnMQmanager();
        }
        catch (MQException e) {
            logger.error(e.getMessage(), e);
            result = new SendResult(e.getErrorCode() + "|" + e.getMessage());
            return result;
        }
        boolean isSentSuc = sendMsg(qMgr, message, messageCodeEnum);
        if (isSentSuc) {
            result = new SendResult();
        }
        else {
            result = new SendResult(ErrorCodeFE.SEND_MSG_FAIL.getValue());
        }
        return result;
    }

    /**
     * 获取队列连接管理器
     * 
     * @throws MQException
     */
    private MQQueueManager getConnMQmanager() throws MQException {
        MQQueueManager qMgr = null;
        MQEnvironment.hostname = RESOURCE.getString("hostname");// MQ服务器IP
        MQEnvironment.channel = RESOURCE.getString("channel"); // 队列管理器对应的服务器连接通道
        MQEnvironment.CCSID = Integer.parseInt(RESOURCE.getString("ccid")); // 字符编码
        MQEnvironment.port = Integer.parseInt(RESOURCE.getString("port")); // 队列管理器的端口号
        MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_BINDINGS);
        qMgr = new MQQueueManager(RESOURCE.getString("queueManager"));// 队列管理器名称
        return qMgr;
    }

    /**
     * 向队列中发送消息
     * 
     * @param msgStr
     */
    public boolean sendMsg(MQQueueManager qMgr, String msgStr, MessageCodeEnum messageCodeEnum) {
        boolean isSentSuc = true;
        int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;
        MQQueue queue = null;
        try {
            // 建立通道的连接
            queue = qMgr.accessQueue(messageCodeEnum.getCode(), openOptions, null, null, null);
            // 要写入队列的消息
            MQMessage msg = new MQMessage();
            msg.format = MQC.MQFMT_STRING;
            msg.characterSet = Integer.parseInt(RESOURCE.getString("ccid"));
            // 将消息写入消息对象中
            msg.writeObject(msgStr);
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            // 设置消息用不过期
            msg.expiry = -1;
            // 将消息放入队列
            queue.put(msg, pmo);
        }
        catch (MQException e) {
            logger.error(e.getMessage(), e);
            isSentSuc = false;
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            isSentSuc = false;
        }
        finally {
            if (queue != null) {
                try {
                    queue.close();
                }
                catch (MQException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (qMgr != null) {
                try {
                    qMgr.close();
                }
                catch (MQException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return isSentSuc;

    }

    @Override
    public ResultBean queryReturnResult(String msgId) {
        logger.info("msgID:{}", msgId);
        for (int i = 0; i < 1; i++) {
            String xmlStr = getXMLByCycle(msgId);
            logger.info("从redis中取得key【{}】值为{}", KEY + msgId, xmlStr);
            if (StringUtils.isEmpty(xmlStr)) {
                ResultBean resultBean = parseObject(xmlStr);
                logger.info("msgID:{},结果数据:{}", msgId, resultBean.toString());
                return resultBean;
            }
            else {
                try {
                    TimeUnit.MILLISECONDS.sleep(900);
                }
                catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        logger.info("end time {}", System.currentTimeMillis());
        return null;
    }

    /**
     * 将XML类型字符串转换为通用返回结果ResultBean
     * 
     * @param xmlStr
     * @return
     */
    private ResultBean parseObject(String xmlStr) {
        // TODO
        ResultBean rb = new ResultBean();
        return rb;
    }

    private String getXMLByCycle(String msgId) {
        Jedis jedis = RedisFactory.getInstance().getRedis();
        // String tn = jedis.get(KEY+msgId);
        List<String> brpop = jedis.brpop(40, KEY + msgId);
        // TODO 一个请求返回两条 兼容处理
        if (brpop.size() > 0) {
            String tn = brpop.get(1);
            if (!StringUtils.isEmpty(tn)) {
                return tn;
            }
        }
        jedis.close();
        return null;
    }

}
