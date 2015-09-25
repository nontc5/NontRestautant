package utcc.nontchaiyakarn.nontrestautant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nontchaiyakarn on 9/24/15 AD.
 */
public class foodTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String TABLE_USER = "foodTABLE";  // ชื่อ Table
    public static final String COLUMN_ID_FOOD = "_id";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";




    public foodTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    public String[] readAllPrice() {

        String[] MyResult = null;
        Cursor objCursor = readSqLiteDatabase.query(TABLE_USER,
                new String[]{COLUMN_ID_FOOD,COLUMN_PRICE},
                null, null, null, null, null);

        if (objCursor != null) {

            objCursor.moveToFirst();
            MyResult = new String[objCursor.getCount()];

            for (int i = 0; i < objCursor.getCount(); i++) {

                MyResult[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_PRICE));
                objCursor.moveToNext();


            }   // For

        } // If

        return MyResult;
    }   // Method  readALlPrice


    public String[] readAllSource() {

        String[] MyResult = null;
        Cursor objCursor = readSqLiteDatabase.query(TABLE_USER,
                new String[]{COLUMN_ID_FOOD,COLUMN_SOURCE},
                null, null, null, null, null);

        if (objCursor != null) {

            objCursor.moveToFirst();
            MyResult = new String[objCursor.getCount()];

            for (int i = 0; i < objCursor.getCount(); i++) {

                MyResult[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_SOURCE));
                objCursor.moveToNext();


            }   // For

        } // If

        return MyResult;
    }   // Method  readALlSource


    public String[] readAllFood() {

        String[] MyResult = null;
        Cursor objCursor = readSqLiteDatabase.query(TABLE_USER,
                new String[]{COLUMN_ID_FOOD,COLUMN_FOOD},
                null, null, null, null, null);

        if (objCursor != null) {

            objCursor.moveToFirst();
            MyResult = new String[objCursor.getCount()];

            for (int i = 0; i < objCursor.getCount(); i++) {

                MyResult[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_FOOD));
                objCursor.moveToNext();


            }   // For

        } // If

        return MyResult;
    }   // Method  readALlFood


    public long addNewFood (String strFood, String strSource, String strPrice) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_FOOD, strFood);
        objContentValues.put(COLUMN_SOURCE, strSource);
        objContentValues.put(COLUMN_PRICE, strPrice);


        return writeSqLiteDatabase.insert(TABLE_USER, null, objContentValues);
    }


}   // Main Class
