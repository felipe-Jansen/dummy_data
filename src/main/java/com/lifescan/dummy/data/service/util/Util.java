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

import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.xml.DeviceDataDataSet;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

  /**
   * Method responsible for reading the object that was in the xml file and converting it into a
   * java object.
   *
   * @param file that concerns to the name of xml
   * @return the object created by information in xml file
   * @throws JAXBException that concerns to the errors when trying to read the xml file
   */
  public static DeviceDataDataSet getDeviceDataDataSet(String file) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(DeviceDataDataSet.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    return (DeviceDataDataSet) jaxbUnmarshaller.unmarshal(new File(file));
  }

  /**
   * Method responsible for converting a string date in a localdatetime
   *
   * @param date Informed date in a string
   * @return A localdatetime object
   */
  public static LocalDateTime convertFromStringtoLocalDateTime(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter)
        .atTime(Util.getRandomNumberBetween(0, 23), Util.getRandomNumberBetween(0, 59));
  }

  /**
   * Method responsible for converting a string date in a localdate
   *
   * @param date Informed date in a string
   * @return A localdate object
   */
  public static LocalDate convertFromStringtoLocalDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter)
        .atTime(Util.getRandomNumberBetween(0, 23), Util.getRandomNumberBetween(0, 59))
        .toLocalDate();
  }

  /**
   * Method responsible for validating the number of events that will be generated, based on the
   * size of the list and quantity of events to be generated informed by user.
   *
   * @param sizeOfList number of elements in the list
   * @return number of events that will be generated
   */
  public static int getNumberOfEvents(int sizeOfList) {
    int days =
        (int)
            ChronoUnit.DAYS.between(
                convertFromStringtoLocalDate(ArgsParameter.getInstance().getStartDate()),
                convertFromStringtoLocalDate(ArgsParameter.getInstance().getEndDate()));
    days++;
    return days * sizeOfList;
  }

  /**
   * Method responsible for generating a random number between informed range.
   *
   * @param min min value for range
   * @param max maximum value for range
   * @return a random number
   */
  public static int getRandomNumberBetween(int min, int max) {
    return new Random().nextInt(max - min + 1) + min;
  }
}
