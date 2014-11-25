<%-- 
    Document   : rental-state-form
    Created on : 20.11.2014, 18:43:32
    Author     : Tomas Svoboda
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" session="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="rental.form.state.title"/>




<custom:layout title="${title}">
    <jsp:attribute name="content">
        <c:if test="${msg}">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${msg}" />
            </div>
        </c:if>
        <c:if test="${error}">
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                    &times;
                </button>
                <fmt:message key="${error}"/>
            </div>
        </c:if>


        <c:url var="changeStateUrl" value="/auth/user/${userId}/rental/${id}/edit" />
        <form:form action="${changeStateUrl}" method="POST" modelAttribute="rentalState">
            <form:errors cssClass="alert alert-danger alert-dismissable" element="dir" path="*"/>
                <c:forEach items="${states}" var="s">
                    <div class="radio">
                        <label>
                            <c:if test="${checked == s}">
                                <input type="radio" path="state" name="state" value="${s}" checked="" />
                            </c:if>
                            <c:if test="${checked != s}">
                                <form:radiobutton path="state" value="${s}" />
                            </c:if>
                            <fmt:message key="rental.states.${s}" />
                        </label>
                    </div>
                </c:forEach>

                <button type="submit" class="btn btn-success"><fmt:message key="btn.save" /></button>
        </form:form>
    </jsp:attribute>
</custom:layout>
