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

public enum Preset {
  EMILY(1),
  HARRY(2),
  HEATHER(3),
  MARIANNE(4),
  SEBASTIAN(5);

  private final int id;

  Preset(int id) {
    this.id = id;
  }
}
