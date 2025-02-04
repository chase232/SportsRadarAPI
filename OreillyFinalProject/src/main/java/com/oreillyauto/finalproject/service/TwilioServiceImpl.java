// Used to send text messages 

package com.oreillyauto.finalproject.service;

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

import com.twilio.sdk.LookupsClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.lookups.PhoneNumber;

public class TwilioServiceImpl {
    private static final String LANDLINE = "landline";
    private static final String CHASE_CELL = "+15736945653";
    private static final String NICHOLAS_CELL = "+14175974289";
    private static final String TWILIO_CELL = "+15733045982";
    private String ACCOUNT_SID = null;
    private String AUTH_TOKEN = null;
    
    public TwilioServiceImpl() throws IOException {
        Properties appProperties = new Properties();
        appProperties.load(WidgetServiceImpl.class.getClassLoader().getResourceAsStream("app.properties"));
        ACCOUNT_SID = appProperties.getProperty("twilio.account.sid");
        AUTH_TOKEN = appProperties.getProperty("twilio.token");
    }
    
    public String sendSms(String number, String body) {
        
        // If the cell is a known/good cell number, send the message
        if (number.equals(CHASE_CELL) || number.equals(NICHOLAS_CELL)) {
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
            return "success";
        }else {
            
            // Checking if landline
            LookupsClient client = new LookupsClient(ACCOUNT_SID, AUTH_TOKEN);
            PhoneNumber phoneNumber = client.getPhoneNumber(number, true);
            
            // Testing for landline
            try {
                if(LANDLINE.equalsIgnoreCase(phoneNumber.getType().toString())) {
                    return "landline";
                } else {
                    return "fail";
                }
            } catch(Exception e) {
                return "fail";
            }
        }
    }
}

