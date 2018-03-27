rfid.hdRead = function (tag) {
	var t = encodeURIComponent(JSON.stringify(tag));
	location.href = "readTrtInfo.html?tag=" + t;
};

dat.back = function () {
	rfid.exit();
};
