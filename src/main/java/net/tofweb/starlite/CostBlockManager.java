package net.tofweb.starlite;

public class CostBlockManager extends BlockManager
{
    public CostBlockManager(final CellSpace space) {
        super(space);
    }
    
    public void blockCell(final Cell blockedCell) {
        final CellSpace space = super.getSpace();
        if (blockedCell.equals(space.getStartCell()) || blockedCell.equals(space.getGoalCell())) {
            return;
        }
        space.makeNewCell(blockedCell);
        space.updateCellCost(blockedCell, -1.0);
    }
    
    @Override
    public boolean isBlocked(final Cell cell) {
        final CellSpace space = super.getSpace();
        final CellInfo info = space.getInfo(cell);
        return info != null && info.getCost() < 0.0;
    }
}
