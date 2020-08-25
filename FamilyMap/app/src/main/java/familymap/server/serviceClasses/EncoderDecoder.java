package familymap.server.serviceClasses;

import com.google.gson.Gson;

import org.json.simple.JSONObject;

import java.io.Reader;
import familymap.server.modelClasses.*;


public class EncoderDecoder {

    Gson gson = new Gson();

    // decode
    public ModelEvents decodeModelEvents(JSONObject object){ return gson.fromJson(gson.toJson(object), ModelEvents.class); }
    public ModelUsers decodeModelUsers(String jSON){ return gson.fromJson(jSON, ModelUsers.class); }
    public ModelPersons decodeModelPersons(String jSON){ return gson.fromJson(jSON, ModelPersons.class); }
    public ModelAuthTokens decodeModelAuthTokens(String jSON){ return gson.fromJson(jSON, ModelAuthTokens.class); }

    public RequestLoad decodeRequestLoad(String jSON){ return gson.fromJson(jSON, RequestLoad.class); }
    public RequestLogin decodeRequestLogin(String jSON){ return gson.fromJson(jSON, RequestLogin.class); }
    public RequestRegister decodeRequestRegister(String jSON){ return gson.fromJson(jSON, RequestRegister.class); }

    public ResponsePerson decodeResponsePerson(String jSON){ return gson.fromJson(jSON, ResponsePerson.class); }
    public ResponsePerson decodeResponsePersonReader(Reader jSON) {return gson.fromJson(jSON, ResponsePerson.class); }
    public ResponseEvent decodeResponseEvent(String jSON){ return gson.fromJson(jSON, ResponseEvent.class); }

    // encode
    public String encodeModelUsers(ModelUsers user){ return gson.toJson(user); }
    public String encodeModelPersons(ModelPersons person){ return gson.toJson(person); }
    public String encodeModelEvents(ModelEvents events){ return gson.toJson(events); }
    public String encodeModelAuthToken(ModelAuthTokens authToken){ return gson.toJson(authToken); }

    public String encodeRequestLoad(RequestLoad load){ return gson.toJson(load); }
    public String encodeRequestLogin(RequestLogin login){ return gson.toJson(login); }
    public String encodeRequestRegister(RequestRegister register){ return gson.toJson(register); }

    public String encodeResponseClear(ResponseClear clear){ return gson.toJson(clear);}
    public String encodeResponseEvent(ResponseEvent event){ return gson.toJson(event); }
    public String encodeResponseEvents(ResponseEvents events) {return gson.toJson(events); }
    public String encodeResponseFill(ResponseFill fillResponse){ return gson.toJson(fillResponse);}
    public String encodeResponseLoad(ResponseLoad loadResponse){return gson.toJson(loadResponse);}
    public String encodeResponseLogin(ResponseLogin loginResponse){return gson.toJson(loginResponse);}
    public String encodeResponsePeople(ResponsePeople peopleResponse){return gson.toJson(peopleResponse);}
    public String encodeResponsePerson(ResponsePerson personResponse){return gson.toJson(personResponse);}
    public String encodeResponseRegister(ResponseRegister registerResponse){return gson.toJson(registerResponse);}


    public ResponseLogin decodeResponseLogin(Reader jSON) {return gson.fromJson(jSON, ResponseLogin.class); }

    public ResponseRegister decodeResponseRegister(Reader jSON) {return gson.fromJson(jSON, ResponseRegister.class); }

    public ResponseEvents decodeResponseEvents(Reader jSON) {return gson.fromJson(jSON, ResponseEvents.class); }

    public ResponsePeople decodeResponsePeople(Reader jSON) {return gson.fromJson(jSON, ResponsePeople.class); }


}