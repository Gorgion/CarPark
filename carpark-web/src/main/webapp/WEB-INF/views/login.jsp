<%-- 
    Document   : login
    Created on : 13.12.2014, 23:18:43
    Author     : Tomas Svoboda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="login.title"/>
<custom:login title="${title}">    
    <jsp:attribute name="content">
        <c:if test="${not empty error}" >
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${error}" />
            </div>
        </c:if>
        <c:if test="${not empty msg}" >
            <div class="alert alert-info alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${msg}" />
            </div>
        </c:if>

        <form name='loginForm' action="<c:url value="/login"/>" method='POST' role="form" class="form-horizontal">
            <div class="form-group has-feedback">   
                <div class="">                                
                    <input type='test' id='username' name='username' class="form-control" autofocus="true" placeholder="Username"/>
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
            </div>
            <div class="form-group has-feedback">
                <div class="">
                    <input type='password' id='password' name='password' class="form-control" placeholder="********"/>
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
            </div>            
            <div class="form-group">
                <div>                    
                    <button type="submit" class="btn btn-primary" ><fmt:message key="signin" /></button>
                </div>                
            </div>                        
        </form>
    </jsp:attribute>
</custom:login>
