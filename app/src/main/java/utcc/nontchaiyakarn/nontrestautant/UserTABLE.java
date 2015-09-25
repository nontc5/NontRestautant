package utcc.nontchaiyakarn.nontrestautant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nontchaiyakarn on 9/24/15 AD.
 */
public class UserTABLE {

    // Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String TABLE_USER = "userTABLE";        // ชื่อ Table
    public static final String COLUMN_ID_USER = "_id";          // ชื่อ Column
    public static final String COLUMN_USER = "User";            // ชื่อ Column
    public static final String COLUMN_PASSWORD = "Password";    // ชื่อ Column
    public static final String COULUMN_NAME = "Name";           // ชื่อ Column


    public UserTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);  // สร้าง Object ใช้เชื่อมต่อ
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase(); // ใช้เขียน
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase(); // ใช้อ่าน

    }   // Constructor

    // Update User
    public long addNewUser(String strUser, String strPassword, String strName) {

        ContentValues objContentValues = new ContentValues();   //สะพานส่งข้อมูล
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COULUMN_NAME, strName);

        return writeSqLiteDatabase.insert(TABLE_USER, null, objContentValues);
    }   //


}       // Main Class
