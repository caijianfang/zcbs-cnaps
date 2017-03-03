package com.zcbspay.platform.cnaps.transfer.message.assemble;

import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;

/**
 * 报文组装接口
	1.生成报文头bean
	2.生成数字签名域
	3.组装报文
 * @author guojia
 * @version
 * @date 2017年2月23日 下午5:54:29
 * @since
 */
public interface MessageAssemble {

	/**
	 * 生成报文头bean:
		此方法主要是根据报文bean，为报文头bean进行赋值，只有通信级参考号需要从数据库中获取，其他数据均可以从报文体重获得,
		MessageBean为报文体的接口
	 * @param bean
	 * @return
	 */
	public MessageHeadBean createMessageHead(MessageBean bean);
	/**
	 * 数字签名方法，根据报文bean以及报文的类型，对报文中的关键要素进行签名串的拼接，调用软加密服务的签名方法生成数字签名字符串
	 * @param bean
	 * @return
	 */
	public String signature(MessageBean bean);
	/**
	 * 组装报文方法，返回报文字符串
		报文格式：报文头+数字签名+报文体
	 * @param bean
	 * @return
	 */
	public String assemble(MessageBean bean);
}
