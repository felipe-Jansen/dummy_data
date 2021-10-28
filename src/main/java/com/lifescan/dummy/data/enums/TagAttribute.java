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

public enum TagAttribute {
  EXERCISE_INTENSITY_HARD(1),
  EXERCISE_INTENSITY_MEDIUM(2),
  EXERCISE_INTENSITY_MILD(3);

  private static final List<TagAttribute> VALUES =
      Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  private final int id;

  TagAttribute(int id) {
    this.id = id;
  }

  public static TagAttribute randomTagAttribute() {
    return VALUES.get(RANDOM.nextInt(SIZE));
  }
}
