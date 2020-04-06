package com.example.tema3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.tema3.Adapters.ListOfUsersAdapter;
import com.example.tema3.Models.ToDo;
import com.example.tema3.Models.User;
import com.example.tema3.VolleyHelpers.HttpRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private RecyclerView ListOfUsers;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> users= new ArrayList<>();
    private View.OnClickListener onClickListener;
    private ToDosFragment ToDosFragment = new ToDosFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getUsers();
        View layout = inflater.inflate(R.layout.fragment_users, container, false);
        ListOfUsers =  layout.findViewById(R.id.list_of_users);
        ListOfUsers.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        ListOfUsers.setLayoutManager((layoutManager));

        onClickListener = new MyClickListener();
        adapter = new ListOfUsersAdapter(users,onClickListener);
        ListOfUsers.setAdapter(adapter);

        return layout;
    }

    private void getUsers()
    {
        String url = "https://my-json-server.typicode.com/MoldovanG/JsonServer/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ((ListOfUsersAdapter)adapter).clearList();

                for(int index = 0; index < response.length(); index++){
                    try {
                        User user = new User().fromJSON(response.getJSONObject(index));
                        ((ListOfUsersAdapter)adapter).addItem(user);

                    } catch (JSONException ex){

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        HttpRequestQueue.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    public  class MyClickListener implements View.OnClickListener {

        private int Id;

        public void setId(int id) {
            Id = id;
        }

        @Override
        public void onClick(View v) {
            ToDosFragment.setUserId(Id);
            ((MainActivity)getActivity()).replaceFragment(ToDosFragment);
        }
    }

}
