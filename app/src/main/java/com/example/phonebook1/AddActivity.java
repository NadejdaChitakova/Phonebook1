package com.example.phonebook1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddActivity extends DBActivity implements OnItemSelectedListener {
    protected EditText editFirstName, editLastName, editPhoneNum, editEmailAddress, editBirthday;
    protected Button addContact, backToAll;
    Spinner snipper ;
    String selectedTextSpinner = "";

        String[] category = { "", "Семейство",
            "Приятели", "Колеги"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_activity);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhoneNum = findViewById(R.id.editPhoneNum);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        editBirthday = findViewById(R.id.editBirthday);
        addContact = findViewById(R.id.AddContact);
        backToAll = findViewById(R.id.Back);
        snipper = findViewById(R.id.Snipper);

        backToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAllContracts();
            }
        });

        snipper.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snipper.setAdapter(aa);

    }
    public void SaveContact(View view) {
        try {
            ExecSQL("INSERT INTO PHONEBOOK(FIRSTNAME, LASTNAME, PHONE_NUM, DATE_OF_BIRTH, EMAIL, CATEGORY) VALUES(?,?,?,?,?,?)",
                    new Object[]{
                            editFirstName.getText().toString(),
                            editLastName.getText().toString(),
                            editPhoneNum.getText().toString(),
                            editBirthday.getText().toString(),
                            editEmailAddress.getText().toString(),
                            selectedTextSpinner},
                    () -> Toast.makeText(getApplicationContext(),
                            "Successfully save", Toast.LENGTH_LONG).show());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        OpenAllContracts();
    }

    protected void OpenAllContracts(){
        Intent intent = new Intent (getApplicationContext(), AllContacts.class);
        startActivity(intent);
        finish();
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedTextSpinner = snipper.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}