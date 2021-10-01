/*
 * @author fjansen@lifescan.com
 * @version 1
 * Copyright: Copyright (c) 2021
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.model;

public class Result {

  private String country;
  private boolean analyticsEnabled;
  private String loginname;
  private boolean isReflectUser;
  private String dateOfBirth;
  private boolean consentRequired;
  private String isRevealUser;
  private String email;
  private String token;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public boolean isAnalyticsEnabled() {
    return analyticsEnabled;
  }

  public void setAnalyticsEnabled(boolean analyticsEnabled) {
    this.analyticsEnabled = analyticsEnabled;
  }

  public String getLoginname() {
    return loginname;
  }

  public void setLoginname(String loginname) {
    this.loginname = loginname;
  }

  public boolean isReflectUser() {
    return isReflectUser;
  }

  public void setReflectUser(boolean reflectUser) {
    isReflectUser = reflectUser;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public boolean isConsentRequired() {
    return consentRequired;
  }

  public void setConsentRequired(boolean consentRequired) {
    this.consentRequired = consentRequired;
  }

  public String getIsRevealUser() {
    return isRevealUser;
  }

  public void setIsRevealUser(String isRevealUser) {
    this.isRevealUser = isRevealUser;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
