package thaumcraft.api.crafting;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;

public interface IDustTrigger
{
    public static final ArrayList<IDustTrigger> triggers = new ArrayList<IDustTrigger>();
    
    Placement getValidFace(final World p0, final EntityPlayer p1, final BlockPos p2, final EnumFacing p3);
    
    void execute(final World p0, final EntityPlayer p1, final BlockPos p2, final Placement p3, final EnumFacing p4);
    
    default List<BlockPos> sparkle(final World world, final EntityPlayer player, final BlockPos pos, final Placement placement) {
        return Arrays.asList(pos);
    }
    
    default void registerDustTrigger(final IDustTrigger trigger) {
        IDustTrigger.triggers.add(trigger);
    }
    
    public static class Placement
    {
        public int xOffset;
        public int yOffset;
        public int zOffset;
        public EnumFacing facing;
        
        public Placement(final int xOffset, final int yOffset, final int zOffset, final EnumFacing facing) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.zOffset = zOffset;
            this.facing = facing;
        }
    }
}
