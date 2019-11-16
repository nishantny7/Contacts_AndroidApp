package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;

import static com.example.contacts.MainActivity.contacts;
import static com.example.contacts.MainActivity.namesList;

public class AddContact extends AppCompatActivity {

    private DBhandler dbhandler;

    EditText et_name, et_number, et_email, et_organisation;
    Button bt_add_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        dbhandler = new DBhandler(this);

        et_name = (EditText) findViewById(R.id.editText_name);
        et_number = (EditText) findViewById(R.id.editText_number);
        et_email = (EditText) findViewById(R.id.editText_email);
        et_organisation = (EditText) findViewById(R.id.editText_organisation);

        bt_add_contact = (Button) findViewById(R.id.button_add_contact);

        bt_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String number = et_number.getText().toString();
                String email = et_email.getText().toString();
                String organisation = et_organisation.getText().toString();

                if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(number) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(organisation)) {
                    Contact contact = new Contact(name, number, email, organisation);
                    dbhandler.addContact(contact);
                    Toast.makeText(getApplicationContext(),"Contact Saved!",Toast.LENGTH_SHORT).show();
                    /*contacts.clear();
                    contacts = dbhandler.getAllContacts();
                    namesList.clear();
                    for(int i=0; i<contacts.size(); i++) {
                        namesList.add(contacts.get(i).getName());
                    }
                    Collections.sort(namesList);
                    notifyAll();*/
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
