import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // Generating a string that is 255 characters long
        String longText = "A".repeat(255);
        Message msg = new Message("+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", msg.checkMessageLength());
    }

    @Test
    public void testRecipientFormattedCorrectly() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFormattedIncorrectly() {
        Message msg = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashIsCorrect() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.getMessageHash();
        String expectedEnd = ":HITONIGHT";
        
        // Because the ID is random, we assert that the hash ends with the correct word logic
        assertTrue(hash.endsWith(expectedEnd), "Hash should end with " + expectedEnd);
        
        // Testing specific format: 2 chars + colon + number + colon + FirstLast
        String[] parts = hash.split(":");
        assertEquals(3, parts.length);
        assertEquals(2, parts[0].length()); // First two of ID
    }

    @Test
    public void testMessageIDIsCreated() {
        Message msg = new Message("+27718693002", "Test");
        assertNotNull(msg.getMessageID());
        assertTrue(msg.getMessageID().length() <= 10);
    }

    @Test
    public void testMessageSentOptions() {
        Message msg = new Message("+27718693002", "Test");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}
