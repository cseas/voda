package com.susmit.floatingicontest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.susmit.floatingicontest.firebase.FirebaseObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    Intent i;
    Context context;
    FirebaseAuth auth;
    ViewPager pager;

    static FirebaseDatabase database;
    static DatabaseReference databaseReference;

    public static final String DB_NAME = "vodafonedemo-5b4ca", PHONE = String.valueOf(1234506789);

    static List<FirebaseObject> objects;

    String email, password;

    Thread updater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        objects = new ArrayList<>();

        email = "susmit600@gmail.com";
        password = "susmit200";

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "U", Toast.LENGTH_SHORT).show();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                // there was an error
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for(DataSnapshot dataSnapshot1 : iterable){
                    objects.add(dataSnapshot1.getValue(FirebaseObject.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pager = findViewById(R.id.adPages);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        updater = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int item = pager.getCurrentItem();
                            if(item==5)
                                item = 0;
                            pager.setCurrentItem(++item, true);
                        }
                    });
                }
            }
        });
        updater.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(!Settings.canDrawOverlays(this)){
                Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
                startActivityForResult(i, 0);
            }
            else {
                i = new Intent(MainActivity.this, FloatingIcon.class);
                startForegroundService(i);
            }
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!Settings.canDrawOverlays(this)){
                Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
                startActivityForResult(i, 0);
            }
            else {
                i = new Intent(MainActivity.this, FloatingIcon.class);
                startService(i);
            }
        }
        else{
            i = new Intent(MainActivity.this, FloatingIcon.class);
            startService(i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        i = new Intent(MainActivity.this, FloatingIcon.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(i);
        }
        else{
            startService(i);
        }
    }

    @Override
    protected void onPause() {
        stopService(i);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopService(i);
        super.onDestroy();
    }
}
