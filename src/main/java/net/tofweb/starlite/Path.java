package net.tofweb.starlite;

import java.util.*;

public class Path extends LinkedList<Cell>
{
    private static final long serialVersionUID = -5572661613938583005L;
    private boolean isComplete;
    
    public Path() {
        this.isComplete = false;
    }
    
    public boolean isComplete() {
        return this.isComplete;
    }
    
    protected void setComplete(final boolean isComplete) {
        this.isComplete = isComplete;
    }
}
