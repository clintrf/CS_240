package handlerClasses;

import com.google.gson.Gson;
import modelClasses.ModelEvent;
import modelClasses.ModelPerson;
import modelClasses.ModelUser;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.*;

import java.lang.reflect.Type;
import java.util.jar.JarEntry;

public class EncoderDecoder {

    Gson gson = new Gson();

    //object to Json encoding
    public String encode(Object object){
        return  gson.toJson(object);
    }

    //Json to Object decoding
    public ModelUser decodeToModelUser(String Json){
        return gson.fromJson(Json, ModelUser.class);
    }
    public ModelPerson decodeToModelPerson(String Json){
        return gson.fromJson(Json,ModelPerson.class);
    }
    public ModelEvent decodeToModelEvent(String Json){
        return gson.fromJson(Json, ModelEvent.class );
    }

    public RequestLogin decodeToRequestLogin(String Json) {
        return gson.fromJson(Json, RequestLogin.class);
    }
    public RequestLoad decodeToRequestLoad(String Json) {
        return gson.fromJson(Json, RequestLoad.class);
    }
    public RequestRegister decodeToRequestRegister(String Json) {
        return gson.fromJson(Json, RequestRegister.class);
    }

    public String encodeResponseRegister(ResultsRegister registerResponse) {
        return gson.toJson(registerResponse);
    }

    public String encodeResultsClear(ResultsClear clear) {
        return gson.toJson(clear);
    }

    public String encodeResultsEvent(ResultsEvent eventResponse) {
        return gson.toJson(eventResponse);
    }

    public String encodeResultsEvents(ResultsEvents eventsResponse) {
        return gson.toJson(eventsResponse);
    }

    public String encodeRegister(RequestRegister request) {
        return gson.toJson(request);
    }
}