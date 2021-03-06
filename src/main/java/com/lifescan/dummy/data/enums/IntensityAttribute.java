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
package com.lifescan.dummy.data.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum IntensityAttribute {
  EXERCISE_INTENSITY_HARD,
  EXERCISE_INTENSITY_MEDIUM,
  EXERCISE_INTENSITY_MILD;

  private static final List<IntensityAttribute> VALUES =
      Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  public static IntensityAttribute randomIntensityAttribute() {
    return VALUES.get(RANDOM.nextInt(SIZE));
  }
}
