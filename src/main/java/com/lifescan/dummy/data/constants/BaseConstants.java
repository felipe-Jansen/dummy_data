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

class BaseConstants {
  static final String UTILITY_CLASSES_SHOULD_NOT_BE_INSTANTIATED =
      "Utility classes should not be instantiated.";

  private BaseConstants() {
    throw new IllegalStateException(UTILITY_CLASSES_SHOULD_NOT_BE_INSTANTIATED);
  }
}
