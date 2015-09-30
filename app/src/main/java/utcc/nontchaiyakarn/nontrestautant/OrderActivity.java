package utcc.nontchaiyakarn.nontrestautant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private ListView foodListView;
    private String subjectString, idString;    //จะเอา 4 ตัวนี้โยนขึ้น DB



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bindWidget();

        createListView();

    }   // onCreate Method

    private void createListView() {

        DataTABLE objDataTABLE = new DataTABLE(this);

        final String[] subjectStrings = objDataTABLE.readAllSubject();
        String[] imgStrings = objDataTABLE.readAllImg();
        String[] typeStrings = objDataTABLE.readAllType();

        MyAdapter objMyAdapter = new MyAdapter(OrderActivity.this, imgStrings, subjectStrings, typeStrings);
        foodListView.setAdapter(objMyAdapter);

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subjectString = subjectStrings[i];


                // ไปเปิดอีกหน้า
                    Intent objIntent = new Intent(OrderActivity.this, DetailActivity.class);
                    objIntent.putExtra("key", subjectString);    //Name เป็น Key ที้่ใช้ในการโยน  Data ไปอีกหน้า
                    startActivity(objIntent);

                //chooseItem(subjectStrings[i]);  // แสดง Popup Dialog box
            }
        });

    }   // Create ListView

    private void chooseItem(String foodString) {

        final CharSequence[] choiceCharSequences = {"1 set", "2 set", "3 set", "4 set", "5 set"};
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setTitle(foodString);
        objBuilder.setSingleChoiceItems(choiceCharSequences, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int intItem = i + 1;
                subjectString = Integer.toString(intItem);
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }

    private void bindWidget() {

        foodListView = (ListView) findViewById(R.id.listView);      // เอาชื่อมาจาก Widget นั้นๆเลย


    }


}   // Main Class

