<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName != null ?resume.fullName: "" }</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form id="resume" method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="section" items="<%=SectionType.values()%>">
            <dl>
                <dt>${section.title}</dt>
                <c:choose>
                    <c:when test="${section.equals(SectionType.PERSONAL) || section.equals(SectionType.OBJECTIVE)}">
                        <dd>
                            <input type="text" name="${section.name()}" size="30" value="${resume.getSection(section)}"><br>
                        </dd>
                    </c:when>
                    <c:when test="${section.equals(SectionType.ACHIEVEMENT) || section.equals(SectionType.QUALIFICATIONS)}">
                        <dd>
                            <textarea rows="3" cols="40" name="${section.name()}"><c:forEach var="element"
                                                                                             items="${resume.getSection(section).allText}">${element}</c:forEach></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${section.equals(SectionType.EDUCATION) || section.equals(SectionType.EXPERIENCE)}">

                        <dd>
                            <input type="text" name="${section.name()}" size="30" value="${}">
                        </dd>

                        <br>
                        <c:forEach var="organization" items="${resume.getSection(section).organizations}">
                            <jsp:useBean id="organization" class="com.urise.webapp.model.Organization"/>
                            <dd>
                                <input type="text" name="${section.name()}" size="30" value="${organization.homePage.name}">
                            </dd>
<%--                            <c:forEach var="position" items="${organization.positions}">--%>
<%--                                <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>--%>
<%--                                <table>--%>
<%--                                    <tr>--%>
<%--                                        <td><%=position.getStartDate().format(Position.FORMAT) + "-" + position.getEndDate().format(Position.FORMAT)%>--%>
<%--                                        </td>--%>
<%--                                        <td><strong>${position.title}</strong></td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>${position.description}</td>--%>
<%--                                    </tr>--%>
<%--                                </table>--%>
<%--                                <br>--%>
<%--                            </c:forEach>--%>
                        </c:forEach>


                    </c:when>
                </c:choose>
                    <%--                <dd><input type="text" name="${section.name()}" size="30" value="${resume.getSection(section)}"><br>--%>
                    <%--                </dd>--%>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
