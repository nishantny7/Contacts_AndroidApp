package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.contacts.MainActivity.contacts;
import static com.example.contacts.MainActivity.namesList;

public class ContactDescription extends AppCompatActivity {

    private List<Contact> contacts;
    private DBhandler dbhandler;
    TextView des_name, des_number, des_email, des_organisation;
    private String name, number, email, organisation;
    Button edit_button, delete_button;
    LinearLayout linear_layout_ver1, linear_layout_ver2, linear_layout_ver3;

    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_description);

        final Intent mIntent = getIntent();
        final int id_key = mIntent.getIntExtra("ID",0);

        contacts = new ArrayList<>();
        dbhandler = new DBhandler(this);

        contacts = dbhandler.getAllContacts();

        final Contact contact = dbhandler.getContact(id_key);

        des_name = (TextView) findViewById(R.id.description_name);
        des_number = (TextView) findViewById(R.id.description_number);
        des_email = (TextView) findViewById(R.id.description_email);
        des_organisation = (TextView) findViewById(R.id.description_organisation);
        edit_button = (Button) findViewById(R.id.description_editButton);
        delete_button = (Button) findViewById(R.id.description_deleteButton);
        linear_layout_ver1 = (LinearLayout) findViewById(R.id.linear_layout_ver1);
        linear_layout_ver2 = (LinearLayout) findViewById(R.id.linear_layout_ver2);
        linear_layout_ver3 = (LinearLayout) findViewById(R.id.linear_layout_ver3);

        name = contact.getName();
        number = contact.getNumber();
        email = contact.getEmail();
        organisation = contact.getOrganisation();

        des_name.setText(name);
        des_number.setText(number);
        des_email.setText(email);
        des_organisation.setText(organisation);

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), UpdateContact.class);
                myIntent.putExtra("ID", id_key);
                startActivity(myIntent);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ContactDescription.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete the contact")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbhandler.deleteContact(contact);
                                /*contacts.clear();
                                contacts = dbhandler.getAllContacts();
                                namesList.clear();
                                for(int i=0; i<contacts.size(); i++) {
                                    namesList.add(contacts.get(i).getName());
                                }
                                notifyAll();*/
                                finish();
                            }
                        }).setNegativeButton("No", null).show();
            }
        });

        linear_layout_ver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        linear_layout_ver2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        linear_layout_ver3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        String mailTo = "mailto:" + des_email.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailTo));
        startActivity(emailIntent);
    }

    private void makePhoneCall() {
        String number = des_number.getText().toString();
        if(number.trim().length()>0) {
            if (ContextCompat.checkSelfPermission(ContactDescription.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactDescription.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(ContactDescription.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS() {
        String phoneNo = "smsto:" + des_number.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(phoneNo)); // This ensures only SMS apps respond
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CALL:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}
