	var request;
	var minpage;
	var maxpage;
	var startindex;
	var hasnext;

	function doSearch(type) {

		var result = document.getElementById("result");
		var pagingdiv = document.getElementById("paging");
		result.innerHTML = "";
		pagingdiv.innerHTML = "";

		if (type != 'paging') {
			var startindexinput = document.getElementById("startindex");
			startindexinput.value = "1";
		}

		request = {
			startindex : 1,
			query : ""
		};
		DWRUtil.getValues(request);

		searchService.getSearchResults(request, fillPage);
	}

	
	function fillPage(data) {

		var list = data.results;
		var resultdiv = document.getElementById("result");
		var pagingdiv = document.getElementById("paging");

		resultdiv.innerHTML = "";
		pagingdiv.innerHTML = "";

		if (list.length == 0) {
			resultdiv.innerHTML = "<span>Sorry, we can't find what you want...</span>";
			return;
		}

		for (var i = 0; i < list.length; i++) {
			var ele = document.createElement("div");
			ele.setAttribute("id", "info" + list[i]);
		//	ele.innerHTML = "<span>loading..</span>";
			resultdiv.appendChild(ele);
			searchService.getSearchResultById(list[i], fillDetailResult);
		}

		minpage = data.minpage;
		maxpage = data.maxpage;
		startindex = data.startindex;
		hasnext = data.hasnext;

		if (minpage != 1) {
			var link = document.createElement("a");
			link.setAttribute("href", "javascript:paging('"
					+ ((minpage - 11) * 10 + 1) + "')");
			link.innerHTML = "前10页";
			pagingdiv.appendChild(link);
		}

		for (var j = minpage; j <= maxpage; j++) {
			if ((j - 1) * 10 + 1 != startindex) {
				var link = document.createElement("a");
				link.setAttribute("href", "javascript:paging('"
						+ ((j - 1) * 10 + 1) + "')");
				link.innerHTML = "第" + j + "页";
				pagingdiv.appendChild(link);
			} else {
				pagingdiv.innerHTML += ("第" + j + "页");
			}
		}

		if (hasnext == 1) {
			var link = document.createElement("a");
			link.setAttribute("href", "javascript:paging('"
					+ (maxpage * 10 + 1) + "')");
			link.innerHTML = "后10页";
			pagingdiv.appendChild(link);
		}
	}

	function fillDetailResult(record) {
		if(record.id == 0){
			return;
		}
		var result = document.getElementById("info" + record.id);
		result.innerHTML = "<table border='0' cellpadding='0' cellspacing='0' width=600><tr><td width='120'></td><td><a href='"
                  + record.url+ 
                  "' target='_blank'><font color=#C60A00 size='2'>"
				+ "<font size='2'>标题：</font>"
				+ record.title
				+ "</font></a><br><font color=#000000 size='2'>"
				+ record.abstractConcent
				+ "......"
				+ "</font><br><a href='" + record.url + "'target='_blank'>"
				+ "<font size=2>"
				+ record.url
				+ "</font>"
				+ "</a><br><font size='2' color=green>索引时间:</font>"
				+ "<font size=2 color=green>"
				+ record.indexCreateTIme
				+ "</font>"
				+ "</td>"
				+ "</tr><tr><td height='20' bgcolor='#ffffff'></td></tr></table>";
	}

	function paging(newindex) {
		document.getElementById("startindex").value = newindex;
		doSearch("paging");
	}

	function handlekey() {
		if (document.getElementById("query").value == ""){
			return;
			}			
		if (window.event) {
			if (event.keyCode == 13) {
				doSearch("");
			}
		}
	}
	
	function keyup(){
		setvalue();
	}
	function setvalue(){
		document.getElementById("maindiv").style.display="none";
		document.getElementById("searchdiv").style.display="block";
		document.getElementById("query").value = document.getElementById("word").value;
		document.getElementById("query").focus();
	}