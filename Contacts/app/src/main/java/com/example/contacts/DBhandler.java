package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DBhandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DB_Name = "ContactsDB";

    private static final String CONTACTS_TABLE  = "contacts";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private static final String EMAIL = "email";
    private static final String ORGANISATION = "organisation";

    public DBhandler(@Nullable Context context) {
        super(context, DB_Name, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + CONTACTS_TABLE + "("
                + ID + " integer PRIMARY KEY autoincrement, "
                + NAME + " TEXT, "
                + NUMBER + " TEXT, "
                + EMAIL + " TEXT , "
                + ORGANISATION + " TEXT)";

        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + CONTACTS_TABLE;
        db.execSQL(sql);

        onCreate(db);

    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(NUMBER, contact.getNumber());
        values.put(EMAIL, contact.getEmail());
        values.put(ORGANISATION, contact.getOrganisation());

        db.insert(CONTACTS_TABLE, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                CONTACTS_TABLE,
                new String[]{ID, NAME, NUMBER, EMAIL, ORGANISATION},
                ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        Contact contact;
        if(cursor!=null) {
            cursor.moveToFirst();
            contact = new Contact(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            return contact;
        }
        else {
            return null;
        }
    }

    public List<Contact> getAllContacts() {
        SQLiteDatabase db = getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();

        String query = "SELECT * FROM " + CONTACTS_TABLE;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();

                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setOrganisation(cursor.getString(4));

                contacts.add(contact);
            }while(cursor.moveToNext());
        }
        return contacts;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(NUMBER, contact.getNumber());
        values.put(EMAIL, contact.getEmail());
        values.put(ORGANISATION, contact.getOrganisation());

        return db.update(
                CONTACTS_TABLE,
                values,
                ID + " = ?",
                new String[]{String.valueOf(contact.getId())}
        );
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(
                CONTACTS_TABLE,
                ID + " = ?",
                new String[]{String.valueOf(contact.getId())}
        );

        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + CONTACTS_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();

    }


}
