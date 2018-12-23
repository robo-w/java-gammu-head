package rwi.sms.broker.gammu.head.message.data;

import java.io.Serializable;
import java.util.Objects;

public class TextMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private final TextMessageTarget target;
    private final String content;

    public TextMessage(final TextMessageTarget target, final String content) {
        this.target = Objects.requireNonNull(target, "Text message target must not be null.");
        this.content = Objects.requireNonNull(content, "Text message content must not be null.");
    }

    public TextMessageTarget getTarget() {
        return target;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextMessage that = (TextMessage) o;
        return Objects.equals(target, that.target) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, content);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "target=" + target +
                ", content='" + content + '\'' +
                '}';
    }
}
