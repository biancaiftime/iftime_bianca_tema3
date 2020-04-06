package com.example.tema3.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class ToDo {
    public int UserId;
    public int Id;
    public String Title;
    public boolean Completed;

    public ToDo(int userId, int id, String title, boolean completed) {
        UserId = userId;
        Id = id;
        Title = title;
        Completed = completed;
    }

    public ToDo() {
    }
    public ToDo fromJSON(JSONObject jsonObject) throws JSONException {
        return new ToDo(jsonObject.getInt("userId"),
                jsonObject.getInt("id"),
                jsonObject.getString("title"),
                jsonObject.getBoolean("completed"));
    }
}
