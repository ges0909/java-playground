package streams;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogEntry {

  static String regex = "\\[(.*)]\\s\\[(.*):(.*)]\\s\\[(.*)]\\s\\[(.*)]\\s(.+)";
  static Pattern pattern = Pattern.compile(regex);

  long timestamp;
  String level;
  boolean isValid = false;

  public long getTimestamp() {
    return this.timestamp;
  }

  public String getLevel() {
    return this.level;
  }

  public boolean isValid() {
    return this.isValid;
  }

  public boolean isError() {
    return this.level.equals("error");
  }

  public LogEntry(String line) {
    Matcher matches = pattern.matcher(line);
    if (matches.find()) {
      String group1 = matches.group(1);
      this.timestamp = LocalDateTime.parse(group1, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
          .atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
      this.level = matches.group(3);
      this.isValid = true;
    }
  }
}
