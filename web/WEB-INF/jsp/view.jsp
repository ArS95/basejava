<%@ page import="com.urise.webapp.util.HtmlUtil" %>
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
        <%=HtmlUtil.toHtmlContact(contactEntry.getKey(), contactEntry.getValue())%><br>
    </c:forEach></dl>
    <dl><c:forEach var="sectionEntry" items="${resume.allSections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <%=HtmlUtil.toHtmlSection(sectionEntry.getKey(), sectionEntry.getValue())%><br>
    </c:forEach></dl>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
