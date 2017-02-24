package com.zcbspay.platform.cnaps.fe.send;

import com.zcbspay.platform.cnaps.fe.send.bean.MessageCodeEnum;
import com.zcbspay.platform.cnaps.fe.send.bean.SendResult;


/**
 * 报文发送接口
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午3:42:37
 * @since
 */
public interface MessageSend {

	/**
	 * 报文发送方法 ,依据MessageCodeEnum选择发送的队列
	 * @param message 业务消息报文
	 * @param messageCodeEnum 报文类型
	 * @return SendResult
	 */
	public SendResult send(String message, MessageCodeEnum messageCodeEnum);
}
