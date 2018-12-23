package rwi.sms.broker.gammu.head.message.sender;

public enum SendTextMessageResult {
    /**
     * The message was successfully sent to the underlying SMS sender.
     */
    SUCCESS,

    /**
     * The external program for sending text messages was not found.
     */
    PROVIDER_NOT_FOUND,

    /**
     * The communication with the phone failed.
     */
    COMMUNICATION_ERROR,

    /**
     * The command was not handled in time.
     */
    COMMAND_TIMED_OUT,
}
