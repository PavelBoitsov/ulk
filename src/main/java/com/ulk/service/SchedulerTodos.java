package com.ulk.service;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class SchedulerTodos extends HttpServlet {
    private static final Logger log = Logger.getLogger(SchedulerTodos.class.getName());
    private final TodoUsersDataSore todoUsersDataSore = new TodoUsersDataSore();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.severe("Running cron loop");
        processJob();
    }

    private void processJob() throws IOException {
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder.withUrl("/sender"));

        todoUsersDataSore.startTos();
        todoUsersDataSore.deleteOldTodos();
    }
}
