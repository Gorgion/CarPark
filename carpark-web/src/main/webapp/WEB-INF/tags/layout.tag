<%-- 
    Document   : layout
    Created on : 17.11.2014, 15:08:14
    Author     : Tomas Svoboda
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="master page layout" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>
<%@attribute name="title" required="true" %>
<%@attribute name="head" fragment="true" %>
<%@attribute name="content" fragment="true" required="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- any content can be specified here e.g.: --%>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
        <title><c:out value="${title}"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />      
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css"/>
        
        <jsp:invoke fragment="head"/>
                
    </head>
    <body>
        <div class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="head-container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a href="/pa165" class="navbar-brand"><span class="glyphicon glyphicon-leaf"></span> Car Park</a>
                </div>

                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a id="office" href="/pa165/auth/office"><fmt:message key="offices" /></a></li>
                        <li><a id="car" href="/pa165/auth/car"><fmt:message key="cars" /></a></li>
                        <li><a id="rental" href="/pa165/auth/user/1/rental"><fmt:message key="rentals" /></a></li>
                        <li><a id="user" href="/pa165/auth/user"><fmt:message key="users" /></a></li>
                        <li><a id="info" href="#"><fmt:message key="info" /></a></li>
                    </ul>
                </div>
                <div class="page-header">
                </div>
            </div>
        </div>

        <div class="container">
            <c:if test="${not empty message}">
                <div class="message"><c:out value="${message}"/></div>
            </c:if>
            <jsp:invoke fragment="content"/>
        </div>

        <footer>
            <div class="container">
            <p>&copy; Car Park 2014</p>
            <%--<p><a href="/pa165"><fmt:message key="navigation.backToTop"/></a></p> --%>           
            </div>
        </footer>


        <!-- Bootstrap core JavaScript
            ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/static/js/carpark.js'/>"></script>
        <script>
            var url = window.location.toString();

        if(url.indexOf("office") > -1) {
            $("li #office").addClass("active");
        }
            
        if(url.indexOf("car") > -1) 
            $("li #car").addClass("active");
          
        if(url.indexOf("rental") > -1) 
            $("li #rental").addClass("active");
            
        if(url.indexOf("user") > -1 && !(url.indexOf("rental") > -1)) 
            $("li #user").addClass("active");
        </script>
    </body>
</html>