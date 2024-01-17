package com.rpgmap;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class ValueWrapper {
    private static final int min = 0;
    private int value;

    @SuppressLint("ClickableViewAccessibility")
    public ValueWrapper(TextView textView, ImageButton increaseButton, ImageButton decreaseButton, String format, int max, int delay) {

        CountDownTimer increase = new CountDownTimer(Long.MAX_VALUE, delay) {
            @Override
            public void onTick(long millisUntilFinished) {
                value = value == max ? min : value + 1;
                textView.setText(String.format(Locale.getDefault(), format, value));
            }

            @Override
            public void onFinish() {}
        };

        CountDownTimer decrease = new CountDownTimer(Long.MAX_VALUE, delay) {
            @Override
            public void onTick(long millisUntilFinished) {
                value = value == min ? max : value - 1;
                textView.setText(String.format(Locale.getDefault(), format, value));
            }

            @Override
            public void onFinish() {}
        };

        increaseButton.setOnTouchListener((v, ev) -> {
            switch(ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    increase.start(); break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    increase.cancel(); break;
            }
            return true;
        });

        decreaseButton.setOnTouchListener((v, ev) -> {
            switch(ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    decrease.start(); break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    decrease.cancel(); break;
            }
            return true;
        });
    }

    public int getValue() {
        return value;
    }
}
