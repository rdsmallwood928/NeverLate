package com.neverlate.NeverLate.activities.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neverlate.NeverLate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/10/14
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class AndroidClickSpinner extends LinearLayout {

    int currentIndex = 0;
    List<Object> spinnerModel;

    public AndroidClickSpinner(Context context, List<Object> values) {
        super(context);
        if(values == null) throw new NullPointerException();
        spinnerModel = new ArrayList<Object>(values);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.androidclickspinner, this, true);
        ImageButton upButton = (ImageButton) findViewById(R.id.clickSpinnerUp);
        upButton.setImageResource(R.drawable.arrow);

        final TextView middle = (TextView) findViewById(R.id.clickSpinnerTextView);
        middle.setText(spinnerModel.get(0).toString());
        middle.setGravity(Gravity.FILL_HORIZONTAL);

        ImageButton downButton = (ImageButton) findViewById(R.id.clickSpinnerDownButton);
        Bitmap bmpOriginal = BitmapFactory.decodeResource(this.getResources(), R.drawable.arrow);
        Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bmResult);
        tempCanvas.rotate(180, bmpOriginal.getWidth()/2, bmpOriginal.getHeight()/2);
        tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
        downButton.setImageBitmap(bmResult);


        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentIndex < spinnerModel.size()) {
                    currentIndex++;
                    middle.setText(spinnerModel.get(currentIndex).toString());
                }
            }
        });

        downButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(currentIndex > 0) {
                    currentIndex--;
                    middle.setText(spinnerModel.get(currentIndex).toString());
                }
            }
        });
    }

    public Object getSelectedItem() {
        return spinnerModel.get(currentIndex);
    }
}
