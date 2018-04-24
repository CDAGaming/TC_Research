package net.tofweb.starlite;

public class Costs
{
    private Double costPlusHeuristic;
    private Double cost;
    
    public Costs(final Double costPlusHeuristic, final Double cost) {
        this.costPlusHeuristic = costPlusHeuristic;
        this.cost = cost;
    }
    
    public Double getCostPlusHeuristic() {
        return this.costPlusHeuristic;
    }
    
    public Double getCost() {
        return this.cost;
    }
    
    public void setCostPlusHeuristic(final Double object1) {
        this.costPlusHeuristic = object1;
    }
    
    public void setCost(final Double object2) {
        this.cost = object2;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.costPlusHeuristic == null) ? 0 : this.costPlusHeuristic.hashCode());
        result = 31 * result + ((this.cost == null) ? 0 : this.cost.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Costs other = (Costs)obj;
        if (this.costPlusHeuristic == null) {
            if (other.costPlusHeuristic != null) {
                return false;
            }
        }
        else if (!this.costPlusHeuristic.equals(other.costPlusHeuristic)) {
            return false;
        }
        if (this.cost == null) {
            if (other.cost != null) {
                return false;
            }
        }
        else if (!this.cost.equals(other.cost)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Costs [costPlusHeuristic=" + this.costPlusHeuristic + ", cost=" + this.cost + "]";
    }
}
