package rwi.sms.broker.gammu.head.controller.send;

import rwi.sms.broker.gammu.head.message.data.PhoneNumber;

import java.util.regex.Pattern;

public class InternationalPhoneNumberValidator implements PhoneNumberValidator {
    private static final Pattern PATTERN = Pattern.compile("\\+[0-9]{1,30}");

    @Override
    public boolean isValid(final PhoneNumber phoneNumber) {
        return PATTERN.matcher(phoneNumber.getNumber()).matches();
    }
}
