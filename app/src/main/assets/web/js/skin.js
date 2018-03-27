function init(c) {
	dat.cur = c;
	rfid.flushTim();
}

dat = {
	cur: "",

	set: function (c) {
		if (dat.cur !== c) {
			document.getElementById(dat.cur).className = "skin_pic_txt";
			document.getElementById(c).className = "skin_pic_txt skin_pic_txtH";
			dat.cur = c;
		}
	},

	sav: function () {
		location.href = "home.html"
	},

	// 退出页面
	back: function () {
		location.href = "home.html"
	}
};
