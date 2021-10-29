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

import com.lifescan.dummy.data.constants.PresetsConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Preset {
  EMILY(1, PresetsConstants.EMILY),
  HARRY(2, PresetsConstants.HARRY),
  HEATHER(3, PresetsConstants.HEATHER),
  MARIANNE(4, PresetsConstants.MARIANNE),
  SEBASTIAN(5, PresetsConstants.SEBASTIAN);

  private static final List<Preset> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();
  private final String address;
  private final int id;

  Preset(int id, String address) {
    this.id = id;
    this.address = address;
  }

  public static Preset randomPreset() {
    return VALUES.get(RANDOM.nextInt(SIZE));
  }
}
