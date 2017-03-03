package com.zcbspay.platform.cnaps.utils;

import java.util.List;

/**
 * 类型转换
 * 
 * @author 张连海
 *
 */
public class TypeCast {
	/**
	 * String类型的List,通过分隔符拼接成字符串
	 * @param 	stringList
	 * 			String类型的List
	 * @param 	separator
	 * 			分隔符
	 * @return
	 */
	public static String listToString(List<String> stringList, String separator) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (String string : stringList) {
			result.append(string).append(separator);
		}
		return result.toString();
	}
}