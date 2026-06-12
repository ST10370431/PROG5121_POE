import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipientCell;
    private String messageText;
    private String messageHash;
    
    // Static variables persist across all instances, perfect for keeping a running total
    private static int totalMessages = 0;
    private static StringBuilder allMessagesHistory = new StringBuilder();

    public Message(String recipientCell, String messageText) {
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageNumber = totalMessages; 
        totalMessages++;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        // Generates a 10-digit number
        long id = (long) (rand.nextDouble() * 10000000000L);
        return String.format("%010d", id);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        // Re-using the logic from Part 1 as requested by the assignment hint
        if (recipientCell != null && recipientCell.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        
        // Split the message into words, format to remove punctuation
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        
        String rawHash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        return rawHash.toUpperCase();
    }

    public String SentMessage(int choice) {
        String details = "\nMessage ID: " + messageID + 
                         "\nMessage Hash: " + messageHash + 
                         "\nRecipient: " + recipientCell + 
                         "\nMessage: " + messageText + "\n";

        switch (choice) {
            case 1:
                allMessagesHistory.append(details);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                allMessagesHistory.append(details);
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    public void storeMessage() {
        // Basic JSON formatting using standard FileWriter
        try (FileWriter file = new FileWriter("messages.json", true)) {
            String jsonStr = "{\n" +
                    "  \"messageID\": \"" + messageID + "\",\n" +
                    "  \"messageHash\": \"" + messageHash + "\",\n" +
                    "  \"recipient\": \"" + recipientCell + "\",\n" +
                    "  \"message\": \"" + messageText + "\"\n" +
                    "}\n";
            file.write(jsonStr);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the JSON file.");
        }
    }

    public String printMessages() {
        return allMessagesHistory.toString();
    }

    public int returnTotalMessagess() {
        return totalMessages;
    }

    // Getters necessary for the JUnit tests
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}
