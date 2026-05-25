import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {
        LoginPage login = new LoginPage("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        LoginPage login = new LoginPage("kyle!!!!!!!", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertFalse(login.checkUserName());
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.registerUser());
    }

    @Test
    public void testPasswordMeetsComplexity() {
       LoginPage login = new LoginPage("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        LoginPage login = new LoginPage("kyl_1", "password", "John", "Doe", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.registerUser());
    }

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        LoginPage login = new LoginPage("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        LoginPage login = new LoginPage("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {
        LoginPage login = new LoginPage("kyl_1", "Ch&&sec@ke99!", "user first name", "user last name", "+27838968976");
        boolean isLogged = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(isLogged);
        assertEquals("Welcome user first name, user last name it is great to see you again.", login.returnLoginStatus(isLogged));
    }

    @Test
    public void testLoginFailed() {
        LoginPage login = new LoginPage("kyl_1", "Ch&&sec@ke99!", "user first name", "user last name", "+27838968976");
        boolean isLogged = login.loginUser("wrong_user", "wrong_pass");
        assertFalse(isLogged);
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(isLogged));
    }
}
