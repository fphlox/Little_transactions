import java.util.UUID;

public class TransactionsService {
    private UserList userList;

    TransactionsService() {userList = new UsersArrayList();}

    public void addingUser(User user) {userList.addUser(user);}

    public int retrievingUsersBalance(int userId) {return userList.retrieveUserById(userId).getBalance();}

    public void performingTransferTransaction(int recipientId, int senderId, int amount) {
        if (amount <= 0) {
            System.err.println("Transfer amount must be a positive number");
            throw new IllegalTransactionException();
        }
        try {
            userList.retrieveUserById(senderId);
            userList.retrieveUserById(recipientId);
        }
        catch (Exception UserNotFoundException) {
            throw new IllegalTransactionException();
        }
        UUID uuid = UUID.randomUUID();
        userList.retrieveUserById(senderId).getTransactions().addTransaction
                (new Transaction(uuid, userList.retrieveUserById(recipientId), userList.retrieveUserById(senderId), Transaction.Category.CREDIT, amount * (-1)));
        userList.retrieveUserById(recipientId).getTransactions().addTransaction
                (new Transaction(uuid, userList.retrieveUserById(recipientId), userList.retrieveUserById(senderId), Transaction.Category.DEBIT, amount));
    }

    public Transaction[] retrievingTransfersSpecificUser(int userId) {
            return userList.retrieveUserById(userId).getTransactions().transformIntoArray();
    }

    public void removingTransactionSpecificUser(UUID transactionId ,int userId) {
        userList.retrieveUserById(userId).getTransactions().removeTransactionById(transactionId);
    }

    public Transaction[] checkValidityTransactions() {
        Transaction[] result = new Transaction[0];
        User[] users = userList.getUsersArray();
        for (int i = 0; i < userList.retrieveNumberOfUsers(); i++){
            Transaction[] transactions = users[i].getTransactions().transformIntoArray();
            for (int j = 0; j < transactions.length; j++){
                if (transactions[j].getTransferCategory().equals(Transaction.Category.DEBIT)) {
                    if (transactions[j].getSender().getTransactions().findTransaction(transactions[j].getIdentifier()) == false) {
                        result = addToUnpairedArray(result, transactions[j]);
                    }
                }
                else {
                    if (transactions[j].getRecipient().getTransactions().findTransaction(transactions[j].getIdentifier()) == false) {
                        result = addToUnpairedArray(result, transactions[j]);
                    }
                }
            }
        }
        return result;
    }

    public User getUserById(int userId) {
        return userList.retrieveUserById(userId);
    }

    private Transaction[] addToUnpairedArray(Transaction[] result,  Transaction transaction) {
        Transaction[] bufResult = new Transaction[result.length + 1];
        for (int i = 0; i < result.length; i++)
            bufResult[i] = result[i];
        result = bufResult;
        result[result.length - 1] = transaction;
        return result;
    }
}
