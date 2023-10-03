import java.util.UUID;

public class TransactionNode {
    private Transaction transaction;
    private TransactionNode next;
    private TransactionNode previous;

    public TransactionNode(Transaction trans) {
        transaction = trans;
    }
    public TransactionNode addNext(Transaction trans){
        next = new TransactionNode(trans);
        next.previous = this;
        return next;
    }

    public TransactionNode remove(UUID id){
        if (this.transaction.getIdentifier().equals(id)) {
            if (this.previous != null)
                return this.previous;
            printRemoved(this.transaction);
            return null;
        }

        TransactionNode seeker = this;
        while (seeker.previous != null) {
            if (seeker.transaction.getIdentifier().equals(id)){
                if (seeker.previous != null)
                    seeker.previous.next = seeker.next;
                if (seeker.next != null)
                    seeker.next.previous = seeker.previous;
                printRemoved(seeker.transaction);
                seeker = null;
                return this;
            }
            seeker = seeker.previous;
        }
        if (seeker.transaction.getIdentifier().equals(id)) {
            if (seeker.next != null)
                seeker.next.previous = seeker.previous;
            printRemoved(seeker.transaction);
            seeker = null;
            return this;
        }
        System.err.println("Transaction not found");
        throw new TransactionNotFoundException();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionNode getPrevious() {
        return previous;
    }

    private void printRemoved(Transaction trans) {
        System.out.print("Transfer ");
        if (trans.getTransferCategory() == Transaction.Category.CREDIT) {
            System.out.print("To " + trans.getRecipient().getName());
            System.out.print("(id = " + trans.getRecipient().getIdentifier() + ") ");
            System.out.println((trans.getTransferAmount() * (-1)) + " removed");
        }
        else {
            System.out.print("From " + trans.getSender().getName());
            System.out.print("(id = " + trans.getSender().getIdentifier() + ") ");
            System.out.println(trans.getTransferAmount() + " removed");
        }
    }
}
