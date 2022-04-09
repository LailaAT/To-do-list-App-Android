package com.example.doitcheckit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doitcheckit.Adapter.ListAdapter;
import com.example.doitcheckit.Adapter.ToDoAdapter;
import com.example.doitcheckit.Model.ListsModel;
import com.example.doitcheckit.Utils.ListDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListView extends AppCompatActivity implements DialogCloseListener {

    private FloatingActionButton backButton;
    private FloatingActionButton addCategory;
    private ListAdapter adapter;
    private RecyclerView recyclerView; //view the tasks will be displayed in
    private ListDAO listDAO; //used to display saved tasks
    private List<ListsModel> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists);//defining the list/category overview

        listDAO = new ListDAO(this);
        listDAO.open();//opening database

        categoryList = new ArrayList<>();

        recyclerView = findViewById(R.id.listsOverview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListAdapter(ListView.this, listDAO);
        recyclerView.setAdapter(adapter);
        //setting adapter to recycler view

        //adding the swiping features for deleting + editing
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new ItemHelper(adapter, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        categoryList = listDAO.getAllLists();//getting all tasks from database
        adapter.setList(categoryList);
        //on click listener for back button
        backButton = findViewById(R.id.backButtonL);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Menu.class);
                v.getContext().startActivity(intent);
            }
        });
        //on click listener for adding a category button
        addCategory = findViewById(R.id.addList);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCategory.newInstance().show(getSupportFragmentManager(), AddCategory.TAG);
            }
        });
    }


    @Override
    public void handleDialogClose(DialogInterface dialog) {
        categoryList = listDAO.getAllLists();
        Collections.reverse(categoryList);
        adapter.setList(categoryList);
        adapter.notifyDataSetChanged();
    }
}
