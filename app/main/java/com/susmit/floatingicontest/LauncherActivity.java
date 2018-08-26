package com.susmit.floatingicontest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.android.AIConfiguration;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class LauncherActivity extends Activity implements AIListener{

    View rootView;
    LinearLayout rootLayout;

    ScrollView container;

    DisplayMetrics metrics;

    AIConfiguration config;
    AIService aiService;
    AIDataService aiDataService;
    AIRequest aiRequest;

    Context context;

    public static final String API_KEY = "db6b4d6160fb431faa38c83abbc5e606";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        context = this;

        metrics = new DisplayMetrics();
        getDisplay().getMetrics(metrics);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        setTheme(R.style.MyStyle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);

        rootView = findViewById(R.id.rootView);
        ViewGroup.LayoutParams lparams = rootView.getLayoutParams();
        lparams.height = (metrics.heightPixels*3)/4;
        lparams.width = metrics.widthPixels;
        rootView.setLayoutParams(lparams);

        rootLayout = findViewById(R.id.rootLayout);

        container=  findViewById(R.id.scrollview);

        config = new AIConfiguration(API_KEY, AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(context, config);
        aiService.setListener(this);

        aiDataService = new AIDataService(config);

        aiRequest = new AIRequest();

        aiService.startListening();

        addConversation(false, "Hi! I am Lily, Vodafone personalized assistant! How may I help you");
    }

    @Override
    protected void onPause() {
        aiService.pause();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aiService.resume();
    }

    @Override
    protected void onDestroy() {
        aiService.stopListening();
        super.onDestroy();
    }

    public void addConversation(boolean byUser, String msg){
        LinearLayout layout = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.message_view, null);
        TextView tv = (TextView)layout.getChildAt(1);
        if(byUser) {
            layout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            tv.setBackground(getDrawable(R.drawable.rounded_corner_user));
        }
        else {
            ImageView v = (ImageView)layout.findViewById(R.id.profilePic);
            v.setImageDrawable(getDrawable(R.drawable.voda_logo));
            layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            tv.setBackground(getDrawable(R.drawable.rounded_corner_assistant));
        }
        tv.setText(msg);
        rootLayout.addView(layout);
        container.smoothScrollTo(0, container.getBottom());
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onResult(AIResponse result) {
        addConversation(true, result.getResult().getResolvedQuery());
        aiRequest.setQuery(result.getResult().getResolvedQuery());
        new AITask().execute(aiRequest);
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        Toast.makeText(getApplicationContext(), "Started Listening", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {
        Toast.makeText(getApplicationContext(), "Stopped Listening", Toast.LENGTH_SHORT).show();
    }

    public class AITask extends AsyncTask<AIRequest, Void, AIResponse> {
        @Override
        protected AIResponse doInBackground(AIRequest... requests) {
            final AIRequest request = requests[0];
            try {
                final AIResponse response = aiDataService.request(aiRequest);
                return response;
            } catch (AIServiceException e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(AIResponse aiResponse) {
            if (aiResponse != null) {
                addConversation(false, aiResponse.getResult().getFulfillment().getSpeech());
            }
        }
    }
}
