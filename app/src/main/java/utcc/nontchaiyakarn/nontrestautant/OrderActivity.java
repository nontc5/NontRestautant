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

    private ListView foodListView;
    private String officerString, deskString, foodString, itemString;    //จะเอา 4 ตัวนี้โยนขึ้น DB



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bindWidget();

        createListView();

    }   // onCreate Method

    private void createListView() {

        DataTABLE objDataTABLE = new DataTABLE(this);

        String[] subjectStrings = objDataTABLE.readAllSubject();
        String[] imgStrings = objDataTABLE.readAllImg();
        String[] typeStrings = objDataTABLE.readAllType();

        MyAdapter objMyAdapter = new MyAdapter(OrderActivity.this, imgStrings, subjectStrings, typeStrings);
        foodListView.setAdapter(objMyAdapter);

    }   // Create ListView



    private void bindWidget() {

        foodListView = (ListView) findViewById(R.id.listView);      // เอาชื่อมาจาก Widget นั้นๆเลย


    }


}   // Main Class

