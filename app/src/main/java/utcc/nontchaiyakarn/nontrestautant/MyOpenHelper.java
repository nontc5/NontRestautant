package utcc.nontchaiyakarn.nontrestautant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nontchaiyakarn on 9/24/15 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit ประกาศตัวแปร

    private static final String DATABASE_NAME = "Restaurant.db";
    private static final int DATABASE_VERSION = 1;  // ประกาศตัวแปร ที่เป็นค่าคงที่ ไม่สามารถเปลี่ยนแปลงได้
    private static final String CREATE_USER = "create table userTABLE (_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Name text);";   // Table ที่จะสร้างมีองค์ประกอบอะไรบ้าง โดยใช้คำสั่ง SQL ของ SQLite *** Column แรก จะต้องขึ้นต้นด้วย _id เสมอ!!!


    private static final String CREATE_FOOD = "create table foodTABLE (_id integer primary key, " +
            "Food text, " +
            "Source text, " +
            "Price text);";


    private static final String CREATE_DATA = "create table dataTABLE (_id integer primary key, " +
            "Subject text, " +
            "IMG text, " +
            "Type text);";



    public MyOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);


    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_FOOD);
        sqLiteDatabase.execSQL(CREATE_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}    // Main Class
