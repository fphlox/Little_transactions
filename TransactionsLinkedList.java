import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private int length;
    private TransactionNode head;

    public TransactionsLinkedList(){ length = 0; }

    public void addTransaction(Transaction transaction){
        if (length == 0) {
            head = new TransactionNode(transaction);
            length++;
            return;
        }
        head = head.addNext(transaction);
        length++;
    }

    public void removeTransactionById(UUID id){
        head = head.remove(id);
        length--;
    }

    public Transaction[] transformIntoArray(){
        Transaction[] transactions = new Transaction[length];
        TransactionNode seeker = head;
        for (int i = length - 1; i >= 0; i--) {
            transactions[i] = seeker.getTransaction();
            seeker = seeker.getPrevious();
        }
        return transactions;
    }

    public boolean findTransaction(UUID id) {
        TransactionNode seeker = head;
        for (int i = length - 1; i >= 0; i--) {
            if (seeker.getTransaction().getIdentifier().equals(id))
                return true;
            seeker = seeker.getPrevious();
        }
        return false;
    }
}
