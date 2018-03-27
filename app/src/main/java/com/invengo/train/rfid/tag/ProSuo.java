package com.invengo.train.rfid.tag;

/**
 * 配属所信息
 * Created by LZR on 2017/7/27.
 */

public class ProSuo {
	private String id;
	private String nam;

	public ProSuo setId(String id) {
		this.id = id;
		return this;
	}

	public ProSuo setNam(String nam) {
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
