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

public class Meta {

  private String sourceApp;
  private String sourceAppVersion;

  public String getSourceApp() {
    return sourceApp;
  }

  public void setSourceApp(String sourceApp) {
    this.sourceApp = sourceApp;
  }

  public String getSourceAppVersion() {
    return sourceAppVersion;
  }

  public void setSourceAppVersion(String sourceAppVersion) {
    this.sourceAppVersion = sourceAppVersion;
  }
}
