package rwi.sms.broker.gammu.head.contract.send;

import com.google.gson.annotations.SerializedName;

public enum SendResult {
    @SerializedName("success")
    SUCCESS,

    @SerializedName("argumentValidationFailed")
    ARGUMENT_VALIDATION_FAILED,

    @SerializedName("malformedRequestBody")
    MALFORMED_REQUEST_BODY,

    @SerializedName("authenticationFailed")
    AUTHENTICATION_FAILED,
}
