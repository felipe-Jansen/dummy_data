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
package com.lifescan.dummy.data.service;

import com.lifescan.dummy.data.model.*;
import com.lifescan.dummy.data.networking.service.EventServiceCore;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

  private final SecurityService securityService;
  private final EventServiceCore eventServiceCore;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public EventServiceImpl(SecurityService securityService, EventServiceCore eventServiceCore) {
    this.securityService = securityService;
    this.eventServiceCore = eventServiceCore;
  }

  @Override
  public void publishEvent(Login login) {
    try {
      String token = securityService.getToken(login);
      logger.info("token -> {}", token);
      eventServiceCore.publishEvent(token, generatingEvent()).toString();
    } catch (FeignException ex) {
      logger.error(ex.contentUTF8());
    }
  }

  private Event generatingEvent() {
    Event event = new Event();
    event.setBgReadings(generatingBgReading());
    event.setIsBackgroundSync(false);
    event.setMeta(generatingMeta());
    return event;
  }

  private Meta generatingMeta() {
    Meta meta = new Meta();
    meta.setSourceApp("REVEAL_MOBILE_IOS");
    meta.setSourceAppVersion("5.3.1");
    return meta;
  }

  private List<BgReading> generatingBgReading() {
    List<BgReading> bgReadings = new ArrayList<>();

    BgReading bgReading = new BgReading();
    bgReading.setActive("true");
    bgReading.setManual("true");
    bgReading.setReadingDate("2021-09-21 01:12:00");
    bgReading.setId(String.valueOf(System.currentTimeMillis()));
    bgReading.setExtendedAttributes(generatingAttributeValue());
    bgReading.setBgValue(generatingBgValue());
    bgReading.setMealTag("MEAL_TAG_POST_MEAL");
    bgReading.setLastUpdatedDate(System.currentTimeMillis());

    bgReadings.add(bgReading);
    return bgReadings;
  }

  private AttributeValue generatingAttributeValue() {
    AttributeValue attributeValue = new AttributeValue();
    attributeValue.setAttributeValue(generatingAttribute());
    return attributeValue;
  }

  private List<Attribute> generatingAttribute() {
    List<Attribute> attributes = new ArrayList<>();
    Attribute attribute = new Attribute();
    attribute.setName("dataLogs_glucose_lifestyletags");
    attribute.setType("string");
    attribute.setValue("");
    attributes.add(attribute);
    return attributes;
  }

  private BgValue generatingBgValue() {
    BgValue bgValue = new BgValue();
    bgValue.setValue(111);
    bgValue.setUnits("mg/dL");
    return bgValue;
  }
}
