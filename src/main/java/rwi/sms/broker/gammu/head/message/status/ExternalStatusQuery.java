package rwi.sms.broker.gammu.head.message.status;

import java.util.Optional;

public interface ExternalStatusQuery {
    Optional<Long> getMessageCountInInbox();

    Optional<Long> getMessageCountInOutbox();

    Optional<Long> getMessageCountOfSent();

    Optional<Long> getMessageCountOfError();
}
