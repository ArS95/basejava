package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");

        String fullName = request.getParameter("fullName");
        if (uuid != null && !uuid.equals("")) {
            Resume resume = storage.get(uuid);
            resume.setFullName(fullName);
            addContactAndSection(resume, request);
            storage.update(resume);
        } else {
            Resume resume = new Resume(fullName);
            addContactAndSection(resume, request);
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                resume = new Resume();
                break;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    private void addContactAndSection(Resume resume, HttpServletRequest request) {
        addContact(resume, request);
        addSection(resume, request);
    }

    private void addContact(Resume resume, HttpServletRequest request) {
        resume.clearAllContacts();
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().equals("")) {
                resume.addContact(type, value.trim());
            }
        }
    }

    private void addSection(Resume resume, HttpServletRequest request) {
        resume.clearAllSections();
        for (SectionType type : SectionType.values()) {
            String sectionName = type.name();
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(sectionName);
                    if (value != null && !value.equals("")) {
                        SimpleTextSection simpleTextSection = new SimpleTextSection(value);
                        resume.addSection(type, simpleTextSection);
                    }
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENT:
                    String val = request.getParameter(sectionName);
                    if (val != null && !val.trim().equals("")) {
                        MarkedTextSection markedTextSection = new MarkedTextSection(val.split("\\n"));
                        resume.addSection(type, markedTextSection);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    String sizeStrOrg = request.getParameter("sizeOrg" + sectionName);
                    if (sizeStrOrg != null) {
                        int sizeOrg = Integer.parseInt(sizeStrOrg);
                        OrganizationSection organizationSection = new OrganizationSection();
                        for (int i = 0; i < sizeOrg + 1; i++) {

                            String orgName = request.getParameter("orgName" + sectionName + i);
                            if (orgName != null && !orgName.trim().equals("")) {
                                String sizeStrPos = request.getParameter("sizePos" + sectionName + i);
                                if (sizeStrPos != null) {
                                    int sizePos = Integer.parseInt(sizeStrOrg);
                                    List<Organization.Position> positionList = new ArrayList<>();
                                    for (int j = 0; j < sizePos + 1; j++) {

                                        String pfx = sectionName + i + j;
                                        String title = request.getParameter("title" + pfx);
                                        if (title != null && !title.trim().equals("")) {
                                            Organization.Position position;
                                            String endStrDate = request.getParameter("endDate" + pfx);
                                            String startStrDate = getTrimmedParameter(request, "startDate" + pfx);
                                            String description = getTrimmedParameter(request, "description" + pfx);
                                            position = new Organization.Position(DateUtil.of(startStrDate), endStrDate == null ? DateUtil.NOW : DateUtil.of(endStrDate.trim()), title.trim(), description);
                                            positionList.add(position);
                                        }
                                    }
                                    String orgUrl = getTrimmedParameter(request, "orgUrl" + sectionName + i);
                                    Organization organization = new Organization(new Link(orgName.trim(), orgUrl), positionList);
                                    organizationSection.addOrganization(organization);
                                }
                            }
                        }
                        if (organizationSection.getOrganizations().size() > 0) {
                            resume.addSection(type, organizationSection);
                        }
                    } else {
                        String orgName = request.getParameter("orgName" + sectionName);
                        if (orgName != null && !orgName.trim().equals("")) {
                            String orgUrl = getTrimmedParameter(request, "orgUrl" + sectionName);
                            String title = request.getParameter("title" + sectionName);
                            List<Organization.Position> positionList = new ArrayList<>();
                            if (title != null && !title.trim().equals("")) {
                                String endStrDate = request.getParameter("endDate" + sectionName);
                                String startStrDate = getTrimmedParameter(request, "startDate" + sectionName);
                                String description = getTrimmedParameter(request, "description" + sectionName);
                                positionList.add(new Organization.Position(DateUtil.of(startStrDate), endStrDate == null ? DateUtil.NOW : DateUtil.of(endStrDate.trim()), title.trim(), description));
                            }
                            Organization organization = new Organization(new Link(orgName.trim(), orgUrl), positionList);
                            OrganizationSection section = new OrganizationSection(organization);
                            resume.addSection(type, section);
                        }
                    }
                    break;
            }
        }
    }

    private String getTrimmedParameter(HttpServletRequest request, String name) {
        return request.getParameter(name).trim();
    }
}