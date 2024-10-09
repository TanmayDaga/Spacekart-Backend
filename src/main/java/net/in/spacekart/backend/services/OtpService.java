package net.in.spacekart.backend.services;

import net.in.spacekart.backend.exceptions.SpacekartBaseException;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class OtpService {

    private final RestTemplate restTemplate;
    @Value("${otp.clientId}")
    private String clientId;
    @Value("${otp.clientSecret}")
    private String clientSecret;


    //The error codes for which the error message should not be Internal Server Error
    private final Integer[] errorCodesSendOtp = new Integer[]{400, 429, 422};
    private final Integer[] errorCodesResendOtp = new Integer[]{400, 429};
    private final Integer[] errorCodesVerifyOtp = new Integer[]{400, 429};

    @Autowired
    public OtpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendOtp(String channelValue, UtilsService.EMAILID_PHONENUMBER channel) throws SpacekartBaseException {
        String url = "https://auth.otpless.app/auth/otp/v1/send";
        HttpHeaders headers = createHeaders();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("otpLength", 6);
        requestBody.put("expiry", 300);
        switch (channel) {
            case EMAIL_ID -> {
                requestBody.put("email", channelValue);
                requestBody.put("channel", "EMAIL");
            }
            case PHONE_NUMBER -> {
                requestBody.put("phoneNumber", channelValue);
                requestBody.put("channel", "SMS");
            }
        }
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.getBody()));

            if (response.getStatusCode() == HttpStatus.OK) {
                return jsonObject.getString("orderId");
            } else {
                handleErrorResponse(response.getStatusCode().value(), jsonObject, errorCodesSendOtp);
            }
        } catch (JSONException | NullPointerException e) {
            throw new SpacekartBaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred", e, "");
        }
        return null; // This line is necessary for method completion.
    }

    public boolean verifyOtp(String orderId, String otp, UtilsService.EMAILID_PHONENUMBER channel, String channelValue) throws SpacekartBaseException {
        String url = "https://auth.otpless.app/auth/otp/v1/verify";

        HttpHeaders headers = createHeaders();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("orderId", orderId);
        requestBody.put("otp", otp);

        switch (channel) {
            case EMAIL_ID -> requestBody.put("email", channelValue);
            case PHONE_NUMBER -> requestBody.put("phoneNumber", channelValue);
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.getBody()));

            if (response.getStatusCode() == HttpStatus.OK) {
                return jsonObject.getBoolean("isOTPVerified");
            } else {
                handleErrorResponse(response.getStatusCode().value(), jsonObject, errorCodesVerifyOtp);
            }
        } catch (JSONException | NullPointerException e) {
            throw new SpacekartBaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred", e, "");
        }
        return false;
    }

    public String resendOTP(String orderId) throws SpacekartBaseException {
        final String url = "https://auth.otpless.app/auth/otp/v1/resend";
        HttpHeaders headers = createHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(Map.of("orderId", orderId), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.getBody()));

            if (response.getStatusCode() == HttpStatus.OK) {
                return jsonObject.getString("orderId");
            } else {
                handleErrorResponse(response.getStatusCode().value(), jsonObject, errorCodesResendOtp);
            }
        } catch (JSONException | NullPointerException e) {
            throw new SpacekartBaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred", e, "");
        }
        return null;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", clientId);
        headers.set("clientSecret", clientSecret);
        return headers;
    }

    private void handleErrorResponse(Integer responseCode, JSONObject jsonObject, Integer[] errorCodesSameMessage) throws JSONException, SpacekartBaseException {
        String message = jsonObject.getString("message");

        String errorMessage = "Internal Server Error";
        for (Integer errorCode : errorCodesSameMessage) {
            if (responseCode.equals(errorCode)) {
                errorMessage = message;
                break;
            }
        }

        throw new SpacekartBaseException(HttpStatusCode.valueOf(responseCode), message, null, errorMessage);
    }
}
