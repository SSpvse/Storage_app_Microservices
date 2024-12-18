package com.email.emailservice.service;

import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.repository.EmailServiceRepository;
import lombok.Setter;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    // For testing purposes, email can be changed here to alter the email address that the email is sent to
    @Setter
    private String myEmail;


    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    EmailServiceRepository emailServiceRepository;

    public void addDateItemToRepo(List<DateDTO> items) {
        logger.info("Adding {} items to the repository", items.size());
        emailServiceRepository.saveAll(items);
    }



    public void sortingItemsForMail(List<DateDTO> dateDTOs) {

        logger.info(": : : sortingItemsForMail method called with {} items", dateDTOs != null ? dateDTOs.size() : 0);

        if (dateDTOs == null || dateDTOs.isEmpty()) {
            logger.warn(": : : Empty or null list provided to sortingItemsForMail");
            return;
        }



    }


    @Override
    public void SendDateItemToEmail(List<DateDTO> dateDTOs) {
        logger.info(": : : SendDateItemToEmail method called with {} items", dateDTOs != null ? dateDTOs.size() : 0);

        if (dateDTOs == null || dateDTOs.isEmpty()) {
            logger.warn(": : : Empty or null list provided to SendDateItemToEmail");
            return;
        }

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        // Construct the subject dynamically by using dateDTOs values
        String subject = "Expiring items from unit_name: ";

        StringBuilder itemsForMail = new StringBuilder();
        Long itemsOwnerID = null;
        for (DateDTO dateDTO : dateDTOs) {
            itemsForMail.append(String.format("X_X_X_X_X_(EmailServiceImpl)<p><b>Name:</b> %s</p>", dateDTO.getName()));
            itemsForMail.append(String.format("X_X_X_X_X_(EmailServiceImpl)<p><b>Date of expiring:</b> %s</p>", dateDTO.getDate()));
            itemsForMail.append("<p> - - - - - - - </p>");  // separator
            logger.debug("Processing item: {} with expiration date: {}", dateDTO.getName(), dateDTO.getDate());
            System.out.println("X_X_X_X_X_(EmailServiceImpl) Processing item: " + dateDTO.getName() + " with expiration date: " + dateDTO.getDate() + "AND THE ITEM OWNER ID::: " + dateDTO.getUserID());
            itemsOwnerID = dateDTO.getUserID();
        }

        if (itemsOwnerID == null) {
            logger.error("X_X_X_X_X_(EmailServiceImpl)No items owner ID found in the list of items");
            return;
        }else {

            ResponseEntity<String> resEmail = restTemplate.getForEntity("http://localhost:8080/auth/getMail-by-id/" + itemsOwnerID, String.class);
            myEmail = resEmail.getBody();
        }


        String text = String.format("X_X_X_X_X_(EmailServiceImpl)Your items that are running out: %s", itemsForMail);

        // Construct the JSON body dynamically
        String jsonBody = String.format(
                "{\"from\":{\"email\":\"hello@demomailtrap.com\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"%s\"}],\"subject\":\"%s\",\"html\":\"%s\",\"category\":\"Integration Test\"}",
                myEmail, subject, text
        );

        // Create the request body
        RequestBody body = RequestBody.create(jsonBody.getBytes(), mediaType);

        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 501020a950cc626a04d615fa1ce94127")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            logger.info("Sending email with subject: '{}' and body: '{}'", subject, text);
            logger.info("HERE IS THE request body : ~ : ~ : " + request.body().toString());
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                logger.error("Failed to send email. HTTP Status: {}. Response: {}", response.code(), response.body().string());
                throw new IOException("Unexpected HTTP status code " + response);
            }
            logger.info("Email sent successfully with response code: {}", response.code());
        } catch (IOException e) {
            logger.error("Error occurred while sending email", e);
            throw new RuntimeException(e);
        }
    }

}
