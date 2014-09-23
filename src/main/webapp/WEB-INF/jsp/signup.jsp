<%@ page session="false" contentType="text/html; charset=utf-8" 
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"
%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="description" content="springsecuritylogin"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/index.css"/>
    <title>springsecuritylogin</title>
  </head>
<body id="body">

<h2>Signup</h2>

<div id="info">${info}</div>
<form:form action="" method="post" modelAttribute="user">
  <table>
    <tr>
      <td>Username: </td><td><form:input path="username"/><form:errors path="username" /></td>
    </tr>
    <tr>
      <td>Password: </td><td><form:password path="password"/><form:errors path="password" /></td>
    </tr>  
    <tr>
      <td>Email: </td><td><form:input path="email"/><form:errors path="email" /></td>
    </tr>
  </table>
  <input type="submit" value="Signup"/>
</form:form>

</body>
</html>