package com.email.emailservice.service;

import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.repository.ReceiverRepository;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {


    ReceiverRepository receiverRepository;

    public void addDateItemToRepo(List<DateDTO> items) {
        receiverRepository.saveAll(items);
    }



    @Override
    public void SendDateItemToEmail(List<DateDTO> dateDTOs) {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        // Construct the subject dynamically by using dateDTOs values
        String subject = String.format("Expiring items from unit_name: ");

        // ------
        // FOR FUTURE :: if you want to say what container its in, call unitService and use getUnitID to get the name ... here
        String text = String.format("Your items that are running out: %s", dateDTOs.getName());

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

    }

}
