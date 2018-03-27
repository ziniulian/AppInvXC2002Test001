package com.invengo.train.rfid.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * 旧版的标签解析
 * Created by LZR on 2017/12/6.
 */

public class ParseTagOld {
	// 标签的编码类型
	private enum TagEncodeType {
		UnKnow, FSK, FM0
	}

	// 标签类型
	private enum TagType {
		UnKnow, XCTF3J, XCTF2, XCTF2J, XCTF5
	}

	// 标签解析
	public static Map<String, String> parse (byte[] src) {
		if (src == null || src.length != 19) {
			return null;
		}

		TagEncodeType tet;
		TagType tt;
		int p;

		// 判断编码类型
		if (src[0] == 0x23) {	// FSK
			tet = TagEncodeType.FSK;
			p = src[1];
		} else if (src[0] == 0x2a) {	// FM0
			tet = TagEncodeType.FM0;
			p = src[2];
		} else {
			return null;
		}

		// 判断标签类型
		p &= 0x0fc;
		p >>= 2;
		if (tet == TagEncodeType.FSK && (p == 49 || p == 52)) {
			tt = TagType.XCTF2;
		} else if (p == 42) {
			if (tet == TagEncodeType.FM0) {
				tt = TagType.XCTF3J;
			} else {
				tt = TagType.XCTF2J;
			}
		} else if (p == 43 && tet == TagEncodeType.FM0) {
			tt = TagType.XCTF5;
		} else {
			return null;
		}

		// 解析标签
		Map<String, String> r = null;
		switch (tt) {
			case XCTF2:
				r = parseXCTF2(src);
				break;
		}
		return r;
	}

	private static Map<String, String> parseXCTF2 (byte[] src) {
		Map<String, String> r = new HashMap<>();
		byte[] t = new byte[20];
		int t1;
		int t2;
		int i = 1;
		int j = 0;

//		t1 = (src[i] & 0xfc);
//		t1 >>= 2;
//		t[j++] = (byte)(t1 + 0x20);
//
//		t1 = (src[i++] & 0x03);
//		t2 = (src[i] & 0xf0);
//		t1 = ((t1 << 4) | (t2 >> 4));
//		t[j++] = (byte)(t1 + 0x20);
//
//		t1 = (src[i++] & 0x0f);
//		t2 = (src[i] & 0xc0);
//		t1 = ((t1 << 2) | (t2 >> 6));
//		t[j++] = (byte)(t1 + 0x20);
//
//		t1 = (src[i++] & 0x3f);
//		t[j++] = (byte)(t1 + 0x20);

		// 属性码 6bit
		t1 = (src[i] & 0xfc);
		t1 >>= 2;
		t[j++] = (byte)(t1 + 0x20);
		r.put("property", new String(new byte[]{t[0]}));

		//车种 6bit
		t1 = (src[i++] & 0x03);
		t2 = (src[i] & 0xf0);
		t1 = ((t1 << 4) | (t2 >> 4));
		t[j++] = (byte)(t1 + 0x20);
		r.put("trainType", new String(new byte[]{t[1]}));

		// 车型 30bit
		t1 = (src[i++] & 0x0f);	// 1
		t2 = (src[i] & 0xc0);
		t1 = ((t1 << 2) | (t2 >> 6));
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x3f);	// 2
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i] & 0xfc);	// 3
		t1 >>= 2;
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x03);	// 4
		t2 = (src[i] & 0xf0);
		t1 = ((t1 << 4) | (t2 >> 4));
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x0f);	// 5
		t2 = (src[i] & 0xc0);
		t1 = ((t1 << 2) | (t2 >> 6));
		t[j++] = (byte)(t1 + 0x20);
		r.put("trainModel", new String(new byte[]{t[2], t[3], t[4], t[5], t[6]}));

		// 车号 42bit
		t1 = (src[i++] & 0x3f);	// 1
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i] & 0xfc);	// 2
		t1 >>= 2;
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x03);	// 3
		t2 = (src[i] & 0xf0);
		t1 = ((t1 << 4) | (t2 >> 4));
		t[j++] = (byte)(t1 + 0x20);

		i++;	// 没按套路的最奇怪

//		t1 = (src[i++] & 0x0f);
//		t2 = (src[i] & 0xc0);
//		t1 = ((t1 << 2) | (t2 >> 6));
//		t[j++] = (byte)(t1 + 0x20);
//
//		t1 = (src[i++] & 0x3f);
//		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i] & 0xfc);	// 4
		t1 >>= 2;
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x03);	// 5
		t2 = (src[i] & 0xf0);
		t1 = ((t1 << 4) | (t2 >> 4));
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x0f);	// 6
		t2 = (src[i] & 0xc0);
		t1 = ((t1 << 2) | (t2 >> 6));
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x3f);	// 7
		t[j++] = (byte)(t1 + 0x20);
		r.put("trainNumber", new String(new byte[]{t[7], t[8], t[9], t[10], t[11], t[12], t[13]}));

		// 换长 12bit
		t1 = (src[i] & 0xfc);	// 高位
		t1 >>= 2;
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x03);	// 低位
		t2 = (src[i] & 0xf0);
		t1 = ((t1 << 4) | (t2 >> 4));
		t[j++] = (byte)(t1 + 0x20);

		// 制造厂
		t1 = (src[i++] & 0x0f);
		t2 = (src[i] & 0xc0);
		t1 = ((t1 << 2) | (t2 >> 6));
		t[j++] = (byte)(t1 + 0x20);
		r.put("trainFactory", new String(new byte[]{t[16]}));

		// 制造年月
		t1 = (src[i++] & 0x3f);
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i] & 0xfc);
		t1 >>= 2;
		t[j++] = (byte)(t1 + 0x20);

		t1 = (src[i++] & 0x03);
		t2 = (src[i] & 0xf0);
		t1 = ((t1 << 4) | (t2 >> 4));
		t[j++] = (byte)(t1 + 0x20);
		r.put("trainDate", new String(new byte[]{t[17], t[18], t[19]}));

		r.put("tagCode", new String(t));
		return r;
	}

}
