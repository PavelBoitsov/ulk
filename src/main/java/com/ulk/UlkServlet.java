package com.ulk;

import com.ulk.service.TodoUsersDataSore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UlkServlet extends HttpServlet {

    private final TodoUsersDataSore todoUsersDataSore = new TodoUsersDataSore();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        resp.setContentType("text/plain");
        resp.getWriter().println("hello app engine");

        todoUsersDataSore.startTos();


    }

}
