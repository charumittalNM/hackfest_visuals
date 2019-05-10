package com.example.hackfest.models;

import java.util.ArrayList;

public class OpenFigiArray {

  private ArrayList<OpenFigiRequest> openFigiRequests = new ArrayList<>();

  public ArrayList<OpenFigiRequest> getOpenFigiRequests() {
    return openFigiRequests;
  }

  public void setOpenFigiRequests(
      ArrayList<OpenFigiRequest> openFigiRequests) {
    this.openFigiRequests = openFigiRequests;
  }

  @Override
  public String toString() {
    return "OpenFigiArray{" +
        "openFigiRequests=" + openFigiRequests +
        '}';
  }
}
