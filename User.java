public class User {
    private int identifier;
    private String name;
    private int balance;
    private TransactionsList transactions;

    User(String nam, int bal){
        if (bal < 0) {
            throw new UserNotFoundException("Balance cannot be negative");
        }
        identifier = UserIdsGenerator.getInstance().generateId();
        name = nam;
        balance = bal;
        transactions = new TransactionsLinkedList();
        System.out.println("User with id = " + identifier + " is added");
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int bal) {
        if (bal < 0) {
            throw new UserNotFoundException("Balance cannot be negative");
        }
        balance = bal;
    }

    public TransactionsList getTransactions() {
        return transactions;
    }
}