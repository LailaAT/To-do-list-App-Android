package com.example.doitcheckit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doitcheckit.Adapter.ToDoAdapter;

public class ItemHelper extends ItemTouchHelper.SimpleCallback {

    private ToDoAdapter adapter;

    public ItemHelper(ToDoAdapter adapter){
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        //supports left and right swipes
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction){
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){ //user swipes to delete
            //building a pop-up message
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this task ?"); //asking user for confirmation
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteItem(position); //calls delete task method from adpater class
                        }
                    });

            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    //esnures display is updated
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{ //if not deleting calls the edit item method from adapter class
            adapter.editItem(position);
        }
    }

    //method for drawing the swiping feature
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dx, float dy, int actionState, boolean isCurrentlyActive){
        super.onChildDraw(c, recyclerView, viewHolder,dx, dy, actionState, isCurrentlyActive);
        Drawable icon; //icon that will be shown when swiping
        ColorDrawable background; //colour of background

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        //if it is from the left (i.e. editing)
        if(dx > 0){
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.edit);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.pastelBrown));
        } else{ //if it is from the right (i.e. deleting)
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.deleteicon);
            background = new ColorDrawable(Color.RED);
        }

        //drawing swiping in general
        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        //assigning icon position based on previous if statement
        if(dx > 0){ //swiping towards the right from the left (editing)
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int)dx) + backgroundCornerOffset, itemView.getBottom());
        }
        else if(dx < 0){ //swiping towards the left from the right (deleting)
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(itemView.getRight() + ((int) dx) -backgroundCornerOffset, itemView.getTop(),
                    itemView.getRight(), itemView.getBottom());
        }
        else{ //if no swiping
            background.setBounds(0,0,0,0);
        }
        background.draw(c); //displays both icon and background
        icon.draw(c);
    }
}
