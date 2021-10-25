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
package com.lifescan.dummy.data.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PresetsConstants {

  // Diabetes 1 with Bolus, Food and Exercises.
  public static final String EMILY = "src/main/resources/Emily.xml";

  // Diabetes 2.
  public static final String HARRY = "src/main/resources/Harry.xml";

  // Gestational Diabetes.
  public static final String HEATHER = "src/main/resources/Heather.xml";

  // Diabetes 2 with Bolus, Food and Exercises.
  public static final String MARIANNE = "src/main/resources/Marianne.xml";

  // Diabetes 1 with Food and Exercises.
  public static final String SEBASTIAN = "src/main/resources/Sebastian.xml";
}
