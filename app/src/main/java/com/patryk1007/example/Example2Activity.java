package com.patryk1007.example;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.patryk1007.fillme.FillMe;
import com.patryk1007.fillme.enums.FillMode;

public class Example2Activity extends AppCompatActivity {

    CheckBox fillVertical;
    CheckBox fillHorizontal;
    FillMe fillMeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        initView();
    }

    private void initView() {
        fillMeView = (FillMe) findViewById(R.id.fill_me_view);
        fillMeView.setConvexFigure(false);
        fillMeView.setFillMode(FillMode.BOTH);
        fillMeView.setFillPercentHorizontalAndVertical(1.0f, 1.0f);
        fillMeView.setImage(BitmapFactory.decodeResource(getResources(),
                R.drawable.mess));

        fillVertical = (CheckBox) findViewById(R.id.fill_vertical_checkbox);
        fillVertical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fillMeView.setFillMode(getMode());
            }
        });

        fillHorizontal = (CheckBox) findViewById(R.id.fill_horizontal_checkbox);
        fillHorizontal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fillMeView.setFillMode(getMode());
            }
        });

    }

    private FillMode getMode() {
        if (fillVertical.isChecked() && fillHorizontal.isChecked()) {
            return FillMode.BOTH;
        }
        if (fillVertical.isChecked()) {
            return FillMode.VERTICAL;
        }
        if (fillHorizontal.isChecked()) {
            return FillMode.HORIZONTAL;
        }
        return FillMode.NONE;
    }
}
