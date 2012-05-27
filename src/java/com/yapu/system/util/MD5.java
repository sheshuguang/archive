package com.yapu.system.util;

import java.security.MessageDigest;

/**
 * @author wangf
 * 
 */
public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * @param origin
	 *            需要编码的字符串
	 * @return String
	 */
	public static String encode(String origin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return byteArrayToHexString(md.digest(origin.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return origin;
	}

	public static void main(String[] args) {
		System.out.println(encode("1"));
	}

}
