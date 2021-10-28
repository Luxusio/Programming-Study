package vendingmachine;

public class ChangesModule {

    private int changes;

    public ChangesModule() {
        this(0);
    }

    public ChangesModule(final int changes) {
        this.changes = changes;
    }

    public void put(final int changes) {
        this.changes += changes;
    }

    public void withdraw(final int changes) {
        final int result = this.changes - changes;
        if (result < 0) {
            throw new IllegalStateException();
        }
        this.changes = result;
    }

    public int getChanges() {
        return changes;
    }

    public VendingMachine() {
        this(0);
    }

    public VendingMachine(final int changes) {
        this.changes = changes;
    }

    private int changes;

    public void put(final int changes) {
        this.changes += changes;
    }

    public void withdraw(final int changes) {
        int result = this.changes - changes;
        if (result < 0) {
            throw new IllegalStateException();
        }
        this.changes = result;
    }

    public int getChanges() {
        return changes;
    }
}
