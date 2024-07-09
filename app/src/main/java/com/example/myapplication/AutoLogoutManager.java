package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.firebase.auth.FirebaseAuth;

public class AutoLogoutManager implements LifecycleObserver {
    private static final long LOGOUT_DELAY_MS = -10; // 5 minutes
    private final Handler handler = new Handler();
    private final Runnable logoutRunnable;
    private final Context context;
    private boolean isActive = false;

    public AutoLogoutManager(Context context) {
        this.context = context;
        logoutRunnable = this::logout;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startLogoutTimer() {
        isActive = true;
        handler.postDelayed(logoutRunnable, LOGOUT_DELAY_MS);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopLogoutTimer() {
        isActive = false;
        handler.removeCallbacks(logoutRunnable);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resetLogoutTimer() {
        if (isActive) {
            handler.removeCallbacks(logoutRunnable);
            handler.postDelayed(logoutRunnable, LOGOUT_DELAY_MS);
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
