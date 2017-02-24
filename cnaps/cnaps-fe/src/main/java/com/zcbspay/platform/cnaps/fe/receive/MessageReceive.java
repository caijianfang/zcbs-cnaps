package com.zcbspay.platform.cnaps.fe.receive;

/**
 * 报文接收
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午3:46:54
 * @since
 */
public interface MessageReceive {
	/**
	 * 接收报文接口，需配合长轮询使用，这里是对报文内容的具体处理
	 * @param message
	 */
	public void receiveMsg(String message);
}
