<%@ page session="false" contentType="text/html; charset=utf-8" 
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"
%><%@ page import ="java.util.*, java.text.SimpleDateFormat,java.net.*"
%><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	  <meta name="description" content="springsecuritylogin"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/index.css"/>
    <title>springsecuritylogin</title>
  </head>
<body>
  <c:if test="${empty user}">
      <form name="f" action="${ctx}/j_spring_security_check" method="post">
        <div>
          <div class="username">
            Username<br/>
            <input class="logininput" type="text" name="j_username" value=""/> 
          </div>
          <div class="password">
            Password<br/>
            <input class="logininput" type="password" name="j_password"/>
            <input name="submit" type="submit" value="Login"/>
          </div>
         </div>
      </form>
      You are not loggedin: [<a href="${ctx}/signup/">Signup</a>]<br/>
  </c:if>
  <c:if test="${not empty user}">
    <p>Welcome ${user.username} [<a href="${ctx}/j_spring_security_logout">Logout</a>]</p>
    User roles: ${user.authorities}
  </c:if>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		This content will only be visible to users who have
		the "supervisor" authority in their list of <tt>GrantedAuthority</tt>s.
	</sec:authorize>
</body>
</html>