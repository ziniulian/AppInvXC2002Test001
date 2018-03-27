package com.invengo.train.rfid.tag;

/**
 * Created by LZR on 2017/6/29.
 */

public class TagT extends BaseTag {
	private String typ;	// 车种
	private String mod;	// 车型
	private String num;	// 车号
	private String lng;	// 换长
	private String fac;	// 制造厂
	private String tim;	// 制造年月

	@Override
	protected void parseByCod(String cod) {
		typ = cod.substring(1, 2);	// 车种
		mod = cod.substring(2, 7);	// 车型
		num = cod.substring(7, 14);	// 车号
		lng = cod.substring(14, 16);	// 换长
		fac = cod.substring(16, 17);	// 制造厂
		tim = cod.substring(17, 20);	// 制造年月
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
		s.append("\"},\n\t\"typ\":{\"src\":\"");
		s.append(typ);
		s.append("\",\"nam\":\"");
		s.append(getTypNam());
		s.append("\"},\n\t\"mod\":\"");
		s.append(mod);
		s.append("\",\n\t\"num\":\"");
		s.append(num);
		s.append("\",\n\t\"lng\":\"");
		s.append(lng);
		s.append("\",\n\t\"fac\":{\"src\":\"");
		s.append(fac);
		s.append("\",\"nam\":\"");
		s.append(getFacNam());
		s.append("\",\"snam\":\"");
		s.append(getFacShortNam());
		s.append("\"},\n\t\"tim\":{\"src\":\"");
		s.append(tim);
		s.append("\",\"nam\":\"");
		s.append(getTimFmt());
		s.append("\"}\n}");
		return s.toString();
	}

	public String getTimFmt() {
		return super.getTimFmt(tim);
	}

	public String getFacNam() {
		String r = "";
		try {
			r = tpro.getFacs().get(fac).getFullNam();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public String getFacShortNam() {
		String r = "";
		try {
			r = tpro.getFacs().get(fac).getNam();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public String getTypNam() {
		String r = "";
		try {
			r = tpro.getTyps().get(typ).getNam();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public String getTyp() {
		return typ;
	}

	public String getMod() {
		return mod;
	}

	public String getNum() {
		return num;
	}

	public String getLng() {
		return lng;
	}

	public String getFac() {
		return fac;
	}

	public String getTim() {
		return tim;
	}
}
