package com.ulk.com.endpoints;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class GetByStatus extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        resp.setContentType("text/plain");
        resp.getWriter().println("\n\n\t\t appengine list based on status from datastore \n\n\n");
        System.out.println("list");
        String pathInfo = req.getPathInfo(); // /{value}/test
        String[] pathParts = pathInfo.split("/");
        System.out.println(Arrays.toString(pathParts));
        String status = pathParts[1]; // {value}
//        String part2 = pathParts[2]; // test
        if (status.equals("new") || status.equals("no_changes") || status.equals("old"))
            filterdListByStatus(resp, status);
        else
            resp.getWriter().println("status " + status + " not correct \n\n " +
                    "correct status:\n " +
                    "\tnew\n" +
                    "\tno_changes\n" +
                    "\told\n");

    }

    public void filterdListByStatus(HttpServletResponse resp, String status) throws IOException {
        List<Entity> users = getEntitiesByFilter(status);
        if (users != null) {
            for (Entity entity : users) {
                resp.getWriter().println(entity);
                System.out.println(entity);
            }
        }
    }

    private List<Entity> getEntitiesByFilter(String status) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoUsers");
        query.setFilter(new Query.FilterPredicate("status", Query.FilterOperator.EQUAL, status));

        return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
    }
}
