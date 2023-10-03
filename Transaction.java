import java.util.UUID;

public class Transaction {

    enum Category {
        DEBIT,
        CREDIT
    }

    private UUID Identifier;
    private User Recipient;
    private User Sender;
    private Category TransferCategory;
    private int TransferAmount;

    Transaction(UUID identifier, User recipient, User sender, Category category, int amount) {
        if (category == Category.DEBIT) {
            if (amount <= 0) {
                System.err.println("Debit transactions must be positive");
                throw new IllegalTransactionException();
            }
        }
        if (category == Category.CREDIT) {
            if (amount >= 0) {
                System.err.println("Credit transactions must be negative");
                throw new IllegalTransactionException();
            }
        }

        if (sender.getBalance() + amount < 0) {
            System.err.println("Not enough resources on balance");
            throw new IllegalTransactionException();
        }

        Identifier = identifier;
        Recipient = recipient;
        Sender = sender;
        TransferCategory = category;
        TransferAmount = amount;

        if (TransferCategory == Category.CREDIT)
            sender.setBalance(sender.getBalance() + amount);
        if (TransferCategory == Category.DEBIT)
            recipient.setBalance(recipient.getBalance() + amount);
    }

    public UUID getIdentifier() {
        return Identifier;
    }

    public User getRecipient() {
        return Recipient;
    }

    public User getSender() {
        return Sender;
    }

    public Category getTransferCategory() {
        return TransferCategory;
    }

    public int getTransferAmount() {
        return TransferAmount;
    }
}