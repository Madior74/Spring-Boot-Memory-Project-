package com.example.schooladmin.service;


import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification; 


@Service
public class NotificationService {

    public void sendNoteCreatedNotification(String fcmToken, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK") // utile pour gérer le tap
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Notification envoyée : " + response);
    }
}