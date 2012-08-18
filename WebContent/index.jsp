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

		<script src="js/util.js"></script>
		<script src="js/loader.js"></script>

		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=true"></script>
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
<!-- 				<form class="addr-form" action="AsyncService" method="post"> -->
				<div class="addr-form">
					<%
						String nameCommand = AppConstants.REQ_PARAM_NAME_COMMAND;
						String nameAddress = AppConstants.REQ_PARAM_NAME_ADDRESS;
						String valueLocate = AppConstants.REQ_PARAM_VALUE_LOCATE;
					%>
<!-- 					<input type="hidden" name="<%= nameCommand %>" id="<%= nameCommand %>" value="<%= valueLocate %>" /> -->
					<input class="addr-input round-17" id="<%= nameAddress %>" name="<%= nameAddress %>" type="autocomplete" placeholder="Where you're at? Fill your address please." />
					<a class="addr-submit round-14" href="AsyncService?<%= nameCommand %>=<%= valueLocate %>"></a>
				</div>
<!-- 					<input class="addr-submit round-14" type="submit" value="" /> -->
<!-- 				</form> -->
			</div>
			<div class="clear pad-b"></div>
			<aside id="sidebar" class="grid_4">
				<ul>
					<li>
						City: city.<br />
						Address: Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.
					</li>
				</ul>
			</aside>
			<div id="content" class="grid_8">
				<div id="map-container" class="frame">
					<div id="map-canvas"></div>
				</div>
			</div>
			<div class="clear"></div>
			<footer id="footer" class="grid_12 right-text">
				Footer
			</footer>
		</div>
	</body>
</html>