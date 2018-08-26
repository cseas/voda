package com.susmit.floatingicontest;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class FloatingIcon extends Service {
    private WindowManager mWindowManager;
    private LinearLayout AppHeadLayout;
    private LinearLayout IconsLayout;
    private WindowManager.LayoutParams params;
    /*package*/ DisplayMetrics mDisplayMetrics;
    private ImageView appHeadIcon;

    int initialX;
    int initialY;
    float initialTouchX;
    float initialTouchY;
    boolean isHidden = true;

    View indicator;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(AppHeadLayout);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, createNotificationChannel())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("My Awesome App")
                    .setContentText("Doing some work...").build();
            startForeground(1337, notification);
        }

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplayMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);

        AppHeadLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_launcher, null);
        indicator = AppHeadLayout.findViewById(R.id.indicator);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }
        else{
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.TOP | Gravity.END;

        params.x = 0;
        params.y = 100;

        mWindowManager.addView(AppHeadLayout,params);


        appHeadIcon = AppHeadLayout.findViewById(R.id.launcher_head);
        IconsLayout = AppHeadLayout.findViewById(R.id.iconLayout);

        appHeadIcon.setOnTouchListener(new GestureHelper(this){

            @Override
            public void onClick() {
                indicator.setVisibility(View.GONE);
                openChatActivity();
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick() {

            }

            @Override
            public void onDoubleTap() {
                toggleApps();
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if (getScrollDircetion() == 0)
                        moveLeft();
                    else if(getScrollDircetion() ==1)
                        moveRight();
                }
                return getGestureDetector().onTouchEvent(event);
            }

        });

        mWindowManager.updateViewLayout(AppHeadLayout,params);
    }

    void openChatActivity() {
        Intent i = new Intent(FloatingIcon.this, LauncherActivity.class);
        PendingIntent pi = PendingIntent.getActivity(FloatingIcon.this,1337,i,0);
        try {
            pi.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    void moveLeft(){
        @SuppressLint("ObjectAnimatorBinding") PropertyValuesHolder pvx = PropertyValuesHolder.ofInt("x", params.x, 0);
        ObjectAnimator va = ObjectAnimator.ofPropertyValuesHolder(AppHeadLayout, pvx);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.x = (int) animation.getAnimatedValue("x");
                //mWindowManager.updateViewLayout(AppHeadLayout, params);
            }
        });
        va.start();
    }

    void toggleApps(){
        if(isHidden) {
            showApps();
        }
        else {
            hideApps();
        }
    }

    void showApps(){
        indicator.setVisibility(View.GONE);
        IconsLayout.setVisibility(View.VISIBLE);
        isHidden = false;
        //appHeadIcon.setImageDrawable(getDrawable(R.drawable.floater_opened));
    }

    void hideApps(){
        IconsLayout.setVisibility(View.GONE);
        isHidden = true;
        //appHeadIcon.setImageDrawable(getDrawable(R.drawable.floater_closed));
    }

    void moveRight(){
        @SuppressLint("ObjectAnimatorBinding") PropertyValuesHolder pvx = PropertyValuesHolder.ofInt("x", params.x, mDisplayMetrics.widthPixels);
        ObjectAnimator va = ObjectAnimator.ofPropertyValuesHolder(AppHeadLayout, pvx);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.x = (int) animation.getAnimatedValue("x");
                //mWindowManager.updateViewLayout(AppHeadLayout, params);
            }
        });
        va.start();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String channelId = "FloatingLauncherService";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }

    public void hideFloater(){
        AppHeadLayout.setVisibility(View.GONE);
    }
    public void showFloater(){
        AppHeadLayout.setVisibility(View.VISIBLE);
    }
    public boolean isFloaterHidden(){
        return AppHeadLayout.getVisibility()==View.GONE;
    }

    public abstract class GestureHelper implements View.OnTouchListener {

        private final GestureDetector mGestureDetector;
        private int scrollDircetion = -1;

        public GestureHelper(Context context) {
            mGestureDetector = new GestureDetector(context, new GestureListener(this));
        }

        public GestureDetector getGestureDetector(){
            return mGestureDetector;
        }

        public void onSwipeRight() {

        }


        public void onSwipeLeft() {

        }


        public void onSwipeTop() {

        }


        public void onSwipeBottom() {

        }


        public abstract void onDoubleTap();

        public void setScrollDirection(int dir){
            scrollDircetion = dir;
        }

        abstract public void onClick();

        abstract public void onLongClick();

        @Override
        public abstract boolean onTouch(View v, MotionEvent event);

        public int getScrollDircetion(){
            return scrollDircetion;
        }

    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 200;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        private GestureHelper mHelper;

        private long touchTime;

        public GestureListener(GestureHelper helper) {
            mHelper = helper;
        }

        public long getTouchTime(){
            return touchTime;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            initialX = params.x;
            initialY = params.y;

            initialTouchX = e.getRawX();
            initialTouchY = e.getRawY();

            touchTime = System.nanoTime();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mHelper.onClick();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mHelper.onDoubleTap();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mHelper.onLongClick();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(System.nanoTime()-getTouchTime() < 150000000)
                return false;
            params.x = initialX + (int) (e2.getRawX() - initialTouchX);
            params.y = initialY + (int) (e2.getRawY() - initialTouchY);

            if(params.x<initialX)
                mHelper.setScrollDirection(0);
            else
                mHelper.setScrollDirection(1);
            mWindowManager.updateViewLayout(AppHeadLayout, params);
            return true;
        }
    }
}
