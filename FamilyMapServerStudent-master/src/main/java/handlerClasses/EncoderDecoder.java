package handlerClasses;

import com.google.gson.Gson;
import modelClasses.ModelEvent;
import modelClasses.ModelPerson;
import modelClasses.ModelUser;
import org.json.simple.JSONObject;
import serviceClasses.*;

public class EncoderDecoder {

    Gson gson = new Gson();

    public ModelUser decodetoModelUser(String jSON){
        return gson.fromJson(jSON, ModelUser.class);
    }
    public ResponsePerson decodetoResponsePerson(String jSON){
        return gson.fromJson(jSON, ResponsePerson.class);
    }
    public ResponseEvent decodetoResponseEvent(String jSON){
        return gson.fromJson(jSON, ResponseEvent.class);
    }
    public ModelPerson decodetoModelPersons(String jSON){
        return gson.fromJson(jSON, ModelPerson.class);
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

    public String encode(Object thisObject){
        return gson.toJson(thisObject);
    }
    public String encodeModelUsers(ModelUser user){
        return gson.toJson(user);
    }
    public String encodeRegister(RequestRegister register){
        return gson.toJson(register);
    }
    public String encodeModelPersons(ModelPerson person){
        return gson.toJson(person);
    }
    public String encodeModelEvents(ModelEvent events){
        return gson.toJson(events);
    }
    public String encodetoResponseClear(ResponseClear clear){ return gson.toJson(clear);}
    public String encodeResponseEvent(ResponseEvent event){ return gson.toJson(event); }
    public String encodeResponseEvents(ResponseEvents events) {return gson.toJson(events); }
    public String encodeResponseFill(ResponseFill fillResponse){ return gson.toJson(fillResponse);}
    public String encodeResponseLoad(ResponseLoad loadResponse){return gson.toJson(loadResponse);}
    public String encodeResponseLogin(ResponseLogin loginResponse){return gson.toJson(loginResponse);}
    public String encodeResponsePerson(ResponsePerson personResponse){return gson.toJson(personResponse);}
    public String encodeResponsePeople(ResponsePeople peopleResponse){return gson.toJson(peopleResponse);}
    public String encodeResponseRegister(ResponseRegister registerResponse){return gson.toJson(registerResponse);}
    public ModelEvent changetoModelEvents(JSONObject object){
        return gson.fromJson(gson.toJson(object), ModelEvent.class);
    }
}
