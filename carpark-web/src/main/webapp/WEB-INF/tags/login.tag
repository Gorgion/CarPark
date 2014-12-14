<%-- 
    Document   : login
    Created on : 14.12.2014, 15:08:15
    Author     : Karolina Burska
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
    
    <body class="login-body">
        <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="post"></form>
        <div class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="head-container">
                <div class="navbar-header">
                    <a href="/pa165" class="navbar-brand">Car Park</a>
                </div>
                <div class="collapse navbar-collapse"></div>  
            </div>
        </div>
        <c:if test="${not empty message}">
                <div class="message"><c:out value="${message}"/></div>
        </c:if>
        <div class="container login">  
            <jsp:invoke fragment="content"/>
        </div>

        <!-- Bootstrap core JavaScript
            ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/static/js/carpark.js'/>"></script>
        <script type="text/javascript" charset="utf-8">
            function logout() {
                document.getElementById("logoutForm").submit();
            }
            ;
        
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