package com.invengo.train.rfid.tag;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.invengo.tagdata2str.DataConver.Conver;

/**
 * Created by LZR on 2017/6/29.
 */

public abstract class BaseTag {
	private String cod;	// 标签源码
	protected TagPro tpro;	// 标签属性
	private static String able = "TQ!KJD";	// 遮罩
	private static Map<String, TagPro> ts = new HashMap<>();	// 类型集合

	public void setCod(String cod) {
		this.cod = cod;
		parseByCod(cod);
	}

	public void setTpro(TagPro tpro) {
		this.tpro = tpro;
	}

	public String getCod() {
		return cod;
	}

	// 获取属性码
	public String getPro() {
		return tpro.getPro();
	}

	// 获取属性名
	public String getProNam () {
		return tpro.getProNam();
	}

	// 获取格式化的制造年月
	protected String getTimFmt (String s) {
		int y = Integer.parseInt(s.substring(0, 2));
		int m = Integer.parseInt(s.substring(2), 16);

		if (y > 50) {
			y += 1900;
		} else {
			y += 2000;
		}

		return String.format("%4d年%02d月", y, m);
	}

	/********************* 接口 **************************/

	// 解析标签源码
	protected abstract void parseByCod (String cod);

	// 获取JSON格式
	public abstract String toJson ();

	/********************* 静态方法 **************************/

	// 屏蔽属性码
	private static String shield (String c) {
		String r = c.substring(0, 1);
		if (!able.contains(r)) {
			r = null;
		} else {
			// 纠正标签源码与属性码不一致的问题
			switch (r) {
				case "!":
					r = "Q";
					break;
			}
		}
		return r;
	}

	public static String getAble () {
		return able;
	}

	// 解析标签
	public static BaseTag parse (String c) {
		BaseTag tag;
		try {
			tag = ts.get(shield(c)).crtTag(c);
		} catch (Exception e) {
//			e.printStackTrace();
			tag = new TagUn();
			tag.setCod(c.substring(0, 1));
		}
		return tag;
	}

	// 解析标签
	public static BaseTag parse (byte[] src) {
		BaseTag tag = null;
		if (src != null) {
			if (src.length == 19) {
				if (src[0] == 42) {
					int i;
					for (i = 1; i < src.length; i++) {
						src[i - 1] = src[i];
					}
					src[i - 1] = 0;
				}
				try {
					// 虽在 parse 处已作异常处理，但不知为何还会死机，故在此处再做一次异常处理看是否能解决该问题。
					// 可能 Conver 接口传回了空值，也可能是 Conver 接口死机造成的。
					String s = Conver(src, 0);
//Log.i("---------", s);
//				tag = parse("TC50   400000211A12C");
//				tag = parse("!C64K  Q06505812A94C");
//				tag = parse("KYW25T 677083E073 066");
//				tag = parse("J10489001401AH  TK88188 ");
//				tag = parse("D3010000002101   G12345B");
					tag = parse(s);
				} catch (Exception e) {
					tag = parse("?");
				}
			} else {
				tag = parse("?");
			}
		}
		return tag;
	}

	// 遮罩配置
	public static String confAble (File ini, InputStream confis) throws Exception {
		String r = null;

		// 文件复制
		if (!ini.exists()) {
			if (!ini.getParentFile().exists()) {
				ini.getParentFile().mkdirs();
			}
			FileOutputStream os = new FileOutputStream(ini);
			byte[] buf = new byte[1024];
			int n = confis.read(buf);
			while (n != -1) {
				os.write(buf, 0, n);
				n = confis.read(buf);
			}
			os.close();
		}
		confis.close();

		// 遮罩配置
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbd = dbf.newDocumentBuilder();
		InputStream cis = new FileInputStream(ini);

		Document cdoc = dbd.parse(cis);
		String k = cdoc.getElementsByTagName("conf").item(0).getAttributes().getNamedItem("use").getNodeValue();
		Node n = cdoc.getElementsByTagName(k).item(0);
		able = n.getAttributes().getNamedItem("mask").getNodeValue();
		r = n.getAttributes().getNamedItem("name").getNodeValue();
		cis.close();

		return r;
	}

	// 读取 XML 文件
	public static void load (InputStream fs) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbd = dbf.newDocumentBuilder();

		// 属性配置
		if (ts.isEmpty()) {
			Document doc = dbd.parse(fs);

			loadPro(doc.getElementsByTagName("VehicleProperty"));	// 标签属性
			loadTyp(doc.getElementsByTagName("VehicleType"));	// 车种
			loadFac(doc.getElementsByTagName("MadeFactory"));	// 制造厂
			loadMod(doc.getElementsByTagName("VehicleModel"));	// 车型
			loadSuo(doc.getElementsByTagName("Station"));	// 配属所
		}
		fs.close();
	}

	// 读取标签属性
	private static void loadPro (NodeList na) {
		TagPro tp;
		Node n;
		int i;

		for (i = 0; i < na.getLength(); i++) {
			n = na.item(i);

			tp = new TagPro();
			tp.setPro(n.getAttributes().getNamedItem("PropertyCode").getNodeValue());
			tp.setProNam(n.getAttributes().getNamedItem("PropertyName").getNodeValue());
			switch (tp.getPro()) {
				case "K":
					tp.setCls(TagK.class);
					break;
				case "D":
					tp.setCls(TagD.class);
					break;
				case "J":
					tp.setCls(TagJ.class);
					break;
				case "T":
					tp.setCls(TagT.class);
					break;
				case "!":
				case "Q":
					tp.setCls(TagQ.class);
					break;
			}

			ts.put(tp.getPro(), tp);
		}
	}

	// 读取车种
	private static void loadTyp (NodeList na) {
		Node n;
		String k;
		ProTyp t;
		Map<String, Map<String, ProTyp>> m = new HashMap<>();
		int i;

		for (i = 0; i < na.getLength(); i++) {
			n = na.item(i);
			t = new ProTyp();
			t.setId(n.getAttributes().getNamedItem("TypeCode").getNodeValue());
			t.setNam(n.getAttributes().getNamedItem("TypeName").getNodeValue());

			k = n.getParentNode().getAttributes().getNamedItem("Property").getNodeValue();
			if (!m.containsKey(k)) {
				m.put(k, new HashMap<String, ProTyp>());
			}
			m.get(k).put(t.getId(), t);
		}

		for (Map.Entry<String, Map<String, ProTyp>> e: m.entrySet()) {
			k = e.getKey();
			for (i = 0; i < k.length(); i++) {
				ts.get(Character.toString(k.charAt(i))).setTyps(e.getValue());
			}
		}
	}

	// 读取制造厂
	private static void loadFac (NodeList na) {
		Node n;
		String k;
		ProFac f;
		Map<String, Map<String, ProFac>> m = new HashMap<>();
		int i;

		for (i = 0; i < na.getLength(); i++) {
			n = na.item(i);
			f = new ProFac();
			f.setId(n.getAttributes().getNamedItem("FactoryCode").getNodeValue());
			f.setNam(n.getAttributes().getNamedItem("ShortName").getNodeValue());
			f.setFullNam(n.getAttributes().getNamedItem("FactoryName").getNodeValue());

			k = n.getParentNode().getAttributes().getNamedItem("Property").getNodeValue();
			if (!m.containsKey(k)) {
				m.put(k, new HashMap<String, ProFac>());
			}
			m.get(k).put(f.getId(), f);
		}

		for (Map.Entry<String, Map<String, ProFac>> e: m.entrySet()) {
			k = e.getKey();
			for (i = 0; i < k.length(); i++) {
				ts.get(Character.toString(k.charAt(i))).setFacs(e.getValue());
			}
		}
	}

	// 读取制造厂
	private static void loadMod (NodeList na) {
		Node n;
		String k;
		ProMod d;
		Map<String, Map<String, ProMod>> m = new HashMap<>();
		int i;

		for (i = 0; i < na.getLength(); i++) {
			n = na.item(i);
			d = new ProMod();
			d.setId(n.getAttributes().getNamedItem("ModelCode").getNodeValue());
			d.setNam(n.getAttributes().getNamedItem("ModelName").getNodeValue());

			k = n.getAttributes().getNamedItem("Property").getNodeValue();
			if (!m.containsKey(k)) {
				m.put(k, new HashMap<String, ProMod>());
			}
			m.get(k).put(d.getId(), d);
		}

		for (Map.Entry<String, Map<String, ProMod>> e: m.entrySet()) {
			k = e.getKey();
			for (i = 0; i < k.length(); i++) {
				ts.get(Character.toString(k.charAt(i))).setMods(e.getValue());
			}
		}
	}

	// 读取配属所
	private static void loadSuo (NodeList na) {
		Node n;
		Node p;
		String k;
		String j;
		ProJu u;
		ProSuo s;
		Map<String, Map<String, ProJu>> m = new HashMap<>();
		Map<String, ProJu> mj;
		int i;

		for (i = 0; i < na.getLength(); i++) {
			n = na.item(i);
			s = new ProSuo();
			s.setId(n.getAttributes().getNamedItem("StationCode").getNodeValue());
			s.setNam(n.getAttributes().getNamedItem("StationName").getNodeValue());

			p = n.getParentNode();
			k = p.getAttributes().getNamedItem("Property").getNodeValue();
			j = p.getAttributes().getNamedItem("AreaCode").getNodeValue();
			if (!m.containsKey(k)) {
				m.put(k, new HashMap<String, ProJu>());
			}
			mj = m.get(k);
			if (!mj.containsKey(j)) {
				u = new ProJu();
				u.setId(j);
				u.setNam(p.getAttributes().getNamedItem("AreaName").getNodeValue());
				mj.put(j, u);
			} else {
				u = mj.get(j);
			}
			u.getSuos().put(s.getId(), s);
		}

		for (Map.Entry<String, Map<String, ProJu>> e: m.entrySet()) {
			k = e.getKey();
			for (i = 0; i < k.length(); i++) {
				ts.get(Character.toString(k.charAt(i))).setJus(e.getValue());
			}
		}
	}
}
