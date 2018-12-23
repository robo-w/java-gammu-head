package rwi.sms.broker.gammu.head.controller.send;

import rwi.sms.broker.gammu.head.message.data.PhoneNumber;

import java.util.regex.Pattern;

public class AustrianPhoneNumberValidator implements PhoneNumberValidator {
    private static final Pattern PATTERN = Pattern.compile("\\+43[0-9]{1,28}");

    @Override
    public boolean isValid(final PhoneNumber phoneNumber) {
        return PATTERN.matcher(phoneNumber.getNumber()).matches();
    }
}
