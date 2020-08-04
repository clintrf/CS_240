package main.java.serviceClasses;

import com.google.gson.Gson;

import main.java.modelClasses.ModelEvents;
import main.java.modelClasses.ModelPersons;
import main.java.modelClasses.ModelUsers;
import org.json.simple.JSONObject;


public class EncoderDecoder {

    Gson gson = new Gson();

    public ModelEvents decodeModelEvents(JSONObject object){ return gson.fromJson(gson.toJson(object), ModelEvents.class); }
    public ModelUsers decodetoModelUser(String jSON){
        return gson.fromJson(jSON, ModelUsers.class);
    }
    public ResponsePerson decodetoResponsePerson(String jSON){
        return gson.fromJson(jSON, ResponsePerson.class);
    }
    public ResponseEvent decodetoResponseEvent(String jSON){
        return gson.fromJson(jSON, ResponseEvent.class);
    }
    public ModelPersons decodetoModelPersons(String jSON){
        return gson.fromJson(jSON, ModelPersons.class);
    }
    public RequestLoad decodetoRequestLoad(String jSON){
        return gson.fromJson(jSON, RequestLoad.class);
    }
    public RequestLogin decodetoRequestLogin(String jSON){
        return gson.fromJson(jSON, RequestLogin.class);
    }
    public RequestRegister decodetoRequestRegister(String jSON){
        return gson.fromJson(jSON, RequestRegister.class);
    }

    public String encodeModelUsers(ModelUsers user){
        return gson.toJson(user);
    }
    public String encodeRegister(RequestRegister register){
        return gson.toJson(register);
    }
    public String encodeModelPersons(ModelPersons person){
        return gson.toJson(person);
    }
    public String encodeModelEvents(ModelEvents events){
        return gson.toJson(events);
    }
    public String encodeResponseClear(ResponseClear clear){ return gson.toJson(clear);}
    public String encodeResponseEvent(ResponseEvent event){ return gson.toJson(event); }
    public String encodeResponseEvents(ResponseEvents events) {return gson.toJson(events); }
    public String encodeResponseFill(ResponseFill fillResponse){ return gson.toJson(fillResponse);}
    public String encodeResponseLoad(ResponseLoad loadResponse){return gson.toJson(loadResponse);}
    public String encodeResponseLogin(ResponseLogin loginResponse){return gson.toJson(loginResponse);}
    public String encodeResponsePerson(ResponsePerson personResponse){return gson.toJson(personResponse);}
    public String encodeResponsePeople(ResponsePeople peopleResponse){return gson.toJson(peopleResponse);}
    public String encodeResponseRegister(ResponseRegister registerResponse){return gson.toJson(registerResponse);}


}
