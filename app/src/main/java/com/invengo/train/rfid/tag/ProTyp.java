package com.invengo.train.rfid.tag;

/**
 * 车种信息
 * Created by LZR on 2017/7/27.
 */

public class ProTyp {
	private String id;
	private String nam;

	public ProTyp setId(String id) {
		this.id = id;
		return this;
	}

	public ProTyp setNam(String nam) {
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
