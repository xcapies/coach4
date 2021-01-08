package com.tuk.coacher.cash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MCoacher {

    public String authenticate(String appKey, String appSecret) {
        String appKeySecret = appKey + ":" + appSecret;
        byte[] bytes = new byte[0];
        try {
            bytes = appKeySecret.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encoded = Base64.getEncoder().encodeToString(bytes);


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                .get()
                .addHeader("authorization", "Basic " + encoded)
                .addHeader("cache-control", "no-cache")

                .build();

        Response response = null;
        JSONObject jsonObject = null;
        String access_token = "";
        try {
            response = client.newCall(request).execute();
            jsonObject = new JSONObject(response.body().string());
            System.out.println(jsonObject.getString("access_token"));
            access_token = jsonObject.getString("access_token");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    public String C2BSimulation(String access_token, String shortCode, String commandID, String amount, String MSISDN, String billRefNumber) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("ShortCode", shortCode);
            jsonObject.put("CommandID", commandID);
            jsonObject.put("Amount", amount);
            jsonObject.put("Msisdn", MSISDN);
            jsonObject.put("BillRefNumber", billRefNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");
        System.out.println(requestJson);
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/safaricom/c2b/v1/simulate")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.body().toString();
    }

    public String B2CRequest(String access_token, String initiatorName, String securityCredential, String commandID, String amount, String partyA, String partyB, String remarks, String queueTimeOutURL, String resultURL, String occassion) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InitiatorName", initiatorName);
            jsonObject.put("SecurityCredential", securityCredential);
            jsonObject.put("CommandID", commandID);
            jsonObject.put("Amount", amount);
            jsonObject.put("PartyA", partyA);
            jsonObject.put("PartyB", partyB);
            jsonObject.put("Remarks", remarks);
            jsonObject.put("QueueTimeOutURL", queueTimeOutURL);
            jsonObject.put("ResultURL", resultURL);
            jsonObject.put("Occassion", occassion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");


        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().toString();
    }

    public String B2BRequest(String access_token, String initiatorName, String accountReference, String securityCredential, String commandID, String senderIdentifierType, String receiverIdentifierType, float amount, String partyA, String partyB, String remarks, String queueTimeOutURL, String resultURL, String occassion) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Initiator", initiatorName);
            jsonObject.put("SecurityCredential", securityCredential);
            jsonObject.put("CommandID", commandID);
            jsonObject.put("SenderIdentifierType", senderIdentifierType);
            jsonObject.put("RecieverIdentifierType", receiverIdentifierType);
            jsonObject.put("Amount", amount);
            jsonObject.put("PartyA", partyA);
            jsonObject.put("PartyB", partyB);
            jsonObject.put("Remarks", remarks);
            jsonObject.put("AccountReference", accountReference);
            jsonObject.put("QueueTimeOutURL", queueTimeOutURL);
            jsonObject.put("ResultURL", resultURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");
        System.out.println(requestJson);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/safaricom/b2b/v1/paymentrequest")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token)
                .addHeader("cache-control", "no-cache")

                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    public String STKPushSimulation(String access_token, String businessShortCode, String password, String timestamp, String transactionType, String amount, String phoneNumber, String partyA, String partyB, String callBackURL, String queueTimeOutURL, String accountReference, String transactionDesc) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("BusinessShortCode", businessShortCode);
            jsonObject.put("Password", password);
            jsonObject.put("Timestamp", timestamp);
            jsonObject.put("TransactionType", transactionType);
            jsonObject.put("Amount", amount);
            jsonObject.put("PhoneNumber", phoneNumber);
            jsonObject.put("PartyA", partyA);
            jsonObject.put("PartyB", partyB);
            jsonObject.put("CallBackURL", callBackURL);
            jsonObject.put("AccountReference", accountReference);
            jsonObject.put("QueueTimeOutURL", queueTimeOutURL);
            jsonObject.put("TransactionDesc", transactionDesc);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");

        OkHttpClient client = new OkHttpClient();
        String url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token)
                .addHeader("cache-control", "no-cache")
                .build();


        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().toString();
    }

    public String STKPushTransactionStatus(String access_token, String businessShortCode, String password, String timestamp, String checkoutRequestID) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("BusinessShortCode", businessShortCode);
            jsonObject.put("Password", password);
            jsonObject.put("Timestamp", timestamp);
            jsonObject.put("CheckoutRequestID", checkoutRequestID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query")
                .post(body)
                .addHeader("authorization", "Bearer " + access_token)
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().toString();

    }

    public String reversal(String initiator, String securityCredential, String commandID, String transactionID, String amount, String receiverParty, String recieverIdentifierType, String resultURL, String queueTimeOutURL, String remarks, String ocassion) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Initiator", initiator);
            jsonObject.put("SecurityCredential", securityCredential);
            jsonObject.put("CommandID", commandID);
            jsonObject.put("TransactionID", transactionID);
            jsonObject.put("Amount", amount);
            jsonObject.put("ReceiverParty", receiverParty);
            jsonObject.put("RecieverIdentifierType", recieverIdentifierType);
            jsonObject.put("QueueTimeOutURL", queueTimeOutURL);
            jsonObject.put("ResultURL", resultURL);
            jsonObject.put("Remarks", remarks);
            jsonObject.put("Occasion", ocassion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");
        System.out.println(requestJson);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/safaricom/reversal/v1/request")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer xNA3e9KhKQ8qkdTxJJo7IDGkpFNV")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().string();
    }

    public String balanceInquiry(String initiator, String commandID, String securityCredential, String partyA, String identifierType, String remarks, String queueTimeOutURL, String resultURL) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Initiator", initiator);
            jsonObject.put("SecurityCredential", securityCredential);
            jsonObject.put("CommandID", commandID);
            jsonObject.put("PartyA", partyA);
            jsonObject.put("IdentifierType", identifierType);
            jsonObject.put("Remarks", remarks);
            jsonObject.put("QueueTimeOutURL", queueTimeOutURL);
            jsonObject.put("ResultURL", resultURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");
        System.out.println(requestJson);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/safaricom/accountbalance/v1/query")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer fwu89P2Jf6MB1A2VJoouPg0BFHFM")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "2aa448be-7d56-a796-065f-b378ede8b136")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String registerURL(String access_token, String shortCode, String responseType, String confirmationURL, String validationURL) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ShortCode", shortCode);
            jsonObject.put("ResponseType", responseType);
            jsonObject.put("ConfirmationURL", confirmationURL);
            jsonObject.put("ValidationURL", validationURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");
        System.out.println(requestJson);

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + access_token)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().string();
    }

}
