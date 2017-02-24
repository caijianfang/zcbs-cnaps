package com.zcbspay.platform.cnaps.transfer.message.unpack;

import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;

public interface MessageUnpack {

	/**
	 * 解包报文头部
		按照报文规范截取报文前132字节，调用MessageHeadBean的构造方法，实例化MessageHeadBean
	 * @param message
	 * @return
	 */
	public MessageHeadBean unpackMessageHead(String message);
	/**
	 * 验签
		截取出数字签名字符串，根据发送机构的机构号从数据库中的T_CNAPS_CERTIFICATE中取得公钥证书字符串，从证书的DN域中取得ou和CN值，判断秘钥是否属于当前报文的发送机构，调用验签方法对报文体进行验签
	 * @param message
	 * @return
	 */
	public boolean verification(String message);
	/**
	 * 解包
		解包头+验签最后返回报文体bean
	 * @param message
	 * @return
	 */
	public MessageBean unpack(String message);
}
