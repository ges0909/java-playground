package stream;

import java.util.Optional;

abstract class Entry {

  enum Format {
    LOG_ENTRY
  }

  protected long timestamp;
  protected String level;

  public long getTimestamp() {
    return this.timestamp;
  }

  abstract public boolean isError();

  public static Optional<Entry> of(String line, Format format) {
    if (format == Format.LOG_ENTRY) {
      return Optional.of(new LogEntry(line));
    }
    return Optional.empty();
  }
}
