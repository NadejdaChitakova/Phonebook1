package com.example.phonebook1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllContacts extends DBActivity {
    protected Button createNewContact;
    protected ListView allContacts;
    public static int Id = 0;

    public void FillListView() throws Exception{
        final ArrayList<String> listResult = new ArrayList<String>();
        SelectSQL("SELECT * FROM PHONEBOOK ORDER BY FIRSTNAME",null, (id, firstname, lastname,phoneNum, dateOfBirth,email,category) -> {
            listResult.add(id + "\t" + firstname+ "\t" +lastname+ "\t" +phoneNum+ "\t" +dateOfBirth+ "\t" +email+"\t" +category+"\n");
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.activity_list_view,
                R.id.textView,
                listResult
        );
        allContacts.setAdapter(arrayAdapter);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewContact = findViewById(R.id.AddNewContact);
        allContacts =findViewById(R.id.allContacts);
        try {
            InitDB();
            FillListView();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        createNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCrudActivity();
            }
        });

        allContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AllContacts.this.OpenUpdateActivity();
                TextView clickedText = view.findViewById(R.id.textView);
                String selected = clickedText.getText().toString();
                String[] elements = selected.split("\t");
                Id =Integer.parseInt(elements[0]);

            }
        });
    }
    public void OpenCrudActivity(){
        Intent intent = new Intent (getApplicationContext(), AddActivity.class);
        startActivity(intent);
        finish();
    }

    public void OpenUpdateActivity(){
        Intent intent = new Intent (getApplicationContext(), UpdateDeleteActivity.class);
        startActivity(intent);
        finish();
    }

}