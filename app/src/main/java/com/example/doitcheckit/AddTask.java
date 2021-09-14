package com.example.doitcheckit;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
//import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.doitcheckit.Model.TasksModel;
import com.example.doitcheckit.Utils.Database;
import com.example.doitcheckit.Utils.TaskDAO;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private EditText durationText;
    private Button saveButton;
    //private Database db;
    private TaskDAO taskDAO;

    //object
    TasksModel task;

    // AT: task vs taskDAO

    public static AddTask newInstance() {
        return new AddTask();
        //the new instance allows me to use the functions of this class in the main one
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //this will help the bottom sheet dialog to readjust and move upwards when the user types something and the keyboard is pressed
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTaskText = requireView().findViewById(R.id.newTaskText);
        durationText = requireView().findViewById(R.id.durationText);
        saveButton = getView().findViewById(R.id.newTaskButton);
        taskDAO = new TaskDAO(getContext());

        boolean isUpdate = false;
        //whether a new task is being created or an old one is being updated
        //this variable helps distinguish between these two cases

        final Bundle bundle = getArguments();
        //this allows me to get the data from the adapter and then pass it on to the dialog sheet fragment
        if(bundle != null) {
            isUpdate = true;
            String taskName = bundle.getString("taskName");     // AT: ok to have two variables with the same name "task" ?
            long num = bundle.getLong("duration");
            newTaskText.setText(taskName);
            assert taskName != null;
            if (taskName.length() > 0 && num != 0)
                //if the user inputted text
                saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pastelBrown));
                //whenever a suitable input is entered the color of the text will change
        }
        taskDAO.open();

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    saveButton.setEnabled(false);
                    saveButton.setTextColor(Color.GRAY);
                }else{
                    saveButton.setEnabled(true);
                    saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pastelBrown));
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });



        final boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                long num = Long.parseLong(durationText.getText().toString());
                        //getLong.parseLong(String.valueOf(durationText.getText()));

                if(finalIsUpdate){
                        taskDAO.updateTask(bundle.getInt("task_id"), text);
                        taskDAO.updateDuration(bundle.getInt("task_id"), num);
                } else {
                    task = new TasksModel();
                    task.setStatus(0);
                    task.setTaskName(text);
                    task.setDuration(num);
                    taskDAO.insertTask(task);
                }

                dismiss();
            }
        });

    }

    public void updateTask(Bundle bundle, boolean isUpdate, String text, int num){      // AT: why not defining this in the TaskDAO class?
            taskDAO.updateTask(bundle.getInt("task_id"), text);
            taskDAO.updateDuration(bundle.getInt("task_id"), num);
    }

    public void createTask(String text, long num, TaskDAO taskDAO){             // AT: why not defining this in the TaskDAO class?
        task = new TasksModel();
        task.setStatus(0);
        task.setTaskName(text);
        task.setDuration(num);
        taskDAO.insertTask(task);
    }

    public int toInteger(boolean x){
        int num;
        if(x == true){
            num = 1;
        } else{
            num = 0;
        }
        return num;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            //the DialogCloseListener is an interface which will be responsible for database tasks
            //like recycler view, refreshing and updating the view

            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}

               /*try {
                    num = Long.parseLong(String.valueOf(durationText.getText()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    num = 1800000;
                    //default for a task is ot have 30 min if the user does not specify a duration
                } */



