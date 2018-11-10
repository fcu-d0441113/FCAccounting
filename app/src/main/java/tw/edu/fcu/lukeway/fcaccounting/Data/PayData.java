package tw.edu.fcu.lukeway.fcaccounting.Data;

public class PayData {

    public String payId;
    public String memberId;
    public String targetAmount;
    public String timeLimit;
    public String payName;
    public String feeAmount;
    public String currentAmount;

    public PayData(String payId, String memberId, String targetAmount, String timeLimit, String payName, String feeAmount, String  currentAmount) {
        this.payId = payId;
        this.memberId = memberId;
        this.targetAmount = targetAmount;
        this.timeLimit = timeLimit;
        this.payName = payName;
        this.feeAmount = feeAmount;
        this.currentAmount = currentAmount;
    }
}
