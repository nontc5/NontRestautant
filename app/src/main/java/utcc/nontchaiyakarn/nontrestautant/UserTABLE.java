package utcc.nontchaiyakarn.nontrestautant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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


    // Search หา User
    public String[] searchUser(String strUser) {

        try {

            String[] strResult = null;
            Cursor objCursor = readSqLiteDatabase.query(TABLE_USER,     //Table ตัวไหน
                    new String[]{COLUMN_ID_USER,COLUMN_USER,COLUMN_PASSWORD,COULUMN_NAME},  //ใช้ Column ไหนบ้าง
                    COLUMN_USER + "=?",     // ใช้ Column ไหนเป็นตัว Search
                    new String[]{String.valueOf(strUser)},
                    null, null, null, null);

            if (objCursor != null) {

                if (objCursor.moveToFirst()) {

                    strResult = new String[4];  // ใส่ 4 เพราะมี 4 Column
                    strResult[0] = objCursor.getString(0);
                    strResult[1] = objCursor.getString(1);
                    strResult[2] = objCursor.getString(2);
                    strResult[3] = objCursor.getString(3);



                }   // if
            }   // if

            objCursor.close();  // คืนหน่วยความจำ
            return strResult;


        } catch (Exception e) {

            return null;

        }


        //return new String[0];
    }


    // Update User
    public long addNewUser(String strUser, String strPassword, String strName) {

        ContentValues objContentValues = new ContentValues();   //สะพานส่งข้อมูล
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COULUMN_NAME, strName);

        return writeSqLiteDatabase.insert(TABLE_USER, null, objContentValues);
    }   //


}       // Main Class
