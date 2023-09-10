<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<tiles:insertAttribute name="loadFile"/>
	<title>SpringWeb</title>
</head>
<body>
	<tiles:insertAttribute name="header"/>
	<tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="footer"/>
</body>
</html>