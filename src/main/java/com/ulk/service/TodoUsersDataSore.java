package com.ulk.service;

import com.google.appengine.api.datastore.*;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import com.google.appengine.repackaged.org.codehaus.jackson.type.TypeReference;
import com.ulk.model.TodoUser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class TodoUsersDataSore implements Serializable {
    public TodoUsersDataSore() {
    }

    public void startTos() throws IOException {
        List<TodoUser> todoUserList = new ArrayList<TodoUser>();
        try {
            todoUserList = readJsonFromUrl("http://jsonplaceholder.typicode.com/todos");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (todoUserList != null) {
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            List<Entity> users = datastore.prepare(new Query("TodoUsers")).asList(FetchOptions.Builder.withDefaults());
            for (Entity entity : users) {
                entity.setProperty("status", "old");
                datastore.put(entity);
            }

            for (TodoUser to : todoUserList) {
                putOrUpdateTodo(to);
            }
        }
    }

    // following method trying to put or update received Entries to datastore
    public void putOrUpdateTodo(TodoUser recieved) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity todo;
        Key customerKey = KeyFactory.createKey("TodoUsers", Integer.toString(recieved.getId()));

        try {
            todo = datastore.get(customerKey);
            TodoUser fromEntity = fromDataStoreEntityToTodoUser(todo);

            if (recieved.equals(fromEntity)) {
                todo.setProperty("status", "no_changes");
                todoUser(recieved, datastore, todo);
            } else {
                todo.setProperty("status", "updated");
                todoUser(recieved, datastore, todo);
            }
        } catch (EntityNotFoundException e) {
            todo = new Entity(customerKey);
            todo.setProperty("status", "new");
            todoUser(recieved, datastore, todo);
        }
    }

    // create found user and check it for updates
    TodoUser fromDataStoreEntityToTodoUser(Entity todo) {
        int i = Math.toIntExact((long) todo.getProperty("userId"));
        String t = (String) todo.getProperty("title");
        boolean c = (boolean) todo.getProperty("completed");
        return new TodoUser(i, t, c);
    }

    //save or update it
    void todoUser(TodoUser to, DatastoreService datastore, Entity todo) {
        todo.setProperty("id", to.getId());
        todo.setProperty("userId", to.getUserId());
        todo.setProperty("title", to.getTitle());
        todo.setProperty("completed", to.isCompleted());

        datastore.put(todo);
    }

    //delete it
    public void deleteOldTodos() {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> users = getEntitiesByFilter("old");
        if (users != null) {
            for (Entity entity : users) {
                datastore.delete(entity.getKey());
            }
        }
    }

    // get by status
    private List<Entity> getEntitiesByFilter(String status) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoUsers");
        query.setFilter(new Query.FilterPredicate("status", Query.FilterOperator.EQUAL, status));

        return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
    }


    public List<TodoUser> readJsonFromUrl(String url) throws IOException, JSONException {
        String jsonText = getContentFromUrl(url);

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<TodoUser>> mapType = new TypeReference<List<TodoUser>>() {
        };
        List<TodoUser> todoUserList = objectMapper.readValue(jsonText, mapType);

        return todoUserList;
    }

    private String getContentFromUrl(String url) throws IOException {
        URL ur = new URL(url);
        StringBuilder parsedContentFromUrl = new StringBuilder();
        URLConnection uc;
        uc = ur.openConnection();
        uc.connect();
        uc = ur.openConnection();
        uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        uc.getInputStream();
        BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
        int ch;
        while ((ch = in.read()) != -1) {
            parsedContentFromUrl.append((char) ch);
        }
        return parsedContentFromUrl.toString();
    }
}