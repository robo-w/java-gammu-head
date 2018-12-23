package rwi.sms.broker.gammu.head.controller.send;

import rwi.sms.broker.gammu.head.message.data.PhoneNumber;

public interface PhoneNumberValidator {
    boolean isValid(PhoneNumber phoneNumber);
}
