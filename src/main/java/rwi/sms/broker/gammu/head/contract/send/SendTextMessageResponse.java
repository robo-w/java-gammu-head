package rwi.sms.broker.gammu.head.contract.send;

import java.io.Serializable;
import java.util.Objects;

public class SendTextMessageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final SendResult result;

    public SendTextMessageResponse(final SendResult result) {
        this.result = result;
    }

    public SendResult getResult() {
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendTextMessageResponse that = (SendTextMessageResponse) o;
        return result == that.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }

    @Override
    public String toString() {
        return "SendTextMessageResponse{" +
                "result=" + result +
                '}';
    }
}
