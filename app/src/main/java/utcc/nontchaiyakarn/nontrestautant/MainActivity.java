package utcc.nontchaiyakarn.nontrestautant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private UserTABLE objUserTABLE;
    private foodTABLE objFoodTABLE;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;  // เปลี่ยนค่าจากใน Textbox แล้วมาเป็ฯไว้ในตัวแปรนี้
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        // Bind Widget เอาตัวแปรมาผูกกับ Widget
        bindWidget();
        
        

        // Create and Connect SQLite
        createAndConnected();


        //Test Add New Value
        //testAddNewValue();


        // Test Delete All Data
        deleteAllData();

        // Synchronize Data From JSON to SQLite
        synJSONtoSQLite();

    }   // onCreate


    public void clickLogin(View view) {

        userString = userEditText.getText().toString().trim();  // Trim ตัดช่องว่างหน้าหลัง ออกอัตโนมัติ
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("") ) {  //ถ้ากรอกไม่ครบ

            // ถ้ามันว่างเปล่า (กรอกไม่ครบ)
            myAlertDialop("มีช่องว่าง","กรุณากรอกให้ครบทุกช่อง");

        } else {

            // ถ้ากรอกครบ  No Space
            checkUser();

        }

    }   // ClickLogin

    private void checkUser() {

        try {

            String[] strMyResult = objUserTABLE.searchUser(userString); // Check Username

            if (passwordString.equals(strMyResult[2])) {

                welcome(strMyResult[3]);


            } else {

                myAlertDialop("Password False","Please try again Password False !!!!!!!!");

            }

        } catch (Exception e) {

            myAlertDialop("No User", "No \""+userString+ "\" in my Database");

        }

    }   // Check User

    private void welcome(String strName) {



    }   //welcome

    private void myAlertDialop(String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);  // แสดงรูป Danger ใน Alert Dialog
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();  //กดปุ่ม OK แล้วให้ Dialog หายไป

            }
        });
        objBuilder.show();
    }   // My Alert

    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);



    }

    private void synJSONtoSQLite() {

        // Step ในการทำการ Synchronize ข้อมูลจาก JSON To SQLite
        // ข้อที่ 0.  Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);


        // Sync ข้อมูลจาก Table โดยค่อยๆทำทีละ Table
        int intTimes = 0;    // จำนวนครั้ง
        while (intTimes <= 1) {


            // Constant
            InputStream objInputStream = null;  // โหลดไปใช้ไป
            String strJSON = null;  // จะเปลี่ยน Input Stream ให้เป็น String
            String strUrlUser = "http://swiftcodingthai.com/24Sep/php_get_data_nont.php";   // URL ของไฟล์ JSON ตาราง User
            String strURLFood = "http://swiftcodingthai.com/24Sep/php_get_data_food_master.php";    // URL ของไฟล์ JSON ตารางอาหาร
            HttpPost objHttpPost;   // ประกาศตัวแปรไว้

            // ข้อที่ 1. Create InputStream   ทำให้มันโหลดแบบ Streaming ให้ได้ก่อน
            try {   // สิ่งที่เสี่ยงต่อการ Error ใส่ในนี้

                HttpClient objHttpClient = new DefaultHttpClient();
                if (intTimes != 1) {

                    objHttpPost = new HttpPost(strUrlUser);

                } else {

                    objHttpPost = new HttpPost(strURLFood);

                }

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();


            } catch (Exception e) { // ถ้า Error จะเข้ามาในนี้

                Log.d("Rest","InputStream ==>"+e.toString());

            }


            // ข้อที่ 2. Create strJSON     เปลี่ยนสิ่งที่เรา Streaming มาให้เป็น String
            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();   // ตัวที่ทำหน้าที่รวม
                String strLine = null;  // ตัวแปรที่รับตัวที่ถูกตัดมา

                while ((strLine = objBufferedReader.readLine())!= null ) {  // ถ้า strLine ว่างเปล่า ก็ออกจาก Loop

                    objStringBuilder.append(strLine);   // มีหน้าที่คอยผูก String ไปเรื่อย ๆ


                }   // While Loop
                objInputStream.close();                 // ถ้าหมด ก็ไม่ต้องโหลดต่อ
                strJSON = objStringBuilder.toString();


            } catch (Exception e) {

                Log.d("Rest", "strJSON ==> "+e.toString());

            }



            // ข้้อที่ 3. Update SQLite     เอา strJSON ที่ได้มา มาใส่ใน SQLite
            try {

                final JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {

                    JSONObject object = objJsonArray.getJSONObject(i);  // เอา i มาแทนค่าตำแหน่งของ Array

                    if (intTimes < 1) {

                        // สำหรับ UserTABLE
                        // ได้ String 3 ตัวสำหรับใส่ใน DB แล้ว
                        String strUser = object.getString("User");  // User เป็น Key ใน JSON
                        String strPassword = object.getString("Password");
                        String strName = object.getString("Name");

                        objUserTABLE.addNewUser(strUser, strPassword, strName);


                    } else {

                        // สำหรับ FoodTABLE
                        // ได้ String 3 ตัวสำหรับใส่ใน DB แล้ว
                        String strFood = object.getString("Food");
                        String strSource = object.getString("Source");
                        String strPrice = object.getString("Price");

                        objFoodTABLE.addNewFood(strFood, strSource, strPrice);
                    }

                }   // วิ่งวนตามจำนวน แถวใน JSON

            } catch (Exception e) {

                Log.d("Rest", "Update Error ==> "+e.toString());

            }

            intTimes += 1;  // บวกทีละ 1

        }   // while

    }   // ******  Sync JSON TO SQLite  *******

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
