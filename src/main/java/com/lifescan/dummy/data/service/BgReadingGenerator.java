/*
 * Class responsible for generating the objects with type bgReadingFromXml.
 *
 * @author fjansen@lifescan.com
 * @version 1
 * Copyright: Copyright (c) 2021
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.service;

import com.lifescan.dummy.data.model.BgReading;
import java.util.List;

public interface BgReadingGenerator {

  /**
   * Method responsible for returning a list of bgReadingFromXml.
   *
   * @param file Name of the file in disk
   * @return A list of blood glucose readings.
   */
  List<BgReading> generate(String file);
}
