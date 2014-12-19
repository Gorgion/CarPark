<%-- 
    Document   : layout
    Created on : 17.11.2014, 15:08:14
    Author     : Tomas Svoboda
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%@tag description="master page layout" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>
<%@attribute name="title" required="true" %>
<%@attribute name="head" fragment="true" %>
<%@attribute name="content" fragment="true" required="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- any content can be specified here e.g.: --%>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
        <title><c:out value="${title}"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />      
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/static/img/Icon.ico">
        <jsp:invoke fragment="head"/>

    </head>
    <body>
        <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="post"></form>
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
                        <li><a id="user" href="/pa165/auth/user"><fmt:message key="users" /></a></li>
                            <sec:authorize access="!hasRole('ROLE_BUILT_IN_ADMIN')">
                                <sec:authorize access="isAuthenticated()" var="isAuth" />
                                <c:if test="${isAuth}">        
                                    <sec:authentication property="principal.id" var="principalId" />                                
                                <li><a id="myRentals" href="/pa165/auth/user/${principalId}/rental"><fmt:message key="myRentals" /></a></li>
                                </c:if>
                            </sec:authorize>
                        <li><a id="about-us" href="/pa165/auth/about-us"><fmt:message key="about-us" /></a></li>                        
                    </ul>
                    <sec:authorize access="isAuthenticated()" var="isAuth" />

                    <c:if test="${isAuth}">
                        <ul class="nav navbar-nav navbar-right">
                            <sec:authorize access="hasRole('ROLE_BUILT_IN_ADMIN')">
                                <li><a href="#"><sec:authentication property="principal.username"/></a></li>
                                </sec:authorize>
                                <sec:authorize access="!hasRole('ROLE_BUILT_IN_ADMIN')">
                                    <sec:authentication property="principal.id" var="principalId" />
                                <li><a href="<c:url value="/auth/user/${principalId}/profile"/>"><sec:authentication property="principal.username"/> <span class="caret"></span></a></li>
                                    </sec:authorize>
                            <li><a href="javascript:logout()"><fmt:message key="signout" /></a></li>
                        </ul>
                    </c:if>
                    <c:if test="${!isAuth}">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="/login"><fmt:message key="signin" /></a></li>
                        </ul>
                    </c:if>
                </div>
                <div class="page-header visible-lg visible-md"></div>
            </div>
        </div>

        <div class="container">
            <c:if test="${not empty message}">
                <div class="message"><c:out value="${message}"/></div>
            </c:if>
            <jsp:invoke fragment="content"/>
        </div>

        <footer>
            <p>&copy; Car Park 2014</p>     
        </footer>


        <!-- Bootstrap core JavaScript
            ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/static/js/carpark.js'/>"></script>
        <script>
            var url = window.location.toString();

            if (url.indexOf("office") > -1) {
                $("li #office").addClass("active");
            }

            if (url.indexOf("car") > -1)
                $("li #car").addClass("active");

            if (url.indexOf("user") > -1 && !(url.indexOf("rental") > -1))
                $("li #user").addClass("active");
            
            if (url.indexOf("about-us") > -1)
                $("li #about-us").addClass("active");
            
            if (url.indexOf("rental") > -1)
                $("li #myRentals").addClass("active");
        </script>
        <script type="text/javascript" charset="utf-8">
            function logout() {
                document.getElementById("logoutForm").submit();
            }
            ;
        </script>
        <script>
            $(document).ready(function () {
                $('.page-header').removeClass('visible-md');
                $('.page-header').removeClass('visible-lg');

                var hidden = false;

                $(window).on('load', function ()
                {
                    if ($(window).width() <= 992)
                    {
                        hidden = true;
                    }
                });

                $(window).on('load resize', function ()
                {
                    var width = $(window).width();

                    var div = $('.page-header');

                    if (width <= 992 && !hidden) {
                        div.slideUp('slow');
                        div.addClass('.visible-lg .visible-md');
                    } else {
                        div.slideDown('slow');
                        div.removeClass('.visible-lg .visible-md');
                        hidden = false;
                    }

                });
            });
        </script>
    </body>
</html>