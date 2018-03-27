package com.invengo.train.rfid.xc2002;

import android.content.Context;

import com.atid.lib.system.ModuleControl;
import com.invengo.train.rfid.Base;
import com.invengo.train.rfid.Crc;
import com.invengo.train.rfid.EmCb;
import com.invengo.train.rfid.tag.BaseTag;
import com.invengo.train.rfid.tag.ParseTag;
import com.invengo.train.rfid.tag.TagUn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android_serialport_api.SerialPort;

/**
 * 铁路标签读出器
 * Created by ziniulian on 2017/10/16.
 */

public class Rd extends Base {
	private static final int br = 9600;	// 波特率 （原接口使用的波特率为 ：115200）
	private static final String drv = "/dev/ttyS4";	// 串口驱动名
	private static final String xmlPath = "DataDic.xml";	// XML 文件路径

	private SerialPort sp = null;	// 串口对象
	private ReadThread rt = null;	// 读取线程
	private boolean lock = false;	// 指令锁

	// 开启电源
	private void powerOn () {
		ModuleControl.powerRfidEx(true, 2, 4);
	}

	// 关闭电源
	private void powerOff () {
		ModuleControl.powerRfidEx(false, 2, 4);
	}

	// 读数据线程
	private class ReadThread extends Thread {
		private static final String TAG = "-------- 串口读数据";
		private InputStream is = null;	// 串口的输入流

		ReadThread (InputStream input) {
			this.is = input;
		}

		@Override
		public void run() {
			super.run();
			if (this.is != null) {
				int n = 0;
				int j = 0;
				byte[] r = null;
				while(!isInterrupted()) {
					int size;
					try {
						byte[] buf = new byte[32];
//Log.i(TAG, "挂机 ...");
						size = this.is.read(buf);
						if (size > 0) {
//							Log.i(TAG, "------------ s : " + size);
//							for (int i = 0; i < size; i++) {
//								Log.i(TAG, Integer.toHexString(buf[i]));
//							}
//							Log.i(TAG, "------------ e");

							int i = 0;
							if (size > 4 && buf[0] == 0xFFFFFFaa && buf[1] == 0x55 && buf[3] == 0x32) {
								if (buf[2] == 7) {
									// 没有搜索到标签
									cb(EmCb.ReadNull);

									// 位标标签解析测试：
//									n = 1;
//									r = new byte[n];
//									i = 2;
//									j = 0;

									// 测试用
//									onReadTag(BaseTag.parse("TC50   400000211A12C"));
//									onReadTag(BaseTag.parse("!C64K  Q06505812A94C"));
//									onReadTag(BaseTag.parse("KYW25T 677083E073 066"));
//									onReadTag(BaseTag.parse("J10489001401AH  TK88188 "));
//									onReadTag(BaseTag.parse("D3010000002101   G12345B"));
								} else {
									n = buf[2] - 6;
									r = new byte[n];
									i = 4;
									j = 0;
								}
							} else if (lock && n == 0) {
								onReadTag(BaseTag.parse(new byte[1]));
							}
							if (j < n) {
								for (; i < size; i++) {
									r[j] = buf[i];
									j++;
									if (j == n) {
										j = 0;
										n = 0;

										onReadTag(parse(r));

										break;
									}
								}
							}
						}
					} catch (IOException e) {
						cb(EmCb.ErrRead, e.getMessage());
					}
					lock = false;
				}
//Log.i(TAG, "Over");
			}
		}
	}

	// 解析标签
	protected BaseTag parse(byte[] bs) {
		return BaseTag.parse(bs);
	}

	// 发送数据
	private void send (byte command) {
//		// 指令字说明：
//		QueryStatus = 0x30,		// 查询状态
//		QueryVersion = 0x31,	// 查询版本 （在目前测试模块中，该指令无效）
//		QueryTag = 0x32			// 查询标签

		// 数据包长度
		int len = 6;

		byte[] d = new byte[len];
		d[0] = 0xFFFFFFaa;
		d[1] = 0x55;	// 0xaa+0x55为帧头
		d[2] = (byte)len;	// 长度
		d[3] = command;	//指令字

		int crc = Crc.check(d, 0, len - 2);	// 总长度, 包括帧头一起校验
//Log.i(TAG, Integer.toHexString(crc));
		d[4] = (byte)(crc >> 8);
		d[5] = (byte)(crc & 0x0FF);	// 最后两位为 CRC 校验码

		try {
			this.sp.getOutputStream().write(d);
		} catch (IOException e) {
			cb(EmCb.ErrRead, e.getMessage());
			lock = false;
			return;
		}

		cb(EmCb.Reading);
	}

	@Override
	public void init(Context ct) throws Exception {
		BaseTag.load(ct.getAssets().open(xmlPath));
	}

	@Override
	public void read() {
		if (!lock) {
			lock = true;
			send((byte)0x32);
//			send((byte)0x30);
		}
	}

	@Override
	public void open() {
		// 开启电源
		powerOn();

		// 开启串口
		if (this.sp == null) {
			try {
				this.sp = new SerialPort(new File(drv), br, 0);
			} catch (Exception e) {
				cb(EmCb.ErrConnect, e.getMessage());
				return;
			}

			// 启动读数据线程
			this.rt = new ReadThread(this.sp.getInputStream());
			this.rt.start();

			cb(EmCb.Connected);
		}
	}

	@Override
	public void close() {
		// 关闭串口
		if (this.sp != null) {
			// 关闭读数据线程
			this.rt.interrupt();
			this.rt = null;
			this.sp.close();
			this.sp = null;
		}

		// 关闭电源
		powerOff ();

		cb(EmCb.DisConnected);
	}

}
