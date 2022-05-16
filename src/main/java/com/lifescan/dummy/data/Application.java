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
import com.lifescan.dummy.data.enums.BolusType;
import com.lifescan.dummy.data.enums.MealTags;
import com.lifescan.dummy.data.enums.Preset;
import com.lifescan.dummy.data.exception.BolusTypeInvalid;
import com.lifescan.dummy.data.exception.DatesExceedMaxTimeInterval;
import com.lifescan.dummy.data.exception.MealTagInvalid;
import com.lifescan.dummy.data.exception.StartDateEqualsToToday;
import com.lifescan.dummy.data.exception.StartDateLaterThanEndDateInvalid;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.model.ListOfPatients;
import com.lifescan.dummy.data.service.EventService;
import com.lifescan.dummy.data.service.util.Util;

import java.io.*;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
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
//      buildArgObject(args);
//      eventService.create(
//          ArgsParameter.getInstance().getLanguage(),
//          ArgsParameter.getInstance().getTotalPatients());
      showListOfCreatedPatients();
    } catch (ArrayIndexOutOfBoundsException ex) {
      log.error("No arguments informed!");
    }
  }

  /** Print list of created patients */
  private void showListOfCreatedPatients() {
    log.info("{}", ListOfPatients.getInstance().getEmails() + "\n");

    File file = new File("write.txt");

    try (Writer writer = new BufferedWriter(new FileWriter(file))) {
      String contents = "The quick brown fox" +
              System.getProperty("line.separator") + "jumps over the lazy dog.";

      writer.write(contents);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Builds model of all args input.
   *
   * @param args The input arguments
   */
  private void buildArgObject(String... args) {
    ArgsParameter argModel = ArgsParameter.getInstance();
    argModel.setLanguage(args[ArgsConstants.LANGUAGE_ISO]);
    argModel.setTotalPatients(Integer.parseInt(args[ArgsConstants.NUMBER_PATIENTS]));

    if (args[ArgsConstants.PRESET_ID].contains(ArgsConstants.PRESET)) {
      argModel.setPreset(
          Preset.getById(Long.parseLong(args[ArgsConstants.PRESET_ID].split("=")[1])));
    }
    argModel.setStartDate(args[ArgsConstants.START_DATE]);
    argModel.setEndDate(args[ArgsConstants.END_DATE]);
    validateDates();

    buildExercisesArguments(args);
    buildFoodArguments(args);
    buildBolusArguments(args);
    buildReadingArguments(args);
  }

  /** Validated the inputted time frame. */
  private void validateDates() {
    log.traceEntry("Validating date range.");
    validateIfStartDateIsBeforeThanEndDate();
    validateIfDatesAreWithinRange();
    validateIfStartDateIsDifferentThanToday();
  }

  private void validateIfStartDateIsDifferentThanToday() {
    if (Util.convertFromStringtoLocalDate(ArgsParameter.getInstance().getStartDate())
        .isEqual(LocalDate.now())) {
      throw new StartDateEqualsToToday();
    }
  }

  private void validateIfDatesAreWithinRange() {
    if (Duration.between(
                Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getStartDate()),
                Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getEndDate()))
            .toDays()
        > ArgsConstants.MAX_TIME_INTERVAL) {
      throw new DatesExceedMaxTimeInterval();
    }
  }

  private void validateIfStartDateIsBeforeThanEndDate() {
    if (!Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getStartDate())
        .isBefore(
            Util.convertFromStringtoLocalDateTime(ArgsParameter.getInstance().getEndDate()))) {
      throw new StartDateLaterThanEndDateInvalid();
    }
  }

  /**
   * Get Readings specifications from args.
   *
   * @param args The input arguments
   */
  private void buildReadingArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.READING))
        .forEach(
            arg -> {
              String[] values = arg.split("&");
              for (String value : values) {
                if (value.contains(ArgsConstants.READING) && value.contains("=")) {
                  ArgsParameter.getInstance()
                      .setReadingsNumber(Integer.parseInt(value.split("=")[1]));
                }
                if (value.contains(ArgsConstants.TAG) && value.contains("=")) {
                  String tag = value.split("=")[1];
                  validateTag(tag);
                  ArgsParameter.getInstance()
                      .setReadingsTag(Arrays.stream(tag.split(",")).collect(Collectors.toList()));
                }
              }
            });
  }

  /**
   * Validates incoming mealTags.
   *
   * @param tags Inputted mealTags
   */
  private void validateTag(String tags) {
    Arrays.stream(tags.split(","))
        .collect(Collectors.toList())
        .forEach(
            tag -> {
              if (!MealTags.contains(tag)) {
                throw new MealTagInvalid();
              }
            });
  }

  /**
   * Get bolus specifications from args.
   *
   * @param args The input arguments
   */
  private void buildBolusArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.BOLUS))
        .forEach(
            arg -> {
              String[] values = arg.split("&");

              for (String value : values) {
                if (value.contains(ArgsConstants.BOLUS) && value.contains("=")) {
                  ArgsParameter.getInstance().setBolusNumber(Integer.parseInt(value.split("=")[1]));
                }
                if (value.contains(ArgsConstants.TYPE) && value.contains("=")) {
                  String bolusType = value.split("=")[1];
                  validateBolus(bolusType);
                  ArgsParameter.getInstance().setBolusType(value.split("=")[1]);
                }
              }
            });
  }

  /**
   * Check if bolusType input is valid.
   *
   * @param bolusType The bolus type
   */
  private void validateBolus(String bolusType) {
    if (!BolusType.contains(bolusType)) {
      throw new BolusTypeInvalid();
    }
  }

  /**
   * Get food specifications from args.
   *
   * @param args The input arguments
   */
  private void buildFoodArguments(String... args) {
    Arrays.stream(args)
        .filter(c -> c.contains(ArgsConstants.FOOD))
        .filter(arg -> arg.contains("="))
        .forEach(
            value ->
                ArgsParameter.getInstance().setFoodNumbers(Integer.parseInt(value.split("=")[1])));
  }

  /**
   * Get exercises specifications from args.
   *
   * @param args The input arguments
   */
  private void buildExercisesArguments(String... args) {
    Arrays.stream(args)
        .filter(arg -> arg.contains(ArgsConstants.EXERCISE))
        .filter(arg -> arg.contains("="))
        .forEach(
            value ->
                ArgsParameter.getInstance()
                    .setExerciseNumbers(Integer.parseInt(value.split("=")[1])));
  }
}
