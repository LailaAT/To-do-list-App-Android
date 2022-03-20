package com.example.doitcheckit;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
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

import com.example.doitcheckit.Model.ListsModel;
import com.example.doitcheckit.Utils.ListDAO;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddCategory extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newCategoryText;
    private Button saveButton;
    private ListDAO listDAO;
    ListsModel category;

    public static AddCategory newInstance(){
        return new AddCategory();
        //this statement allows the user to use all the functions of this class
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_list, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //this will help the bottom sheet dialog to readjust and move upwards when the user types something and the keyboard is pressed
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newCategoryText = requireView().findViewById(R.id.listText);
        saveButton = requireView().findViewById(R.id.saveButtonL);
        listDAO = new ListDAO(getContext());

        String categoryName;
        boolean isUpdate = false;
        final Bundle bundle = getArguments();

        if (bundle != null){
            isUpdate = true;
            updateCategory(bundle);
        }
        listDAO.open();
        setListener(newCategoryText, saveButton);

        final boolean finalIsUpdate = isUpdate;
        saveButtonListener(finalIsUpdate, saveButton, bundle, newCategoryText);

    }

    private void setListener(EditText text, Button saveButton) {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 1 && s.length() > 50){ //limits for the name
                    saveButton.setEnabled(true);
                    saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pastelBrown));
                } else{
                    saveButton.setEnabled(false);
                    saveButton.setTextColor(Color.GRAY);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void updateCategory(Bundle bundle){
        String categoryName;
        categoryName = bundle.getString("listName");
        newCategoryText.setText(categoryName);
        if(categoryName != null){
            saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pastelBrown));
        }
    }

    private void saveButtonListener(Boolean update, Button saveButton, Bundle bundle, EditText text){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newCategoryText.getText().toString();
                if(update){
                    listDAO.updateName(bundle.getInt("listId"), text);
                } else {
                    category = new ListsModel();
                    category.setListName(text);
                    listDAO.insertList(category);
                }
                dismiss();
            }
        });
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

