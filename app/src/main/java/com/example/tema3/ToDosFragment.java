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
import com.example.tema3.Adapters.ListOfToDosAdapter;
import com.example.tema3.Adapters.ListOfUsersAdapter;
import com.example.tema3.Models.ToDo;
import com.example.tema3.Models.User;
import com.example.tema3.VolleyHelpers.HttpRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ToDosFragment extends Fragment {

    private  int UserId;
    private RecyclerView ListOfToDos;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ToDo> toDos= new ArrayList<>();
    private DetailsFragment detailsFragment = new DetailsFragment();

    public void setUserId(int userId) {
        UserId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout =  inflater.inflate(R.layout.fragment_todos, container, false);
        getToDos(UserId);
        ListOfToDos =  layout.findViewById(R.id.list_of_to_dos);
        ListOfToDos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        ListOfToDos.setLayoutManager((layoutManager));
        View.OnClickListener onClickListener = new ToDosClickListener();
        adapter = new ListOfToDosAdapter(toDos, onClickListener);
        ListOfToDos.setAdapter(adapter);

        return layout;
    }
    private void getToDos(int id)
    {
        String url = "https://jsonplaceholder.typicode.com/todos?userId=" + id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                ((ListOfToDosAdapter)adapter).clearList();
                for(int index = 0; index < response.length(); index++){
                    try {
                        ToDo toDo = new ToDo().fromJSON(response.getJSONObject(index));
                        ((ListOfToDosAdapter)adapter).addItem(toDo);

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
    public  class ToDosClickListener implements View.OnClickListener {

        private String title;


        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public void onClick(View v) {
            detailsFragment.setToDoTitle(title);
            ((MainActivity)getActivity()).replaceFragment(detailsFragment);
        }
    }
}
