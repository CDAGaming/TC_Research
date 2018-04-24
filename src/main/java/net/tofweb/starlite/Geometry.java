package net.tofweb.starlite;

public class Geometry
{
    public static Double euclideanDistance(final Cell cellA, final Cell cellB) {
        if (cellA == null || cellB == null) {
            return null;
        }
        final float x = cellA.getX() - cellB.getX();
        final float y = cellA.getY() - cellB.getY();
        final float z = cellA.getZ() - cellB.getZ();
        return Math.sqrt(x * x + y * y + z * z);
    }
}
