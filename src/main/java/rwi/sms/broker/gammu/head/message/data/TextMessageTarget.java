package rwi.sms.broker.gammu.head.message.data;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TextMessageTarget implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<PhoneNumber> targetNumbers;

    public TextMessageTarget(final List<PhoneNumber> targetNumbers) {
        this.targetNumbers = ImmutableList.copyOf(targetNumbers);
    }

    public List<PhoneNumber> getTargetNumbers() {
        return targetNumbers;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextMessageTarget that = (TextMessageTarget) o;
        return Objects.equals(targetNumbers, that.targetNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetNumbers);
    }

    @Override
    public String toString() {
        return "TextMessageTarget{" +
                "targetNumbers=" + targetNumbers +
                '}';
    }
}
