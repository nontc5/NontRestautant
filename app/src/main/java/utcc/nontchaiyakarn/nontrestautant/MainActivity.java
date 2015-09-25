package utcc.nontchaiyakarn.nontrestautant;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private UserTABLE objUserTABLE;
    private foodTABLE objFoodTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create and Connect SQLite
        createAndConnected();


        //Test Add New Value
        //testAddNewValue();


        // Test Delete All Data
        //deleteAllData();

        // Synchronize Data From JSON to SQLite
        synJSONtoSQLite();

    }   // onCreate

    private void synJSONtoSQLite() {

        // Step ในการทำการ Synchronize ข้อมูลจาก JSON To SQLite
        // ข้อที่ 0.  Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);


        // ข้อที่ 1. Sync ข้อมูลจาก Table โดยค่อยๆทำทีละ Table
        int intTimes = 0;    // จำนวนครั้ง
        while (intTimes <= 1) {


            // Constant
            InputStream objInputStream = null;  // โหลดไปใช้ไป
            String strJSON = null;  // จะเปลี่ยน Input Stream ให้เป็น String
            



            intTimes += 1;  // บวกทีละ 1

        }   // while

    }   // Sync JSON TO SQLite

    private void deleteAllData() {

        SQLiteDatabase objSQSqLiteDatabase = openOrCreateDatabase("Restaurant.db", MODE_PRIVATE, null);  // ทำตัวเชื่อมต่อกับ DB
        objSQSqLiteDatabase.delete("userTABLE", null, null);
        objSQSqLiteDatabase.delete("foodTABLE", null, null);

    }

    private void testAddNewValue() {

        objUserTABLE.addNewUser("testUser", "testPass", "นนท์ ไชยกาล");
        objFoodTABLE.addNewFood("testFood", "Desktop", "99");

    }

    private void createAndConnected() {

        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new foodTABLE(this);

    }


}   // Main Class
