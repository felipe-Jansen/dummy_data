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
package com.lifescan.dummy.data.service.util;

import java.util.Calendar;
import org.apache.commons.codec.digest.DigestUtils;

public class Util {

  private Util() {}

  public static String generateRequestToken(String emailAddress) {
    return DigestUtils.sha1Hex(DigestUtils.sha1Hex(emailAddress).concat(emailAddress));
  }

  public static String extractCountryFromLanguage(String language) {
    try {
      if (language.contains("-")) {
        return language.split("-")[1];
      } else {
        return language.split("_")[1];
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      return language;
    }
  }

  public static String generateDateOfBirth() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int date = Calendar.getInstance().get(Calendar.DATE);
    return String.valueOf(year - 20)
        .concat(month < 10 ? "0".concat(String.valueOf(month)) : String.valueOf(month))
        .concat(date < 10 ? "0".concat(String.valueOf(date)) : String.valueOf(date));
  }
}
