package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (uuid != null) {
            String fullName = request.getParameter("fullName");
            final Resume resume = storage.get(uuid);
            resume.setFullName(fullName);
            addContact(resume, request);
            addSection(resume, request);
            storage.update(resume);
        } else {
            final Resume resume = new Resume(request.getParameter("fullName"));
            addContact(resume, request);
            addSection(resume, request);
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
            case "add":
                request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
                return;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
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

    private void addContact(Resume resume, HttpServletRequest request) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null) {
                resume.addContact(type, value);
            }
        }
    }

    private void addSection(Resume resume, HttpServletRequest request) {
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        SimpleTextSection simpleTextSection = new SimpleTextSection(value);
                        resume.addSection(type, simpleTextSection);
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        MarkedTextSection markedTextSection = new MarkedTextSection(value);
                        resume.addSection(type, markedTextSection);
                        break;
                }
            }
        }
    }
}
