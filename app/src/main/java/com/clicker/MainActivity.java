package com.clicker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("clicker"); // грузим наш .so
    }

    // объявляем нативные методы
    public native long nativeClick();
    public native void nativeReset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView countView = findViewById(R.id.countView);
        Button clickBtn   = findViewById(R.id.clickBtn);

        clickBtn.setOnClickListener(v -> {
            long count = nativeClick();
            countView.setText(String.valueOf(count));

			// Анимация кнопки сжимание
			ObjectAnimator btnScaleXDown = ObjectAnimator.ofFloat(clickBtn, "ScaleX", 1f, 0.85f);
			ObjectAnimator btnScaleYDown = ObjectAnimator.ofFloat(clickBtn, "ScaleY", 1f, 0.85f);
			ObjectAnimator btnScaleXUp = ObjectAnimator.ofFloat(clickBtn, "ScaleX", 0.85f, 1f);
			ObjectAnimator btnScaleYUp = ObjectAnimator.ofFloat(clickBtn, "ScaleY", 0.85f, 1f);

			btnScaleXDown.setDuration(80);
			btnScaleYDown.setDuration(80);
			btnScaleXUp.setDuration(120);
			btnScaleYUp.setDuration(120);

			btnScaleXDown.setInterpolator(new LinearInterpolator());
			btnScaleYDown.setInterpolator(new LinearInterpolator());
			btnScaleXUp.setInterpolator(new LinearInterpolator());
			btnScaleYUp.setInterpolator(new LinearInterpolator());

			// Анимация текста
			ObjectAnimator textScaleXUp = ObjectAnimator.ofFloat(countView, "ScaleX", 1f, 1.3f);
			ObjectAnimator textScaleYUp = ObjectAnimator.ofFloat(countView, "ScaleY", 1f, 1.3f);
			ObjectAnimator textScaleXDown = ObjectAnimator.ofFloat(countView, "ScaleX", 1.3f, 1f);
			ObjectAnimator textScaleYDown = ObjectAnimator.ofFloat(countView, "ScaleY", 1.3f, 1f);

			textScaleXUp.setDuration(80);
			textScaleYUp.setDuration(80);
			textScaleXDown.setDuration(120);
			textScaleYDown.setDuration(120);

			textScaleXUp.setInterpolator(new LinearInterpolator());
			textScaleYUp.setInterpolator(new LinearInterpolator());
			textScaleXDown.setInterpolator(new LinearInterpolator());
			textScaleYDown.setInterpolator(new LinearInterpolator());

			AnimatorSet down = new AnimatorSet();
			down.playTogether(btnScaleXDown, btnScaleYDown, btnScaleXUp, btnScaleYUp);

			AnimatorSet up = new AnimatorSet();
			up.playTogether(btnScaleXUp, btnScaleYUp, textScaleXDown, textScaleYDown);

			AnimatorSet full = new AnimatorSet();
			full.playSequentially(down, up);
			full.start();
        });
    }
}
