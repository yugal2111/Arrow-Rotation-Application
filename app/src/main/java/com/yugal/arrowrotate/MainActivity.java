package com.yugal.arrowrotate;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Animation rotateAnimation;
    ImageView arrow;
    Button topLeft, topRight, bottomLeft, bottomRight;
    private static final String TAG = "MainActivity";
    private int i;
    private float startAngle = 0;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(MainActivity.this, "Landscape Mode", Toast.LENGTH_SHORT).show();
            topLeft.setVisibility(View.VISIBLE);
            topRight.setVisibility(View.VISIBLE);
            bottomRight.setVisibility(View.VISIBLE);
            bottomLeft.setVisibility(View.VISIBLE);
            startArrow();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Toast.makeText(MainActivity.this, "Portrait Mode", Toast.LENGTH_SHORT).show();
            topLeft.setVisibility(View.GONE);
            topRight.setVisibility(View.GONE);
            bottomRight.setVisibility(View.GONE);
            bottomLeft.setVisibility(View.GONE);
            startAngle = 0;
            stopArrow(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        startAngle=0;
        setContentView(R.layout.activity_main);

        findAllbyid();


        if (rotation == 0) {
            topLeft.setVisibility(View.GONE);
            topRight.setVisibility(View.GONE);
            bottomRight.setVisibility(View.GONE);
            bottomLeft.setVisibility(View.GONE);
            startAngle = 0;
            stopArrow(0);
        }
            i = 0;
            topLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detectDoubleOrSingleClick(300, 110);
                    //stopArrow(325);
                }
            });

            topRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detectDoubleOrSingleClick(65, 245);
                    //stopArrow(45);
                }
            });

            bottomLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detectDoubleOrSingleClick(245, 65);
                    //stopArrow(225);
                }
            });

            bottomRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detectDoubleOrSingleClick(110, 300);
                    //stopArrow(155);
                }
            });
    }

    private void findAllbyid() {
        arrow = findViewById(R.id.imageView);
        topLeft = findViewById(R.id.topLeft);
        topRight = findViewById(R.id.topRight);
        bottomLeft = findViewById(R.id.bottomLeft);
        bottomRight = findViewById(R.id.bottomRight);
    }

    private void detectDoubleOrSingleClick(float singleClickAngle, float doubleClickAngle) {
        i++;
        Handler handler = new Handler();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                i = 0;
            }
        };

        if (i == 1) {
            //Single click
            handler.postDelayed(r, 250);
            Toast.makeText(this, "On single Tap", Toast.LENGTH_SHORT).show();
            stopArrow(singleClickAngle);
        } else if (i == 2) {
            //Double click
            i = 0;
            Toast.makeText(this, "On double Tap", Toast.LENGTH_SHORT).show();
            stopArrow(doubleClickAngle);
        }
    }

    private void stopArrow(float endAngle) {
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        final RotateAnimation animRotate = new RotateAnimation(startAngle, endAngle,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animRotate.setDuration(1000);
        animRotate.setFillAfter(true);
        animSet.addAnimation(animRotate);
        arrow.startAnimation(animSet);
        startAngle = endAngle;
    }

    private void startArrow() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        arrow.startAnimation(rotateAnimation);
    }

}