package net.tofweb.starlite;

public class CellInfo
{
    private double g;
    private double rhs;
    private double cost;
    
    public CellInfo() {
        this.cost = 1.0;
    }
    
    public double getG() {
        return this.g;
    }
    
    public void setG(final double g) {
        this.g = g;
    }
    
    public double getRhs() {
        return this.rhs;
    }
    
    public void setRhs(final double rhs) {
        this.rhs = rhs;
    }
    
    public double getCost() {
        return this.cost;
    }
    
    public void setCost(final double cost) {
        this.cost = cost;
    }
    
    @Override
    public String toString() {
        return "CellInfo [g=" + this.g + ", rhs=" + this.rhs + ", cost=" + this.cost + "]";
    }
}
