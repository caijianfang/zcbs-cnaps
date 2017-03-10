package com.zcbspay.platform.cnaps.transfer.message.assemble.detail;

import com.zcbspay.platform.cnaps.bean.MessageBean;
//import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;

public interface AssembleBase {
	/**
	 * 生成报文头bean:
		此方法主要是根据报文bean，为报文头bean进行赋值，只有通信级参考号需要从数据库中获取，其他数据均可以从报文体重获得,
		MessageBean为报文体的接口
	 * @param bean
	 * @return
	 */
	public MessageHeadBean createMessageHead(MessageBean bean);
	
	/**
	 * 根据报文bean以及报文的类型，对报文中的关键要素进行签名串的拼接
	 * @param bean
	 * @return
	 */
	public String signatureElement(MessageBean bean);
}
