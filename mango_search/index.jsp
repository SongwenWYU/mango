
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>芒果搜索</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<script type='text/javascript'
	src="${pageContext.request.contextPath}/dwr/engine.js"></script>
<script type='text/javascript'
	src="${pageContext.request.contextPath}/dwr/util.js"></script>
<script type='text/javascript'
	src="${pageContext.request.contextPath}/dwr/interface/searchService.js"></script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css" rel="stylesheet" href="./css/mango.css" />

</head>
<body>
<div id="maindiv" style="display:block">
	<div align="center">
		<div style="height: 150px"></div>
		<div >
			<span class="hook"></span> <img src="./image/MangoLogo.png"/>
		</div>
		<div>
			<input class="s_input" type="text" id="word" value=""
						onclick="keyup()" /> 
			<input class="submit s_btn"type="submit" value="GO" id="sub" />
		</div>
	</div>
</div>
	<div id="searchdiv" style="display: none">
		<div style=" background-color: #ffffff; width: 100%; height: 80px;  position: fixed; left: 0; top: 0; overflow: hidden">
			<table style="height: 55px">
				<tr>
					<td align="left">
						<div style="height: 5px"></div>
						<div class="logo">
							<span class="hook"></span> <img src="./image/MangoLogo.png"
								style="width: 101px" />
						</div>
						<div style="height: 20px"></div>
					</td>
					<td height="50" align="center"><input type="hidden"
						name="startindex" id="startindex" value="1"> <input
						class="s_input" type="text" name="query" id="query" value="人民网"
						onkeyup="handlekey()" /> <input class="submit s_btn"
						type="submit" name="submit" value="GO" id="submit"
						onclick="doSearch('')" /></td>
				</tr>
			</table>
		</div>
		<div style="height: 100px"></div>
		<div id="result"></div>

		<hr>

		<div id="paging" class="pagination"></div>
		<div style="height: 120px"></div>
		<div class="footer"
			style="position: fixed; bottom: 0px; width: 100%; background-color: #ffffff">
			<div id="footer" align="center" style="background-color: #ffffff">
				<font size="3"><a href="http://www.wyu.edu.cn/"
					class="hover{color:#ff0000;}">五邑大学</a>-<a
					href="http://www.wyu.edu.cn/">计算机学院</a></font>
				<p id="copyright">Copyright &copy; 2015|何嵩文|芒果搜索引擎</p>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="./js/search.js"></script>
</body>
</html>
