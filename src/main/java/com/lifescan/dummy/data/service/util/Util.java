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

import com.lifescan.dummy.data.constants.ConfigConstants;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.xml.DeviceDataDataSet;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Random;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

  /**
   * Method responsible for get the system's data and convert to string following the pattern
   * yyyy-MM-dd HH:mm:ss
   */
  private static LocalDateTime localDateTime = LocalDateTime.now();

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
   * Method responsible for generating a reading date for the events. Note: each calling to this
   * function increments a delay to time.
   *
   * @return A string with the formatted date
   */
  public static String generateReadingDateFormatted() {
    localDateTime = localDateTime.plusMinutes(ConfigConstants.DELAY_TIME_BETWEEN_EVENTS);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConfigConstants.DATA_FORMAT_PATTERN);
    return randomizeDate().format(formatter);
  }

  /**
   * Method responsible for generating a randomized date using range informed by user
   *
   * @return a object from localDateTime
   */
  private static LocalDateTime randomizeDate() {
    String inicio = ArgsParameter.getInstance().getStartDate();
    String fim = ArgsParameter.getInstance().getEndDate();
    return LocalDateTime.now()
        .withYear(randomElementOfDate(inicio, fim, ChronoField.YEAR))
        .withMonth(randomElementOfDate(inicio, fim, ChronoField.MONTH_OF_YEAR))
        .withDayOfMonth(randomElementOfDate(inicio, fim, ChronoField.DAY_OF_MONTH))
        .withHour(new Random().nextInt(23))
        .withMinute(new Random().nextInt(59));
  }

  /**
   * It randomizes a specified filed of a date
   *
   * @param start beginning of range
   * @param end limit of range
   * @param field field that wants to randomize
   * @return a random number
   */
  private static int randomElementOfDate(String start, String end, ChronoField field) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    int max = formatter.parse(start).get(field);
    int min = formatter.parse(end).get(field);
    int range = (max - min) + 1;
    return (int) (Math.random() * range) + min;
  }

  /**
   * Method resonsible for generating new UUID's for the events.
   *
   * @return A new UUID
   */
  public static String generateId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
