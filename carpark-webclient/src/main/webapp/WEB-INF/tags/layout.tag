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
<%@attribute name="ajaxScript" fragment="true" required="false" %>


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
        <link rel="icon" href="${pageContext.request.contextPath}/static/img/Icon.ico">
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
                    <a href="/pa165/client" class="navbar-brand"><span class="glyphicon glyphicon-leaf"></span> Car Park</a>
                </div>

                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a id="office" href="/pa165/client/office">Offices</a></li>                        
                        <li><a id="user" href="/pa165/client/user">Users</a></li>
                    </ul>
                </div>
                <div class="page-header visible-lg visible-md">
                </div>
            </div>
        </div>

        <div id="mainContent" class="container">
            <c:if test="${not empty message}">
                <div class="message"><c:out value="${message}"/></div>
            </c:if>
            <jsp:invoke fragment="content"/>
        </div>

        <footer>
            <p>&copy; Car Park 2014</p>        
        </footer>

        <div class="modal"></div>

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

            if (url.indexOf("user") > -1)
                $("li #user").addClass("active");
            
            if (url.indexOf("about-us") > -1)
                $("li #about-us").addClass("active");
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
        <jsp:invoke fragment="ajaxScript"/>
        <script>
            $body = $("body");

$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
     ajaxStop: function() { $body.removeClass("loading"); }    
});
        </script>
    </body>
</html>