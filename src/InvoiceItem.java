public class InvoiceItem {
    public String productName;
    public String productDetails;
    public int quantity;
    public float unitprice;

    public InvoiceItem(String productName, String productDetails, int quantity, float unitprice) {
        this.productName = productName;
        this.productDetails = productDetails;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }
}
