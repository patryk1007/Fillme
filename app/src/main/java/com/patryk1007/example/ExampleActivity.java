package com.patryk1007.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.patryk1007.fillme.FillMe;
import com.patryk1007.fillme.listeners.OnFillChangeListener;

public class ExampleActivity extends AppCompatActivity {

    SeekBar fillVertical;
    SeekBar fillHorizontal;
    TextView fillVerticalInfo;
    TextView fillHorizontalInfo;
    FillMe fillMeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        initView();
    }

    private void initView() {
        fillHorizontalInfo = (TextView) findViewById(R.id.fill_horizontal_info);
        fillVerticalInfo = (TextView) findViewById(R.id.fill_vertical_info);

        fillMeView = (FillMe) findViewById(R.id.fill_me_view);
        fillMeView.setImageDrawableId(R.drawable.icone);
        fillMeView.setFillPercentHorizontalAndVertical(0, 0);

        fillMeView.setOnFillChangeListener(new OnFillChangeListener() {
            @Override
            public void onHorizontalValueChange(float fillPercent, int fillPx) {
                fillHorizontalInfo.setText(String.format(getString(R.string.fill_horizontal_info), String.valueOf(fillPercent), String.valueOf(fillPx)));
            }

            @Override
            public void onVerticalValueChange(float fillPercent, int fillPx) {
                fillVerticalInfo.setText(String.format(getString(R.string.fill_vertical_info), String.valueOf(fillPercent), String.valueOf(fillPx)));
            }
        });

        fillVertical = (SeekBar) findViewById(R.id.fill_vertical_percent);
        fillVertical.setMax(100);
        fillVertical.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fillMeView.setFillPercentVertical(progress / 100.0f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fillHorizontal = (SeekBar) findViewById(R.id.fill_horizontal_percent);
        fillHorizontal.setMax(100);
        fillHorizontal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fillMeView.setFillPercentHorizontal(progress / 100.0f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.colour_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillMeView.setFillColour(0xff0099cc);
            }
        });

        findViewById(R.id.colour_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillMeView.setFillColour(0xffff4444);
            }
        });

        findViewById(R.id.colour_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillMeView.setFillColour(0xff99cc00);
            }
        });

    }
}
