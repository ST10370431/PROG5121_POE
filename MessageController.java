import java.util.ArrayList;

public class MessageController {
    // Parallel ArrayLists to hold synchronized message data attributes
    private ArrayList<String> recipients = new ArrayList<>();
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();
    private ArrayList<String> hashes = new ArrayList<>();

    // Adds a newly validated message into the system data streams
    public void addMessage(String recipient, String text, String id, String hash) {
        recipients.add(recipient);
        messages.add(text);
        messageIDs.add(id);
        hashes.add(hash);
    }

    // TASK 1: Displays a complete structural report compilation of all items
    public String getFullStatusReport() {
        if (recipients.isEmpty()) {
            return "No messages currently found in the system database.";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("=========================================\n");
        report.append("         GLOBAL MESSAGE REPORT          \n");
        report.append("=========================================\n");
        
        for (int i = 0; i < recipients.size(); i++) {
            report.append("Record entry number: ").append(i + 1).append("\n")
                  .append("  ID: ").append(messageIDs.get(i)).append("\n")
                  .append("  Recipient Number: ").append(recipients.get(i)).append("\n")
                  .append("  Message Hash: ").append(hashes.get(i)).append("\n")
                  .append("  Text Payload: ").append(messages.get(i)).append("\n")
                  .append("-----------------------------------------\n");
        }
        return report.toString();
    }

    // TASK 2: Scans arrays to locate the entry containing the longest string length
    public String getLongestMessageReport() {
        if (messages.isEmpty()) {
            return "No messages available to analyze.";
        }

        int longestIndex = 0;
        int maxLength = messages.get(0).length();

        for (int i = 1; i < messages.size(); i++) {
            if (messages.get(i).length() > maxLength) {
                maxLength = messages.get(i).length();
                longestIndex = i;
            }
        }

        return "=========================================\n" +
               "         LONGEST MESSAGE DETECTED        \n" +
               "=========================================\n" +
               "Recipient Number: " + recipients.get(longestIndex) + "\n" +
               "Character Count Total: " + maxLength + " units\n" +
               "Message Text: " + messages.get(longestIndex) + "\n" +
               "=========================================";
    }

    // TASK 3: Targeted linear search querying a specific recipient number
    public String searchByRecipient(String targetRecipient) {
        StringBuilder results = new StringBuilder();
        boolean found = false;

        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(targetRecipient)) {
                found = true;
                results.append("-> Found Message ID: ").append(messageIDs.get(i))
                       .append(" | Hash: ").append(hashes.get(i))
                       .append(" | Text: ").append(messages.get(i)).append("\n");
            }
        }

        if (!found) {
            return "No messages located for recipient cell: " + targetRecipient;
        }
        return "Search Results for [" + targetRecipient + "]:\n" + results.toString();
    }

    // TASK 4: Dynamic entry deletion by ID (Shifts array data elements automatically)
    public boolean deleteMessageByID(String targetID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(targetID)) {
                recipients.remove(i);
                messages.remove(i);
                messageIDs.remove(i);
                hashes.remove(i);
                return true; // Successfully located and dropped
            }
        }
        return false; // Target ID did not exist
    }
}
