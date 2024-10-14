package com.email.emailservice.service;

import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.repository.ReceiverRepository;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {


    ReceiverRepository receiverRepository;

    public void addDateItemToRepo(DateDTO item) {
        receiverRepository.save(item);
    }

    @Override
    public HttpStatus SendDateItemToEmail(DateDTO dateDTO) {

        System.out.println("HERE IS THE DTO VALUES:: : : " + dateDTO.getDate());

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        // Construct the subject dynamically by using dateDTO values
        String subject = String.format("You are awesome! Here's the object to check: Date: %s, ID: %s",
                dateDTO.getDate(), dateDTO.getId());

        String text = String.format("Your items that are running out: %s", dateDTO.getDate());

        // Construct the JSON body dynamically
        String jsonBody = String.format(
                "{\"from\":{\"email\":\"hello@demomailtrap.com\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"stefvns003@gmail.com\"}],\"subject\":\"%s\",\"text\":\"%s\",\"category\":\"Integration Test\"}",
                subject, text
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
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return HttpStatus.OK; // Or another status depending on the success of the operation
    }

}
