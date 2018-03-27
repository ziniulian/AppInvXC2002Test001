function init() {
	rfid.startTim();
	rfid.flushTim();
	appnam.innerHTML = rfid.getAppNam();
	dat.flashlight(rfid.getFlashlight());
	if (dat.getUrlReq().dl) {
		dat.timdl = true;
		dialog_content.innerHTML = "当前系统时间为：<br />" + rfid.getTim() + "<br />请确认时间是否正确！<br /><br />";
		dialog.className = "home_dialog";
	}
}

dat = {
	timdl: false,

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

	// 手电筒
	flashlight: function (b) {
		if (b) {
			fldom.className = "home_block home_block_lightH";
		} else {
			fldom.className = "home_block home_block_light";
		}
	},

	// 还原
	restore: function () {
		exit.className = "Lc_nosee";
		document.title = "Home";
	},

	// 关闭对话框
	hidDialog: function () {
		dat.timdl = false;
		dialog.className = "Lc_nosee";
	},

	// 调取时间修改界面
	callTim: function () {
		dat.hidDialog();
		rfid.callTim();
	},

	// 退出页面
	back: function () {
		if (document.title === "Exit") {
			rfid.exit();
		} else if (dat.timdl) {
			dat.hidDialog();
		} else {
			document.title = "Exit";
			exit.className = "home_exit mfs";
			setTimeout(dat.restore, 2000);
		}
	}

};
