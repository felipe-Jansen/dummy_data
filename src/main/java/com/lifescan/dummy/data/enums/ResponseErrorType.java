/*
 * Types of response errors.
 *
 * @author hrajput
 * @version 1
 * Copyright: Copyright (c)  2020
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.enums;

import lombok.Getter;

@Getter
public enum ResponseErrorType {
  BAD_REQUEST,
  NOT_FOUND,
  INTERNAL_SERVER_ERROR,
  UNAUTHORIZED,
  INVALID_PARAMETER,
  FORBIDDEN,
  SERVICE_ERROR,
  EMAIL_INVALID_FORMAT,
  USER_TYPE_INVALID,
  USER_NOT_FOUND
}
