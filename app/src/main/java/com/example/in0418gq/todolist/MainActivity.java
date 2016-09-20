package com.example.in0418gq.todolist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "com.exam";

    ArrayList<String> todoListItems = new ArrayList<String>();  //To store the to do items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewButton = (Button) findViewById(R.id.new_todo_item_button);
        final EditText newToDoEditText = (EditText) findViewById(R.id.new_todo_item_edittext);

        ListView todoListView = (ListView) findViewById(R.id.todo_listview);

        //Create ArrayAdapter, with the todoListItems ArrayList as the data source
        final ArrayAdapter<String> todoListAdapter =
                new ArrayAdapter<String>(this, R.layout.todo_list_item, R.id.todo_item_text, todoListItems);

        todoListView.setAdapter(todoListAdapter);

        //Add listener to the button, to add items to the ListView
        addNewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Read whatever user has typed into newToDoEditText
                String newItem = newToDoEditText.getText().toString();
                //Make sure some data was entered, show error toast and return if not
                if (newItem.length() == 0) {
                    Toast.makeText(MainActivity.this, "Enter a a todo item", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Else, add the String to the ArrayList
                todoListItems.add(newItem);
                //And notify the ArrayAdapter that the data set has changed, to request UI update
                todoListAdapter.notifyDataSetChanged();
                //Clear EditText, ready to type in next item
                newToDoEditText.getText().clear();

            }
        });


        //This listener responds to long presses on individual list items
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //The position of the list item pressed is provided in the event handler
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Remove the item at position in ArrayList, but first ask user if they are sure

                Log.d(TAG, "On long click listener");
                final int indexPosition = position;    //Copy position clicked into final variable so it can be used inside the Dialog's event ha

                                final String todoItemText = todoListItems.get(position);

                AlertDialog areYouSureDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete this item?")
                        .setMessage(todoItemText)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                todoListItems.remove(indexPosition);

                                todoListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .create();

                areYouSureDialog.show();

                return true;

            }

        });
    }
}
