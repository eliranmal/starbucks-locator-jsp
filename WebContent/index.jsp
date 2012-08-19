<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>

<%@ page import="com.starbucks.locator.util.*" %>


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

		<title>Starbucks Locator</title>
		
		<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />

		<link href="css/960_12_col.css" type="text/css" rel="stylesheet" media="screen" />
		<link href="css/styles.css" type="text/css" rel="stylesheet" media="screen" />

		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
	</head>
	<body>
		<div id="wrapper" class="container_12">
			<header id="header">
				<div class="grid_2">
					<a id="logo" href="http://www.ihatestarbucks.com/">
						<img src="images/logo.png" width="154" height="140" alt="starbucks locator logo" />
					</a>
				</div>
				<div class="grid_10">
					<hgroup>
						<h1 id="title" class="center-text text-shadow-1">Where's My Coffee ?!</h1>
						<h2 id="tagline" class="center-text text-shadow-1">
							a starbucks coffee store locator
						</h2>
					</hgroup>
				</div>
			</header>
			<div class="clear"></div>
			<div class="grid_12">
				<div class="addr-form">
					<input id="addr-input" class="addr-input round-17" type="autocomplete" placeholder="Where you're at? Fill your address please." />
					<a id="addr-submit" class="addr-submit round-14" href="#"></a>
				</div>
			</div>
			<div class="clear pad-b"></div>
			<div id="sidebar" class="grid_4">
			</div>
			<div id="content" class="grid_8">
				<div id="map-container" class="frame">
					<div id="map-canvas"></div>
				</div>
			</div>
			<div class="clear"></div>
			<footer id="footer" class="grid_12 right-text">
				powered by me
			</footer>
		</div>

		
		<%
			String nameCommand = AppConstants.REQ_PARAM_NAME_COMMAND;
			String nameLat = AppConstants.REQ_PARAM_NAME_LAT;
			String nameLng = AppConstants.REQ_PARAM_NAME_LNG;
			String valueLocate = AppConstants.REQ_PARAM_VALUE_LOCATE;
		%>
		<script src="js/util.js"></script>
		<script src="js/loader.js"></script>
		<script>
			var nameCommand = '<%= nameCommand %>';
			var nameLat = '<%= nameLat %>';
			var nameLng = '<%= nameLng %>';
			var valueLocate = '<%= valueLocate %>';
			Starbucks.constants.REQ_PARAM_NAME_COMMAND = nameCommand;
			Starbucks.constants.REQ_PARAM_NAME_LAT = nameLat;
			Starbucks.constants.REQ_PARAM_NAME_LNG = nameLng;
			Starbucks.constants.REQ_PARAM_VALUE_LOCATE = valueLocate;
		</script>
		
	</body>
</html>