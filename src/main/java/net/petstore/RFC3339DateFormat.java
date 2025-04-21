package net.petstore;

import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.text.FieldPosition;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RFC3339DateFormat extends StdDateFormat {

  private static final long serialVersionUID = 1L;

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    String value = OffsetDateTime.ofInstant(date.toInstant(), java.time.ZoneOffset.UTC)
                                 .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    toAppendTo.append(value);
    return toAppendTo;
  }

}