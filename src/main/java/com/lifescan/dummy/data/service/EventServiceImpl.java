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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lifescan.dummy.data.model.Attribute;
import com.lifescan.dummy.data.model.AttributeValue;
import com.lifescan.dummy.data.model.BgReading;
import com.lifescan.dummy.data.model.BgValue;
import com.lifescan.dummy.data.model.Event;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.model.Meta;
import com.lifescan.dummy.data.networking.service.EventServiceCore;
import feign.FeignException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EventServiceImpl implements EventService {

  private final SecurityService securityService;
  private final EventServiceCore eventServiceCore;

  public EventServiceImpl(SecurityService securityService, EventServiceCore eventServiceCore) {
    this.securityService = securityService;
    this.eventServiceCore = eventServiceCore;
  }

  /**
   * Method responsible for creating the events for each new patient.
   *
   * @param login
   * @throws JsonProcessingException
   */
  @Override
  public void publishEvent(Login login) {
    try {
      String token = securityService.getToken(login);
      log.info("token -> {}", token);
      eventServiceCore.publishEvent(token, generatingEvent());
      log.info("Event published");
    } catch (FeignException ex) {
      log.error(ex.contentUTF8());
    }
  }

  /**
   * Method responsible for generating events.
   *
   * @return
   */
  private Event generatingEvent() {
    Event event = new Event();
    event.setBgReadings(generatingBgReading());
    event.setBackgroundSync(false);
    event.setMeta(generatingMeta());
    return event;
  }

  /**
   * Method responsible for generating the meta information.
   *
   * @return
   */
  private Meta generatingMeta() {
    Meta meta = new Meta();
    meta.setSourceApp("REVEAL_MOBILE_IOS");
    meta.setSourceAppVersion("5.3.1");
    return meta;
  }

  /**
   * Method responsible for generating the bgReading.
   *
   * @return
   */
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

  /**
   * Method responsible for setting the attributes values.
   *
   * @return
   */
  private AttributeValue generatingAttributeValue() {
    AttributeValue attributeValue = new AttributeValue();
    attributeValue.setAttributeValue(generatingAttribute());
    return attributeValue;
  }

  /**
   * Method responsible for generating attributes.
   *
   * @return
   */
  private List<Attribute> generatingAttribute() {
    List<Attribute> attributes = new ArrayList<>();
    Attribute attribute = new Attribute();
    attribute.setName("dataLogs_glucose_lifestyletags");
    attribute.setType("string");
    attribute.setValue("");
    attributes.add(attribute);
    return attributes;
  }

  /**
   * Method responsible for generating the bg value.
   *
   * @return
   */
  private BgValue generatingBgValue() {
    BgValue bgValue = new BgValue();
    bgValue.setValue(111);
    bgValue.setUnits("mg/dL");
    return bgValue;
  }
}
