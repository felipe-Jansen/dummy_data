/**
 * CustomException for Invalid loginid
 *
 * @author divya.soman
 * @version 1 Copyright: Copyright (c) 2019 Company: LifeScan IP Holdings, LLC This file contains
 *     trade secrets of LifeScan IP Holdings, LLC. No part may be reproduced or transmitted in any
 *     form by any means or for any purpose without the express written permission of LifeScan IP
 *     Holdings, LLC.
 */
package com.lifescan.dummy.data.exception;

/** InvalidTagReading class for invalid loginid */
public class InvalidTagReading extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private static final String MSG = "INVALID_TAG_READING_EXCEPTION";

  /** constructor with custom message */
  public InvalidTagReading() {
    super(MSG);
  }
}
