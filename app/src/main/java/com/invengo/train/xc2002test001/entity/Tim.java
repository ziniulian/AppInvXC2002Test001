package com.invengo.train.xc2002test001.entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.invengo.train.xc2002test001.Ma;
import com.invengo.train.xc2002test001.enums.EmUh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import static com.invengo.train.xc2002test001.Ma.sdDir;

/**
 * 自定义时间
 * Created by ziniulian on 2017/10/27.
 */

public class Tim implements Runnable {
	private static final String path = "tim.txt";

	private Calendar t = Calendar.getInstance();	// 时间
	private long offset = 0;	// 时间差
	private Thread tr = null;	// 时间线程
	private BroadcastReceiver rcv;	// 时间修改监听器
	private Ma ma;	// 主线程

	public Tim (Ma m) {
		this.ma = m;

		try {
			// 读取时间差
			BufferedReader br = new BufferedReader(new InputStreamReader(m.openFileInput(path)));
			this.offset = Long.parseLong(br.readLine());
			br.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}

		rcv = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String dat = intent.getStringExtra("tim");
				String[] t = dat.split(",");
				setT(
						Integer.parseInt(t[0]),
						Integer.parseInt(t[1]) - 1,
						Integer.parseInt(t[2]),
						Integer.parseInt(t[3]),
						Integer.parseInt(t[4]),
						Integer.parseInt(t[5])
				);

				// 生成反馈文件
				try {
					File f = new File(Environment.getExternalStorageDirectory(), sdDir + path);
					if (!f.getParentFile().exists()) {
						f.getParentFile().mkdirs();
					}
					f.createNewFile();
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		};

	}

	private void setT(int y, int m, int d, int h, int f, int s) {
		this.stop();
		this.t.set(y, m, d, h, f, s);
		this.offset = this.t.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		this.start();
		try {
			// 保存时间差
			FileOutputStream fs = this.ma.openFileOutput(path, Context.MODE_PRIVATE);
			fs.write((this.offset + "\n").getBytes());
			fs.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	private void setT() {
		this.t.setTimeInMillis(Calendar.getInstance().getTimeInMillis() + this.offset);
	}

	// 时间差清零
	public void reset() {
		this.stop();
		this.offset = 0;
		this.setT();
		this.start();
		try {
			// 保存时间差
			FileOutputStream fs = this.ma.openFileOutput(path, Context.MODE_PRIVATE);
			fs.write((this.offset + "\n").getBytes());
			fs.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	public Calendar getT() {
		setT();
		return this.t;
	}

	public BroadcastReceiver getRcv() {
		return rcv;
	}

	public Thread start() {
		if (this.tr == null) {
			// 开启线程
			this.tr = new Thread(this);
			this.tr.start();
		}
		return this.tr;
	}

	public void stop() {
		if (this.tr != null) {
			// 关闭线程
			this.tr.interrupt();
			this.tr = null;
		}
	}

	@Override
	public void run() {
		try {
			// 每分钟更新一次
			setT();
			ma.sendUh(EmUh.Tim);
			Thread.sleep(1000 * (60 - this.t.get(Calendar.SECOND)));
			while (!this.tr.isInterrupted()) {
				setT();
				ma.sendUh(EmUh.Tim);
				Thread.sleep(60000);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
}
