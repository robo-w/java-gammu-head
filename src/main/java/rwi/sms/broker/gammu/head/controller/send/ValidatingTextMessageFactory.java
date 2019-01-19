package rwi.sms.broker.gammu.head.controller.send;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwi.sms.broker.gammu.head.contract.send.SendTextMessageRequest;
import rwi.sms.broker.gammu.head.message.data.PhoneNumber;
import rwi.sms.broker.gammu.head.message.data.TextMessage;
import rwi.sms.broker.gammu.head.message.data.TextMessageTarget;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ValidatingTextMessageFactory implements TextMessageFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ValidatingTextMessageFactory.class);
    public static final int MAXIMUM_MESSAGE_CONTENT_LENGTH = 280;
    private final PhoneNumberValidator phoneNumberValidator;

    @Inject
    public ValidatingTextMessageFactory(final PhoneNumberValidator phoneNumberValidator) {
        this.phoneNumberValidator = phoneNumberValidator;
    }

    @Override
    public Optional<TextMessage> createTextMessage(final SendTextMessageRequest request) {
        Optional<TextMessage> textMessage = Optional.empty();

        TextMessageTarget messageTarget = createMessageTarget(request.getTargetPhoneNumbers());
        if (StringUtils.isNotBlank(request.getMessageContent()) && !messageTarget.getTargetNumbers().isEmpty()) {
            TextMessage message = new TextMessage(
                    messageTarget,
                    truncateMessageContent(request.getMessageContent()));
            textMessage = Optional.of(message);
            logIfValidTargetsAreLessThanRequestedTargets(messageTarget.getTargetNumbers(), request.getTargetPhoneNumbers());
        } else {
            LOG.info(
                    "Not all required parameters are set on the request. providedTargetNumbers={}, validTargetPhoneNumbers={}, messageContent='{}'",
                    request.getTargetPhoneNumbers(),
                    messageTarget.getTargetNumbers(),
                    request.getMessageContent());
        }

        return textMessage;
    }

    private void logIfValidTargetsAreLessThanRequestedTargets(
            final List<PhoneNumber> validTargetNumbers,
            final List<String> requestedTargetNumbers) {
        if (validTargetNumbers.size() < requestedTargetNumbers.size()) {
            LOG.debug(
                    "Not all of the provided phone numbers are valid for sending. requestedTargetNumbers={}, validTargetPhoneNumbers={}",
                    requestedTargetNumbers,
                    validTargetNumbers);
        }
    }

    private String truncateMessageContent(final String requestedMessageContent) {
        String truncatedMessage = requestedMessageContent;
        if (requestedMessageContent.length() > MAXIMUM_MESSAGE_CONTENT_LENGTH) {
            LOG.debug("Got message with length of {}. Truncated to {} characters.", requestedMessageContent.length(), MAXIMUM_MESSAGE_CONTENT_LENGTH);
            truncatedMessage = requestedMessageContent.substring(0, MAXIMUM_MESSAGE_CONTENT_LENGTH);
        }

        return truncatedMessage;
    }

    private TextMessageTarget createMessageTarget(final List<String> targetPhoneNumbers) {
        List<PhoneNumber> phoneNumbers = targetPhoneNumbers
                .stream()
                .map(PhoneNumber::new)
                .filter(phoneNumberValidator::isValid)
                .collect(toList());
        return new TextMessageTarget(phoneNumbers);
    }
}
