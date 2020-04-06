package com.example.tema3.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public int Id;
    public String Name;
    public String Username;
    public String Email;

    public User(int id, String name, String username, String email) {
        Id = id;
        Name = name;
        Username = username;
        Email = email;
    }

    public User() {
    }

    public User fromJSON(JSONObject jsonObject) throws JSONException {
        return new User(jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("username"),
                        jsonObject.getString("email"));
    }
}
