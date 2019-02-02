package rwi.sms.broker.gammu.head.contract.status;

import java.io.Serializable;
import java.util.Objects;

public class StatusResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String serviceName;
    private final String apiVersion;
    private final int queuedTextMessages;
    private final GammuStatistics gammuStatistics;

    public StatusResponse(
            final String serviceName,
            final String apiVersion,
            final int queuedTextMessages,
            final GammuStatistics gammuStatistics) {
        this.serviceName = serviceName;
        this.apiVersion = apiVersion;
        this.queuedTextMessages = queuedTextMessages;
        this.gammuStatistics = gammuStatistics;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public int getQueuedTextMessages() {
        return queuedTextMessages;
    }

    public GammuStatistics getGammuStatistics() {
        return gammuStatistics;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusResponse that = (StatusResponse) o;
        return queuedTextMessages == that.queuedTextMessages &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(apiVersion, that.apiVersion) &&
                Objects.equals(gammuStatistics, that.gammuStatistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, apiVersion, queuedTextMessages, gammuStatistics);
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "serviceName='" + serviceName + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", queuedTextMessages=" + queuedTextMessages +
                ", gammuStatistics=" + gammuStatistics +
                '}';
    }
}
