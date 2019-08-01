/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istl.hellospringboot.controller;

import com.istl.hellospringboot.common.util.Defs;
import com.istl.hellospringboot.dto.NIDUser;
import com.istl.hellospringboot.payload.ErrorCode;
import com.istl.hellospringboot.payload.NidList;
import com.istl.hellospringboot.payload.NidUserRequest;
import com.istl.hellospringboot.payload.NidUserResponse;
import com.istl.hellospringboot.payload.NidUserServiceResopnse;
import com.istl.hellospringboot.payload.NidUserVerifyServiceResopnse;
import com.istl.hellospringboot.payload.ServiceError;
import com.istl.hellospringboot.service.NidService;
import com.istl.hellospringboot.service.NidServiceImpl;
import com.istl.hellospringboot.service.NidServiceManager;
import com.istl.hellospringboot.utils.NidException;
import com.istl.hellospringboot.utils.Token;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
public class NidController {

    @Autowired
    private NidServiceManager manager;

    @Autowired
    private NidService nidService;

    @RequestMapping(value = "/nidbd_user/create", method = RequestMethod.GET)
//    @ResponseBody
    String nidBDUser() {
        System.err.println("Hello DB NID User");
        return "Hello DB NID User get !!";
    }

    @PostMapping(path = "/nidbd_user/create")
    @ResponseBody
    NidUserResponse showCreateUser(@RequestBody(required = true) NIDUser user, @Token @RequestParam("token") String token) throws NidException {
        System.err.println("Hello DB NID User(Pst) > " + user.toString() + " token > " + token);

        NidUserResponse nidUserResponse = null;
        try {
            nidUserResponse = manager.createNidUser(user);
            if (nidUserResponse != null) {
                System.err.println(" response  > " + nidUserResponse.getMessage());
                if (nidUserResponse.getMessage().equals(Defs.SUCCESS)) {
                    sendPushNotification(token);
                }
                return nidUserResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nidUserResponse;
    }

    @PostMapping(path = "/nidbd_user/list")
    @ResponseBody
    NidUserServiceResopnse showNidUser(@RequestBody(required = true) NidUserRequest user) throws NidException {
        System.err.println("Hello DB NID User list(Pst) > " + user.toString());
        if (user == null) {
            return new NidUserServiceResopnse(new ServiceError(ErrorCode.REQUIRED_PARAMETER_MISSING.ordinal(), "Request Info"));
        }

        Object object = manager.showNidUser(user);
        if (object instanceof ServiceError) {
            return new NidUserServiceResopnse((ServiceError) object);
        }

        return new NidUserServiceResopnse((NidList) object);
    }

    @PostMapping(path = "/nidbd_user/verify")
    @ResponseBody
    NidUserVerifyServiceResopnse verifyNidUser(@RequestBody(required = true) NidUserRequest user) throws NidException {
        System.err.println("Hello DB NID User list(Pst) > " + user.toString());
        if (user == null) {
            return new NidUserVerifyServiceResopnse(new ServiceError(ErrorCode.REQUIRED_PARAMETER_MISSING.ordinal(), "Request Info"));
        }

        Object object = manager.verifyNidUser(user);
        if (object instanceof ServiceError) {
            return new NidUserVerifyServiceResopnse((ServiceError) object);
        }

        return new NidUserVerifyServiceResopnse((NIDUser) object);
    }

    private void sendPushNotification(String token) {

        String DeviceIdKey = token;
        String authKey = "AAAAfRjS-74:APA91bFUONpJYLnzhN1I0mNYICgVfD1y_fQ-y5ipY59KjmTVLiB4MwhqRJL8foAmVguVx9AZvkgShDgdx0HPeSCs6UIRaf921dtZx6nbEtmOAHmBbnedkoBUvtLknXS81OYqgdzlllr1";
        String FMCurl = "https://fcm.googleapis.com/fcm/send";

        try {
            URL url = new URL(FMCurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + authKey);
            conn.setRequestProperty("Content-Type", "application/json");
            System.out.println(DeviceIdKey);
            JSONObject data = new JSONObject();
            data.put("to", DeviceIdKey.trim());
            JSONObject info = new JSONObject();
            info.put("title", "FCM Notificatoin Title"); // Notification title
            info.put("body", "Hello First Test notification"); // Notification body
            data.put("data", info);
            System.out.println(data.toString());
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data.toString());
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

//                        return Action.SUCCESS;
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
