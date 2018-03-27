package com.invengo.train.rfid;

import android.content.Context;

import com.invengo.train.rfid.tag.BaseTag;

/**
 * 读写器基类
 * Created by ziniulian on 2017/10/16.
 */

public abstract class Base {
	private InfCallBack icl = null;

	// 设置监听
	public void setCallBackListenter(InfCallBack l) {
		this.icl = l;
	}

	// 回调
	public void cb (EmCb e, String... args) {
		if (icl != null) {
			icl.cb(e, args);
		}
	}

	// 读到标签时的触发事件
	protected void onReadTag (BaseTag bt) {
		if (icl != null) {
			icl.onReadTag(bt);
		}
	}

	/********************* 接口 **************************/

	// 初始化
	public abstract void init(Context ct) throws Exception;

	// 打开
	public abstract void open();

	// 关闭
	public abstract void close();

	// 读
	public abstract void read();

}
