package com.clicker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
	public native double nativeGetFps();

	private Handler fpsHandler = new Handler();
	private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView countView = findViewById(R.id.countView);
		TextView fpsView = findViewById(R.id.fpsView);
        Button clickBtn   = findViewById(R.id.clickBtn);

        clickBtn.setOnClickListener(v -> {
            long count = nativeClick();
            countView.setText(String.valueOf(count));

			// Анимация кнопки сжимание

			/* Удалена */

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
			down.playTogether(textScaleXUp, textScaleXDown);

			AnimatorSet up = new AnimatorSet();
			up.playTogether(textScaleXDown, textScaleYDown);

			AnimatorSet full = new AnimatorSet();
			full.playSequentially(down, up);
			full.start();
        });

		// Fps loop каждые 16мс
		running = true;
        android.view.Choreographer.getInstance().postFrameCallback(new android.view.Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (!running) return;
                double fps = nativeGetFps();
                fpsView.setText(String.format("FPS: %.0f", fps));
                android.view.Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		running = false;
		fpsHandler.removeCallbacksAndMessages(null);
	}
}
