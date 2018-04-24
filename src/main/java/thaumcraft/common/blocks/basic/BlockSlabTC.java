package thaumcraft.common.blocks.basic;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.config.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockSlabTC extends BlockSlab
{
    public static final PropertyEnum<Variant> VARIANT;
    boolean wood;
    
    protected BlockSlabTC(final String name, final boolean wood) {
        super(wood ? Material.field_151575_d : Material.field_151576_e);
        this.wood = false;
        this.wood = wood;
        this.func_149663_c(name);
        this.setRegistryName("thaumcraft", name);
        IBlockState iblockstate = this.field_176227_L.func_177621_b();
        if (!this.func_176552_j()) {
            iblockstate = iblockstate.func_177226_a((IProperty)BlockSlabTC.field_176554_a, (Comparable)BlockSlab.EnumBlockHalf.BOTTOM);
            this.func_149647_a(ConfigItems.TABTC);
        }
        this.func_149672_a(wood ? SoundType.field_185848_a : SoundType.field_185851_d);
        this.func_180632_j(iblockstate.func_177226_a((IProperty)BlockSlabTC.VARIANT, (Comparable)Variant.DEFAULT));
        this.field_149783_u = !this.func_176552_j();
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150898_a(state.func_177230_c());
    }
    
    @SideOnly(Side.CLIENT)
    public ItemStack func_185473_a(final World worldIn, final BlockPos pos, final IBlockState state) {
        return new ItemStack(state.func_177230_c());
    }
    
    public IBlockState func_176203_a(final int meta) {
        IBlockState iblockstate = this.func_176223_P().func_177226_a((IProperty)BlockSlabTC.VARIANT, (Comparable)Variant.DEFAULT);
        if (!this.func_176552_j()) {
            iblockstate = iblockstate.func_177226_a((IProperty)BlockSlabTC.field_176554_a, (Comparable)(((meta & 0x8) == 0x0) ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP));
        }
        return iblockstate;
    }
    
    public int func_176201_c(final IBlockState state) {
        int i = 0;
        if (!this.func_176552_j() && state.func_177229_b((IProperty)BlockSlabTC.field_176554_a) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 0x8;
        }
        return i;
    }
    
    protected BlockStateContainer func_180661_e() {
        return this.func_176552_j() ? new BlockStateContainer((Block)this, new IProperty[] { BlockSlabTC.VARIANT }) : new BlockStateContainer((Block)this, new IProperty[] { BlockSlabTC.field_176554_a, BlockSlabTC.VARIANT });
    }
    
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_176552_j() {
        return false;
    }
    
    public String func_150002_b(final int meta) {
        return this.func_149739_a();
    }
    
    public IProperty<?> func_176551_l() {
        return (IProperty<?>)BlockSlabTC.VARIANT;
    }
    
    public Comparable<?> func_185674_a(final ItemStack stack) {
        return Variant.DEFAULT;
    }
    
    public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return this.wood ? 20 : super.getFlammability(world, pos, face);
    }
    
    public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return this.wood ? 5 : super.getFireSpreadSpeed(world, pos, face);
    }
    
    static {
        VARIANT = PropertyEnum.func_177709_a("variant", (Class)Variant.class);
    }
    
    public static class Double extends BlockSlabTC
    {
        public Double(final String name, final boolean wood) {
            super(name, wood);
        }
        
        @Override
        public boolean func_176552_j() {
            return true;
        }
    }
    
    public static class Half extends BlockSlabTC
    {
        public Half(final String name, final boolean wood) {
            super(name, wood);
        }
        
        @Override
        public boolean func_176552_j() {
            return false;
        }
    }
    
    public enum Variant implements IStringSerializable
    {
        DEFAULT;
        
        public String func_176610_l() {
            return "default";
        }
    }
}
