package utcc.nontchaiyakarn.nontrestautant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private DataTABLE objDataTABLE;
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

        // Intent to Order Activitites  เมื่อคลิกแล้วจะให้ไปี่หน้า Order Activitites
        Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
        //objIntent.putExtra("Name", strName);    //Name เป็น Key ที้่ใช้ในการโยน  Data ไปอีกหน้า
        startActivity(objIntent);

    }   // ClickLogin


    private void welcome(final String strName) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle("Welcome");
        objBuilder.setMessage("Welcome " + strName);
        objBuilder.setCancelable(false);        // ถ้าต้องการปิด Popup จะกดไม่ได้ ต้องกด OK อย่างเดียว
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // ถ้ามีกา Click จะให้ทำอะไร

                // Intent to Order Activitites  เมื่อคลิกแล้วจะให้ไปี่หน้า Order Activitites
                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
                objIntent.putExtra("Name", strName);    //Name เป็น Key ที้่ใช้ในการโยน  Data ไปอีกหน้า
                startActivity(objIntent);


                dialogInterface.dismiss();
                finish();   // เมื่อกด Undo ที่โทรศัพท์ ให้ออกจาก App เลย ต้อง Login ใหม่


            }
        });
        objBuilder.show();


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
            String strURLData = "http://nontc5.utcc-ict.com/AdmissionNews/JSONGetData.php";
            HttpPost objHttpPost;   // ประกาศตัวแปรไว้

            // ข้อที่ 1. Create InputStream   ทำให้มันโหลดแบบ Streaming ให้ได้ก่อน
            try {   // สิ่งที่เสี่ยงต่อการ Error ใส่ในนี้

                HttpClient objHttpClient = new DefaultHttpClient();
                if (intTimes != 1) {

                    objHttpPost = new HttpPost(strUrlUser);

                } else {

                    objHttpPost = new HttpPost(strURLData);

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
                        String strSubject = object.getString("DataSubject");
                        String strImg = object.getString("DataIMG");
                        String strType = object.getString("EventsType");

                        objDataTABLE.addNewData(strSubject, strImg, strType);
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
        objSQSqLiteDatabase.delete("dataTABLE", null, null);

    }

    private void testAddNewValue() {

        objUserTABLE.addNewUser("testUser", "testPass", "นนท์ ไชยกาล");
        objFoodTABLE.addNewFood("testFood", "Desktop", "99");
        objDataTABLE.addNewData("Subjectนะ", "Image", "สอบตรง");

    }

    private void createAndConnected() {

        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new foodTABLE(this);
        objDataTABLE = new DataTABLE(this);

    }


}   // Main Class
