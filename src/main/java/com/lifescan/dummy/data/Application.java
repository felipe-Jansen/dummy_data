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
package com.lifescan.dummy.data;

import com.lifescan.dummy.data.constants.ArgsConstants;
import com.lifescan.dummy.data.constants.ConfigConstants;
import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.exception.BolusTypeInvalid;
import com.lifescan.dummy.data.exception.DatesAreMore90DaysApartInvalid;
import com.lifescan.dummy.data.exception.MealTagInvalid;
import com.lifescan.dummy.data.exception.StartDateLaterThanEndDateInvalid;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.ListOfPatients;
import com.lifescan.dummy.data.service.EventService;
import com.lifescan.dummy.data.service.util.Util;
import java.time.Duration;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired private EventService eventService;

  public static void main(String[] args) {
    System.exit(SpringApplication.exit(SpringApplication.run(Application.class, args)));
  }

  @Override
  public void run(String... args) {
    try {
      String language = args[ArgsConstants.LANGUAGE_ISO];
      int qtyPatients = Integer.parseInt(args[ArgsConstants.NUMBER_PATIENTS]);
      buildArgObject(args);
      eventService.create(language, qtyPatients);
      showListOfCreatedPatients();
    } catch (ArrayIndexOutOfBoundsException ex) {
      log.error("No arguments informed!");
    }
  }

  /** Print list of created patients */
  private void showListOfCreatedPatients() {
    log.info("{}", ListOfPatients.getInstance().getEmails());
  }

  /**
   * Builds model of all args input.
   *
   * @param args The input arguments
   */
  private void buildArgObject(String... args) {
    if (args[ArgsConstants.PRESET].contains("preset")) {
      ArgsParameter.getInstance()
          .setPreset(Preset.getById(Long.parseLong(args[ArgsConstants.PRESET].split("=")[1])));
      ArgsParameter.getInstance().setExerciseNumbers(Util.getRandomNumberBetween(0, 10));
      ArgsParameter.getInstance().setFoodNumbers(Util.getRandomNumberBetween(0, 10));
      ArgsParameter.getInstance().setBolusNumber(Util.getRandomNumberBetween(0, 10));
      ArgsParameter.getInstance().setReadingsNumber(Util.getRandomNumberBetween(0, 10));
    }
    ArgsParameter.getInstance().setStartDate(args[ArgsConstants.START_DATE]);
    ArgsParameter.getInstance().setEndDate(args[ArgsConstants.END_DATE]);
    validateDates();
    getExercisesArguments(args);
    getFoodArguments(args);
    getBolusArguments(args);
    getReadingArguments(args);
  }

  private void validateDates() {
    if (Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getStartDate())
            .compareTo(
                Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getEndDate()))
        < 0) {
      if (log.isDebugEnabled()) {
        log.debug("Start date is valid");
      }
    } else {
      throw new StartDateLaterThanEndDateInvalid();
    }

    if (Duration.between(
                Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getStartDate()),
                Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getEndDate()))
            .toDays()
        <= 90) {
      if (log.isDebugEnabled()) {
        log.debug("Dates are valid");
      }
    } else {
      throw new DatesAreMore90DaysApartInvalid();
    }
  }

  /**
   * Get Readings specifications from args.
   *
   * @param args The input arguments
   */
  private void getReadingArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.READING))
        .forEach(
            value -> {
              String[] values = value.split("&");
              for (String s : values) {
                if (s.contains(ArgsConstants.READING)) {
                  if (s.contains("=")) {
                    ArgsParameter.getInstance()
                        .setReadingsNumber(Integer.parseInt(s.split("=")[1]));
                  } else {
                    ArgsParameter.getInstance()
                        .setReadingsNumber(ConfigConstants.DEFAULT_EVENTS_NUMBER);
                  }
                }
                if (s.contains(ArgsConstants.TAG)) {
                  String tag = s.split("=")[1];
                  validateTag(tag);
                  ArgsParameter.getInstance().setReadingsTag(tag);
                }
              }
            });
  }

  private void validateTag(String tag) {
    if (tag.equals("MEAL_TAG_PRE_MEAL")
        || tag.equals("MEAL_TAG_POST_MEAL")
        || tag.equals("MEAL_TAG_NOTAG")) {
      if (log.isDebugEnabled()) {
        log.debug("meal tag is valid");
      }
    } else {
      throw new MealTagInvalid();
    }
  }

  /**
   * Get bolus specifications from args.
   *
   * @param args The input arguments
   */
  private void getBolusArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.BOLUS))
        .forEach(
            value -> {
              String[] values = value.split("&");
              for (String s : values) {
                if (s.contains(ArgsConstants.BOLUS)) {
                  if (s.contains("=")) {
                    ArgsParameter.getInstance().setBolusNumber(Integer.parseInt(s.split("=")[1]));
                  } else {
                    ArgsParameter.getInstance()
                        .setBolusNumber(ConfigConstants.DEFAULT_EVENTS_NUMBER);
                  }
                }
                if (s.contains(ArgsConstants.TYPE)) {
                  String bolusType = s.split("=")[1];
                  validateBolus(bolusType);
                  ArgsParameter.getInstance().setBolusType(s.split("=")[1]);
                }
              }
            });
  }

  private void validateBolus(String bolusType) {
    if (bolusType.equals("BOLUS_INSULIN_SHORT")
        || bolusType.equals("FAST")
        || bolusType.equals("LONG")
        || bolusType.equals("MIXED")
        || bolusType.equals("NPH")
        || bolusType.equals("OTHERS")) {
      if (log.isDebugEnabled()) {
        log.debug("Bolus type is valid");
      }
    } else {
      throw new BolusTypeInvalid();
    }
  }

  /**
   * Get food specifications from args.
   *
   * @param args The input arguments
   */
  private void getFoodArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.FOOD))
        .forEach(
            value -> {
              if (value.contains("=")) {
                ArgsParameter.getInstance().setFoodNumbers(Integer.parseInt(value.split("=")[1]));
              } else {
                ArgsParameter.getInstance().setFoodNumbers(ConfigConstants.DEFAULT_EVENTS_NUMBER);
              }
            });
  }

  /**
   * Get exercises specifications from args.
   *
   * @param args The input arguments
   */
  private void getExercisesArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.EXERCISE))
        .forEach(
            value -> {
              if (value.contains("=")) {
                ArgsParameter.getInstance()
                    .setExerciseNumbers(Integer.parseInt(value.split("=")[1]));
              } else {
                ArgsParameter.getInstance()
                    .setExerciseNumbers(ConfigConstants.DEFAULT_EVENTS_NUMBER);
              }
            });
  }
}
