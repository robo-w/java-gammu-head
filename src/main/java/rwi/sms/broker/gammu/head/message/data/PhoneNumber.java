package rwi.sms.broker.gammu.head.message.data;

import java.io.Serializable;
import java.util.Objects;

public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String number;

    public PhoneNumber(final String number) {
        this.number = Objects.requireNonNull(number, "Phone number must not be null.");
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" + number + '}';
    }
}
