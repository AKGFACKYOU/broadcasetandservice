package com.example.jack.serviceapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String PLAY = "com.jf.studentjfmusic.play";

    //通知-暂停播放音乐的通知，
    public final static String PAUSE = "com.jf.studentjfmusic.pause";


    private static final String TAG ="MainActivity" ;
    protected MyService.MyBinder mMyBinder;
    private MyChangeBroadcastReceiver mMyBroadcastReceiver;
    private Button bt1;
    private Button bt2;
    private ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = (Button) findViewById(R.id.bt_connService);
        bt2 = (Button) findViewById(R.id.bt_stopService);
        im = (ImageView) findViewById(R.id.im);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        Log.e(TAG, "onCreate:开始执行服务和广播 ");
        bindMyService();
        registerBroadcast();


    }
    public void bindMyService()
    {

        Log.e(TAG, "bindMyService: 绑定服务");
        Intent intent=new Intent(this,MyService.class);
        bindService(intent,mConn,BIND_AUTO_CREATE);
    }

    ServiceConnection mConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Log.e(TAG, "onServiceConnected: 链接服务" );
            mMyBinder= (MyService.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    private void registerBroadcast()
    {
        Log.e(TAG, "registerBroadcast: 注册广播");
        mMyBroadcastReceiver=new MyChangeBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("play");
        intentFilter.addAction("notifi");
        intentFilter.addAction("stopnitifi");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMyBroadcastReceiver,intentFilter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_connService:
                mMyBinder.connService();
                mMyBinder.NotifiStart();
                break;
            case R.id.bt_stopService:
                mMyBinder.StopNitifi();

                break;

        }

    }


    class MyChangeBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction())
            {
                case "play":
                    Log.e(TAG, "onReceive: 现在的状态是" +"ok");
                    break;
                case "notifi":
                    Log.e(TAG, "onReceive: 顶部通知已经打开" );
                    Toast.makeText(context, "顶部通知已经打开", Toast.LENGTH_SHORT).show();
                    break;
                case "stopnitifi":
                    Log.e(TAG, "onReceive: 顶部通知栏已经关闭" );
                    Toast.makeText(context, "顶部通知栏已经关闭", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: 销毁广播和服务" );
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMyBroadcastReceiver);
        unbindService(mConn);
    }
}
