package watchservice;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.util.logging.Logger;

public class WatchServiceDemo {

    private static final Path PATH = Paths.get("./data");
    private static final Logger log = Logger.getLogger(WatchServiceDemo.class.getName());

    public void run() {
        log.fine("start long-running filesystem watch service");
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            PATH.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE, OVERFLOW);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Watchable watchable = key.watchable();
                    Path head = (Path) watchable; // observed path
                    WatchEvent<Path> ev = cast(event);
                    Path tail = ev.context(); // name of file created/modified/deleted
                    if (kind == OVERFLOW) {
                        log.warning("watch service overflow for observed path: " + head);
                        continue;
                    }
                    Path file = head.resolve(tail).normalize();
                    if (kind == ENTRY_CREATE) {
                        log.fine("create: " + file);
                    } else if (kind == ENTRY_MODIFY) {
                        log.fine("modify: " + file);
                    } else if (kind == ENTRY_DELETE) {
                        log.fine("delete: " + file);
                    }
                }
                if (!key.reset()) {
                    break;
                }
            }
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
        }
    }

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
}
