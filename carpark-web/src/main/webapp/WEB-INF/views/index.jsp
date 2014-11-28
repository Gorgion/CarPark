<%-- 
    Document   : index
    Created on : 15.11.2014, 15:13:50
    Author     : Tomas Svoboda
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:message var="title" key="index.title"/>
<custom:layout title="${title}">
    <jsp:attribute name="content">
        <fmt:message key="aboutus" />
        <br/>
        <fmt:message key="contact-us" />
        <br/>
        <a href="/pa165/auth/about-us" class="main-contact"><fmt:message key="contact" /></a>
    </jsp:attribute>
</custom:layout>
