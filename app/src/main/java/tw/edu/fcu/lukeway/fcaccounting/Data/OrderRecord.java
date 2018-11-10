package tw.edu.fcu.lukeway.fcaccounting.Data;

public class OrderRecord {

    public String orderId = "";
    public String memberId = "";
    public String payId = "";
    public String payName = "";
    public String totalPrice = "";
    public String fee = "";
    public String orderDate = "";
    public String paymentType = "";
    public String status = "";
    public String orderToken = "";

    public OrderRecord(String orderId, String memberId, String payId, String payName, String totalPrice, String fee, String orderDate, String paymentType, String status, String orderToken) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.payId = payId;
        this.payName = payName;
        this.totalPrice = totalPrice;
        this.fee = fee;
        this.orderDate = orderDate;
        this.paymentType = paymentType;
        this.status = status;
        this.orderToken = orderToken;
    }
}
