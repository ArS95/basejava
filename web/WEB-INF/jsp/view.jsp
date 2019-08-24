<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.SimpleTextSection" %>
<%@ page import="com.urise.webapp.model.MarkedTextSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
    <dl><c:forEach var="contactEntry" items="${resume.allContacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <dt>
            <h2> ${contactEntry.key.title} </h2>
        </dt>
        <dd>
            <a href="${contactEntry.value}">${contactEntry.value}</a>
        </dd><br>
        <%--        <%=HtmlUtil.toHtmlContact(contactEntry.getKey(), contactEntry.getValue())%>--%>
    </c:forEach></dl>
    <dl><c:forEach var="sectionEntry" items="${resume.allSections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <c:choose>
            <c:when test="${sectionEntry.key.equals(SectionType.PERSONAL) || sectionEntry.key.equals(SectionType.OBJECTIVE)}">
                <c:if test="${ sectionEntry.key != null && sectionEntry.value != null}">
                    <dt>
                        <h2>${sectionEntry.key.title}</h2>
                    </dt>
                    <br>
                    <dd>
                        <ul>
                            <li>${sectionEntry.value}</li>
                        </ul>
                    </dd>
                    <br>
                </c:if>
                <%--                <%=HtmlUtil.toHtmlSimpleTextSection(sectionEntry.getKey(), (SimpleTextSection) sectionEntry.getValue())%>--%>
            </c:when>
            <%--            <c:when test="${sectionEntry.key.equals(SectionType.ACHIEVEMENT) || sectionEntry.key.equals(SectionType.QUALIFICATIONS)}">--%>
            <%--                <jsp:useBean id="markedTextSection" type="com.urise.webapp.model.MarkedTextSection"/>--%>
            <%--                <dt>--%>
            <%--                    <h2>${sectionEntry.key}</h2><--%>
            <%--                </dt><br>--%>
            <%--                <dd>--%>
            <%--                    <ul>--%>
            <%--                        <c:forEach var="text" items="${markedTextSection.allText}">--%>
            <%--                            <li>${text}</li>--%>
            <%--                        </c:forEach>--%>
            <%--                    </ul>--%>
            <%--                </dd>--%>

            <%--&lt;%&ndash;                <%=HtmlUtil.toHtmlMarkedTextSection(sectionEntry.getKey(), (MarkedTextSection) sectionEntry.getValue())%>&ndash;%&gt;--%>
            <%--            </c:when>--%>
            <c:when test="${sectionEntry.key.equals(SectionType.EXPERIENCE) || sectionEntry.key.equals(SectionType.EDUCATION)}">
                <%--                <%=HtmlUtil.toHtmlOrganizationSection(sectionEntry.getKey(), (OrganizationSection) sectionEntry.getValue())%>--%>
            </c:when>
        </c:choose>
        <%--        <%=HtmlUtil.toHtmlSection(sectionEntry.getKey(), sectionEntry.getValue())%>--%>
    </c:forEach></dl>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
