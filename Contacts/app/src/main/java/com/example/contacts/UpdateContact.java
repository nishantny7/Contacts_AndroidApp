package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContact extends AppCompatActivity {

    EditText edit_name, edit_number, edit_email, edit_organisation;
    private String name, number, email , organisation;
    private DBhandler dbhandler;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        Intent mIntent = getIntent();
        final int id_key = mIntent.getIntExtra("ID", 0);

        edit_name = (EditText) findViewById(R.id.editText_editName);
        edit_number = (EditText) findViewById(R.id.editText_editNumber);
        edit_email = (EditText) findViewById(R.id.editText_editEmail);
        edit_organisation = (EditText) findViewById(R.id.editText_editOrg);
        save = (Button) findViewById(R.id.saveButton);

        dbhandler = new DBhandler(this);

        Contact contact = dbhandler.getContact(id_key);

        name = contact.getName();
        number = contact.getNumber();
        email = contact.getEmail();
        organisation = contact.getOrganisation();

        edit_name.setText(name);
        edit_number.setText(number);
        edit_email.setText(email);
        edit_organisation.setText(organisation);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edit_name.getText().toString();
                number = edit_number.getText().toString();
                email = edit_email.getText().toString();
                organisation = edit_organisation.getText().toString();

                Contact contact = new Contact(id_key, name, number, email, organisation);
                dbhandler.updateContact(contact);
                Toast.makeText(getApplicationContext(),"Contact Saved!",Toast.LENGTH_LONG).show();
                finish();

            }
        });


    }
}
