package com.harrisonmoses.store;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Notification {
    private NotificationService notification;

     public  Notification(@Qualifier("email")NotificationService notification) {
         this.notification = notification;
     }

     protected void placeholder(String message) {
          this.notification.sendNotification(message);
     }

}
