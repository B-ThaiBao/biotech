package com.hcmut.assignment.scalejoystick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.hcmut.assignment.biotech.R;

public class ScaleJoystickView extends View {
	private int centerX, centerY;
	private Paint innerFillPaint, innerStrokePaint, outerFillPaint, outerStrokePaint;
	private Point touchPoint = new Point();
	private int innerRadius, outerWidth, outerHeight;
	private ScaleJoystickListener scaleJoystickListener;
	public ScaleJoystickView(Context context) {
		super(context);
	}
	public ScaleJoystickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initScaleJoystick();
	}

	public void setScaleJoystickListener(ScaleJoystickListener listener) {
		this.scaleJoystickListener = listener;
	}

	private void initScaleJoystick() {
		innerFillPaint = new Paint();
		innerFillPaint.setColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
		innerFillPaint.setStyle(Paint.Style.FILL);
		innerFillPaint.setAntiAlias(true);

		innerStrokePaint = new Paint();
		innerStrokePaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
		innerStrokePaint.setStyle(Paint.Style.STROKE);
		innerStrokePaint.setStrokeWidth(20f);
		innerStrokePaint.setAntiAlias(true);

		outerFillPaint = new Paint();
		outerFillPaint.setColor(ContextCompat.getColor(getContext(), R.color.light_blue));
		outerFillPaint.setStyle(Paint.Style.FILL);

		outerStrokePaint = new Paint();
		outerStrokePaint.setColor(ContextCompat.getColor(getContext(), R.color.white_blur));
		outerStrokePaint.setStyle(Paint.Style.STROKE);
		outerStrokePaint.setStrokeWidth(16f);
		outerStrokePaint.setAntiAlias(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
		outerHeight = Math.min(w, h) / 2 - 100;
		innerRadius = outerHeight * 2 / 5;
		outerWidth = innerRadius;
		touchPoint.set(centerX, centerY);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRoundRect(centerX - outerWidth, centerY - outerHeight, centerX + outerWidth, centerY + outerHeight, 50, 50, outerFillPaint);
		canvas.drawRoundRect(centerX - outerWidth, centerY - outerHeight + 8, centerX + outerWidth, centerY + outerHeight - 8, 50, 50, outerStrokePaint);

		canvas.drawCircle(touchPoint.x, touchPoint.y, innerRadius, innerFillPaint);
		canvas.drawCircle(touchPoint.x, touchPoint.y, innerRadius, innerStrokePaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int yTouch = (int) event.getY();

		if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN) {
			// Đảm bảo touchPoint luôn nằm trong phạm vi của hình chữ nhật
			if (yTouch >= centerY - outerHeight && yTouch <= centerY + outerHeight) {
				touchPoint.set(centerX, yTouch);
			} else if (yTouch < centerY - outerHeight) {
				touchPoint.set(centerX, centerY - outerHeight);
			} else if (yTouch > centerY + outerHeight) {
				touchPoint.set(centerX, centerY + outerHeight);
			}

			if (scaleJoystickListener != null) {
				// Chỉ truyền giá trị yPercent
				float yPercent = (float) (touchPoint.y - (centerY - outerHeight)) / (outerHeight * 2);
				scaleJoystickListener.onScaleMoved(yPercent);
			}
		} else if (action == MotionEvent.ACTION_UP) {
			touchPoint.set(centerX, centerY);
			if (scaleJoystickListener != null) {
				scaleJoystickListener.onScaleMoved(0);
			}
		}

		invalidate();
		return true;
	}

	public interface ScaleJoystickListener {
		void onScaleMoved(float yPercent);
	}
}
