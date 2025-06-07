package com.harrisonmoses.store;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("sms")
@Primary
public class SMSNotification implements NotificationService {

    @Override
    public void sendNotification(String message) {
        System.out.println("SMS Notification received");
       System.out.println(message);
    }
}
