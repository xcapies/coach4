package com.tuk.coacher.mpesa.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by miles on 18/11/2017.
 */

public class Config {
    public static final String BASE_URL = "https://sandbox.safaricom.co.ke/";
    public static String PRODUCTION_BASE_URL = "https://api.safaricom.co.ke/";
    public static String stk_url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
    public static final String ACCESS_TOKEN_URL = "oauth/v1/generate?grant_type" +
            "=client_credentials";
    public static final String PROCESS_REQUEST_URL = "mpesa/stkpush/v1/processrequest";

    public static final String ERROR = "An error occurred while processing the request.Please " +
            "check" +
            " " +
            "your internet connection and try again.";
    public static final String DEFAULT_TRANSACTION_TYPE = "CustomerPayBillOnline";

    public static final String CONSUMER_KEY = "XkxSW2DesHWEBR8jxksytosOUsKjxHi8";
    public static final String CONSUMER_SECRET = "3tMpMYfEnJJDACZ4";
    public static final String BUSINESS_SHORT_CODE = "174379";
//    public static final String MPESA_PASS_KEY =
//            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public Response response = null;

    public Config() {
        OkHttpClient client = new OkHttpClient();
        String APP_KEY_SECRET = CONSUMER_KEY + ":" + CONSUMER_SECRET;
        byte[] bytes;
        try {
            bytes = APP_KEY_SECRET.getBytes("ISO-8859-1");
            String auth = Base64.getEncoder().encodeToString(bytes);
            Request request = new Request.Builder()
                    .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                    .get()
                    .addHeader("authorization", "Basic " + auth)
                    .addHeader("cache-control", "no-cache")
                    .build();
             response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getProductionBaseUrl() {
        return PRODUCTION_BASE_URL;
    }

    public static void setProductionBaseUrl(String productionBaseUrl) {
        PRODUCTION_BASE_URL = productionBaseUrl;
    }

    public static String getStk_url() {
        return stk_url;
    }

    public static void setStk_url(String stk_url) {
        Config.stk_url = stk_url;
    }

    public static String getAccessTokenUrl() {
        return ACCESS_TOKEN_URL;
    }

    public static String getProcessRequestUrl() {
        return PROCESS_REQUEST_URL;
    }

    public static String getERROR() {
        return ERROR;
    }

    public static String getDefaultTransactionType() {
        return DEFAULT_TRANSACTION_TYPE;
    }

    public static String getConsumerKey() {
        return CONSUMER_KEY;
    }

    public static String getConsumerSecret() {
        return CONSUMER_SECRET;
    }

    public static String getBusinessShortCode() {
        return BUSINESS_SHORT_CODE;
    }



    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
