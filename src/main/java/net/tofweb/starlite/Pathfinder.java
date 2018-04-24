package net.tofweb.starlite;

import java.util.*;

public class Pathfinder
{
    private Path path;
    private BlockManager blockManager;
    
    public Pathfinder(final BlockManager blockManager) {
        this.path = new Path();
        this.blockManager = blockManager;
    }
    
    public Path findPath() {
        this.path.clear();
        final CellSpace space = this.blockManager.getSpace();
        LinkedList<Cell> potentialNextCells = new LinkedList<Cell>();
        Cell currentCell = space.getStartCell();
        if (space.getG(space.getStartCell()) == Double.POSITIVE_INFINITY) {
            return this.path;
        }
        boolean isTrapped = false;
        while (!currentCell.equals(space.getGoalCell()) && !isTrapped) {
            isTrapped = true;
            this.path.add(currentCell);
            potentialNextCells = space.getSuccessors(currentCell);
            if (potentialNextCells.isEmpty()) {
                return this.path;
            }
            double minimumCost = Double.POSITIVE_INFINITY;
            Cell minimumCell = new Cell();
            for (final Cell potentialNextCell : potentialNextCells) {
                if (this.blockManager.isBlocked(potentialNextCell)) {
                    continue;
                }
                isTrapped = false;
                double costToMove = Geometry.euclideanDistance(currentCell, potentialNextCell);
                final double euclideanDistance = Geometry.euclideanDistance(potentialNextCell, space.getGoalCell()) + Geometry.euclideanDistance(space.getStartCell(), potentialNextCell);
                costToMove += space.getG(potentialNextCell);
                if (space.isClose(costToMove, minimumCost)) {
                    if (0.0 <= euclideanDistance) {
                        continue;
                    }
                    minimumCost = costToMove;
                    minimumCell = potentialNextCell;
                }
                else {
                    if (costToMove >= minimumCost) {
                        continue;
                    }
                    minimumCost = costToMove;
                    minimumCell = potentialNextCell;
                }
            }
            if (isTrapped) {
                continue;
            }
            potentialNextCells.clear();
            currentCell = new Cell(minimumCell);
        }
        if (!isTrapped) {
            this.path.add(space.getGoalCell());
        }
        this.path.setComplete(this.blockManager.getSpace().getGoalCell().equals(((LinkedList<Object>)this.path).getLast()));
        return this.path;
    }
}
