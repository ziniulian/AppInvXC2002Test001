rfid = {
	hdRead: function (tag) {
		// todo
	},

	read: function () {
		rfdo.read();
	},


/*******************************************************/

	dbSav: function (cod, xiu) {
		rfdo.dbSav(cod, xiu);
	},

	dbDel: function (ids) {
		rfdo.dbDel(ids);
	},

	dbClear: function () {
		rfdo.dbClear();
	},

	dbSet: function (id, xiu) {
		rfdo.dbSet(id, xiu);
	},

	dbGet: function (s, l) {
		return rfdo.dbGet(s, l);
	},

	dbCount: function () {
		return rfdo.dbCount();
	},

	getAppNam: function () {
		return rfdo.getAppNam();
	},

	flashlight: function () {
		rfdo.flashlight();
	},

	getFlashlight: function () {
		return rfdo.getFlashlight();
	},

	tim: function (t) {
		var d = document.getElementById("homTim");
		if (d) {
			homTim.innerHTML = t;
		}
	},

	startTim: function () {
		rfdo.startTim();
	},

	flushTim: function () {
		rfdo.flushTim();
	},

	getTim: function () {
		return rfdo.getTim();
	},

	callTim: function () {
		rfdo.callTim();
	},

	exit: function () {
		rfdo.exit();
	}

};
