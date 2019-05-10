package com.example.hackfest.service;

import com.example.hackfest.models.OpenFigiArray;
import com.example.hackfest.models.OpenFigiRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenFigiService {

  @Autowired
  Gson gson;
  @Autowired
  private RestTemplate restTemplate;
  @Value("${open.figi.api}")
  private String openFigiApi;

  public void hitAPI() {

    String json = "[ { \"idType\": \"ID_ISIN\", \"idValue\": \"US4592001014\" }]";

    HttpEntity<OpenFigiArray> requestEntity;
    ResponseEntity<String> responseEntity;
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(new MediaType("application", "json"));
    requestEntity = new HttpEntity<>(
        gson.fromJson(json, new TypeToken<ArrayList<OpenFigiRequest>>() {
        }.getType()), requestHeaders);

    try {
      responseEntity = restTemplate
          .exchange(openFigiApi, HttpMethod.POST, requestEntity, String.class);
      if (responseEntity.getStatusCode() != null
          && responseEntity.getStatusCode() == HttpStatus.OK) {
        System.out.println(responseEntity.getBody());
      }

    } catch (Exception e) {
      System.out.println("error" + e.getMessage());
    }
  }


}
