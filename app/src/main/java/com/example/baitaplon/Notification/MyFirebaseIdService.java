package com.example.baitaplon.Notification;


import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseIdService extends FirebaseMessagingService {
    //t viết r mà nhưng chắc ko dc đâu
    // ko ông trong video nó extend cái FirebaseInstantIdservice cơ như cái tên class ấy
    // dung cai firebasemessagingservice , toi thay cno bao thay cai kia = cai mesaging service rui
    // nhưng mà t ko biết viết thế nào ông

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
