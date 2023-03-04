package com.example.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class Watch extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    public Watch(Context context) {
        super(context);
    }

    public Watch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Watch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        int maxValue = 60;
        int color = 0xff98ffb0;
        int textColor = 0xff90a0ff;
        int markRange = 5;


        float aspect = width / height;
        final float normalAspect = 2f / 1f;
        if (aspect > normalAspect) {
            width = normalAspect * height;
        } if (aspect < normalAspect) {
            height = width / normalAspect;
        }

        canvas.save();

        canvas.translate(width / 2, height);
        canvas.scale(.5f * width, -1f * height);


        paint.setColor(0x40ffffff);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(0, 0, 1, paint);

        paint.setColor(0x20000000);

        canvas.drawCircle(0, 0, 0.8f, paint);

        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.005f);

        float scale = 0.9f;
        float longScale = 0.9f;
        float textPadding = 0.85f;

        double step = Math.PI * 2 / maxValue;
        for (int i = 0; i <= maxValue; i++) {
            float x1 = (float) Math.cos(Math.PI - step*i);
            float y1 = (float) Math.sin(Math.PI - step*i);
            float x2;
            float y2;
            if (i % markRange == 0) {
                x2 = x1 * scale * longScale;
                y2 = y1 * scale * longScale;
            } else {
                x2 = x1 * scale;
                y2 = y1 * scale;
            }
            canvas.drawLine(x1, y1, x2, y2, paint);
        }

        canvas.restore();

        canvas.save();

        canvas.translate(width / 2, 0);

        paint.setTextSize(height / 10);
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);

        float factor = height * scale * longScale * textPadding;

        for (int i = 1; i <= 12; i++) {
            float x = (float) Math.sin(Math.PI - step*i*5) * factor;
            float y = (float) Math.cos(Math.PI - step*i*5) * factor;
            String text = Integer.toString(i);
            int textLen = Math.round(paint.measureText(text));
            canvas.drawText(Integer.toString(i), x - textLen / 2, height + y, paint);
        }

        canvas.restore();

        canvas.save();

        canvas.translate(width / 2, height);
        canvas.scale(.5f * width, -1f * height);
        canvas.rotate(360 - (float) 180 * (hour*10 / (float) maxValue));

        paint.setColor(0xff55ff99);
        paint.setStrokeWidth(0.05f);
        canvas.drawLine(0.02f, 0, 0, .6f, paint);
        canvas.drawLine(-0.02f, 0, 0, .6f, paint);

        canvas.restore();

        canvas.save();

        canvas.translate(width / 2, height);
        canvas.scale(.5f * width, -1f * height);
        canvas.rotate(360 - (float) 180 * (minute*2 / (float) maxValue));

        paint.setColor(0xff55ff99);
        paint.setStrokeWidth(0.03f);
        canvas.drawLine(0.01f, 0, 0, .7f, paint);
        canvas.drawLine(-0.01f, 0, 0, .7f, paint);

        canvas.restore();

        canvas.save();

        canvas.translate(width / 2, height);
        canvas.scale(.5f * width, -1f * height);
        canvas.rotate(360 - (float) 180 * (second*2 / (float) maxValue));

        paint.setColor(0xff55ff99);
        paint.setStrokeWidth(0.01f);
        canvas.drawLine(0.01f, 0, 0, .8f, paint);
        canvas.drawLine(0f, 0, 0, .8f, paint);
        canvas.drawLine(-0.01f, 0, 0, .8f, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff88ff99);
        canvas.drawCircle(0f, 0f, .05f, paint);

        canvas.restore();
    }
}
