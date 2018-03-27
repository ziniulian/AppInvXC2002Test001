function init() {
	content.onscroll = dat.toNext;
	rfid.flushTim();
	dat.count = rfid.dbCount();
	count.innerHTML = "共 " + dat.count + " 条";
	dat.nextPage();

	// 测试
	// dat.flush(
	// 	[
	// 		{
	// 			id:3,
	// 			tim:"20000110071944",
	// 			xiu:"",
	// 			tag:{
	// 				cod:"D2617162813DD<:2155A",
	// 				pro:{src:"D",nam:"动车"},
	// 				mod:{"src":"261",nam:""},
	// 				num:"716",
	// 				ju:{src:"28",nam:"西安局"},
	// 				suo:{src:"13",nam:""},
	// 				tnoLet:"DD",
	// 				tnoNum:"2155",
	// 				pot:"A"
	// 			}
	// 		},
	// 		{
	// 			id:8,
	// 			tim:"20000110071944",
	// 			xiu:"C",
	// 			tag:{
	// 				cod:"D2617162813DD<:2155A",
	// 				pro:{src:"T",nam:"货车8"},
	// 				typ:{"src":"C",nam:"敞车"},
	// 				mod:"28",
	// 				num:"716",
	// 				fac:{src:"A",snam:"齐厂"},
	// 				tim:{src:"075",nam:"2017年5月"}
	// 			},
	// 			enb: 1
	// 		},
	// 		{
	// 			id:18,
	// 			tim:"20000110071944",
	// 			xiu:"C",
	// 			tag:{
	// 				cod:"D2617162813DD<:2155A",
	// 				pro:{src:"T",nam:"货车1"},
	// 				typ:{"src":"C",nam:"敞车"},
	// 				mod:"28",
	// 				num:"716",
	// 				fac:{src:"A",snam:"齐厂"},
	// 				tim:{src:"075",nam:"2017年5月"}
	// 			}
	// 		},
	// 		{
	// 			id:28,
	// 			tim:"20000110071944",
	// 			xiu:"C",
	// 			tag:{
	// 				cod:"D2617162813DD<:2155A",
	// 				pro:{src:"T",nam:"货车2"},
	// 				typ:{"src":"C",nam:"敞车"},
	// 				mod:"28",
	// 				num:"716",
	// 				fac:{src:"A",snam:"齐厂"},
	// 				tim:{src:"075",nam:"2017年5月"}
	// 			}
	// 		},
	// 		{
	// 			id:35,
	// 			tim:"20000110071944",
	// 			xiu:"",
	// 			tag:{
	// 				cod:"D2617162813DD<:2155A",
	// 				pro:{src:"D",nam:"动车5"},
	// 				mod:{"src":"261",nam:""},
	// 				num:"716",
	// 				ju:{src:"28",nam:"西安局5"},
	// 				suo:{src:"13",nam:""},
	// 				tnoLet:"DD",
	// 				tnoNum:"2155",
	// 				pot:"A"
	// 			}
	// 		},
	// 		{
	// 			id:34,
	// 			tim:"20000110071944",
	// 			xiu:"",
	// 			tag:{
	// 				cod:"D2617162813DD<:2155A",
	// 				pro:{src:"D",nam:"动车4"},
	// 				mod:{"src":"261",nam:""},
	// 				num:"716",
	// 				ju:{src:"28",nam:"西安局4"},
	// 				suo:{src:"13",nam:""},
	// 				tnoLet:"DD",
	// 				tnoNum:"2155",
	// 				pot:"A"
	// 			}
	// 		},
	// 		{
	// 			id:13,
	// 			tim:"20000110071944",
	// 			xiu:"",
	// 			tag:{
	// 				cod:"D2617162813DD<:2155A",
	// 				pro:{src:"D",nam:"动车1"},
	// 				mod:{"src":"261",nam:""},
	// 				num:"716",
	// 				ju:{src:"28",nam:"西安局1"},
	// 				suo:{src:"13",nam:""},
	// 				tnoLet:"DD",
	// 				tnoNum:"2155",
	// 				pot:"A"
	// 			}
	// 		}
	// 	]
	// );

}

dat = {
	busy: true,
	pagesize: 5,
	total: 0,	// 页面显示的记录数
	count: 0,	// 数据库的记录总数
	ts: {},
	scds: {},
	xiu: "C",
	setTag: null,

	getUrlReq: function () {
		var url = location.search;
		var theRequest = {};
		if (url.indexOf("?") != -1) {
			url = url.substr(1).split("&");
			for(var i = 0; i < url.length; i ++) {
				var str = url[i].split("=");
				theRequest[str[0]] = decodeURIComponent(str[1]);
			}
		}
		return theRequest;
	},

	// 下一页
	toNext: function () {
// console.log ( (content.scrollTop + content.clientHeight) + " , " + content.scrollHeight + " , " + content.scrollTop + " , " + content.clientHeight);
		if (content.scrollTop && !dat.busy && (content.scrollTop + content.clientHeight === content.scrollHeight)) {
			dat.nextPage();
		}
	},

	// 下一页
	nextPage: function () {
		if (dat.total < dat.count) {
			dat.busy = true;
			dat.flush(JSON.parse(rfid.dbGet(dat.total, dat.pagesize)));
		}
	},

	flush: function (a) {
		for(var i = 0; i < a.length; i ++) {
			a[i].dom = dat.domOut(a[i]);
			switch (a[i].tag.pro.src) {
				case "T":
				case "Q":
				case "!":
					dat.domT(a[i]);
					a[i].xiuDom = a[i].dom.lastChild.lastChild.lastChild;
					break;
				case "J":
					dat.domJ(a[i]);
					break;
				case "D":
					dat.domD(a[i]);
					break;
				case "K":
					dat.domK(a[i]);
					break;
			}
			content.appendChild(a[i].dom);
			dat.ts[a[i].id] = a[i];
			dat.total ++;
		}
		dat.busy = false;
	},

	scd: function (id) {
		var t = dat.ts[id]
		var d = t.dom;
		if (t.scd) {
			d.className = "check_out";
			delete (dat.scds[id]);
			t.scd = false;
		} else {
			d.className = "check_out check_outH";
			dat.scds[id] = true;
			t.scd = true;
		}
	},

	// 显示对话框
	showDialog: function () {
		var i = 0;
		for (var s in dat.scds) {
			i ++;
		}
		if (i > 0) {
			dialog.className = "home_dialog";
		} else {
			noDialog.className = "home_dialog";
		}
	},

	// 关闭对话框
	hidDialog: function () {
		dialogSet.className = "Lc_nosee";
		noDialog.className = "Lc_nosee";
		dialog.className = "Lc_nosee";
	},

	del: function () {
		var ids = "";
		for (var s in dat.scds) {
			if (ids) {
				ids += ",";
			}
			ids += s;
			content.removeChild(dat.ts[s].dom);
			delete dat.ts[s];
			dat.total --;
			dat.count --;
		}
		if (ids) {
			rfid.dbDel(ids);
			dat.scds = {};
// console.log ( (content.scrollTop + content.clientHeight) + " , " + content.scrollHeight + " , " + content.scrollTop + " , " + content.clientHeight);
			if (!dat.busy && (content.scrollHeight === content.clientHeight) ) {
				dat.nextPage();
			}
		}
		count.innerHTML = "共 " + dat.count + " 条";

		// 重新排序
		var cs = content.children;
		for (var i = 0; i < cs.length; i ++) {
			cs[i].dat.iddom.innerHTML = (i + 1);
		}

		dat.hidDialog();
	},

	domOut: function (t) {
		var d = document.createElement("div");
		t.iddom = document.createElement("div");
		t.scd = false;
		d.className = "check_out";
		d.appendChild(dat.domId((dat.total + 1), t.iddom));	// id
		d.dat = t;
		d.onclick = function (e) {
			dat.scd(t.id);
		};
		return d;
	},

	domT: function (t) {
		var d = document.createElement("div");
		d.appendChild(dat.domLine("时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间", dat.parseTim(t.tim)));	// 时间
		d.appendChild(dat.domLine("属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性", t.tag.pro.src + "[" + t.tag.pro.nam + "]"));
		d.appendChild(dat.domLine("车种车型", t.tag.typ.src + " " + t.tag.mod));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号", t.tag.num));
		d.appendChild(dat.domLine("制&nbsp;&nbsp;造&nbsp;&nbsp;厂", t.tag.fac.src + "[" + t.tag.fac.snam + "]"));
		d.appendChild(dat.domLine("制造年月", t.tag.tim.src + "[" + t.tag.tim.nam + "]"));
		d.appendChild(dat.domLine("修&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程", dat.parseXiu(t)));
		d.className = "check_out_body";
		t.dom.appendChild(d);
	},

	domJ: function (t) {
		var d = document.createElement("div");
		d.appendChild(dat.domLine("时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间", dat.parseTim(t.tim)));	// 时间
		d.appendChild(dat.domLine("属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性", t.tag.pro.src));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型", t.tag.mod.src + "[" + t.tag.mod.nam + "]"));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号", t.tag.num));
		d.appendChild(dat.domLine("配属局段", t.tag.ju.src + t.tag.suo.src + "[" + t.tag.ju.nam + t.tag.suo.nam + "]"));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", t.tag.tnoLet + t.tag.tnoNum));
		d.appendChild(dat.domLine("端&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码", t.tag.pot));
		d.appendChild(dat.domLine("客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货", t.tag.kh));
		d.className = "check_out_body";
		t.dom.appendChild(d);
	},

	domD: function (t) {
		var d = document.createElement("div");
		d.appendChild(dat.domLine("时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间", dat.parseTim(t.tim)));	// 时间
		d.appendChild(dat.domLine("属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性", t.tag.pro.src));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型", t.tag.mod.src + "[" + t.tag.mod.nam + "]"));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号", t.tag.num));
		d.appendChild(dat.domLine("配属局段", t.tag.ju.src + t.tag.suo.src + "[" + t.tag.ju.nam + t.tag.suo.nam + "]"));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", t.tag.tnoLet + t.tag.tnoNum));
		d.appendChild(dat.domLine("端&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码", t.tag.pot));
		d.className = "check_out_body";
		t.dom.appendChild(d);
	},

	domK: function (t) {
		var d = document.createElement("div");
		d.appendChild(dat.domLine("时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间", dat.parseTim(t.tim)));	// 时间
		d.appendChild(dat.domLine("属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性", t.tag.pro.src));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型", t.tag.typ.src + t.tag.mod + "[" + t.tag.typ.nam + "]"));
		d.appendChild(dat.domLine("车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号", t.tag.num));
		d.appendChild(dat.domLine("制&nbsp;&nbsp;造&nbsp;&nbsp;厂", t.tag.fac.src + "[" + t.tag.fac.snam + "]"));
		d.appendChild(dat.domLine("制造年月", t.tag.tim.src + "[" + t.tag.tim.nam + "]"));
		d.appendChild(dat.domLine("定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员", t.tag.cap));
		d.className = "check_out_body";
		t.dom.appendChild(d);
	},

	domLine: function (n, v) {
		var o = document.createElement("div");
		var d = document.createElement("div");
		// d.className = "check_key sfs check_key_space" + n.length;
		d.className = "check_key sfs";
		d.innerHTML = n;
		o.appendChild(d);
		d = document.createElement("div");
		d.className = "check_split sfs";
		d.innerHTML = ":";
		o.appendChild(d);
		d = document.createElement("div");
		d.className = "check_value sfs";
		d.innerHTML = v;
		o.appendChild(d);
		return o;
	},

	domId: function (id, dom) {
		var d = document.createElement("div");
		d.className = "check_idbg";
		dom.className = "check_id sfs";
		dom.innerHTML = id;
		d.appendChild(dom);
		return d;
	},

	parseTim: function (t) {
		var r = "";
		for (var i = 0; i < t.length; i ++) {
			r += t[i];
			switch (i) {
				case 3:
				case 5:
					r += "/";
					break;
				case 7:
					r += " ";
					break;
				case 9:
				case 11:
					r += ":";
					break;
			}
		}
		return r;
	},

	parseXiu: function (t) {
		var x = t.xiu;
		switch (x) {
			case "C":
				x += "[厂修]";
				break;
			case "D":
				x += "[段修]";
				break;
			case "F":
				x += "[辅修]";
				break;
			case "L":
				x += "[临修]";
				break;
			case "Z":
				x += "[轴修]";
				break;
			case "B":
				x += "[报废]";
				break;
		}
		if (!t.enb) {
			x += "<div class='check_set mfs' onclick='dat.chg(" + t.id + ");'>修改</div>";
		}
		return x;
	},

	chg: function (id) {
		var e = arguments.callee.caller.arguments[0];
		e.stopPropagation();
		var t = dat.ts[id];
		dat.setTag = t;
		setId.innerHTML = id;
		setTim.innerHTML = dat.parseTim(t.tim);
		setPro.innerHTML = t.tag.pro.src + "[" + t.tag.pro.nam + "]";
		setTyp.innerHTML = t.tag.typ.src + " " + t.tag.mod;
		setNum.innerHTML = t.tag.num;
		setFac.innerHTML = t.tag.fac.src + "[" + t.tag.fac.snam + "]";
		setTtim.innerHTML = t.tag.tim.src + "[" + t.tag.tim.nam + "]";
		dat.setXiu(t.xiu);
		dialogSet.className = "home_dialog";
	},

	setXiu: function (x) {
		if (dat.xiu !== x) {
			document.getElementById("xiu" + dat.xiu).className = "check_xiu sfs";
			document.getElementById("xiu" + x).className = "check_xiu check_xiuH sfs";
			dat.xiu = x;
		}
	},

	savXiu: function () {
		if (dat.setTag.xiu !== dat.xiu) {
			dat.setTag.xiu = dat.xiu;
			dat.setTag.xiuDom.innerHTML = dat.parseXiu(dat.setTag);
			rfid.dbSet(dat.setTag.id, dat.setTag.xiu);
		}
		dat.hidDialog();
	},

	// 退出页面
	back: function () {
		if (dialog.className === "Lc_nosee" && dialogSet.className === "Lc_nosee") {
			location.href = "home.html";
		} else {
			dat.hidDialog();
		}
	}

};
