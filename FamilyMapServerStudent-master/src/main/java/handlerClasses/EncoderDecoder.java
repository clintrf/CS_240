package handlerClasses;

import com.google.gson.Gson;
import modelClasses.ModelUser;
import serviceClasses.requestService.RequestLogin;

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

    public ModelUser decodeToUser(String Json){
        return gson.fromJson(Json, ModelUser.class);
    }

    public RequestLogin decodeToRequestLoad(String Json) {
        return gson.fromJson(Json, RequestLogin.class);
    }
}
