package com.lzr.utl;

/**
 * 十六进制字符串与字节数组之间的转换
 * Created by LZR on 2017/12/6.
 */

public class Hex2Bytes {
	private static final String digital = "0123456789ABCDEF";

	public static String byt2Hex(byte[] data) {
		StringBuilder r = new StringBuilder(data.length * 2);
		for (byte b : data) {
			r.append(digital.charAt((b >> 4) & 0xF));
			r.append(digital.charAt(b & 0xF));
		}
		return r.toString();
	}

	public static byte[] hex2Byt(String dat) {
		int n = dat.length();
		byte[] bs = new byte[n/2];
		for (int i=0; i<n; i+=2) {
			bs[i/2] = (byte) (digital.indexOf(dat.charAt(i)) * 16 + digital.indexOf(dat.charAt(i+1)));
		}
		return bs;
	}
}
