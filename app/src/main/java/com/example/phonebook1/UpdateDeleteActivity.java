package com.example.phonebook1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateDeleteActivity extends DBActivity implements AdapterView.OnItemSelectedListener {
    protected EditText editFirstName;
    protected EditText editLastName;
    protected EditText editPhoneNum;
    protected EditText editEmailAddress;
    protected EditText editBirthday;
    protected Button updateContact, deleteContact, back;
    Spinner snipper ;
    String selectedTextSpinner = "";

    String[] category = { "", "Семейство",
            "Приятели", "Колеги"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhoneNum = findViewById(R.id.editPhoneNum);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        editBirthday = findViewById(R.id.editBirthday);
        updateContact = findViewById(R.id.UpdateContact);
        deleteContact = findViewById(R.id.DeleteContact);
        back = findViewById(R.id.BackToAll);
        snipper = findViewById(R.id.Snipper1);

        InitializationTextViews();
        updateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateContact(view);
                OpenAllContracts();
            }
        });
        deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteContact(view);
                OpenAllContracts();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
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
    public void UpdateContact(View view){
        int parsedId = Integer.parseInt(String.valueOf(AllContacts.Id));
        try {
            ExecSQL("UPDATE PHONEBOOK SET FIRSTNAME=?, LASTNAME=?, PHONE_NUM=?,DATE_OF_BIRTH=?,EMAIL=?,CATEGORY WHERE ID=?",new Object[]{
                            editFirstName.getText().toString(),
                            editLastName.getText().toString(),
                            editPhoneNum.getText().toString(),
                            editBirthday.getText().toString(),
                            editEmailAddress.getText().toString(),
                            selectedTextSpinner,
                   parsedId},
                    () -> Toast.makeText(getApplicationContext(),"Update successful", Toast.LENGTH_LONG).show());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void DeleteContact(View view) {
        try {
            ExecSQL("DELETE FROM PHONEBOOK WHERE ID = ?", new Object[]{AllContacts.Id}, () -> Toast.makeText(getApplicationContext(),"Delete successful", Toast.LENGTH_LONG).show());
        }catch (Exception exception){
            Toast.makeText(getApplicationContext(),"Delete error" + exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected ArrayList<String> GetContact(){
        String sql = String.format("SELECT * FROM PHONEBOOK WHERE ID=%s", AllContacts.Id);
        final ArrayList<String> listResult = new ArrayList<String>();
        try {
            SelectSQL(sql,null, (id, firstname, lastname,phoneNum, dateOfBirth,email, categoty) -> {
                listResult.add(id + "\t" + firstname+ "\t" +lastname+ "\t" +phoneNum+ "\t" +dateOfBirth+ "\t" +email+ "\t" +categoty+ "\n");
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return listResult;
    }
    protected void InitializationTextViews(){
        final ArrayList<String> listResult = GetContact();
        String[] elements = listResult.get(0).split("\t");
        String f = elements[1];
        String l = elements[2];
        editFirstName.setText(elements[1]);
        editLastName.setText(elements[2]);
        editPhoneNum.setText(elements[3]);
        editBirthday.setText(elements[4]);
        editEmailAddress.setText(elements[5]);

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