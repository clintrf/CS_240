package handlerClasses;

import com.google.gson.Gson;

public class EncoderDecoder {

    Gson gson = new Gson();

    //object to Json encoding
    public String encode(Object object){
        return  gson.toJson(object);
    }

    //Json to Object decoding
    public Object decode(String Json){
        return gson.fromJson(Json, Object.class);
    }
}
