package rwi.sms.broker.gammu.head.message.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class SpoolStatusQuery implements ExternalStatusQuery {
    private static final Logger LOG = LoggerFactory.getLogger(SpoolStatusQuery.class);

    @Override
    public Optional<Long> getMessageCountInInbox() {
        return readCountOfFilesInPath("/var/spool/gammu/inbox/");
    }

    @Override
    public Optional<Long> getMessageCountInOutbox() {
        return readCountOfFilesInPath("/var/spool/gammu/outbox/");
    }

    @Override
    public Optional<Long> getMessageCountOfSent() {
        return readCountOfFilesInPath("/var/spool/gammu/sent/");
    }

    @Override
    public Optional<Long> getMessageCountOfError() {
        return readCountOfFilesInPath("/var/spool/gammu/error/");
    }

    private Optional<Long> readCountOfFilesInPath(final String pathToCheck) {
        Optional<Long> messageCount = Optional.empty();

        try (Stream<Path> fileList = Files.list(Paths.get(pathToCheck))) {
            messageCount = Optional.of(fileList.count());
        } catch (IOException e) {
            LOG.warn("Failed to read count of files in '{}'. Message: {}", pathToCheck, e.getMessage());
            LOG.debug("Underlying IO Exception", e);
        }
        return messageCount;
    }
}
