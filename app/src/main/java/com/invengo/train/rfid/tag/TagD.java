package com.invengo.train.rfid.tag;

/**
 * Created by LZR on 2017/6/29.
 */

public class TagD extends BaseTag {
	private String mod;	// 型号
	private String num;	// 车辆编号
	private String ju;	// 配属局
	private String suo;	// 配属段（所）
	private String tnoLet;	// 车次字母区
	private String tnoNum;	// 车次数字区
	private String pot;	// 双机车状态（端位）

	@Override
	protected void parseByCod(String cod) {
		mod = cod.substring(1, 4);	// 型号
		num = cod.substring(4, 10);	// 车辆编号
		ju = cod.substring(10, 12);	// 配属局
		suo = cod.substring(12, 14);	// 配属段（所）
		tnoLet = cod.substring(14, 18);	// 车次字母区
		tnoNum = cod.substring(18, 23);	// 车次数字区
		pot = cod.substring(23, 24);	// 双机车状态（端位）
	}

	@Override
	public String toJson() {
		StringBuilder s = new StringBuilder();
		s.append("{\n\t\"cod\":\"");
		s.append(getCod());
		s.append("\",\n\t\"pro\":{\"src\":\"");
		s.append(getPro());
		s.append("\",\"nam\":\"");
		s.append(getProNam());
		s.append("\"},\n\t\"mod\":{\"src\":\"");
		s.append(mod);
		s.append("\",\"nam\":\"");
		s.append(getModNam());
		s.append("\"},\n\t\"num\":\"");
		s.append(num);
		s.append("\",\n\t\"ju\":{\"src\":\"");
		s.append(ju);
		s.append("\",\"nam\":\"");
		s.append(getJuNam());
		s.append("\"},\n\t\"suo\":{\"src\":\"");
		s.append(suo);
		s.append("\",\"nam\":\"");
		s.append(getSuoNam());
		s.append("\"},\n\t\"tnoLet\":\"");
		s.append(tnoLet);
		s.append("\",\n\t\"tnoNum\":\"");
		s.append(tnoNum);
		s.append("\",\n\t\"pot\":\"");
		s.append(pot);
		s.append("\"\n}");
		return s.toString();
	}

	public String getModNam() {
		String r = "";
		try {
			r = tpro.getMods().get(mod).getNam();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public String getJuNam () {
		String r = "";
		try {
			r = tpro.getJus().get(ju).getNam();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public String getSuoNam () {
		String r = "";
		try {
			r = tpro.getJus().get(ju).getSuos().get(suo).getNam();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public String getMod() {
		return mod;
	}

	public String getNum() {
		return num;
	}

	public String getJu() {
		return ju;
	}

	public String getSuo() {
		return suo;
	}

	public String getTnoLet() {
		return tnoLet;
	}

	public String getTnoNum() {
		return tnoNum;
	}

	public String getPot() {
		return pot;
	}
}
