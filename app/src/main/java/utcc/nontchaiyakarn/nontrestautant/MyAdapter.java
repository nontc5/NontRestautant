package utcc.nontchaiyakarn.nontrestautant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by nontchaiyakarn on 9/25/15 AD.
 */
public class MyAdapter extends BaseAdapter{


    private Context objContext;
    private String[] imgStrings, subjectStrings, typeStrings;    // ตามจำนวนวัตถุที่อยู่ใน List นั้น ๆ

    public MyAdapter(Context objContext, String[] imgStrings, String[] subjectStrings, String[] typeStrings) {
        this.objContext = objContext;
        this.imgStrings = imgStrings;
        this.subjectStrings = subjectStrings;
        this.typeStrings = typeStrings;
    }   // Constructor

    @Override
    public int getCount() { // จะวน Loop กี่ครั้ง
        return subjectStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = objLayoutInflater.inflate(R.layout.food_listview, viewGroup, false);

        // for title รายชื่ออาหาร
        TextView foodTextView = (TextView) view1.findViewById(R.id.textView2);
        foodTextView.setText(subjectStrings[i]);


        // for price
        TextView priceTextView = (TextView) view1.findViewById((R.id.textView3));
        priceTextView.setText(typeStrings[i]);


        //for IMAGE
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView2);
        Picasso.with(objContext).load(imgStrings[i]).resize(120,120).into(iconImageView);



        return view1;
    }
}   // Main Class
