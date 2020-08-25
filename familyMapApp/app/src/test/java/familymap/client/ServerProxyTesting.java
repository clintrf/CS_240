package familymap.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import familymap.client.Net.ServerProxy;
import familymap.server.serviceClasses.RequestLogin;
import familymap.server.serviceClasses.RequestRegister;
import familymap.server.serviceClasses.ResponseEvents;
import familymap.server.serviceClasses.ResponseLogin;
import familymap.server.serviceClasses.ResponsePeople;
import familymap.server.serviceClasses.ResponsePerson;
import familymap.server.serviceClasses.ResponseRegister;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerProxyTesting {

    private URL testURL;
    private ServerProxy proxy;

    @Before
    public void setUP(){
        proxy = new ServerProxy();
        try{
            testURL = new URL("http://");
        } catch (Exception e ){
            assertEquals("Throwing exception", e.getMessage());
        }

    }

    @After
    public void tearDown(){
        testURL = null;
        proxy = null;
    }


    @Test
    public void testRegister(){
        RequestRegister request = new RequestRegister();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUserName("username7");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }



        ResponseRegister out = proxy.getRegister(testURL, request);

        ResponseRegister expectedResponse = new ResponseRegister();
        expectedResponse.setUserName(request.getUserName());
        expectedResponse.setSuccess(true);

        expectedResponse.setAuthToken(out.getAuthToken());
        expectedResponse.setPersonID(out.getPersonID());

        assertTrue(expectedResponse.getPersonID().equals(out.getPersonID()));

    }


    @Test
    public void testLogin(){
        //Uses register to register user then login in with that user's info
        RequestRegister request = new RequestRegister();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUserName("username1");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        ResponseRegister expectedResponse = new ResponseRegister();

        expectedResponse.setUserName(request.getUserName());
        expectedResponse.setSuccess(true);

        ResponseRegister out = proxy.getRegister(testURL, request);
        expectedResponse.setAuthToken(out.getAuthToken());
        expectedResponse.setPersonID(out.getPersonID());



        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setPort("8080");
        requestLogin.setHost("127.0.0.1");
        requestLogin.setUserName("username1");
        requestLogin.setPassword("password");

        try{
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/login");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        ResponseLogin expectedResponseLogin = new ResponseLogin();
        expectedResponseLogin.setSuccess(true);
        expectedResponseLogin.setUserName(request.getUserName());

        ResponseLogin outLogin = proxy.getLogin(testURL, requestLogin);
        expectedResponseLogin.setPersonID(outLogin.getPersonID());
        expectedResponseLogin.setAuthToken(outLogin.getAuthToken());

        assertTrue(expectedResponseLogin.getPersonID().equals(outLogin.getPersonID()));
    }


    @Test
    public void testEvent(){
        RequestRegister request = new RequestRegister();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUserName("username2");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        ResponseRegister out = proxy.getRegister(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/event/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        ResponseEvents outTwo = proxy.getEvents(testURL, authToken);

        assertEquals(outTwo.getData().size(), 91);
        assertTrue(outTwo.getSuccess());
    }

    @Test
    public void testPeople(){
        RequestRegister request = new RequestRegister();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUserName("username3");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }



        ResponseRegister out = proxy.getRegister(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/person/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        ResponsePeople outTwo = proxy.getPeople(testURL, authToken);

        assertEquals(outTwo.getData().size(), 31);
        assertTrue(outTwo.getSuccess());
    }



    @Test
    public void testGetPersonURL(){
        RequestRegister request = new RequestRegister();
        request.setPort("8080");
        request.setHost("127.0.0.1");
        request.setUserName("username4");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("first");
        request.setLastName("last");
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }



        ResponseRegister out = proxy.getRegister(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getHost() + ":" + request.getPort() + "/person/" + out.getPersonID());
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        ResponsePerson outTwo = proxy.getPerson(testURL, authToken);

        assertEquals(outTwo.getFirstName(), "first");
        assertEquals(outTwo.getLastName(), "last");
        assertTrue(outTwo.getSuccess());
    }

}
