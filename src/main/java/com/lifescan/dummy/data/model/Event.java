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
package com.lifescan.dummy.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Event {

  private List<BgReading> bgReadings;
  private Meta meta;
  private boolean isBackgroundSync;

  public List<BgReading> getBgReadings() {
    return bgReadings;
  }

  public void setBgReadings(List<BgReading> bgReadings) {
    this.bgReadings = bgReadings;
  }

  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  @JsonProperty(value = "isBackgroundSync")
  public boolean isBackgroundSync() {
    return isBackgroundSync;
  }

  public void setIsBackgroundSync(boolean backgroundSync) {
    isBackgroundSync = backgroundSync;
  }
}
