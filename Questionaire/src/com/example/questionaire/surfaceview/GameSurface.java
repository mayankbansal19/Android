package com.example.questionaire.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.aquimo.eventmanager.Event;
import com.aquimo.eventmanager.EventManager;
import com.aquimo.eventmanager.IEventListener;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback, IEventListener {
	private static final String TAG = "GameSurface";
	protected GameThread mGameThread;
	protected IGameLooper gameLooper;
	protected Runtime runtime;

	public GameSurface(Context context) {
		super(context);
		init(context, false);
	}
	
	public GameSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, true);
	}
	
	public GameSurface(Context context, boolean debugMode) {
		super(context);
		init(context, debugMode);
	}
	
	private void init(Context context, boolean debugMode) {
		Log.d(TAG, "Inside GamePanel constructor");
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DebugInfo.screenWidth = display.getWidth();
		DebugInfo.screenHeight = display.getHeight();
		
		runtime = Runtime.getRuntime();
		DebugInfo.maxMemory = (int) (runtime.maxMemory()/(1024 * 1024));
		getHolder().addCallback(this);
		setFocusable(true);
	}
	
	public void registerGameLooper(IGameLooper looper) {
		this.gameLooper = looper;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.e(TAG, "Surface Changed");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		EventManager.getInstance().registerEventListener(GameSurface.this, StartGameThreadEvent.class);
		mGameThread = new GameThread(getHolder(), this, gameLooper);
		gameLooper.init(getContext());
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	    boolean retry = true;
		Log.e(TAG, "Stopping timer");
		mGameThread.timer.cancel();
		mGameThread.timer.purge();
		mGameThread.setRun(false);
		Log.e(TAG, "Thread setRun set to false");
		while(retry) {
			try {
				Log.e(TAG, "Trying to join the game thread");
				mGameThread.join();
				retry = false;
				Log.e(TAG, "Thread joined!");
			} catch (InterruptedException e) {
				//Try shutting down the thread again
				e.printStackTrace();
			}
		}
		gameLooper.destroy();
		EventManager.getInstance().unRegisterEventListener(GameSurface.this, StartGameThreadEvent.class);
		Log.e(TAG, "Thread stopped");
	}

    public void update(long delta){
        // Log.d(TAG, "Inside parent update");
    }
	
	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gameLooper.onTouch(event);
		return true;
	}

    @Override
    public void onEvent(Event event)
    {
        if(event instanceof StartGameThreadEvent)
        {
            mGameThread.setRun(true);
            mGameThread.start();
            Log.d("StartGameThreadEvent", "gamesurface created end...");
        }
    }
}
