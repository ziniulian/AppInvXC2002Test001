function init() {
	rfid.flushTim();
	if (dat.getUrlReq().read) {
		rfid.read();
	}
}

rfid.hdRead = function (tag) {
	var t = encodeURIComponent(JSON.stringify(tag));
	var u;
	switch (tag.pro.src) {
		case "T":
		case "Q":
		case "!":
			u = "infoT.html?tag=";
			break;
		case "J":
			u = "infoJ.html?tag=";
			break;
		case "D":
			u = "infoD.html?tag=";
			break;
		case "K":
			u = "infoK.html?tag=";
			break;
		default:
			u = "infoUn.html?tag=";
			break;
	}
	location.href = u + t;
};

dat = {
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

	reading: function () {
		boso.className = "boso Lc_noadr bg scan_bg_i";
		btn.className = "Lc_nosee";
		scaning.className = "scan_ing";
	},

	readNull: function () {
		boso.className = "boso Lc_noadr bg scan_bg_e";
		scaning.className = "Lc_nosee";
		btn.className = "scan_btn scan_btn_e";
	},

	// 退出页面
	back: function () {
		location.href = "home.html";
	}

};
