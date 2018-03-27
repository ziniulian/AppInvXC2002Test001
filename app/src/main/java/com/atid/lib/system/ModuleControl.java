package com.atid.lib.system;

/**
 * JNI电源接口 (仅 AT911N ， var1=2， var2=4)
 * Created by LZR on 2017/5/22.
 */

public class ModuleControl {
	static {
		System.loadLibrary("system_control");
	}

	public static native void powerRfidEx(boolean var0, int var1, int var2);
}
