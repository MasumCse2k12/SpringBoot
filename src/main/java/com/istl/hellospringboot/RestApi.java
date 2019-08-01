package com.istl.hellospringboot;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.istl.hellospringboot.dto.NIDUser;
import com.istl.hellospringboot.entity.SendPushNotification;
import com.istl.hellospringboot.payload.NidUserResponse;
import com.istl.hellospringboot.service.NidService;
import com.istl.hellospringboot.service.NidServiceImpl;
import com.istl.hellospringboot.service.NidServiceManager;
import com.istl.hellospringboot.utils.NidException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
@SpringBootApplication
public class RestApi {

    public static void main(String[] args) {
        System.err.println("Spring Boot");
        System.setProperty("server.servlet.context-path", "/HelloSpringBoot");
        SpringApplication.run(RestApi.class, args);

        // Obtain serverKey from Project Settings -> Cloud Messaging tab
        // for "My Notification Client" project in Firebase Console.
//        String serverKey = "test";
//        Thread t   = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Sender sender = new SendPushNotification(serverKey);
//                    Message message = new Message.Builder()
//                            .collapseKey("message")
//                            .timeToLive(3)
//                            .delayWhileIdle(true)
//                            .addData("message", "Notification from Java application")
//                            .build();
//
//                    // Use the same token(or registration id) that was earlier
//                    // used to send the message to the client directly from
//                    // Firebase Console's Notification tab.
//                    Result result = sender.send(message,
//                            "APA91bFfIFjSCcSiJ111rbmkpnMkZY-Ej4RCpdBZFZN_mYgfHwFlx-M1UXS5FqDBcN8x1efrS2md8L9K_E9N21qB-PIHUqQwmF4p7Y3U-86nCGH7KNkZNjjz_P_qjcTR0TOrwXMh33vp",
//                            1);
//                    System.out.println("Result: " + result.toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            
//        };
//
//        try {
//             t.start;
//            t.join();
//        } catch (InterruptedException iex) {
//            iex.printStackTrace();
//        }
    }
}
