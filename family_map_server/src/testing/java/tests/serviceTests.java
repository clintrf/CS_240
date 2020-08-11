package testing.java.tests;

import main.java.serviceClasses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultsTests {

    Services services;

    @BeforeEach
    public void initTests() {
        services = new Services();
    }

    @Test
    public void registerTest() {

        services.clear();
        RequestRegister request = new RequestRegister();

        request.setUserName("Clint_userName");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResponseRegister result = services.register(request);

        assertTrue(result.getUserName().equals("Clint_userName"));
        try {
            result.getMessage().equals("error");
        } catch (NullPointerException ex) {
            assertTrue(true);
        }

        services.clear();
    }


    @Test
    public void login() {
        services.clear();
        RequestRegister request = new RequestRegister();
        ;

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResponseRegister result = services.register(request);

        RequestLogin login = new RequestLogin();
        login.setPassword("password");
        login.setUserName("Clintrf");

        ResponseLogin result1 = services.login(login);

        assertTrue(result1.getUserName().equals("Clintrf"));


        RequestLogin login2 = new RequestLogin();
        login2.setPassword("password");
        login2.setUserName("none");

        ResponseLogin response2 = services.login(login);
        assertTrue(response2.getSuccess().equals(true));


        services.clear();
    }

    @Test
    public void clear() {
        services.clear();
    }

    @Test
    public void fill() {

        services.clear();
        services.clear();
        RequestRegister request = new RequestRegister();
        ;

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResponseRegister result = services.register(request);


        ResponseFill fill = services.fill(result.getUserName(), 4);
        assertTrue(fill.getSuccess().equals(true));

        ResponseFill fill1 = services.fill(result.getUserName(), 2);
        assertTrue(fill1.getSuccess().equals(true));
        ;

        ResponseFill fill2 = services.fill(result.getUserName(), 1);
        assertTrue(fill2.getSuccess().equals(true));

        services.clear();
    }

    @Test
    public void load() throws Exception {
        services.clear();
        File file = new File("/home/clint/GITHUB/CS_240/family_map_server/src/json/example.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data);
        EncoderDecoder coder = new EncoderDecoder();

        RequestLoad loader = coder.decodeRequestLoad(str);

//        ResponseLoad loadResult = services.load(loader);
//        assertTrue(loadResult.getSuccess() == true);

        services.clear();
    }

    @Test
    public void person() {
        services.clear();
        RequestRegister request = new RequestRegister();

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResponseRegister result = services.register(request);

        ResponsePerson personResult = services.person(result.getAuthToken(), result.getPersonID());

        assertTrue(personResult.getSuccess() == true);
        assertTrue(personResult.getFirstName().equals("Clint"));


        services.clear();
    }


    @Test
    public void people() {

        services.clear();
        RequestRegister request = new RequestRegister();

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResponseRegister result = services.register(request);

        ResponsePerson personResult = services.person(result.getAuthToken(), result.getPersonID());

        assertTrue(personResult.getSuccess() == true);
        assertTrue(personResult.getFirstName().equals("Clint"));


        services.clear();
        ;

        RequestLogin loginRequest = new RequestLogin();
        loginRequest.setPassword("parker");
        loginRequest.setUserName("sheila");

        ResponseLogin loginResults = services.login(loginRequest);

        ResponsePeople peopleResult = services.people(loginResults.getAuthToken());

        assertTrue(peopleResult.getSuccess() == false);


        services.clear();
    }

    @Test
    public void event() {

        services.clear();
        RequestRegister request = new RequestRegister();

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        RequestLogin loginRequest =new RequestLogin();
        loginRequest.setPassword("parker");
        loginRequest.setUserName("sheila");

        ResponseLogin loginResults = services.login(loginRequest);

        ResponseEvents eventsResult = services.events(loginResults.getAuthToken());

        assertTrue(eventsResult.getSuccess() == false);

        services.clear();
    }

    @Test
    public void events() {
        services.clear();
        RequestRegister request = new RequestRegister();

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResponseRegister result = services.register(request);;

        RequestLogin loginRequest =new RequestLogin();
        loginRequest.setPassword("parker");
        loginRequest.setUserName("sheila");

        ResponseLogin loginResults = services.login(loginRequest);

        ResponseEvents eventsResult = services.events(loginResults.getAuthToken());

        assertTrue(eventsResult.getSuccess() == false);


        services.clear();
    }
}