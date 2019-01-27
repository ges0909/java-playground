import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogEntry extends Entry {

    private static String regex = "\\[(.*)]\\s\\[(.*):(.*)]\\s\\[(.*)]\\s\\[(.*)]\\s(.+)";
    private static Pattern pattern = Pattern.compile(regex);

    LogEntry(String line) {
        Matcher matches = pattern.matcher(line);
        if (matches.find()) {
            String group1 = matches.group(1);
            this.timestamp = LocalDateTime.parse(group1, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    .atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
            this.level = matches.group(3);
        }
    }

    public boolean isError() {
        return this.level.equals("error");
    }
}
