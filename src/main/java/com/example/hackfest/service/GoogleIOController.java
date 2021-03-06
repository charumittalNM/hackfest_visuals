package com.example.hackfest.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/google")
public class GoogleIOController {

  @Autowired
  GoogleCloudStorageService googleCloudStorageService;

  @Autowired
  OpenFigiService openFigiService;

  @RequestMapping(value = "/upsell_widgets", method = RequestMethod.GET)
  public void getMealPlan() throws IOException {
    googleCloudStorageService.integratedGCP();
  }

  @RequestMapping(value = "/download", method = RequestMethod.GET)
  public void getFile() throws IOException {
    googleCloudStorageService.downloadFile();
  }

  @RequestMapping(value = "/hitOpenFigi", method = RequestMethod.GET)
  public void hitOpenFigi() throws IOException {
    openFigiService.hitAPI();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/imageUpload112")
  public String uploadFile(@RequestParam("fileseee") MultipartFile fileStream)
      throws IOException, ServletException {

    Credentials credentials = GoogleCredentials.fromStream(
        new FileInputStream("/Users/vaibhav.mittal/Desktop/My First Project-6d60a3371ac0.json"));

    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    String bucketName = "hackfest_visuals";
    checkFileExtension(fileStream.getName());
    DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
    DateTime dt = DateTime.now(DateTimeZone.UTC);
    String dtString = dt.toString(dtf);
    final String fileName = fileStream.getName() + dtString;

    File file = convertMultiPartToFile(fileStream);

    BlobInfo blobInfo =
        storage.create(
            BlobInfo
                .newBuilder(bucketName, fileName)
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                .build(), new FileInputStream(file));
    System.out.println(blobInfo.getMediaLink());
    return blobInfo.getMediaLink();
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }


  private void checkFileExtension(String fileName) throws ServletException {
    if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
      String[] allowedExt = {".jpg", ".jpeg", ".png", ".gif"};
      for (String ext : allowedExt) {
        if (fileName.endsWith(ext)) {
          return;
        }
      }
      throw new ServletException("file must be an image");
    }
  }


}
