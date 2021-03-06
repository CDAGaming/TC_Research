package thaumcraft.common.lib.utils;

import java.util.*;

public class HexUtils
{
    static final int[][] NEIGHBOURS;
    
    public static int getDistance(final Hex a1, final Hex a2) {
        return (Math.abs(a1.q - a2.q) + Math.abs(a1.r - a2.r) + Math.abs(a1.q + a1.r - a2.q - a2.r)) / 2;
    }
    
    public static Hex getRoundedHex(final double qq, final double rr) {
        return getRoundedCubicHex(qq, rr, -qq - rr).toHex();
    }
    
    public static CubicHex getRoundedCubicHex(final double xx, final double yy, final double zz) {
        int rx = (int)Math.round(xx);
        int ry = (int)Math.round(yy);
        int rz = (int)Math.round(zz);
        final double x_diff = Math.abs(rx - xx);
        final double y_diff = Math.abs(ry - yy);
        final double z_diff = Math.abs(rz - zz);
        if (x_diff > y_diff && x_diff > z_diff) {
            rx = -ry - rz;
        }
        else if (y_diff > z_diff) {
            ry = -rx - rz;
        }
        else {
            rz = -rx - ry;
        }
        return new CubicHex(rx, ry, rz);
    }
    
    public static ArrayList<Hex> getRing(final int radius) {
        Hex h = new Hex(0, 0);
        for (int k = 0; k < radius; ++k) {
            h = h.getNeighbour(4);
        }
        final ArrayList<Hex> ring = new ArrayList<Hex>();
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < radius; ++j) {
                ring.add(h);
                h = h.getNeighbour(i);
            }
        }
        return ring;
    }
    
    public static ArrayList<Hex> distributeRingRandomly(final int radius, final int entries, final Random random) {
        final ArrayList<Hex> ring = getRing(radius);
        final ArrayList<Hex> results = new ArrayList<Hex>();
        final float spacing = ring.size() / entries;
        float pos = random.nextInt(ring.size());
        for (int i = 0; i < entries; ++i) {
            results.add(ring.get(Math.round(pos)));
            pos += spacing;
            if (pos >= ring.size()) {
                pos -= ring.size();
            }
        }
        return results;
    }
    
    public static HashMap<String, Hex> generateHexes(final int radius) {
        final HashMap<String, Hex> results = new HashMap<String, Hex>();
        Hex h = new Hex(0, 0);
        results.put(h.toString(), h);
        for (int k = 0; k < radius; ++k) {
            h = h.getNeighbour(4);
            Hex hd = new Hex(h.q, h.r);
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j <= k; ++j) {
                    results.put(hd.toString(), hd);
                    hd = hd.getNeighbour(i);
                }
            }
        }
        return results;
    }
    
    static {
        NEIGHBOURS = new int[][] { { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 } };
    }
    
    public static class Hex
    {
        public int q;
        public int r;
        
        public Hex(final int q, final int r) {
            this.q = 0;
            this.r = 0;
            this.q = q;
            this.r = r;
        }
        
        public CubicHex toCubicHex() {
            return new CubicHex(this.q, this.r, -this.q - this.r);
        }
        
        public Pixel toPixel(final int size) {
            return new Pixel(size * 1.5 * this.q, size * Math.sqrt(3.0) * (this.r + this.q / 2.0));
        }
        
        public Hex getNeighbour(final int direction) {
            final int[] d = HexUtils.NEIGHBOURS[direction];
            return new Hex(this.q + d[0], this.r + d[1]);
        }
        
        public boolean equals(final Hex h) {
            return h.q == this.q && h.r == this.r;
        }
        
        @Override
        public String toString() {
            return this.q + ":" + this.r;
        }
        
        public static Hex fromString(final String hs) {
            final String[] ss = hs.split(":");
            if (ss.length == 2) {
                try {
                    final int q = Integer.parseInt(ss[0]);
                    final int r = Integer.parseInt(ss[1]);
                    return new Hex(q, r);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
    
    public static class CubicHex
    {
        public int x;
        public int y;
        public int z;
        
        public CubicHex(final int x, final int y, final int z) {
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Hex toHex() {
            return new Hex(this.x, this.z);
        }
    }
    
    public static class Pixel
    {
        public double x;
        public double y;
        
        public Pixel(final double x, final double y) {
            this.x = 0.0;
            this.y = 0.0;
            this.x = x;
            this.y = y;
        }
        
        public Hex toHex(final int size) {
            final double qq = 0.6666666666666666 * this.x / size;
            final double rr = (0.3333333333333333 * Math.sqrt(3.0) * -this.y - 0.3333333333333333 * this.x) / size;
            return HexUtils.getRoundedHex(qq, rr);
        }
    }
}
