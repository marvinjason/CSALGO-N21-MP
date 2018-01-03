
public class Record
{
    private int id;
    private String senderName;
    private String senderAddress;
    private String recipientName;
    private String recipientAddress;
    private int kilo;
    private int amount;
    private String shipDate;
    private String shipTime;
    private int batch;
    
    public Record(int id, String senderName, String senderAddress, String recipientName, String recipientAddress, int kilo, int amount, String shipDate, String shipTime, int batch)
    {
        this.id = id;
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.recipientName = recipientName;
        this.recipientAddress = recipientAddress;
        this.kilo = kilo;
        this.amount = amount;
        this.shipDate = shipDate;
        this.shipTime = shipTime;
        this.batch = batch;
    }
    
    public int getID()
    {
        return this.id;
    }
    
    public String getSenderName()
    {
        return this.senderName;
    }
    
    public String getSenderAddress()
    {
        return this.senderAddress;
    }
    
    public String getRecipientName()
    {
        return this.recipientName;
    }
    
    public String getRecipientAddress()
    {
        return this.recipientAddress;
    }
    
    public int getKilo()
    {
        return this.kilo;
    }
    
    public int getAmount()
    {
        return this.amount;
    }
    
    public String getShipDate()
    {
        return this.shipDate;
    }
    
    public String getShipTime()
    {
        return this.shipTime;
    }
    
    public int getBatch()
    {
        return this.batch;
    }
}
