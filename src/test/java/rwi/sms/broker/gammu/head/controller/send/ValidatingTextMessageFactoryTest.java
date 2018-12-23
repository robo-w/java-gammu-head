package rwi.sms.broker.gammu.head.controller.send;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rwi.sms.broker.gammu.head.contract.send.SendTextMessageRequest;
import rwi.sms.broker.gammu.head.message.data.PhoneNumber;
import rwi.sms.broker.gammu.head.message.data.TextMessage;
import rwi.sms.broker.gammu.head.message.data.TextMessageTarget;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidatingTextMessageFactoryTest {
    private ValidatingTextMessageFactory sut;
    private PhoneNumberValidator phoneNumberValidator;

    @BeforeEach
    void init() {
        phoneNumberValidator = mock(PhoneNumberValidator.class);
        when(phoneNumberValidator.isValid(any())).thenReturn(true);
        sut = new ValidatingTextMessageFactory(phoneNumberValidator);
    }

    @Test
    void validParameters_returnTextMessage() {
        SendTextMessageRequest request = new SendTextMessageRequest(
                createAuthenticationToken(),
                createListOfTargets(),
                "This is a text message.");

        Optional<TextMessage> textMessage = sut.createTextMessage(request);

        TextMessage expectedTextMessage = createExpectedTextMessageForRequest(request);
        assertThat(textMessage, isPresentAnd(equalTo(expectedTextMessage)));
    }

    @Test
    void noMessageContent_returnEmptyOptional() {
        SendTextMessageRequest request = new SendTextMessageRequest(createAuthenticationToken(), createListOfTargets(), "   ");

        Optional<TextMessage> textMessage = sut.createTextMessage(request);

        assertThat(textMessage, isEmpty());
    }

    @Test
    void messageLongerThan140Chars_returnTruncatedMessage() {
        String messageContent = createLongMessageContent();
        SendTextMessageRequest request = new SendTextMessageRequest(
                createAuthenticationToken(),
                createListOfTargets(),
                messageContent);

        Optional<TextMessage> textMessage = sut.createTextMessage(request);

        String expectedMessageContent = messageContent.substring(0, 140);
        TextMessage expectedTextMessage = new TextMessage(createExpectedTarget(request.getTargetPhoneNumbers()), expectedMessageContent);
        assertThat(textMessage, isPresentAnd(equalTo(expectedTextMessage)));
    }

    @Test
    void oneTargetInvalid_returnMessage() {
        String validTarget = "+12345";
        String invalidTarget = "+8801234";
        String messageContent = createLongMessageContent();
        SendTextMessageRequest request = new SendTextMessageRequest(
                createAuthenticationToken(),
                ImmutableList.of(validTarget, invalidTarget),
                messageContent);
        when(phoneNumberValidator.isValid(new PhoneNumber(invalidTarget))).thenReturn(false);

        Optional<TextMessage> textMessage = sut.createTextMessage(request);

        String expectedMessageContent = messageContent.substring(0, 140);
        TextMessage expectedTextMessage = new TextMessage(createExpectedTarget(ImmutableList.of(validTarget)), expectedMessageContent);
        assertThat(textMessage, isPresentAnd(equalTo(expectedTextMessage)));
    }

    @Test
    void allTargetsInvalid_returnEmptyOptional() {
        String invalidTarget = "+8801234";
        String messageContent = createLongMessageContent();
        SendTextMessageRequest request = new SendTextMessageRequest(
                createAuthenticationToken(),
                ImmutableList.of(invalidTarget),
                messageContent);
        when(phoneNumberValidator.isValid(new PhoneNumber(invalidTarget))).thenReturn(false);

        Optional<TextMessage> textMessage = sut.createTextMessage(request);

        assertThat(textMessage, isEmpty());
    }

    private TextMessage createExpectedTextMessageForRequest(final SendTextMessageRequest request) {
        return new TextMessage(createExpectedTarget(request.getTargetPhoneNumbers()), request.getMessageContent());
    }

    private TextMessageTarget createExpectedTarget(final List<String> targetPhoneNumbers) {
        List<PhoneNumber> phoneNumbers = targetPhoneNumbers
                .stream()
                .map(PhoneNumber::new)
                .collect(Collectors.toList());
        return new TextMessageTarget(phoneNumbers);
    }

    private String createLongMessageContent() {
        return "This is a very long text. With multiple sentences this text has more than hundred and forty characters. " +
                "This is too long to send it in one text message. Therefore it is truncated by the factory. " +
                "Every queued message will contain only message with at most 140 characters.";
    }

    private ImmutableList<String> createListOfTargets() {
        return ImmutableList.of("+123456", "+43123456");
    }

    private String createAuthenticationToken() {
        return "Authentication Token";
    }
}