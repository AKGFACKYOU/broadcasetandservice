package com.example.jack.serviceapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * 位置：
 * 作用：
 * 时间：2017/6/20
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "connService:服务绑定意图" );
        return new MyBinder();
    }
    public class MyBinder extends Binder {
        public void connService()
        {

            Log.e(TAG, "connService: 发送广播" );
            Toast.makeText(MyService.this, "成功获取服务", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent("play");
        LocalBroadcastManager.getInstance(MyService.this).
        sendBroadcast(intent1);
        }
        public void NotifiStart()
        {
            Intent intent=new Intent(MyService.this,MainActivity.class);
            PendingIntent pi=PendingIntent.getActivity(MyService.this,0,intent,0);
            Notification notifi=new NotificationCompat.Builder(MyService.this)
                    .setContentTitle("我他妈看今天谁敢念诗")
                    .setContentText("苟利国家生死以，岂因福祸避趋之！")
                    .setSmallIcon(R.mipmap.rt)
                    .setContentIntent(pi)
                    .build();
            startForeground(1,notifi);



            Intent intent1 = new Intent("notifi");
            LocalBroadcastManager.getInstance(MyService.this).
                    sendBroadcast(intent1);
        }

        public void StopNitifi()
        {
            stopForeground(true);

            Intent intent1 = new Intent("stopnitifi");
            LocalBroadcastManager.getInstance(MyService.this).
                    sendBroadcast(intent1);
        }
    }
}

