package com.invengo.train.rfid.tag;

import com.lzr.utl.Hex2Bytes;

import java.util.HashMap;
import java.util.Map;

/**
 * 可解析位标信息的标签解析
 * Created by LZR on 2017/12/6.
 */

public class ParseTag {
	public static String parse (byte[] src) {
//		src = Hex2Bytes.hex2Byt("2A69A8162C092C01B2230123456789AB");	// 测试数据

		if ((src[0] != 0x2A) || (src[1] != 0x69)) {
			return "<br />不符合2A69的数据 ： " + Hex2Bytes.byt2Hex(src) + "<br />";
		}

		StringBuilder r = new StringBuilder();
		byte[] t = new byte[1];
		int i = 2;
		Map<String, String> m = new HashMap<>();
		m.put("<br />原数据", Hex2Bytes.byt2Hex(src) + "<br />");

		if (i < src.length) {
			t[0] = (byte)(src[i] & 0x0f0);
			t[0] >>= 4;
			t[0] &= 0x0f;
//			m.put("厂家", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);
			m.put("厂家", "" + t[0]);

			t[0] = (byte)(src[i++] & 0x0f);
//			m.put("报文类型", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);
			m.put("报文类型", "" + t[0]);
		}
		if (i < src.length) {
			t = new byte[2];
			t[1] = (byte)(src[i++] & 0x0ff);
		}
		if (i < src.length) {
			t[0] = (byte)(src[i] & 0x03);
//			m.put("位标序号", Hex2Bytes.byt2Hex(t) + "H , " + ((((int)t[0]) << 8) | t[1]));
			m.put("位标序号", "" + ((((int)t[0]) << 8) | t[1]));

			t = new byte[1];
			t[0] = (byte)(src[i] & 0x04);
			t[0] >>= 2;
//			m.put("正向运行是否有效", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);
			m.put("正向运行是否有效", "" + t[0]);

			t[0] = (byte)(src[i] & 0x08);
			t[0] >>= 3;
//			m.put("反向运行是否有效", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);
			m.put("反向运行是否有效", "" + t[0]);

			t[0] = (byte)(src[i] & 0x030);
			t[0] >>= 4;
			m.put("位标序号预留", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);

			t[0] = (byte)(src[i++] & 0x0c0);
			t[0] >>= 6;
			t[0] &= 0x03;
			m.put("车站序号预留", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);
		}
		if (i < src.length) {
			t = new byte[2];
			t[1] = (byte)(src[i++] & 0x0ff);
		}
		if (i < src.length) {
			t[0] = (byte)(src[i++] & 0x0ff);
//			m.put("车站代号", Hex2Bytes.byt2Hex(t) + "H , " + ((((int)t[0]) << 8) | t[1]));
			m.put("车站代号", "" + ((((int)t[0]) << 8) | t[1]));
		}
		if (i < src.length) {
			t = new byte[1];
			t[0] = (byte)(src[i] & 0x0f);
			m.put("位标格式版本号", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);

			t[0] = (byte)(src[i++] & 0x0f0);
			t[0] >>= 4;
			t[0] &= 0x0f;
			m.put("位标编程月", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);
		}
		if (i < src.length) {
			t[0] = (byte)(src[i] & 0x01);
			m.put("上半月/下半月", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);

			t[0] = (byte)(src[i++] & 0x0fe);
			t[0] >>= 1;
			t[0] &= 0x07f;
			m.put("位标编程年", Hex2Bytes.byt2Hex(t) + "H , " + t[0]);
		}
		if (i < src.length) {
			t = new byte[6];
			for (int j = 0; j < 6; j++) {
				if (i < src.length) {
					t[j] = (byte)(src[i++] & 0x0ff);
				}
			}
			m.put("功能信息包", Hex2Bytes.byt2Hex(t) + "H");
		}

//		for (Map.Entry<String, String> e : m.entrySet()) {
//			r.append("<br />");
//			r.append(e.getKey());
//			r.append(" : ");
//			r.append(e.getValue());
//		}

		r.append("<br />位标序号：");
		r.append(m.get("位标序号"));
		r.append("<br /><br />");
		r.append("<br />厂家代码：");
		r.append(m.get("厂家"));
		r.append("<br />报文类型：");
		r.append(m.get("报文类型"));
		r.append("<br />运行方向：");
		if (m.get("正向运行是否有效").equals("0")) {
			r.append("反向");
		} else {
			r.append("正向");
		}
		r.append("<br />车站代号：");
		r.append(m.get("车站代号"));
		r.append("<br />版本号：");
		r.append(m.get("位标格式版本号"));
		r.append("<br />编程时间：");
		r.append(m.get("位标编程年"));
		r.append("年");
		r.append(m.get("位标编程月"));
		r.append("月");
		r.append("<br /><br />原数据");
		r.append(m.get("原数据"));

		return r.toString();
	}
}
