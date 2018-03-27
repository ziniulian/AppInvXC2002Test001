
dat.setXiu = function (x) {
	if (dat.tag.xiu) {
		if (dat.tag.xiu.src === x) {
			return;
		} else {
			dat.tag.xiu.dom.className = "scan_xiu";
		}
	}
	var d = document.getElementById("xiu" + x);
	d.className = "scan_xiu scan_xiuH";
	dat.tag.xiu = {
		src: x,
		dom: d,
		nam: d.innerHTML
	};
};

dat.flush = function (x) {
	pro.innerHTML = dat.tag.pro.src + "[" + dat.tag.pro.nam + "]";
	typ.innerHTML = dat.tag.typ.src + " " + dat.tag.mod;
	num.innerHTML = dat.tag.num;
	fac.innerHTML = dat.tag.fac.src + "[" + dat.tag.fac.snam + "]";
	tim.innerHTML = dat.tag.tim.src + "[" + dat.tag.tim.nam + "]";
	if (!x) {
		x = "C";
	}
	dat.setXiu(x);
};
