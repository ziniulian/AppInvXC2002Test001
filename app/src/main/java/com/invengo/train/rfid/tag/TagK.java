package com.invengo.train.rfid.tag;

/**
 * Created by LZR on 2017/6/29.
 */

public class TagK extends BaseTag {
	private String typ;	// 车种
	private String mod;	// 车型
	private String num;	// 车号
	private String fac;	// 制造厂
	private String tim;	// 制造年月
	private String pot;	// 端位
	private String cap;	// 定员

	@Override
	protected void parseByCod(String cod) {
		typ = cod.substring(1, 3);	// 车种
		mod = cod.substring(3, 7);	// 车型
		num = cod.substring(7, 13);	// 车号
		fac = cod.substring(13, 14);	// 制造厂
		tim = cod.substring(14, 17);	// 制造年月
		pot = cod.substring(17, 18);	// 端位
		cap = cod.substring(18, 21);	// 定员
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
		s.append("\"},\n\t\"pot\":\"");
		s.append(pot);
		s.append("\",\n\t\"cap\":\"");
		s.append(cap);
		s.append("\"\n}");
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

	public String getFac() {
		return fac;
	}

	public String getTim() {
		return tim;
	}

	public String getPot() {
		return pot;
	}

	public String getCap() {
		return cap;
	}
}
