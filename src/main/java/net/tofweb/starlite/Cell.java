package net.tofweb.starlite;

public class Cell
{
    public static final Double BILLIONTH;
    public static final double DEFAULT_COST = 1.0;
    private int x;
    private int y;
    private int z;
    private Costs key;
    
    public Cell() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.key = new Costs(0.0, 0.0);
    }
    
    public Cell(final Cell other) {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.key = new Costs(0.0, 0.0);
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.key = other.key;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public void setZ(final int z) {
        this.z = z;
    }
    
    public Costs getKey() {
        return this.key;
    }
    
    public void setKey(final Costs key) {
        this.key = key;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
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
        final Cell other = (Cell)obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }
    
    @Override
    public String toString() {
        return "Cell [x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", key=" + this.key + "]";
    }
    
    static {
        BILLIONTH = 1.0E-6;
    }
}
