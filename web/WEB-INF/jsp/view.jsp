<%@ page import="com.urise.webapp.model.Organization.Position" %>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <dl>
        <dt><strong>Ф.И.О</strong></dt>
        <dd>
            <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a>
            </h2>
        </dd>
    </dl>
    <hr>
    <h1>Контакты</h1>
    <dl><c:forEach var="contactEntry" items="${resume.allContacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <dt>
            <strong> ${contactEntry.key.title} </strong>
        </dt>
        <dd>
            <a href="${contactEntry.value}">${contactEntry.getValue()}</a>
        </dd>
        <br>
        <br>
    </c:forEach></dl>
    <dl><c:forEach var="sectionEntry" items="${resume.allSections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <dt>
            <h3>${sectionEntry.key.title}</h3>
        </dt>
        <c:choose>
            <c:when test="${sectionEntry.key.equals(SectionType.PERSONAL) || sectionEntry.key.equals(SectionType.OBJECTIVE)}">
                <br>
                <dd>
                    <ul>
                        <li>${sectionEntry.value.content}</li>
                    </ul>
                </dd>
                <br>
            </c:when>
            <c:when test="${sectionEntry.key.equals(SectionType.ACHIEVEMENT) || sectionEntry.key.equals(SectionType.QUALIFICATIONS)}">
                <br>
                <dd>
                    <ul>
                        <c:forEach var="text" items="${sectionEntry.value.allText}">
                            <li>${text}</li>
                        </c:forEach>
                    </ul>
                </dd>
                <br>
            </c:when>
            <c:when test="${sectionEntry.key.equals(SectionType.EXPERIENCE) || sectionEntry.key.equals(SectionType.EDUCATION)}">
                <br>
                <c:forEach var="organization" items="${sectionEntry.value.organizations}">
                    <jsp:useBean id="organization" class="com.urise.webapp.model.Organization"/>
                    <h4>
                        <a href="${organization.homePage.url}">${organization.homePage.name}</a>
                    </h4>
                    <c:forEach var="position" items="${organization.positions}">
                        <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                        <table>
                            <tr>
                                <td><%=position.getStartDate().format(Position.FORMAT) + "-" + position.getEndDate().format(Position.FORMAT)%>
                                </td>
                                <td><strong>${position.title}</strong></td>
                            </tr>
                            <tr>
                                <td>${position.description}</td>
                            </tr>
                        </table>
                        <br>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach></dl>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
