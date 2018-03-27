package com.invengo.train.rfid.tag;

/**
 * 车型信息
 * Created by LZR on 2017/7/27.
 */

public class ProMod {
	private String id;
	private String nam;

	public ProMod setId(String id) {
		this.id = id;
		return this;
	}

	public ProMod setNam(String nam) {
		this.nam = nam;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getNam() {
		return nam;
	}
}
