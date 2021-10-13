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
package com.lifescan.dummy.data.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "deviceDataDataSet")
public class DeviceDataDataSet {

  @XmlElement(name = "bgReadingDataLog")
  private BgReadingDataLogFromXml bgReadingDataLog;

  @XmlElement(name = "foodDataLog")
  private FoodDataLogFromXml foodDataLog;

  @XmlElement(name = "bolusDataLog")
  private BolusDataLogFromXml bolusDataLog;

  @XmlElement(name = "healthAttribsDataLog")
  private HealthAttribsDataLogFromXml healthAttribsDataLog;
}
