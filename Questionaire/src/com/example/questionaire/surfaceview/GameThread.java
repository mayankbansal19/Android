package com.example.questionaire.surfaceview;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	private static final String TAG = "BoilerPlate";
	private SurfaceHolder surfaceHolder;
	private volatile boolean mRun;
	private IGameLooper gameLooper;

	private int noOfFramesPerSecond;

	public Timer timer;
	private TimerTask calculateFPS;

	public Boolean getRun() {
		return mRun;
	}

	public void setRun(Boolean mRun) {
		this.mRun = mRun;
	}

	public GameThread(SurfaceHolder surfaceHolder, GameSurface gamePanel,
			IGameLooper looper) {
		Log.d(TAG, "GameThread Constructor");
		this.surfaceHolder = surfaceHolder;
		this.gameLooper = looper;
		noOfFramesPerSecond = 0;
		timer = new Timer();
		calculateFPS = new TimerTask() {
			@Override
			public void run() {
				// Log.d(TAG, "Running FPS task");
				calculateFPS();
			}
		};
		timer.schedule(calculateFPS, 0, 1000);
	}

	@Override
	public void run() {
		Log.d(TAG, "Inside run");
		Canvas canvas = null;
		long currentFrameTime;
		long delta = 0;
		long lastFrameTime = System.currentTimeMillis();
		while (mRun) {
			currentFrameTime = System.currentTimeMillis();
			delta = currentFrameTime - lastFrameTime;
			DebugInfo.instantaneousFrameRate = (1f/delta) * 1000;
			lastFrameTime = currentFrameTime;
		
			canvas = surfaceHolder.lockCanvas();
			try {
				synchronized (surfaceHolder) {
					gameLooper.update(delta);
					gameLooper.render(canvas);
					noOfFramesPerSecond++;
//					Log.w("Delta", "Delta in Game Loop: " + delta);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					// Log.d(TAG, "Inside finally");
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	private void calculateFPS() {
		synchronized (surfaceHolder) {
			// Log.e(TAG, "Calculating FPS==========================");
			DebugInfo.FPS = noOfFramesPerSecond;
			noOfFramesPerSecond = 0;
		}
	}

}
