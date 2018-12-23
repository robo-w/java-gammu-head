package rwi.sms.broker.gammu.head.controller.send;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rwi.sms.broker.gammu.head.message.data.PhoneNumber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class InternationalPhoneNumberValidatorTest {
    private InternationalPhoneNumberValidator sut;

    @BeforeEach
    void init() {
        sut = new InternationalPhoneNumberValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"+43550234", "+12345", "+88012345"})
    void validNumbers_returnTrue(final String number) {
        boolean valid = sut.isValid(new PhoneNumber(number));

        assertThat(valid, equalTo(true));
    }

    @ParameterizedTest
    @ValueSource(strings = {"+a000", "06761234", "1-2-3-4"})
    void invalidNumbers_returnFalse(final String number) {
        boolean valid = sut.isValid(new PhoneNumber(number));

        assertThat(valid, equalTo(false));
    }
}