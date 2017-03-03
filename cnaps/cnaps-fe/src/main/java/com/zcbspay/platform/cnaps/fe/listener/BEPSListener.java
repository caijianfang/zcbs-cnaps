package com.zcbspay.platform.cnaps.fe.listener;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("messageListenerBEPS")
public class BEPSListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(BEPSListener.class);

    public void onMessage(Message message) {
        try {
            logger.info("CNAPSListener onMessage ... Begin ");
            String tx = "";
            if (message instanceof TextMessage) {
                tx = ((TextMessage) message).getText();
            }
            else if (message instanceof BytesMessage) {
                BytesMessage byteMessage = (BytesMessage) message;
                byte[] buf = new byte[(int) byteMessage.getBodyLength()];
                byteMessage.readBytes(buf);
                tx = new String(buf, "UTF-8");
            }
            else {
                throw new Exception("Message type is unknown Receice Message : " + message);
            }
            // TODO 解析报文，获取报文类型，调用对应处理接口
            
            logger.info("Listener Receice Message:" + tx);
            logger.info("CNAPSListener onMessage  ... end");
        }
        catch (Exception e) {
            logger.error("CNAPSListener onMessage exception!!!", e);
        }
    }
}
