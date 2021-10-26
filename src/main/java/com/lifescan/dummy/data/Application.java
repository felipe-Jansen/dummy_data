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

import com.lifescan.dummy.data.service.PatientService;
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

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
  }

  @Override
  public void run(String[] args) {
    try {
      String language = args[0];
      int qtyPatients = Integer.parseInt(args[1]);
      patientService.execute(language, qtyPatients);
    } catch (ArrayIndexOutOfBoundsException ex) {
      log.error("No arguments informed!");
    }
  }
}
