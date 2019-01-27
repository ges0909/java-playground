import java.util.Optional;

abstract class Entry {

    long timestamp;
    String level;

    static Optional<Entry> of(String line, Format format) {
        if (format == Format.LOG_ENTRY) {
            return Optional.of(new LogEntry(line));
        }
        return Optional.empty();
    }

    long getTimestamp() {
        return this.timestamp;
    }

    abstract public boolean isError();

    enum Format {
        LOG_ENTRY
    }
}
