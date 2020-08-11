package com.e.fmap.Model;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import server.serviceClasses.*;

public class ServerProxy {

    public ResponseLogin getLoginUrl(URL url, RequestLogin loginRequest) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");

            EncoderDecoder coder = new EncoderDecoder();

            String json = coder.encodeRequestLogin(loginRequest);

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                ResponseLogin out = coder.decodeResponseLogin(reader);
                out.setSuccess(true);
                connection.getInputStream().close();
                return out;
            } else {
                ResponseLogin out = new ResponseLogin();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;
    }

    public ResponseRegister getRegisterUrl(URL url, RequestRegister request){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");

            EncoderDecoder coder = new EncoderDecoder();
            String json = coder.encodeRequestRegister(request);

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                ResponseRegister out = coder.decodeResponseRegister(reader);
                out.setSuccess(true);
                connection.getInputStream().close();
                return out;
            } else {
                ResponseRegister out = new ResponseRegister();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;

    }

    public ResponseEvents getAllEventsUrl(URL url, String auth){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                EncoderDecoder coder = new EncoderDecoder();
                ResponseEvents out = coder.decodeResponseEvents(reader.toString());
                out.setSuccess(true);
                return out;

            } else {
                ResponseEvents out = new ResponseEvents();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
            ResponseEvents badResponse = new ResponseEvents();
            badResponse.setMessage("Bad Request");
            badResponse.setSuccess(false);
            return badResponse;
        }
    }

    public ResponsePeople getAllPeopleUrl(URL url, String auth){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                EncoderDecoder coder = new EncoderDecoder();
                ResponsePeople out = coder.decodeResponsePeople(reader.toString());
                out.setSuccess(true);

                return out;

            } else {
                ResponsePeople out = new ResponsePeople();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
        }
        return null;
    }

    public ResponsePerson getPersonURL(URL url, String auth){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                EncoderDecoder coder = new EncoderDecoder();
                ResponsePerson out = coder.decodeResponsePerson(reader.toString());
                out.setSuccess(true);
                return out;

            } else {
                ResponsePerson out = new ResponsePerson();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
        }
        return null;
    }


    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}




