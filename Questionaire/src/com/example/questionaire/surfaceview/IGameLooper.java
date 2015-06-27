package com.example.questionaire.surfaceview;

import com.aquimo.eventmanager.EventManager;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IGameLooper {
	void init(Context context);
	void update(long delta);
	void render(Canvas canvas);
	void onTouch(MotionEvent event);
	void destroy();
}
