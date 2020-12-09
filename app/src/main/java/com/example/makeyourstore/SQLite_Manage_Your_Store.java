package com.example.makeyourstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import object_App.Account;
import object_App.Product;

public class SQLite_Manage_Your_Store extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;
    static final String DB_NAME = "ManageData";
    static final String DB_TABLE_ACCOUNT = "Accounts";
    static final String DB_TABLE_PRODUCT = "Products";
    static final int DB_VERSION = 1;
    static final String ACCOUNT_ID = "ID";
    static final String ACCOUNT_USER_NAME = "userName";
    static final String ACCOUNT_PASSWORD = "password";
    static final String ACCOUNT_FULL_NAME = "fullName";
    static final String ACCOUNT_DATE_OF_BIRTH = "dateOfBirth";
    static final String ACCOUNT_PHONE = "phone";
    static final String ACCOUNT_QUESTION = "question";
    static final String ACCOUNT_ANSWER = "answer";
    static final String PRODUCT_ID = "IDPr";
    static final String PRODUCT_NAME = "name";
    static final String PRODUCT_PRICE = "price";
    static final String PRODUCT_AMOUNT = "amount";
    static final String PRODUCT_TYPE = "type";
    static final String PRODUCT_DESCRIBE = "describe";
    static final String PRODUCT_IMAGE = "image";
    static final String PRODUCT_PRODUCER = "producer";


    public SQLite_Manage_Your_Store(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTableAccounts = "CREATE TABLE Accounts (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "userName TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "fullName TEXT NOT NULL," +
                "dateOfBirth TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "question TEXT NOT NULL," +
                "answer TEXT NOT NULL)";
        String queryCreateTableProducts = "CREATE TABLE Products (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price LONG NOT NULL," +
                "amount INTEGER NOT NULL," +
                "type TEXT NOT NULL," +
                "describe TEXT NOT NULL," +
                "image INTERGER NOT NULL," +
                "producer TEXT NOT NULL)";
        db.execSQL(queryCreateTableAccounts);
        db.execSQL(queryCreateTableProducts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }

    public void insertAccount(Account account) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(ACCOUNT_USER_NAME, account.getUserName());
        contentValues.put(ACCOUNT_PASSWORD, account.getPassword());
        contentValues.put(ACCOUNT_FULL_NAME, account.getFullName());
        contentValues.put(ACCOUNT_DATE_OF_BIRTH, account.getDateOfBirth());
        contentValues.put(ACCOUNT_PHONE, account.getPhone());
        contentValues.put(ACCOUNT_QUESTION, account.getQuestion());
        contentValues.put(ACCOUNT_ANSWER, account.getAnswer());
        sqLiteDatabase.insert(DB_TABLE_ACCOUNT, null, contentValues);
    }

    public void updateAccount(Account account) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(ACCOUNT_USER_NAME, account.getUserName());
        contentValues.put(ACCOUNT_PASSWORD, account.getPassword());
        contentValues.put(ACCOUNT_FULL_NAME, account.getFullName());
        contentValues.put(ACCOUNT_DATE_OF_BIRTH, account.getDateOfBirth());
        contentValues.put(ACCOUNT_PHONE, account.getPhone());
        contentValues.put(ACCOUNT_QUESTION, account.getQuestion());
        contentValues.put(ACCOUNT_ANSWER, account.getAnswer());
        sqLiteDatabase.update(DB_TABLE_ACCOUNT, contentValues, "ID = ?", new String[]{String.valueOf(account.getID())});
    }

    public void deleteAccount() {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_ACCOUNT, null, null);
    }

    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        Account account;
        sqLiteDatabase = getReadableDatabase();
        try {
            cursor = sqLiteDatabase.query(false, DB_TABLE_ACCOUNT, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                int ID = cursor.getInt(cursor.getColumnIndex(ACCOUNT_ID));
                String userName = cursor.getString(cursor.getColumnIndex(ACCOUNT_USER_NAME));
                String password = cursor.getString(cursor.getColumnIndex(ACCOUNT_PASSWORD));
                String fullName = cursor.getString(cursor.getColumnIndex(ACCOUNT_FULL_NAME));
                String dateOfBirth = cursor.getString(cursor.getColumnIndex(ACCOUNT_DATE_OF_BIRTH));
                String phone = cursor.getString(cursor.getColumnIndex(ACCOUNT_PHONE));
                String question = cursor.getString(cursor.getColumnIndex(ACCOUNT_QUESTION));
                String answer = cursor.getString(cursor.getColumnIndex(ACCOUNT_ANSWER));
                account = new Account(ID, userName, password, fullName, dateOfBirth, phone, question, answer);
                accountList.add(account);
            }
        return accountList;
    }
    public void insertProduct(Product product) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(PRODUCT_NAME,product.getNameProduct());
        contentValues.put(PRODUCT_PRICE,product.getPrice());
        contentValues.put(PRODUCT_AMOUNT,product.getAmount());
        contentValues.put(PRODUCT_TYPE,product.getType());
        contentValues.put(PRODUCT_DESCRIBE,product.getDescribe());
        contentValues.put(PRODUCT_IMAGE,product.getImage());
        contentValues.put(PRODUCT_PRODUCER,product.getProducer());
        sqLiteDatabase.insert(DB_TABLE_PRODUCT, null, contentValues);
    }

//    public void updateAccount(Account account) {
//        sqLiteDatabase = getWritableDatabase();
//        contentValues = new ContentValues();
//        //contentValues.put(ACCOUNT_ID, account.getID());
//        contentValues.put(ACCOUNT_USER_NAME, account.getUserName());
//        contentValues.put(ACCOUNT_PASSWORD, account.getPassword());
//        contentValues.put(ACCOUNT_FULL_NAME, account.getFullName());
//        contentValues.put(ACCOUNT_DATE_OF_BIRTH, account.getDateOfBirth());
//        contentValues.put(ACCOUNT_PHONE, account.getPhone());
//        contentValues.put(ACCOUNT_QUESTION, account.getQuestion());
//        contentValues.put(ACCOUNT_ANSWER, account.getAnswer());
//        sqLiteDatabase.update(DB_TABLE_ACCOUNT, contentValues, "ID = ?", new String[]{String.valueOf(account.getID())});
//    }
//
//    public void deleteAccount() {
//        sqLiteDatabase = getWritableDatabase();
//        sqLiteDatabase.delete(DB_TABLE_ACCOUNT, null, null);
//    }

    public List<Product> getAllPrduct() {
        List<Product> productList = new ArrayList<>();
        Product product;
        sqLiteDatabase = getReadableDatabase();
        try {
            cursor = sqLiteDatabase.query(false, DB_TABLE_PRODUCT, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                String ID = cursor.getString(cursor.getColumnIndex(ACCOUNT_ID));
                String nameProduct = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
                long price = cursor.getLong(cursor.getColumnIndex(PRODUCT_PRICE));
                int amount = cursor.getInt(cursor.getColumnIndex(PRODUCT_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndex(PRODUCT_TYPE));
                String describe = cursor.getString(cursor.getColumnIndex(PRODUCT_DESCRIBE));
                int image = cursor.getInt(cursor.getColumnIndex(PRODUCT_IMAGE));
                String producer = cursor.getString(cursor.getColumnIndex(PRODUCT_PRODUCER));
                productList.add(new Product(ID+"",nameProduct,price,amount,type,describe,image,producer));
            }
        return productList;
    }
}
