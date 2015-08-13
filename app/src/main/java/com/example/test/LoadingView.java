package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class LoadingView extends ImageView implements Runnable {
	public static boolean isStop = false;
	private int counter = 0;

	private int[] imageIds = new int[6];
	private int index = 0;
	private int length = 1;
	
	private Thread th;

	public LoadingView(Context context) {
		this(context, null);
		
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setImageIds();
	}

	public void setImageIds() {
		imageIds[0] = R.drawable.loader_frame_1;
		imageIds[1] = R.drawable.loader_frame_2;
		imageIds[2] = R.drawable.loader_frame_3;
		imageIds[3] = R.drawable.loader_frame_4;
		imageIds[4] = R.drawable.loader_frame_5;
		imageIds[5] = R.drawable.loader_frame_6;
		if (imageIds != null && imageIds.length > 0) {
			length = imageIds.length;
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		isStop = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (imageIds != null && imageIds.length > 0) {
			this.setImageResource(imageIds[index]);
		}
	}

	@Override
	public void run() {
		while (MainActivity.backendRunning) {
			index = ++index % length;
			postInvalidate();
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void startAnim() {
		(th = new Thread(this)).start();
	}
	
}
