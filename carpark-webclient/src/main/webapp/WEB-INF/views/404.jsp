<%-- 
    Document   : error
    Created on : 22.11.2014, 16:30:58
    Author     : Jiri Dockal
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<custom:layout title="404">
    <jsp:attribute name="content">
        <div class="alert alert-danger">        
            404
        </div>
        <button type="button" class="btn btn-warning" onclick="history.back()">Back</button>
    </jsp:attribute>
</custom:layout>
 
