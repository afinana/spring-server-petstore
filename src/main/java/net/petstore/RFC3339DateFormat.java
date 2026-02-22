package net.petstore;

import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class RFC3339DateFormat extends StdDateFormat {

  private static final long serialVersionUID = 1L;

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    toAppendTo.append(sdf.format(date));
    return toAppendTo;
  }
}