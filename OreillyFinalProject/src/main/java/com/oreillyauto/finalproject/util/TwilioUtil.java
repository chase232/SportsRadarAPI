package com.oreillyauto.finalproject.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.oreillyauto.finalproject.service.WidgetServiceImpl;

public class TwilioUtil {
    private static final String LANDLINE = "landline";
    private static final String CHASE_CELL = "+15736945653";
    private static final String TWILIO_CELL = "+15733045982";
    private String ACCOUNT_SID = null;
    private String AUTH_TOKEN = null;
    
    public TwilioUtil() throws IOException {
        Properties appProperties = new Properties();
        appProperties.load(WidgetServiceImpl.class.getClassLoader().getResourceAsStream("app.properties"));
        ACCOUNT_SID = appProperties.getProperty("twilio.account.sid");
        AUTH_TOKEN = appProperties.getProperty("twilio.token");
    }
    
    public String sendSms(String number, String body) {
        
        System.out.println("Got here");
        // If the cell is a known/good cell number, send the message
        if (number.equals(CHASE_CELL)) {
            String url = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/Messages.json";
            
            // Setup authentication and encode it
            String auth = ACCOUNT_SID + ":" + AUTH_TOKEN;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            
            // Create Request Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "Basic " + new String(encodedAuth));
            
            // Create Request Body (Payload)
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("To", number);
            params.add("From", TWILIO_CELL);
            params.add("Body", body);
            
            // Send The Request to the Web Service and Print the Response
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            System.out.println(response);
            return "success";
        } else {
            System.out.println("failed");
            return "fail";
        }
    }
    
    public static void main(String[] args) throws IOException {
        TwilioUtil t = new TwilioUtil();
        t.sendSms("+15736945653", "Data from table");
        
        //new Twilio("5736945653", "Test");
    }
}

