package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.sql.helper.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

public class ResumeServlet extends HttpServlet {
    private final String dbUrl = Config.get().getUrl();
    private final String dbUser = Config.get().getUser();
    private final String dbPass = Config.get().getPassword();
    private final SqlHelper sqlHelper = new SqlHelper(dbUrl, dbUser, dbPass);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String init_table = request.getParameter("name");
        if (init_table != null) {
            sqlHelper.<Void>execute(st -> {
                try (PrintWriter writer = response.getWriter()) {
                    final ResultSet resultSet = st.executeQuery();
                    writer.write("<!DOCTYPE html>" + '\n');
                    writer.write("<html>" + '\n');
                    writer.write("<head>" + '\n');
                    writer.write("<title> My first Resume Table </title>" + '\n');
                    writer.write("</head>" + '\n');
                    writer.write("<body>" + '\n');
                    writer.write("<table border = \"1\">" + '\n');
                    writer.write("<tr>" + '\n');
                    writer.write("<th>uuid</th>" + '\n');
                    writer.write("<th>full_name</th>" + '\n');
                    writer.write("</tr>" + '\n');
                    while (resultSet.next()) {
                        writer.write("<tr>" + '\n');
                        writer.write("<td>" + resultSet.getString("uuid") + "</td>" + '\n');
                        writer.write("<td>" + resultSet.getString("full_name") + "</td>" + '\n');
                        writer.write("</tr>");
                    }
                    writer.write("</table>" + '\n');
                    writer.write("</body>" + '\n');
                    writer.write("</html>" + '\n');
                } catch (IOException e) {
                    System.out.println("ERROR: " + e + e.getMessage());
                }
                return null;
            }, "SELECT * FROM resume");
        }
    }
}
