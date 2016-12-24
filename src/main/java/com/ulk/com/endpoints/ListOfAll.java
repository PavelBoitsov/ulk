package com.ulk.com.endpoints;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 23-Dec-16.
 */
public class ListOfAll extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        resp.setContentType("text/plain");
        resp.getWriter().println("\n\n\t\t appengine list of all from datastore \n\n\n");

        getAllEntitys(resp);
    }

    public void getAllEntitys(HttpServletResponse resp) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> users = datastore.prepare(new Query("TodoUsers")).asList(FetchOptions.Builder.withDefaults());
        for (Entity entity: users) {
            resp.getWriter().println(entity);
        }
    }
}
