/*
 * Holds values for MealTags.
 *
 * @author lpaccana@lifescan.com
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
import java.util.stream.Collectors;

public enum MealTags {
  MEAL_TAG_PRE_MEAL,
  MEAL_TAG_POST_MEAL,
  MEAL_TAG_NOTAG;

  public static boolean contains(String type) {
    return Arrays.stream(MealTags.values())
        .map(Enum::name)
        .collect(Collectors.toList())
        .contains(type.toUpperCase());
  }
}
