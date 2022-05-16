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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthAttribFromXml {

  @XmlAttribute(name = "active")
  private String active;

  @XmlAttribute(name = "manual")
  private String manual;

  @XmlAttribute(name = "readingDate")
  private String readingDate;

  @XmlAttribute(name = "editable")
  private String editable;

  @XmlElement(name = "annotation")
  private AnnotationFromXml annotation;

  @XmlElement(name = "healthAttributesValue")
  private int healthAttributesValue;

  @XmlElement(name = "healthAtributesLookup")
  private String healthAtributesLookup;

  @XmlElement(name = "extendedAttributes")
  private ExtendedAttributesFromXml extendedAttributes;
}