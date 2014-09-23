<%@ page session="false" contentType="text/html; charset=utf-8" 
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="description" content="springsecuritylogin"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/index.css"/>
    <title>springsecuritylogin</title>
  </head>
	<body onload='document.f.j_username.focus();' id="body">
	<br/>
	<c:if test="${param.loginerror eq 'true'}">
	 Login failed.
	</c:if>
	${user}
	<form name="f" action="${ctx}/j_spring_security_check" method="post">
	 <table>
	    <tr><td>Username:</td><td><input type="text" name="j_username" value=""/></td></tr>
	    <tr><td>Password:</td><td><input type="password" name="j_password"/></td></tr>
	    <tr><td colspan="2"><input name="submit" type="submit" value="Login"/></td></tr>
	  </table>
	</form>
	</body>
</html>