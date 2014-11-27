<%-- 
    Document   : error
    Created on : 22.11.2014, 16:30:58
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<custom:layout title="${error.code}">
    <jsp:attribute name="content">
        <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="error.xxx.body" /><fmt:message key="${error.msg}" />
                <button type="button" class="btn btn-warning" onclick="history.back()"><fmt:message key="btn.back" /></button>
            </div>
    </jsp:attribute>
</custom:layout>
 
