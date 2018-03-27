package com.invengo.train.rfid.tag;

import java.util.Map;

/**
 * 标签属性
 * Created by LZR on 2017/6/29.
 */

public class TagPro {
	private String pro;	// 属性码
	private String proNam;	// 属性名
	private Map<String, ProFac> facs = null;	// 制造厂信息
	private Map<String, ProJu> jus = null;	// 配属局信息
	private Map<String, ProTyp> typs = null;	// 车种信息
	private Map<String, ProMod> mods = null;	// 车型信息
	private Class cls = null;	// 标签类

	public void setCls(Class cls) {
		this.cls = cls;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public void setProNam(String proNam) {
		this.proNam = proNam;
	}

	public void setFacs(Map<String, ProFac> facs) {
		this.facs = facs;
	}

	public void setJus(Map<String, ProJu> jus) {
		this.jus = jus;
	}

	public TagPro setTyps(Map<String, ProTyp> typs) {
		this.typs = typs;
		return this;
	}

	public TagPro setMods(Map<String, ProMod> mods) {
		this.mods = mods;
		return this;
	}

	public String getPro() {
		return pro;
	}

	public String getProNam() {
		return proNam;
	}

	public Map<String, ProFac> getFacs() {
		return facs;
	}

	public Map<String, ProJu> getJus() {
		return jus;
	}

	public Map<String, ProMod> getMods() {
		return mods;
	}

	public Map<String, ProTyp> getTyps() {
		return typs;
	}

	// 生成标签
	public BaseTag crtTag (String c) throws Exception {
		BaseTag t = (BaseTag) cls.newInstance();
		t.setTpro(this);
		t.setCod(c);
		return t;
	}
}
