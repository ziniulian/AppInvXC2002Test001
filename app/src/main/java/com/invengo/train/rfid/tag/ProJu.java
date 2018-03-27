package com.invengo.train.rfid.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * 配属局信息
 * Created by LZR on 2017/7/27.
 */

public class ProJu {
	private String id;
	private String nam;
	private Map<String, ProSuo> suos = new HashMap<>();	// 配属所信息

	public ProJu setId(String id) {
		this.id = id;
		return this;
	}

	public ProJu setNam(String nam) {
		this.nam = nam;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getNam() {
		return nam;
	}

	public Map<String, ProSuo> getSuos() {
		return suos;
	}
}
