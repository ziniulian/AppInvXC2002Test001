package com.invengo.train.rfid;

/**
 * 回调类型
 * Created by ziniulian on 2017/10/16.
 */

public enum EmCb {
	ShowProgress,	// 显示进度条
	HidProgress,	// 隐藏进度条
	ShowToast,		// 信息提示
	Connected,		// 与RFID设备已建立连接
	DisConnected,	// 与RFID设备已断开连接
	ErrConnect,		// 与RFID设备连接失败
	ErrInit,		// 初始化失败
	Reading,		// 正在读取信息
	ReadNull,		// 没有读取到信息
	ErrRead			// 读取失败
}
