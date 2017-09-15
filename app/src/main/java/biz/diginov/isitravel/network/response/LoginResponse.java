package biz.diginov.isitravel.network.response;

import com.squareup.moshi.Json;

public class LoginResponse {

    @Json(name = "response_code")
    private
    String responseCode;

    @Json(name = "message")
    private
    String message;

    public String getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
