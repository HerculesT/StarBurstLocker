package com.hercules.starburstlocker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hercules.starburstlocker.services.AppCheckServices;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AppCheckServices.class));
    }
}
