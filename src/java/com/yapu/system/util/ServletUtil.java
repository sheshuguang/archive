package com.yapu.system.util;

import java.math.BigDecimal;


public class ServletUtil {
	public ServletUtil() {
	}

	private static final int K_BYTE_SIZE = 1024;
	private static final long M_BYTE_SIZE = K_BYTE_SIZE * K_BYTE_SIZE;

	/**
	 * 得到文件尺寸（MB或B）
	 * @param fileSize
	 *            long
	 * @return String
	 */
	public static String getFileSize(Long fileSize) {
		long fileSizeValue = fileSize == null ? 0 : fileSize.longValue();
		String temp = null;
		if (fileSizeValue < K_BYTE_SIZE) {
			temp = fileSizeValue + " B";
		} else if (fileSizeValue < M_BYTE_SIZE) {
			temp = new BigDecimal(fileSizeValue).divide(new BigDecimal(K_BYTE_SIZE), 1, BigDecimal.ROUND_DOWN) + " KB";
		} else {
			temp = new BigDecimal(fileSizeValue).divide(new BigDecimal(M_BYTE_SIZE), 1, BigDecimal.ROUND_DOWN) + " MB";
		}
		return temp;
	}

	/**
	 * 加密
	 * 
	 * @param oldPassword
	 *            String
	 * @return String
	 */
	public static String enCrypt(String oldPassword) {
		String changePassword = "";
		int temp;
		int length = oldPassword.length(); // 取得参数
		char[] arr;
		char c;
		for (temp = 0; temp < length; temp++) {
			c = oldPassword.charAt(temp);
			int i = (int) c;
			changePassword += (char) (i + temp + 1);
		}
		arr = changePassword.toCharArray();
		char c1;
		for (temp = 0; temp < (int) (length / 2); temp++) {
			c1 = arr[temp];
			arr[temp] = arr[length - temp - 1];
			arr[length - temp - 1] = c1;
		}
		return new String(arr);
	}

	/**
	 * 解密
	 * 
	 * @param oldPassword
	 *            String
	 * @return String
	 */
	public static String deCrypt(String oldPassword) {
		String changePassword = "";
		int temp;
		int length = oldPassword.length(); // 取得参数
		char[] arr;
		char c;
		for (temp = 0; temp < length; temp++) {
			c = oldPassword.charAt(temp);
			int i = (int) c;
			changePassword += (char) (i + temp + 1);
		}
		arr = changePassword.toCharArray();
		char c1;
		for (temp = 0; temp < (int) (length / 2); temp++) {
			c1 = arr[temp];
			arr[temp] = arr[length - temp - 1];
			arr[length - temp - 1] = c1;
		}
		return new String(arr);
	}

	/**
	 * 
	 * @param input
	 *            String
	 * @return String
	 */
	public static String filter(String input) {
		if (!hasSpecialChar(input)) {
			return input;
		}
		int length = input.length();
		StringBuffer filtered = new StringBuffer(length);
		char c;
		for (int i = 0; i < length; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}
		}
		return filtered.toString();
	}

	private static boolean hasSpecialChar(String input) {
		boolean flag = false;
		if (input != null && input.length() > 0) {
			char c;
			int length = input.length();
			for (int i = 0; i < length; i++) {
				c = input.charAt(i);
				switch (c) {
				case '<':
					flag = true;
					break;
				case '>':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}