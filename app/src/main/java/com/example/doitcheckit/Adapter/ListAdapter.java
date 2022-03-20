package com.example.doitcheckit.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doitcheckit.AddCategory;
import com.example.doitcheckit.ListView;
import com.example.doitcheckit.Model.ListsModel;
import com.example.doitcheckit.R;
import com.example.doitcheckit.Utils.ListDAO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";
    private List<ListsModel> categoryList;
    //this is the list of categories the user has created
    private ListView listView;
    private ListDAO listDAO;

    public ListAdapter(ListView listView, ListDAO listDAO){
        this.listView = listView;
        this.listDAO = listDAO;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview, parent, false);
        return new ListAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        listDAO.open(); //opening database, specifically list table
        ListsModel item = categoryList.get(position);
        holder.listName.setText(item.getListName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setList(List<ListsModel> categoryList){
        this.categoryList = categoryList;
        notifyDataSetChanged();
        //ensures the view is updated whenever a category is added
    }

    public Context getContext(){ return listView; }

    public void editItem(int position){
        ListsModel item = categoryList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("listId", item.getId());
        bundle.putString("listName", item.getListName());
        AddCategory fragment = new AddCategory();
        fragment.setArguments(bundle);
        fragment.show(listView.getSupportFragmentManager(), AddCategory.TAG);

    }

    public void deleteItem(int position){
        ListsModel item = categoryList.get(position);
        listDAO.deleteList(item.getId()); //deletes item from database
        categoryList.remove(position); //deletes item from list
        notifyItemRemoved(position);
        //updates recycler view
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView listName;
        ViewHolder(View view){
            super(view);
            listName = (TextView) view.findViewById(R.id.listText);
        }
    }
}
