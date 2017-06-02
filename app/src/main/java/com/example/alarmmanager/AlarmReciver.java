package com.example.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by SEVAK on 02.06.2017.
 */

public class AlarmReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) MainActivity.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        Toast.makeText(MainActivity.getInstance(), "Alarm Receiver", Toast.LENGTH_SHORT).show();
    }
}
