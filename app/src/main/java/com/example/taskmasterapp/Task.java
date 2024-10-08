package com.example.taskmasterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Task extends AppCompatActivity {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listView = findViewById(R.id.todo_list);
        editText = findViewById(R.id.edit_text);
        todoItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedTask = todoItems.get(position);
                editText.setText(selectedTask);
                editText.requestFocus();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteTask(position);
                return false;
            }
        });

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addOrUpdateTask();
            }
        });
    }

    private void addOrUpdateTask(){
        String task = editText.getText().toString().trim();

        if (!task.isEmpty()){
            int selectedTaskPosition = listView.getCheckedItemPosition();

            if (selectedTaskPosition != AdapterView.INVALID_POSITION){
                todoItems.set(selectedTaskPosition, task);
            } else{
                todoItems.add(task);
            }
            adapter.notifyDataSetChanged();
            clearEditText();
        }
    }

    private void deleteTask(int position){

        todoItems.remove(position);
        adapter.notifyDataSetChanged();
    }

    private void clearEditText(){
        editText.setText("");
        listView.clearChoices();
        editText.clearFocus();
    }

}