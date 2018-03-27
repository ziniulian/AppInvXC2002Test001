dat.domOut = function (t) {
	var d = document.createElement("div");
	t.iddom = document.createElement("div");
	t.scd = false;
	d.className = "check_out";
	d.appendChild(dat.domId((dat.total + 1), t.iddom));
	d.dat = t;
	// d.onclick = function (e) {
	// 	dat.scd(t.id);
	// };
	t.enb = true;
	return d;
};

dat.del = function () {
	if (dat.count) {
		rfid.dbClear();

		dat.count = 0;
		content.innerHTML = "";
		count.innerHTML = "共 " + dat.count + " 条";
	}
	dat.hidDialog();
};

// 显示对话框
dat.showDialog = function () {
	if (dat.count) {
		dialog.className = "home_dialog";
	} else {
		noDialog.className = "home_dialog";
	}
};

// 关闭对话框
dat.hidDialog = function () {
	noDialog.className = "Lc_nosee";
	dialog.className = "Lc_nosee";
};

// 退出页面
dat.back = function () {
	if (dialog.className === "Lc_nosee") {
		location.href = "home.html";
	} else {
		dat.hidDialog();
	}
};
