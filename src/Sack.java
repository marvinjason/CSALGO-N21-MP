
public class Sack
{
    private int id;
    private int weight;
    private int cost;
    private String address;
    
    public Sack(int id, int weight, int cost, String address)
    {
        this.id = id;
        this.weight = weight;
        this.cost = cost;
        this.address = address;
    }
    
    public int getID()
    {
        return id;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public int getCost()
    {
        return cost;
    }
    
    public String getAddress()
    {
        return address;
    }
}
