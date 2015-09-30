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

public class DetailActivity extends AppCompatActivity {

    private TextView DataTextView;
    private String idString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindWidget();

        showId();



    }


    private void chooseItem(String foodString) {

        final CharSequence[] choiceCharSequences = {"1 set", "2 set", "3 set", "4 set", "5 set"};
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setTitle(foodString);
        objBuilder.setSingleChoiceItems(choiceCharSequences, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int intItem = i + 1;
                idString = Integer.toString(intItem);
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }

    private void showId() {
        idString = getIntent().getStringExtra("key");
        DataTextView.setText(idString);
    }

    private void bindWidget() {

        DataTextView = (TextView) findViewById(R.id.textSubject);      // เอาชื่อมาจาก Widget นั้นๆเลย


    }


}   // Main Class

