<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.Organization" %>
<%@ page import="com.urise.webapp.model.Organization.Position" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="com.urise.webapp.model.Resume" scope="request"/>
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
                <dt><strong>${section.title}</strong></dt>
                <c:choose>
                    <c:when test="${section.equals(SectionType.PERSONAL) || section.equals(SectionType.OBJECTIVE)}">
                        <dd>
                            <input type="text" name="${section.name()}" size="30" value="${resume.getSection(section)}"><br>
                        </dd>
                    </c:when>
                    <c:when test="${section.equals(SectionType.ACHIEVEMENT) || section.equals(SectionType.QUALIFICATIONS)}">
                        <dd>
                            <textarea rows="3" cols="40" name="${section.name()}"
                            ><c:forEach var="element"
                                        items="${resume.getSection(section).allText}">${element}</c:forEach></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${section.equals(SectionType.EDUCATION) || section.equals(SectionType.EXPERIENCE)}">
                        <br>
                        <br>
                        <c:set var="org" value="${section.name()}"/>
                        <c:if test="${resume.getSection(section) != null}">

                            <dt>Название организации:</dt>
                            <dd><input type="text" name="orgName${org}0" size="30"></dd>
                            <br>
                            <dt>Сайт организации:</dt>
                            <dd><input type="text" name="orgUrl${org}0" size="30"></dd>
                            <br>
                            <br>

                            <div style="margin-left: 30px">
                                <input type="hidden" name="sizePos${org}0" value="0">
                                <dt>Начальная дата:</dt>
                                <dd><input type="text" name="startDate${org}00" size="30" placeholder="MM/yyyy">
                                </dd>
                                <br>
                                <dt>Конечная дата:</dt>
                                <dd><input type="text" name="endDate${org}00" size="30" placeholder="MM/yyyy">
                                </dd>
                                <br>
                                <dt>Должность</dt>
                                <dd><input type="text" name="title${org}00" size="30"></dd>
                                <br>
                                <dt>Описание</dt>
                                <dd><textarea rows="3" cols="32" name="description${org}00"></textarea></dd>
                                <br>
                                <br>
                            </div>

                            <c:forEach var="organization" items="${resume.getSection(section).organizations}"
                                       varStatus="orgCount" end="${resume.getSection(section).organizations.size()}">
                                <jsp:useBean id="organization" type="Organization"/>
                                <input type="hidden" name="sizeOrg${org}" value="${orgCount.end}">
                                <dt>Название организации:</dt>
                                <dd><input type="text" name="orgName${org}${orgCount.count}"
                                           value="${organization.homePage.name}"></dd>
                                <br>
                                <dt>Сайт организации:</dt>
                                <dd><input type="text" name="orgUrl${org}${orgCount.count}"
                                           value="${organization.homePage.url}">
                                </dd>
                                <br>
                                <br>

                                <div style="margin-left: 30px">
                                    <dt>Начальная дата:</dt>
                                    <dd><input type="text" name="startDate${org}${orgCount.count}0"
                                               placeholder="MM/yyyy">
                                    </dd>
                                    <br>
                                    <dt>Конечная дата:</dt>
                                    <dd><input type="text" name="endDate${org}${orgCount.count}0" placeholder="MM/yyyy">
                                    </dd>
                                    <br>
                                    <dt>Должность</dt>
                                    <dd><input type="text" name="title${org}${orgCount.count}0"></dd>
                                    <br>
                                    <dt>Описание</dt>
                                    <dd><textarea rows="3" cols="32"
                                                  name="description${org}${orgCount.count}0"></textarea>
                                    </dd>
                                    <br>
                                    <br>
                                </div>

                                <c:forEach var="position" items="${organization.positions}" varStatus="posCount"
                                           end="${organization.positions.size()}">
                                    <jsp:useBean id="position" type="Position"/>
                                    <input type="hidden" name="sizePos${org}${orgCount.count}" value="${posCount.end}">

                                    <div style="margin-left: 30px">
                                        <dt>Начальная дата:</dt>
                                        <dd><input type="text" name="startDate${org}${orgCount.count}${posCount.count}"
                                                   placeholder="MM/yyyy"
                                                   value="<%=position.getStartDate().format(DateUtil.FORMAT)%>">
                                        </dd>
                                        <br>
                                        <dt>Конечная дата:</dt>
                                        <dd><input type="text" name="endDate${org}${orgCount.count}${posCount.count}"
                                                   placeholder="MM/yyyy"
                                                   value="<%=position.getEndDate().format(DateUtil.FORMAT)%>">
                                        </dd>
                                        <br>
                                        <dt>Должность</dt>
                                        <dd><input type="text" name="title${org}${orgCount.count}${posCount.count}"
                                                   value="${position.title}"></dd>
                                        <br>
                                        <dt>Описание</dt>
                                        <dd><textarea rows="3" cols="32"
                                                      name="description${org}${orgCount.count}${posCount.count}"
                                        >${position.description}</textarea></dd>
                                        <br>
                                        <br>
                                    </div>

                                </c:forEach>
                            </c:forEach>
                        </c:if>
                        <c:if test="${resume.getSection(section) == null}">

                            <dt>Название организации:</dt>
                            <dd><input type="text" name="orgName${org}" size="30"></dd>
                            <br>
                            <dt>Сайт организации:</dt>
                            <dd><input type="text" name="orgUrl${org}" size="30"></dd>
                            <br>
                            <br>

                            <div style="margin-left: 30px">
                                <dt>Начальная дата:</dt>
                                <dd><input type="text" name="startDate${org}" size="30" placeholder="MM/yyyy">
                                </dd>
                                <br>
                                <dt>Конечная дата:</dt>
                                <dd><input type="text" name="endDate${org}" size="30" placeholder="MM/yyyy">
                                </dd>
                                <br>
                                <dt>Должность</dt>
                                <dd><input type="text" name="title${org}" size="30"></dd>
                                <br>
                                <dt>Описание</dt>
                                <dd><textarea rows="3" cols="32" name="description${org}"></textarea></dd>
                                <br>
                                <br>
                            </div>

                        </c:if>
                    </c:when>
                </c:choose>
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
<%--<%@ page import="com.urise.webapp.model.ContactType" %>--%>
<%--<%@ page import="com.urise.webapp.model.Organization" %>--%>
<%--<%@ page import="com.urise.webapp.model.Organization.Position" %>--%>
<%--<%@ page import="com.urise.webapp.model.SectionType" %>--%>
<%--<%@ page import="com.urise.webapp.util.DateUtil" %>--%>
<%--<%@ page import="com.urise.webapp.model.MarkedTextSection" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
<%--    <link rel="stylesheet" href="css/style.css">--%>
<%--    <jsp:useBean id="resume" class="com.urise.webapp.model.Resume" scope="request"/>--%>
<%--    <title>Резюме ${resume.fullName != null ?resume.fullName: "" }</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<jsp:include page="fragments/header.jsp"/>--%>
<%--<section>--%>
<%--    <form id="resume" method="post" action="resume" enctype="application/x-www-form-urlencoded">--%>
<%--        <input type="hidden" name="uuid" value="${resume.uuid}">--%>
<%--        <dl>--%>
<%--            <dt>Имя:</dt>--%>
<%--            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>--%>
<%--        </dl>--%>
<%--        <h3>Контакты:</h3>--%>
<%--        <c:forEach var="type" items="<%=ContactType.values()%>">--%>
<%--            <dl>--%>
<%--                <dt>${type.title}</dt>--%>
<%--                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>--%>
<%--            </dl>--%>
<%--        </c:forEach>--%>
<%--        <h3>Секции:</h3>--%>
<%--        <c:forEach var="type" items="<%=SectionType.values()%>">--%>
<%--            <c:set var="section" value="${resume.getSection(type)}"/>--%>
<%--            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>--%>
<%--            <dl>--%>
<%--                <dt><strong>${type.title}</strong></dt>--%>
<%--                <c:choose>--%>
<%--                    <c:when test="${section.equals(SectionType.PERSONAL) || section.equals(SectionType.OBJECTIVE)}">--%>
<%--                        <dd>--%>
<%--                            <input type="text" name="${section.name()}" size="30" value="${section}"><br>--%>
<%--                        </dd>--%>
<%--                    </c:when>--%>
<%--                    <c:when test="${section.equals(SectionType.ACHIEVEMENT) || section.equals(SectionType.QUALIFICATIONS)}">--%>
<%--                        <dd>--%>
<%--                                &lt;%&ndash;                            <textarea rows="3" cols="40" name="${section.name()}"&ndash;%&gt;--%>
<%--                                &lt;%&ndash;                            ><c:forEach var="element"&ndash;%&gt;--%>
<%--                                &lt;%&ndash;                                        items="${resume.getSection(section).allText}">${element}</c:forEach></textarea>&ndash;%&gt;--%>

<%--                            <textarea rows="3" cols="40" name="${section.name()}"--%>
<%--                            ><%=String.join("\n", ((MarkedTextSection) section).getAllText())%></textarea>--%>
<%--                        </dd>--%>
<%--                    </c:when>--%>
<%--                    <c:when test="${section.equals(SectionType.EDUCATION) || section.equals(SectionType.EXPERIENCE)}">--%>
<%--                        <br>--%>
<%--                        <br>--%>
<%--                        <c:set var="org" value="${section.name()}"/>--%>
<%--                        <c:if test="${section != null}">--%>

<%--                            <dt>Название организации:</dt>--%>
<%--                            <dd><input type="text" name="orgName${org}0" size="30"></dd>--%>
<%--                            <br>--%>
<%--                            <dt>Сайт организации:</dt>--%>
<%--                            <dd><input type="text" name="orgUrl${org}0" size="30"></dd>--%>
<%--                            <br>--%>
<%--                            <br>--%>

<%--                            <div style="margin-left: 30px">--%>
<%--                                <input type="hidden" name="sizePos${org}0" value="0">--%>
<%--                                <dt>Начальная дата:</dt>--%>
<%--                                <dd><input type="text" name="startDate${org}00" size="30" placeholder="MM/yyyy">--%>
<%--                                </dd>--%>
<%--                                <br>--%>
<%--                                <dt>Конечная дата:</dt>--%>
<%--                                <dd><input type="text" name="endDate${org}00" size="30" placeholder="MM/yyyy">--%>
<%--                                </dd>--%>
<%--                                <br>--%>
<%--                                <dt>Должность</dt>--%>
<%--                                <dd><input type="text" name="title${org}00" size="30"></dd>--%>
<%--                                <br>--%>
<%--                                <dt>Описание</dt>--%>
<%--                                <dd><textarea rows="3" cols="32" name="description${org}00"></textarea></dd>--%>
<%--                                <br>--%>
<%--                                <br>--%>
<%--                            </div>--%>

<%--                            <c:forEach var="organization" items="${section.organizations}"--%>
<%--                                       varStatus="orgCount" end="${section.organizations.size()}">--%>
<%--                                <jsp:useBean id="organization" type="Organization"/>--%>
<%--                                <input type="hidden" name="sizeOrg${org}" value="${orgCount.end}">--%>
<%--                                <dt>Название организации:</dt>--%>
<%--                                <dd><input type="text" name="orgName${org}${orgCount.count}"--%>
<%--                                           value="${organization.homePage.name}"></dd>--%>
<%--                                <br>--%>
<%--                                <dt>Сайт организации:</dt>--%>
<%--                                <dd><input type="text" name="orgUrl${org}${orgCount.count}"--%>
<%--                                           value="${organization.homePage.url}">--%>
<%--                                </dd>--%>
<%--                                <br>--%>
<%--                                <br>--%>

<%--                                <div style="margin-left: 30px">--%>
<%--                                    <dt>Начальная дата:</dt>--%>
<%--                                    <dd><input type="text" name="startDate${org}${orgCount.count}0"--%>
<%--                                               placeholder="MM/yyyy">--%>
<%--                                    </dd>--%>
<%--                                    <br>--%>
<%--                                    <dt>Конечная дата:</dt>--%>
<%--                                    <dd><input type="text" name="endDate${org}${orgCount.count}0" placeholder="MM/yyyy">--%>
<%--                                    </dd>--%>
<%--                                    <br>--%>
<%--                                    <dt>Должность</dt>--%>
<%--                                    <dd><input type="text" name="title${org}${orgCount.count}0"></dd>--%>
<%--                                    <br>--%>
<%--                                    <dt>Описание</dt>--%>
<%--                                    <dd><textarea rows="3" cols="32"--%>
<%--                                                  name="description${org}${orgCount.count}0"></textarea>--%>
<%--                                    </dd>--%>
<%--                                    <br>--%>
<%--                                    <br>--%>
<%--                                </div>--%>

<%--                                <c:forEach var="position" items="${organization.positions}" varStatus="posCount"--%>
<%--                                           end="${organization.positions.size()}">--%>
<%--                                    <jsp:useBean id="position" type="Position"/>--%>
<%--                                    <input type="hidden" name="sizePos${org}${orgCount.count}" value="${posCount.end}">--%>

<%--                                    <div style="margin-left: 30px">--%>
<%--                                        <dt>Начальная дата:</dt>--%>
<%--                                        <dd><input type="text" name="startDate${org}${orgCount.count}${posCount.count}"--%>
<%--                                                   placeholder="MM/yyyy"--%>
<%--                                                   value="<%=position.getStartDate().format(DateUtil.FORMAT)%>">--%>
<%--                                        </dd>--%>
<%--                                        <br>--%>
<%--                                        <dt>Конечная дата:</dt>--%>
<%--                                        <dd><input type="text" name="endDate${org}${orgCount.count}${posCount.count}"--%>
<%--                                                   placeholder="MM/yyyy"--%>
<%--                                                   value="<%=position.getEndDate().format(DateUtil.FORMAT)%>">--%>
<%--                                        </dd>--%>
<%--                                        <br>--%>
<%--                                        <dt>Должность</dt>--%>
<%--                                        <dd><input type="text" name="title${org}${orgCount.count}${posCount.count}"--%>
<%--                                                   value="${position.title}"></dd>--%>
<%--                                        <br>--%>
<%--                                        <dt>Описание</dt>--%>
<%--                                        <dd><textarea rows="3" cols="32"--%>
<%--                                                      name="description${org}${orgCount.count}${posCount.count}"--%>
<%--                                        >${position.description}</textarea></dd>--%>
<%--                                        <br>--%>
<%--                                        <br>--%>
<%--                                    </div>--%>

<%--                                </c:forEach>--%>
<%--                            </c:forEach>--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${resume.getSection(section) == null}">--%>

<%--                            <dt>Название организации:</dt>--%>
<%--                            <dd><input type="text" name="orgName${org}" size="30"></dd>--%>
<%--                            <br>--%>
<%--                            <dt>Сайт организации:</dt>--%>
<%--                            <dd><input type="text" name="orgUrl${org}" size="30"></dd>--%>
<%--                            <br>--%>
<%--                            <br>--%>

<%--                            <div style="margin-left: 30px">--%>
<%--                                <dt>Начальная дата:</dt>--%>
<%--                                <dd><input type="text" name="startDate${org}" size="30" placeholder="MM/yyyy">--%>
<%--                                </dd>--%>
<%--                                <br>--%>
<%--                                <dt>Конечная дата:</dt>--%>
<%--                                <dd><input type="text" name="endDate${org}" size="30" placeholder="MM/yyyy">--%>
<%--                                </dd>--%>
<%--                                <br>--%>
<%--                                <dt>Должность</dt>--%>
<%--                                <dd><input type="text" name="title${org}" size="30"></dd>--%>
<%--                                <br>--%>
<%--                                <dt>Описание</dt>--%>
<%--                                <dd><textarea rows="3" cols="32" name="description${org}"></textarea></dd>--%>
<%--                                <br>--%>
<%--                                <br>--%>
<%--                            </div>--%>

<%--                        </c:if>--%>
<%--                    </c:when>--%>
<%--                </c:choose>--%>
<%--            </dl>--%>
<%--        </c:forEach>--%>
<%--        <hr>--%>
<%--        <button type="submit">Сохранить</button>--%>
<%--        <button onclick="window.history.back()">Отменить</button>--%>
<%--    </form>--%>
<%--</section>--%>
<%--<jsp:include page="fragments/footer.jsp"/>--%>
<%--</body>--%>
<%--</html>--%>
