package com.example.hackfest.models;

import com.google.gson.annotations.SerializedName;

public class OpenFigiRequest {

  @SerializedName("idType")
  private String idType;
  @SerializedName("idValue")
  private String idValue;

  public String getIdType() {
    return idType;
  }

  public void setIdType(String idType) {
    this.idType = idType;
  }

  public String getIdValue() {
    return idValue;
  }

  public void setIdValue(String idValue) {
    this.idValue = idValue;
  }

  @Override
  public String toString() {
    return "OpenFigiRequest{" +
        "idType='" + idType + '\'' +
        ", idValue='" + idValue + '\'' +
        '}';
  }
}
