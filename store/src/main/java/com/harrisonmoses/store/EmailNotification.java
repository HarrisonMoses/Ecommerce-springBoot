package com.harrisonmoses.store;

import org.springframework.stereotype.Service;

@Service("email")
public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String message) {
        System.out.println("Email Notification received");
        System.out.println(message);
    }
}
