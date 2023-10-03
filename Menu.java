import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionsService serv;
    private boolean dev;
    private Scanner sc;

    public Menu(boolean mode, Scanner scan) {
        dev = mode;
        sc = scan;
        serv = new TransactionsService();
    }

    public void action() {
        int option = 0;
        boolean normal = true;

        try {
            option = sc.nextInt();
        }
        catch (Exception InputMismatchException) {
            String str = sc.nextLine();
            System.out.println("Enter command number");
            System.out.println("---------------------------------------------------------");
            normal = false;
        }
        if (normal) {
            if (dev) {
                if ((option > 7) || (option < 1))
                    System.err.println("No option with this number");
                else if (option == 1)
                    addUser();
                else if (option == 2)
                    viewUserBalances();
                else if (option == 3)
                    performTransfer();
                else if (option == 4)
                    viewAllTransactionsForSpecificUser();
                else if (option == 5)
                    removeTransferById();
                else if (option == 6)
                    checkTransferValidity();
                else if (option == 7) {
                    System.out.println("Goodbye");
                    System.exit(0);
                }
                System.out.println("---------------------------------------------------------");

            } else {
                if ((option > 5) || (option < 1))
                    System.err.println("No option with this number");
                else if (option == 1)
                    addUser();
                else if (option == 2)
                    viewUserBalances();
                else if (option == 3)
                    performTransfer();
                else if (option == 4)
                    viewAllTransactionsForSpecificUser();
                else if (option == 5) {
                    System.out.println("Goodbye");
                    System.exit(0);
                }
                System.out.println("---------------------------------------------------------");
            }
        }
    }

    private void addUser() {
        String name = "none";
        int balance = 0;
        boolean normal = true;

        System.out.println("Enter a user name and a balance");
        try {
            name = sc.next();
            balance = sc.nextInt();
        }
        catch (Exception InputMismatchException) {
            String str = sc.nextLine();
            System.err.println("Input error");
            normal = false;
        }
        if (normal) {
            try {
                serv.addingUser(new User(name, balance));
            }
            catch (Exception UserNotFoundException) {}
        }
    }

    private void viewUserBalances() {
        int id = 0;
        boolean normal = true;

        System.out.println("Enter a user ID");
        try {
            id = sc.nextInt();
        }
        catch (Exception InputMismatchException) {
            String str = sc.nextLine();
            System.err.println("Input error");
            normal = false;
        }
        if (normal) {
            try {
                System.out.println(serv.getUserById(id).getName() + " - " + serv.retrievingUsersBalance(id));
            }
            catch (Exception UserNotFoundException) {
                System.err.println("User not found");
            }
        }
    }

    private void performTransfer() {
        int senderId = 0;
        int recipientId = 0;
        int amount = 0;
        boolean normal = true;

        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        try {
            senderId = sc.nextInt();
            recipientId = sc.nextInt();
            amount = sc.nextInt();
        }
        catch (Exception InputMismatchException) {
            String str = sc.nextLine();
            System.err.println("Input error");
            normal = false;
        }
        if (normal) {
            try {
                serv.performingTransferTransaction(recipientId, senderId, amount);
                System.out.println("The transfer is completed");
            }
            catch (Exception IllegalTransactionException) {
                System.err.println("Transaction error");
            }
        }
    }

    private  void viewAllTransactionsForSpecificUser() {
        int userId = 0;
        Transaction[] transactions = new Transaction[0];
        boolean normal = true;

        System.out.println("Enter a user ID");
        try {
            userId = sc.nextInt();
        }
        catch (Exception e) {
            String str = sc.nextLine();
            normal = false;
        }
        if (normal) {
            try {
                transactions = serv.retrievingTransfersSpecificUser(userId);
            }
            catch (Exception UserNotFoundException) {
                System.out.println("User not found");
                normal = false;
            }
            if (normal) {
                for (int i = 0; i < transactions.length; i++) {
                    if (transactions[i].getTransferCategory() == Transaction.Category.CREDIT) {
                        System.out.print("To " + transactions[i].getRecipient().getName());
                        System.out.print("(id = " + transactions[i].getRecipient().getIdentifier() + ") ");
                        System.out.print(transactions[i].getTransferAmount());
                        System.out.println(" with id = " + transactions[i].getIdentifier());
                    }
                    else {
                        System.out.print("From " + transactions[i].getSender().getName());
                        System.out.print("(id = " + transactions[i].getSender().getIdentifier() + ") ");
                        System.out.print(transactions[i].getTransferAmount());
                        System.out.println(" with id = " + transactions[i].getIdentifier());
                    }
                }
            }
        }
    }

    private void removeTransferById() {
        int userId = 0;
        UUID transferId = null;
        Transaction transaction = null;
        boolean normal = true;

        System.out.println("Enter a user ID and a transfer ID");
        try {
            userId = sc.nextInt();
            transferId = UUID.fromString(sc.next());
        }
        catch (Exception InputMismatchException) {
            String str = sc.nextLine();
            System.err.println("Input error");
            normal = false;
        }
        if (normal) {
            try {
                serv.removingTransactionSpecificUser(transferId, userId);
            } catch (Exception e) {}
        }
    }

    private void checkTransferValidity() {
        Transaction[] transactions = serv.checkValidityTransactions();

        System.out.println("Check results:");
        if (transactions.length == 0)
            System.out.println("No unpaired transactions");
        for (int i = 0; i < transactions.length; i++) {
            if (transactions[i].getTransferCategory().equals(Transaction.Category.DEBIT)) {
                System.out.print(transactions[i].getRecipient().getName());
                System.out.print("(id = " + transactions[i].getRecipient().getIdentifier() + ") ");
                System.out.print("has an unacknowledged transfer id = " + transactions[i].getIdentifier());
                System.out.print(" from " + transactions[i].getSender().getName());
                System.out.print("(id = " + transactions[i].getSender().getIdentifier() + ") ");
                System.out.println("for " + transactions[i].getTransferAmount());
            }
            else {
                System.out.print(transactions[i].getSender().getName());
                System.out.print("(id = " + transactions[i].getSender().getIdentifier() + ") ");
                System.out.print("has an unacknowledged transfer id = " + transactions[i].getIdentifier());
                System.out.print(" to " + transactions[i].getRecipient().getName());
                System.out.print("(id = " + transactions[i].getRecipient().getIdentifier() + ") ");
                System.out.println("for " + transactions[i].getTransferAmount());
            }
        }
    }
}
