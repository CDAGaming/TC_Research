package net.tofweb.starlite;

public abstract class BlockManager
{
    protected CellSpace space;
    
    public BlockManager(final CellSpace space) {
        this.space = space;
    }
    
    public abstract boolean isBlocked(final Cell p0);
    
    public CellSpace getSpace() {
        return this.space;
    }
}
