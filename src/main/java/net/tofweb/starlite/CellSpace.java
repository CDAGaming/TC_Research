package net.tofweb.starlite;

import java.util.*;

public class CellSpace
{
    private HashMap<Cell, CellInfo> cellHash;
    private double kM;
    private Cell startCell;
    private Cell goalCell;
    
    public CellSpace() {
        this.cellHash = new HashMap<Cell, CellInfo>();
        this.kM = 0.0;
    }
    
    public CellInfo getInfo(final Cell cell) {
        return this.cellHash.get(cell);
    }
    
    public void updateCellCost(final Cell cell, final double cost) {
        if (cell == null) {
            return;
        }
        this.cellHash.get(cell).setCost(cost);
    }
    
    public double getG(final Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        final CellInfo info = this.cellHash.get(cell);
        if (info == null) {
            return 0.0;
        }
        return info.getG();
    }
    
    public Cell makeNewCell(final int x, final int y, final int z) {
        return this.makeNewCell(x, y, z, null);
    }
    
    public Cell makeNewCell(final int x, final int y, final int z, final Costs k) {
        final Cell state = new Cell();
        state.setX(x);
        state.setY(y);
        state.setZ(z);
        state.setKey(k);
        return this.makeNewCell(state);
    }
    
    public Cell makeNewCell(final Cell cell) {
        if (this.cellHash.get(cell) != null) {
            return cell;
        }
        final CellInfo cellInfo = new CellInfo();
        if (this.goalCell == null) {
            throw new RuntimeException("Goal cell not set");
        }
        final double costToGoal = Geometry.euclideanDistance(cell, this.goalCell);
        cellInfo.setRhs(costToGoal);
        cellInfo.setG(costToGoal);
        this.cellHash.put(cell, cellInfo);
        final Costs key = cell.getKey();
        if (key != null && !key.equals(new Costs(-1.0, -1.0))) {
            this.updateVertex(cell);
        }
        this.calculateKey(cell);
        return cell;
    }
    
    public void setStartCell(final int x, final int y, final int z) {
        final Cell cell = new Cell();
        cell.setX(x);
        cell.setY(y);
        cell.setZ(z);
        this.startCell = cell;
        final CellInfo startCellInfo = new CellInfo();
        final double totalPathCost = Geometry.euclideanDistance(this.startCell, this.goalCell);
        startCellInfo.setRhs(totalPathCost);
        startCellInfo.setG(totalPathCost);
        this.cellHash.put(this.startCell, startCellInfo);
        this.startCell = this.calculateKey(this.startCell);
    }
    
    public Cell getStartCell() {
        return this.startCell;
    }
    
    public void setGoalCell(final int x, final int y, final int z) {
        final Cell cell = new Cell();
        cell.setX(x);
        cell.setY(y);
        cell.setZ(z);
        this.goalCell = cell;
        this.cellHash.put(this.goalCell, new CellInfo());
    }
    
    public Cell getGoalCell() {
        return this.goalCell;
    }
    
    protected boolean isClose(final double var1, final double var2) {
        return (var1 == Double.POSITIVE_INFINITY && var2 == Double.POSITIVE_INFINITY) || Math.abs(var1 - var2) < 1.0E-5;
    }
    
    private void updateVertex(final Cell cell) {
        LinkedList<Cell> successors = new LinkedList<Cell>();
        if (!cell.equals(this.getGoalCell())) {
            successors = this.getSuccessors(cell);
            double tmp = Double.POSITIVE_INFINITY;
            for (final Cell successor : successors) {
                final double tmp2 = this.getG(successor) + Geometry.euclideanDistance(cell, successor);
                if (tmp2 < tmp) {
                    tmp = tmp2;
                }
            }
            if (!this.isClose(this.getRHS(cell), tmp)) {
                this.setRHS(cell, tmp);
            }
        }
        if (!this.isClose(this.getG(cell), this.getRHS(cell))) {
            this.insertCell(cell);
        }
    }
    
    private void setRHS(final Cell state, final double rhs) {
        this.makeNewCell(state);
        this.cellHash.get(state).setRhs(rhs);
    }
    
    private double getRHS(final Cell state) {
        if (this.goalCell == null) {
            throw new RuntimeException("Goal cell not set");
        }
        if (state == this.goalCell) {
            return 0.0;
        }
        if (this.cellHash.get(state) == null) {
            return Geometry.euclideanDistance(state, this.goalCell);
        }
        return this.cellHash.get(state).getRhs();
    }
    
    private void insertCell(Cell cell) {
        cell = this.calculateKey(cell);
    }
    
    public LinkedList<Cell> getSuccessors(final Cell state) {
        final LinkedList<Cell> successors = new LinkedList<Cell>();
        Cell tempState = this.makeNewCell(state.getX() + 1, state.getY(), state.getZ(), new Costs(-1.0, -1.0));
        successors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY() + 1, state.getZ(), new Costs(-1.0, -1.0));
        successors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX() - 1, state.getY(), state.getZ(), new Costs(-1.0, -1.0));
        successors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY() - 1, state.getZ(), new Costs(-1.0, -1.0));
        successors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY(), state.getZ() + 1, new Costs(-1.0, -1.0));
        successors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY(), state.getZ() - 1, new Costs(-1.0, -1.0));
        successors.addFirst(tempState);
        return successors;
    }
    
    public LinkedList<Cell> getPredecessors(final Cell state) {
        final LinkedList<Cell> predecessors = new LinkedList<Cell>();
        Cell tempState = this.makeNewCell(state.getX() + 1, state.getY(), state.getZ(), new Costs(-1.0, -1.0));
        predecessors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY() + 1, state.getZ(), new Costs(-1.0, -1.0));
        predecessors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX() - 1, state.getY(), state.getZ(), new Costs(-1.0, -1.0));
        predecessors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY() - 1, state.getZ(), new Costs(-1.0, -1.0));
        predecessors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY(), state.getZ() + 1, new Costs(-1.0, -1.0));
        predecessors.addFirst(tempState);
        tempState = this.makeNewCell(state.getX(), state.getY(), state.getZ() - 1, new Costs(-1.0, -1.0));
        predecessors.addFirst(tempState);
        return predecessors;
    }
    
    private Cell calculateKey(final Cell state) {
        final Cell startCell = this.getStartCell();
        if (startCell == null) {
            throw new RuntimeException("Start cell not set");
        }
        final double cost = Math.min(this.getRHS(state), this.getG(state));
        Costs key = state.getKey();
        if (key == null) {
            key = new Costs(0.0, 0.0);
        }
        key.setCostPlusHeuristic(cost + Geometry.euclideanDistance(state, startCell) + this.kM);
        key.setCost(cost);
        return state;
    }
}
