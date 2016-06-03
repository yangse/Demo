package com.jandar.frame.utils;

import java.util.UUID;
/**
 * 生成UUID通用唯一识别码
 * @author ZXP
 *
 */

public class UUIDGenerator {
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		return str.replaceAll("-", "");
	}
	
	// 获得指定数量的UUID
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
}
