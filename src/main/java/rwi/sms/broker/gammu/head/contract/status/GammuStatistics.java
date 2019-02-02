package rwi.sms.broker.gammu.head.contract.status;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;

public class GammuStatistics implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long inboxMessages;
    private final Long outboxMessages;
    private final Long errorMessages;
    private final Long sentMessages;

    public GammuStatistics(
            final Long inboxMessages,
            final Long outboxMessages,
            final Long errorMessages,
            final Long sentMessages) {
        this.inboxMessages = inboxMessages;
        this.outboxMessages = outboxMessages;
        this.errorMessages = errorMessages;
        this.sentMessages = sentMessages;
    }

    @Nullable
    public Long getInboxMessages() {
        return inboxMessages;
    }

    @Nullable
    public Long getOutboxMessages() {
        return outboxMessages;
    }

    @Nullable
    public Long getErrorMessages() {
        return errorMessages;
    }

    @Nullable
    public Long getSentMessages() {
        return sentMessages;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GammuStatistics that = (GammuStatistics) o;
        return Objects.equals(inboxMessages, that.inboxMessages) &&
                Objects.equals(outboxMessages, that.outboxMessages) &&
                Objects.equals(errorMessages, that.errorMessages) &&
                Objects.equals(sentMessages, that.sentMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inboxMessages, outboxMessages, errorMessages, sentMessages);
    }

    @Override
    public String toString() {
        return "GammuStatistics{" +
                "inboxMessages=" + inboxMessages +
                ", outboxMessages=" + outboxMessages +
                ", errorMessages=" + errorMessages +
                ", sentMessages=" + sentMessages +
                '}';
    }
}
