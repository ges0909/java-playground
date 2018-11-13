package streamtest;

import java.util.Optional;

abstract class Entry {

    protected long timestamp;
    protected String level;

    public static Optional<Entry> of(String line, Format format) {
        if (format == Format.LOG_ENTRY) {
            return Optional.of(new LogEntry(line));
        }
        return Optional.empty();
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    abstract public boolean isError();

    enum Format {
        LOG_ENTRY
    }
}
