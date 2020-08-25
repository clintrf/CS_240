package familymap.client.Net;

import android.util.Log;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import familymap.server.serviceClasses.*;

public class ServerProxy {

    public ResponseLogin getLogin(URL url, RequestLogin loginRequest) {
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
                ResponseLogin response = coder.decodeResponseLogin(reader);
                response.setSuccess(true);
                connection.getInputStream().close();
                return response;
            } else {
                ResponseLogin response = new ResponseLogin();
                response.setSuccess(false);
                response.setMessage(connection.getResponseMessage());
                return response;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;
    }

    public ResponseRegister getRegister(URL url, RequestRegister request){
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
                ResponseRegister response = coder.decodeResponseRegister(reader);
                response.setSuccess(true);
                connection.getInputStream().close();
                return response;
            } else {
                ResponseRegister response = new ResponseRegister();
                response.setSuccess(false);
                response.setMessage(connection.getResponseMessage());
                return response;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;

    }

    public ResponseEvents getEvents(URL url, String auth){
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
                ResponseEvents response = coder.decodeResponseEvents(reader);
                response.setSuccess(true);
                return response;

            } else {
                ResponseEvents response = new ResponseEvents();
                response.setSuccess(false);
                response.setMessage(connection.getResponseMessage());
                return response;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
            ResponseEvents badResponse = new ResponseEvents();
            badResponse.setMessage("Bad Request");
            badResponse.setSuccess(false);
            return badResponse;
        }
    }

    public ResponsePeople getPeople(URL url, String auth){
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
                ResponsePeople response = coder.decodeResponsePeople(reader);
                response.setSuccess(true);
                return response;

            } else {
                ResponsePeople response = new ResponsePeople();
                response.setSuccess(false);
                response.setMessage(connection.getResponseMessage());
                return response;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
        }
        return null;
    }

    public ResponsePerson getPerson(URL url, String auth){
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
                ResponsePerson out = coder.decodeResponsePersonReader(reader);
                out.setSuccess(true);
                return out;

            } else {
                ResponsePerson response = new ResponsePerson();
                response.setSuccess(false);
                response.setMessage(connection.getResponseMessage());
                return response;
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




