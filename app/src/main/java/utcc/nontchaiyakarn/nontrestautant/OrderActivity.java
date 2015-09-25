package utcc.nontchaiyakarn.nontrestautant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private TextView officeTextView;
    private Spinner deskSpinner;
    private ListView foodListView;
    private String officerString, deskString, foodString, itemString;    //จะเอา 4 ตัวนี้โยนขึ้น DB



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bindWidget();

        showOfficer();

        createSpinner();

        createListView();

    }   // onCreate Method

    private void createListView() {

        foodTABLE objFoodTABLE = new foodTABLE(this);

        String[] foodStrings = objFoodTABLE.readAllFood();
        String[] sourceStrings = objFoodTABLE.readAllSource();
        String[] priceStrings = objFoodTABLE.readAllPrice();

        MyAdapter objMyAdapter = new MyAdapter(OrderActivity.this, sourceStrings, foodStrings, priceStrings);
        foodListView.setAdapter(objMyAdapter);

    }   // Create ListView

    private void createSpinner() {

        final String[] myDesk = {"1A","2A","3A","4A"};    // เบอร์โต๊ะ
        ArrayAdapter<String> deskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myDesk);
        deskSpinner.setAdapter(deskAdapter);

        deskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {  //

                deskString = myDesk[i];



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {     //ถ้ายังไม่เคยกด จะให้แสดงยังไง

                deskString = myDesk[0];

            }
        });


    }


    private void bindWidget() {

        officeTextView = (TextView) findViewById(R.id.textView);    // เอาชื่อมาจาก Widget นั้นๆเลย
        deskSpinner = (Spinner) findViewById(R.id.spinner);         // เอาชื่อมาจาก Widget นั้นๆเลย
        foodListView = (ListView) findViewById(R.id.listView);      // เอาชื่อมาจาก Widget นั้นๆเลย

        showOfficer();

    }

    private void showOfficer() {

        officerString = getIntent().getStringExtra("Name");     // เอาชื่อที่ส่งมา มาเก็บใน officerString
        officeTextView.setText(officerString);                  // เอาตัวแปรนั้นอ่ะ มาโชว์ใน TextView

    }
}   // Main Class

