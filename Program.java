import java.util.Scanner;
import java.util.UUID;

public class Program {
    public static void printUser(User user){
        System.out.println("Identifier " + user.getIdentifier());
        System.out.println("Name " + user.getName());
        System.out.println("Balance " + user.getBalance());
        System.out.println();
    }

    public static void printTransaction(Transaction transaction){
        System.out.println("Identifier " + transaction.getIdentifier());
        System.out.println("Recipient " + transaction.getRecipient().getName());
        System.out.println("Sender " + transaction.getSender().getName());
        System.out.println("Transfer category " + transaction.getTransferCategory());
        System.out.println("Transfer amount " + transaction.getTransferAmount());
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean mode;
        boolean normal;
        int act = 0;

        if((args.length > 0) && (args[0].equals("--profile=dev")))
            mode = true;
        else
            mode = false;

        Menu menu = new Menu(mode, sc);

        System.out.println("");
        if (mode) {
            while (true) {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. DEV – remove a transfer by ID");
                System.out.println("6. DEV – check transfer validity");
                System.out.println("7. Finish execution");
                menu.action();
            }
        }
        else {
            while (true) {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. Finish execution");
                menu.action();
            }
        }
    }
}
