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
import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.ListOfPatients;
import com.lifescan.dummy.data.service.EventService;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Log4j2
@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired private EventService eventService;

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
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

  /** Print the patient emails. */
  private void showListOfCreatedPatients() {
    if (log.isInfoEnabled()) {
      log.info("{}", ListOfPatients.getInstance().getEmails());
    }
  }

  /**
   * Builds model of all args input.
   *
   * @param args The input arguments
   */
  private void buildArgObject(String... args) {
    // @TODO instead of calling ArgsParameter.getInstance() all the time, can't we just assigned it
    // into a local variable?
    if (args[ArgsConstants.PRESET].contains("preset")) {
      ArgsParameter.getInstance()
          .setPreset(Preset.getById(Long.parseLong(args[ArgsConstants.PRESET].split("=")[1])));
    }
    ArgsParameter.getInstance().setStartDate(args[ArgsConstants.START_DATE]);
    ArgsParameter.getInstance().setEndDate(args[ArgsConstants.END_DATE]);
    getExercisesArguments(args);
    getFoodArguments(args);
    getBolusArguments(args);
    getReadingArguments(args);
  }

  /**
   * Get Readings specifications from args.
   *
   * @param args The input arguments
   */
  private void getReadingArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.READING))
        .findAny()
        .ifPresent(
            value -> {
              String[] values = value.split("&");
              for (String s : values) {
                if (s.contains(ArgsConstants.READING)) {
                  ArgsParameter.getInstance().setReadingsNumber(Integer.parseInt(s.split("=")[1]));
                }
                if (s.contains(ArgsConstants.TAG)) {
                  ArgsParameter.getInstance().setReadingsTag(s.split("=")[1]);
                }
              }
            });
  }

  /**
   * Get Bolus specifications from args.
   *
   * @param args The input arguments
   */
  private void getBolusArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.BOLUS))
        .findAny()
        .ifPresent(
            value -> {
              String[] values = value.split("&");
              for (String s : values) {
                if (s.contains(ArgsConstants.BOLUS)) {
                  ArgsParameter.getInstance().setBolusNumber(Integer.parseInt(s.split("=")[1]));
                }
                if (s.contains(ArgsConstants.TYPE)) {
                  ArgsParameter.getInstance().setBolusType(s.split("=")[1]);
                }
              }
            });
  }

  /**
   * Get Food specifications from args.
   *
   * @param args The input arguments
   */
  private void getFoodArguments(String... args) {
    // @TODO Can you double check if we actually have the & parameter?
    // I don't recall having parameters for food.
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.FOOD))
        .findAny()
        .ifPresent(
            value -> {
              String[] values = value.split("&");
              for (String s : values) {
                ArgsParameter.getInstance().setFoodNumbers(Integer.parseInt(s.split("=")[1]));
              }
            });
  }

  /**
   * Get Exercise specifications from args.
   *
   * @param args The input arguments
   */
  private void getExercisesArguments(String... args) {
    // @TODO Can you double check if we actually have the & parameter?
    // I don't recall having parameters for exercises.
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.EXERCISE))
        .findAny()
        .ifPresent(
            value -> {
              String[] values = value.split("&");
              for (String s : values) {
                ArgsParameter.getInstance().setExerciseNumbers(Integer.parseInt(s.split("=")[1]));
              }
            });
  }
}
