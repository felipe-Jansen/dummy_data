package com.lifescan.dummy.data.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import org.junit.jupiter.api.Test;

class UtilTest {

  @Test
  public void testAdd() {
    assertEquals("20011008", LocalDateTime.now().minusYears(20).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
  }

}
