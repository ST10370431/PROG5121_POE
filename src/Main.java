import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Instantiating the new Part 3 Data Controller
        MessageController messageManager = new MessageController();

        System.out.println("--- User Registration ---");
        
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Cell Phone Number: ");
        String cellNumber = scanner.nextLine();

        LoginPage user = new LoginPage(username, password, firstName, lastName, cellNumber);
        String registrationMessage = user.registerUser();
        System.out.println("\n" + registrationMessage);

        if (user.checkUserName() && user.checkPasswordComplexity()) {
            System.out.println("\n--- User Login ---");
            
            System.out.print("Enter Username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter Password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccess = user.loginUser(loginUsername, loginPassword);
            System.out.println(user.returnLoginStatus(loginSuccess));

            if (loginSuccess) {
                System.out.println("\nWelcome to QuickChat.");
                boolean keepRunning = true;

                while (keepRunning) {
                    System.out.println("\nSelect an option:");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Message Management System");
                    System.out.println("3) Quit");
                    System.out.print("Choice: ");
                    
                    String menuChoice = scanner.nextLine();

                    if (menuChoice.equals("1")) {
                        System.out.print("How many messages do you wish to enter? ");
                        int numMessages = Integer.parseInt(scanner.nextLine());

                        for (int i = 0; i < numMessages; i++) {
                            System.out.println("\n--- Message " + (i + 1) + " ---");
                            
                            System.out.print("Enter Recipient Cell Number: ");
                            String recipient = scanner.nextLine();
                            
                            System.out.print("Enter Message (max 250 characters): ");
                            String text = scanner.nextLine();

                            Message msg = new Message(recipient, text);

                            System.out.println(msg.checkRecipientCell());
                            System.out.println(msg.checkMessageLength());

                            if (msg.checkRecipientCell().equals("Cell phone number successfully captured.") && 
                                msg.checkMessageLength().equals("Message ready to send.")) {
                                
                                System.out.println("\nOptions: 1 to Send, 2 to Disregard, 3 to Store");
                                System.out.print("Enter choice: ");
                                int action = Integer.parseInt(scanner.nextLine());
                                
                                System.out.println(msg.SentMessage(action));
                                
                                if (action == 1 || action == 3) {
                                    System.out.println(msg.getMessageID());
                                    System.out.println("Message Hash: " + msg.getMessageHash());
                                    System.out.println("Recipient: " + recipient);
                                    System.out.println("Message: " + text);
                                    
                                    // NEW PART 3 LOGIC: Save the message to our parallel arrays
                                    messageManager.addMessage(recipient, text, msg.getMessageID(), msg.getMessageHash());
                                }
                            }
                        }
                        
                    } else if (menuChoice.equals("2")) {
                        // NEW PART 3 LOGIC: Sub-menu for Array Operations
                        System.out.println("\n--- Message Management Menu ---");
                        System.out.println("A) View Global Message Report");
                        System.out.println("B) View Longest Message Entry");
                        System.out.println("C) Search Messages by Recipient");
                        System.out.println("D) Delete a Message Entry by ID");
                        System.out.print("Select an option: ");
                        
                        String subChoice = scanner.nextLine().toUpperCase();
                        
                        if (subChoice.equals("A")) {
                            System.out.println("\n" + messageManager.getFullStatusReport());
                        } else if (subChoice.equals("B")) {
                            System.out.println("\n" + messageManager.getLongestMessageReport());
                        } else if (subChoice.equals("C")) {
                            System.out.print("\nEnter Recipient Cell Number to search: ");
                            String searchCell = scanner.nextLine();
                            System.out.println("\n" + messageManager.searchByRecipient(searchCell));
                        } else if (subChoice.equals("D")) {
                            System.out.print("\nEnter the exact Message ID to delete: ");
                            String targetID = scanner.nextLine();
                            boolean deleted = messageManager.deleteMessageByID(targetID);
                            if (deleted) {
                                System.out.println("Message successfully deleted from the system.");
                            } else {
                                System.out.println("Deletion failed: Message ID not found.");
                            }
                        } else {
                            System.out.println("Invalid sub-menu option.");
                        }
                        
                    } else if (menuChoice.equals("3")) {
                        System.out.println("Exiting QuickChat...");
                        keepRunning = false;
                    } else {
                        System.out.println("Invalid selection. Try again.");
                    }
                }
            }
        } else {
            System.out.println("\nRegistration failed. Please run again and verify constraints.");
        }
        
        scanner.close();
    }
}