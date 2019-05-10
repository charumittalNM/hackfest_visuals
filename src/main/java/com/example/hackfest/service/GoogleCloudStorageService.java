package com.example.hackfest.service;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class GoogleCloudStorageService {

  public void integratedGCP() throws IOException {

    GoogleCredentials credentials = GoogleCredentials.fromStream(
        new FileInputStream("/Users/vaibhav.mittal/Desktop/My First Project-6d60a3371ac0.json"))
        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    System.out.println("Buckets:");
    Page<Bucket> buckets = storage.list();
    for (Bucket bucket : buckets.iterateAll()) {
      System.out.println(bucket.toString());
    }

  }

  public void downloadFile() throws IOException {

    GoogleCredentials credentials = GoogleCredentials.fromStream(
        new FileInputStream("/Users/vaibhav.mittal/Desktop/My First Project-6d60a3371ac0.json"))
        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    System.out.println("Buckets:");
    Page<Bucket> buckets = storage.list();
    for (Bucket bucket : buckets.iterateAll()) {
      System.out.println(bucket.toString());

      Page<Blob> blobs = bucket.list();
      for (Blob blob : blobs.getValues()) {
        if ("fileseee-2019-05-09-171550205".equals(blob.getName())) {
          System.out.println(new String(blob.getContent()));
        }
      }
    }

  }


}
