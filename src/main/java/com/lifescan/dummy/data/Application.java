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
import com.lifescan.dummy.data.constants.MappedAttribute;
import com.lifescan.dummy.data.model.ArgsParameter;
import com.lifescan.dummy.data.service.EventService;
import com.lifescan.dummy.data.service.PatientService;
import java.util.Arrays;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Log4j2
@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired private PatientService patientService;

  @Autowired private EventService eventService;

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
  }

  @Override
  public void run(String... args) {
    try {
      String language = args[ArgsConstants.LANGUAGE_ISO];
      int qtyPatients = Integer.parseInt(args[ArgsConstants.NUMBER_PATIENTS]);
      generatingArgObject(args);
      eventService.create(language, qtyPatients);
    } catch (ArrayIndexOutOfBoundsException ex) {
      log.error("No arguments informed!");
    }
  }

  private void generatingArgObject(String... args) {
    ArgsParameter argsParameter = ArgsParameter.getInstance();
    argsParameter.setStartDate(args[ArgsConstants.START_DATE]);
    argsParameter.setEndDate(args[ArgsConstants.END_DATE]);
    argsParameter.setExerciseNumbers(getNumberEvents(args[ArgsConstants.EXERCISE]));
    argsParameter.setFoodNumbers(getNumberEvents(args[ArgsConstants.FOOD]));

    argsParameter.setBolusNumber(getNumberEvents(args[ArgsConstants.BOLUS].split("&")[0]));
    argsParameter.setBolusType(extractInformationFromParameters(ArgsConstants.BOLUS_TYPE, args));

    argsParameter.setReadingsNumber(getNumberEvents(args[ArgsConstants.READING].split("&")[0]));
    argsParameter.setReadingsTag(extractInformationFromParameters(ArgsConstants.READING_TAG, args));
    argsParameter.setReadingsPreset(
        extractInformationFromParameters(ArgsConstants.READING_PRESET, args));
  }

  private String extractInformationFromParameters(MappedAttribute attribute, String... args) {
    if (args[attribute.getIndex()].contains("&")) {
      String[] arg =
          args[attribute.getIndex()].contains("&") ? args[attribute.getIndex()].split("&") : null;
      String value =
          Arrays.stream(arg)
              .filter(c -> c.contains(attribute.getAttribute()))
              .findFirst()
              .orElse(null);
      return (value == null) ? null : value.split("=")[1];
    } else {
      return null;
    }
  }

  private int getNumberEvents(String event) {
    return event.contains("=")
        ? Integer.parseInt(event.split("=")[1])
        : new Random().nextInt(10) + 1;
  }
}
