package com.hcmut.assignment.joystick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.hcmut.assignment.biotech.R;

public class JoystickView extends View {
	public static final float SPEED = 0.05f;
	private int centerX, centerY;
	private int innerRadius, outerRadius;
	private Paint innerFillPaint, innerStrokePaint, outerFillPaint, outerStrokePaint;
	private Point touchPoint = new Point();
	private JoystickListener joystickListener;
	public JoystickView(Context context) {
		super(context);
	}
	public JoystickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initJoystick();
	}

	private void initJoystick() {
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
		outerRadius = Math.min(w, h) / 2 - 100;
		innerRadius = outerRadius * 2 / 5;
		touchPoint.set(centerX, centerY);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(centerX, centerY, outerRadius, outerFillPaint);
		canvas.drawCircle(centerX, centerY, outerRadius - 8, outerStrokePaint);

		canvas.drawCircle(touchPoint.x, touchPoint.y, innerRadius, innerFillPaint);
		canvas.drawCircle(touchPoint.x, touchPoint.y, innerRadius, innerStrokePaint);
	}

	public void setJoystickListener(JoystickListener listener) {
		this.joystickListener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float dist = (float) Math.sqrt(Math.pow(event.getX() - centerX, 2) + Math.pow(event.getY() - centerY, 2));

		if (event.getAction() != MotionEvent.ACTION_UP) {
			if (dist < outerRadius) {
				touchPoint.set((int) event.getX(), (int) event.getY());
			} else {
				float ratio = outerRadius / dist;
				float constrainedX = centerX + (event.getX() - centerX) * ratio;
				float constrainedY = centerY + (event.getY() - centerY) * ratio;
				touchPoint.set((int) constrainedX, (int) constrainedY);
			}

			float xPercent = (touchPoint.x - centerX) / (float) outerRadius;
			float yPercent = (touchPoint.y - centerY) / (float) outerRadius;

			if (joystickListener != null)
				joystickListener.onJoystickMoved(xPercent, yPercent);
		} else {
			touchPoint.set(centerX, centerY);
			if (joystickListener != null)
				joystickListener.onJoystickMoved(0, 0);
		}

		invalidate();
		return true;
	}

	public interface JoystickListener {
		void onJoystickMoved(float xPercent, float yPercent);
	}
}
