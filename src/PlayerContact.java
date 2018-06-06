/*
 * Handles a players contact information.
 */
public class PlayerContact {
    private long userId;
    private String phoneNumber;
    private String phoneCarrier;

    public PlayerContact(long userId) {
        this.userId = userId;
    }

    public PlayerContact(long userId, String phoneNumber, String phoneCarrier) {
        this.userId = userId;
        this.phoneCarrier = phoneCarrier;
        this.phoneNumber = phoneNumber;
    }

    public void setNumber(String n) {
        this.phoneNumber = n;
    }

    public String getNumber() {
        return this.phoneNumber;
    }

    public void setCarrier(String n) {
        this.phoneCarrier = n;
    }

    public String getCarrier() {
        return this.phoneCarrier;
    }

    public long getUserId() {
        return this.userId;
    }

    public String toString() {
        return "" + this.userId + " " + this.phoneNumber + " " + this.phoneCarrier;
    }
}
