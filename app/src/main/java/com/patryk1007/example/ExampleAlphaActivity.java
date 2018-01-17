package com.patryk1007.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.patryk1007.fillme.FillMe;

public class ExampleAlphaActivity extends AppCompatActivity {

    SeekBar fillHorizontal;
    TextView alphaLevelInfo;
    FillMe fillMeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_alpha);
        initView();
    }

    private void initView() {
        alphaLevelInfo = (TextView) findViewById(R.id.fill_horizontal_info);

        fillMeView = (FillMe) findViewById(R.id.fill_me_view);
        fillMeView.setImageDrawableId(R.drawable.circle_with_shadow);

        fillHorizontal = (SeekBar) findViewById(R.id.fill_horizontal_percent);
        fillHorizontal.setMax(255);
        fillHorizontal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fillMeView.setAlphaLevel(progress);
                alphaLevelInfo.setText(String.format(getString(R.string.fill_alpha_info), String.valueOf(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
