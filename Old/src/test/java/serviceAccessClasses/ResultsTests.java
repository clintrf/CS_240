package serviceAccessClasses;

import databaseClasses.DatabaseException;
import handlerClasses.EncoderDecoder;
import handlerClasses.Handler;
import org.junit.jupiter.api.*;
import serviceClasses.Services;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.*;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ResultsTests {

    Services services;

    @BeforeEach
    public void initTests() throws DatabaseException {
        services = new Services();
    }

    @Test
    public void registerTest() {
        services.getClearResult().clearResult();

        RequestRegister request = services.getRegisterRequest();

        request.setUserName("Clint_userName");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResultsRegister result = services.getRegisterResult();
        result.registerResult(request);

        assertTrue(result.getUserName().equals("Clint_userName"));
        try {
            System.out.println(result.getMessage());
            result.getMessage().equals("Could not add to database.");
        }catch(NullPointerException ex){//message should be null
            assertTrue(true);
        }

        services.getClearResult().clearResult();
    }


    @Test
    public void login() throws Exception {
        services.getClearResult().clearResult();

        //sets up database
        RequestRegister request = services.getRegisterRequest();

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");

        ResultsRegister result = services.getRegisterResult();
        result.registerResult(request);
        //valid login

        RequestLogin login = services.getLoginRequest();
        login.setPassword("password");
        login.setUserName("Clintrf");

        ResultsLogin result1 = services.getLoginResult();
        result1.loginResult(login);

        assertTrue(result1.getUserName().equals("Clintrf"));

        /*
        //invalid login
        RequestLogin login2 = new RequestLogin();
        login.setpassword("password");
        login.setUserName("Logan");

        ResponseLogin response2 = facade.login(login);
        assertTrue(response2.getMessage().equals("Credentials not applicable"));

        clear(); //test clear function

         */
        services.getClearResult().clearResult();
    }

    @Test
    public void clear() throws Exception {
        services.getClearResult().clearResult();
    }

    @Test
    public void fill() throws Exception {

        services.getClearResult().clearResult();

        //sets up database
        RequestRegister request = services.getRegisterRequest();

        request.setUserName("Clintrf");
        request.setEmail("email");
        request.setPassword("password");
        request.setFirstName("Clint");
        request.setLastName("Frandsen");
        request.setGender("m");


        ResultsRegister result = services.getRegisterResult();
        result.registerResult(request);

        ResultsFill fill = services.getFillResult();
        fill.fillResult("Clintrf",0);

        assertTrue(fill.getMessage() != null);

        ResultsFill fill1 = services.getFillResult();
        fill1.fillResult("Clintrf",6);
        assertTrue(fill1.getSuccess() == true);

        ResultsFill fill2 = services.getFillResult();
        fill2.fillResult("Clintrf_Bad",6);
        assertTrue(fill2.getSuccess() == false);

        services.getClearResult().clearResult();
    }

    @Test
    public void load() throws Exception {
        services.getClearResult().clearResult();

        File file = new File("/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/example.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8"); //takes in example.json as String
        EncoderDecoder coder = new EncoderDecoder();
        RequestLoad loader = coder.decodeToRequestLoad(str);

        ResultsLoad loadResult = services.getLoadResult();
        loadResult.loadResult(loader);
        assertTrue(loadResult.getSuccess() == true);

        services.getClearResult().clearResult();
    }

    @Test
    public void person() throws Exception {
        services.getClearResult().clearResult();





        //load a database
        RequestRegister send = services.getRegisterRequest();
        send.setUserName("Clint_frandsen");
        send.setEmail("email");
        send.setPassword("password");
        send.setFirstName("Clint");
        send.setLastName("Frandsen");
        send.setGender("m");

        ResultsRegister registerResult = services.getRegisterResult();
        registerResult.registerResult(send);

        ResultsFill fillResult = services.getFillResult();
        fillResult.fillResult(registerResult.getUserName(),1);

        ResultsPerson personResult = services.getPersonResult();
        personResult.personResult(registerResult.getAuthToken(),registerResult.getPersonId());

        assertTrue(personResult.getSuccess() == true);
        assertTrue(personResult.getFirstName().equals("Clint"));

        ResultsPerson personResult2 = services.getPersonResult();
        personResult.personResult("bad_token",registerResult.getPersonId());

        assertFalse(personResult2.getMessage().equals("Clint"));
        assertEquals(false, (boolean) personResult2.getSuccess());

        services.getClearResult().clearResult();
    }

    @Test
    public void people() throws Exception {

        services.getClearResult().clearResult();

        //load a database
        File file = new File("/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/example.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8"); //takes in example.json as String
        RequestLoad loader = services.getCoder().decodeToRequestLoad(str);

        ResultsLoad loadResult = services.getLoadResult();
        loadResult.loadResult(loader);

        //login to receive auth_token
        RequestLogin loginRequest = services.getLoginRequest();
        loginRequest.setPassword("parker");
        loginRequest.setUserName("sheila");

        ResultsLogin loginResults = services.getLoginResult();
        loginResults.loginResult(loginRequest);


        ResultsPeople peopleResult = services.getPeopleResult();
        peopleResult.peopleResult(loginResults.getAuthToken());

        assertTrue(peopleResult.getSuccess() == true);

        ResultsPeople peopleResult2 = services.getPeopleResult();
        peopleResult2.peopleResult("Bad_token");
        //failure
        assertTrue(peopleResult2.getSuccess() == false);

        services.getClearResult().clearResult();
    }

    @Test
    public void event() throws Exception {

        services.getClearResult().clearResult();

        RequestRegister send = services.getRegisterRequest();
        send.setUserName("Clint_frandsen");
        send.setEmail("email");
        send.setPassword("password");
        send.setFirstName("Clint");
        send.setLastName("Frandsen");
        send.setGender("m");

        ResultsRegister registerResult = services.getRegisterResult();
        registerResult.registerResult(send);


        ResultsEvent eventResult = services.getEventResult();
        eventResult.eventResult(registerResult.getAuthToken(),"Bad_ID");


        //tests first failure, make sure message body is received
        assertTrue(eventResult.getSuccess() == false);

        //test second failure case

        ResultsPerson personResult = services.getPersonResult();
        personResult.personResult("badAuthToken",registerResult.getPersonId());

        assertTrue(personResult.getSuccess() == false);

        services.getClearResult().clearResult();
    }

    @Test
    public void events() throws Exception {
        services.getClearResult().clearResult();

        File file = new File("/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/src/json/example.json");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8"); //takes in example.json as String
        RequestLoad loader = services.getCoder().decodeToRequestLoad(str);

        ResultsLoad loadResult = services.getLoadResult();
        loadResult.loadResult(loader);

        //login to receive auth_token
        RequestLogin loginRequest = services.getLoginRequest();
        loginRequest.setPassword("parker");
        loginRequest.setUserName("sheila");

        ResultsLogin loginResults = services.getLoginResult();
        loginResults.loginResult(loginRequest);

        ResultsEvents eventsResult = services.getEventsResult();
        eventsResult.eventsResult(loginResults.getAuthToken());

        assertTrue(eventsResult.getSuccess() == true);

        ResultsEvents eventsResult2 = services.getEventsResult();
        eventsResult2.eventsResult("Bad_token");

        assertTrue(eventsResult2.getSuccess() == false);

        services.getClearResult().clearResult();

    }

}
