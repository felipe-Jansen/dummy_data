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
    argsParameter.setBolusType(
        extractInformationFromParameters(
            MappedAttribute.builder()
                .index(ArgsConstants.BOLUS)
                .event("bolus")
                .attribute("type")
                .build(),
            args));

    argsParameter.setReadingsNumber(getNumberEvents(args[ArgsConstants.READING].split("&")[0]));
    argsParameter.setReadingsTag(
        extractInformationFromParameters(
            MappedAttribute.builder()
                .index(ArgsConstants.READING)
                .event("reading")
                .attribute("Tag")
                .build(),
            args));
    argsParameter.setReadingsPreset(
        extractInformationFromParameters(
            MappedAttribute.builder()
                .index(ArgsConstants.READING)
                .event("reading")
                .attribute("preset")
                .build(),
            args));
  }

  private String extractInformationFromParameters(MappedAttribute attribute, String... args) {
    String value = null;
    if (args[attribute.getIndex()].contains("&")) {
      String[] arg =
          args[attribute.getIndex()].contains("&") ? args[attribute.getIndex()].split("&") : null;
      if (arg != null) {
        value =
            Arrays.stream(arg)
                .filter(c -> c.contains(attribute.getAttribute()))
                .filter(c -> c.contains("="))
                .findFirst()
                .orElse(null);
      }

      return value.split("=")[1];
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
