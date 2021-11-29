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
package com.lifescan.dummy.data.exception;

public class DatesAreMore90DaysApartInvalid extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String MSG = "Dates are more than 90 days apart";

  public DatesAreMore90DaysApartInvalid() {
    super(MSG);
  }
}
