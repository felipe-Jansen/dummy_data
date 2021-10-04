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

import java.util.List;

public class AttributeValue {

  private List<Attribute> attributeValue;

  public List<Attribute> getAttributeValue() {
    return attributeValue;
  }

  public void setAttributeValue(List<Attribute> attributeValue) {
    this.attributeValue = attributeValue;
  }
}
