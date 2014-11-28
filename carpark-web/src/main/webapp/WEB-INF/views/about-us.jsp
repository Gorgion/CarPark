<%-- 
    Document   : about-us
    Created on : 21.11.2014, 15:13:50
    Author     : Karolina Burska
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:message var="title" key="about-us.title"/>
<custom:layout title="${title}">
    <jsp:attribute name="content">
        <b>Tomáš Svoboda</b><br/>
        E-mail: <a href="mailto:396475@mail.muni.cz">396475@mail.muni.cz</a><br/>
        <br/>
        <b>Jiří Dočkal</b><br/>
        E-mail: <a href="mailto:374312@mail.muni.cz">374312@mail.muni.cz</a><br/>
        <br/>
        <b>Tomáš Vašíček</b><br/>
        E-mail: <a href="mailto:432056@mail.muni.cz">432056@mail.muni.cz</a><br/>
        <br/>
        <b>Karolína Burská</b><br/>
        E-mail: <a href="mailto:396296@mail.muni.cz">396296@mail.muni.cz</a><br/>
        <br/>
    </jsp:attribute>
</custom:layout>
