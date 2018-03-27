package com.invengo.train.rfid;

/**
 * Created by LZR on 2017/5/22.
 */

public class Crc {
	private static int[] crc_tab_half = {0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7, 0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef};

	public static int check(byte[] ptr, int startIdx, int len) {
		int mark = 0x0000FFFF;
		int crc = mark;
		int da;
		int t;

		int i = startIdx;
		while (len != 0) {
//Log.i("\nCRC_i : ", Integer.toString(i));
//Log.i("CRC_crc : ", Integer.toHexString(crc));
//Log.i("CRC_ptr : ", Integer.toHexString(ptr[i]));

			// 暂存 crc 高四位
			da = crc;
			da >>= 12;
//Log.i("CRC_da_1 : ", Integer.toHexString(da));

			// crc 左移四位，相当于取 crc 的低12位
			crc <<= 4;
			crc &= mark;
//Log.i("CRC_crc_1 : ", Integer.toHexString(crc));

			// 取本字段前半字节
			t = ptr[i];
			t >>= 4;
			t &= 0x0F;
//Log.i("CRC_t_2 : ", Integer.toHexString(t));

			// 本字段前半字节与crc高4位相加
			da ^= t;
//Log.i("CRC_da_2 : ", Integer.toHexString(da));

			// 查表得到的crc和原crc相加
			crc ^= crc_tab_half[da];
//Log.i("CRC_crc_2 : ", Integer.toHexString(crc));

			// 暂存 crc 高四位
			da = crc;
			da >>= 12;
//Log.i("CRC_da_3 : ", Integer.toHexString(da));

			// crc 左移四位，相当于取 crc 的低12位
			crc <<= 4;
			crc &= mark;
//Log.i("CRC_crc_3 : ", Integer.toHexString(crc));

			// 取本字段后半字节
			t = ptr[i];
			t &= 0x0F;
//Log.i("CRC_t_4 : ", Integer.toHexString(t));

			// 本字段后半字节与crc高4位相加
			da ^= t;
//Log.i("CRC_da_4 : ", Integer.toHexString(da));

			// 查表得到的crc和原crc相加
			crc ^= crc_tab_half[da];
//Log.i("CRC_crc_4 : ", Integer.toHexString(crc));

			i ++;
			len --;
		}

		crc ^= mark;
		return crc;
	}
}
