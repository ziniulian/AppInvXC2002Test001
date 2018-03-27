package com.invengo.train.xc2002test001.entity;

import android.hardware.Camera;

import com.invengo.train.xc2002test001.Ma;
import com.invengo.train.xc2002test001.enums.EmUrl;

/**
 * 手电筒
 * Created by ziniulian on 2017/10/27.
 */

public class FlLight {
	private boolean flb = false;
	private Camera camera;
	private Ma ma;

	public FlLight (Ma m) {
		this.ma = m;
	}

	public boolean isFlb() {
		return flb;
	}

	public void flashlight (boolean stop) {
		if (flb) {
			// 关闭手电筒
			ma.sendUrl(EmUrl.FlashLight, "false");
			camera.stopPreview(); // 关掉亮灯
			camera.release(); // 关掉照相机
			camera = Camera.open();
			//			camera.setPreviewCallback(null);
			camera.stopPreview(); // 关掉亮灯
			camera.release(); // 关掉照相机
			flb = false;
		} else if (!stop) {
			// 打开手电筒
			ma.sendUrl(EmUrl.FlashLight, "true");
			camera = Camera.open();
			Camera.Parameters params = camera.getParameters();
			params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			//			camera.setPreviewCallback(null);
			camera.startPreview(); // 开始亮灯
			flb = true;
		}
	}
}
