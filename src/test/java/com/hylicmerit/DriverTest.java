package com.hylicmerit;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriverTest {
  @Test
  void testInvalidNumArgs() throws IOException {
    String[] args = new String[]{};

    String expected = "";
    String actual = Driver.run(args);

    assertEquals(expected, actual);
  }

  @Test
  void testInvalidOperation() throws IOException {
    String operation = "invalid";
    String key = "key";
    String target = "target";

    String[] args = new String[]{operation, key, target};

    String expected = "";
    String actual = Driver.run(args);

    assertEquals(expected, actual);
  }
}
