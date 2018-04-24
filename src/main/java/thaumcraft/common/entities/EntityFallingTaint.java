package thaumcraft.common.entities;

import net.minecraftforge.fml.common.registry.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.blocks.world.taint.*;
import thaumcraft.client.fx.*;
import net.minecraft.nbt.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class EntityFallingTaint extends Entity implements IEntityAdditionalSpawnData
{
    public IBlockState fallTile;
    BlockPos oldPos;
    public int fallTime;
    private int fallHurtMax;
    private float fallHurtAmount;
    
    public IBlockState getBlock() {
        return this.fallTile;
    }
    
    public EntityFallingTaint(final World par1World) {
        super(par1World);
        this.fallTime = 0;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
    }
    
    public EntityFallingTaint(final World par1World, final double par2, final double par4, final double par6, final IBlockState par8, final BlockPos o) {
        super(par1World);
        this.fallTime = 0;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
        this.fallTile = par8;
        this.field_70156_m = true;
        this.func_70105_a(0.98f, 0.98f);
        this.func_70107_b(par2, par4, par6);
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.field_70169_q = par2;
        this.field_70167_r = par4;
        this.field_70166_s = par6;
        this.oldPos = o;
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    protected void func_70088_a() {
    }
    
    public void writeSpawnData(final ByteBuf data) {
        data.writeInt(Block.func_149682_b(this.fallTile.func_177230_c()));
        data.writeByte(this.fallTile.func_177230_c().func_176201_c(this.fallTile));
    }
    
    public void readSpawnData(final ByteBuf data) {
        try {
            this.fallTile = Block.func_149729_e(data.readInt()).func_176203_a((int)data.readByte());
        }
        catch (Exception ex) {}
    }
    
    public boolean func_70067_L() {
        return !this.field_70128_L;
    }
    
    public void func_70071_h_() {
        if (this.fallTile == null || this.fallTile == Blocks.field_150350_a) {
            this.func_70106_y();
        }
        else {
            this.field_70169_q = this.field_70165_t;
            this.field_70167_r = this.field_70163_u;
            this.field_70166_s = this.field_70161_v;
            ++this.fallTime;
            this.field_70181_x -= 0.03999999910593033;
            this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= 0.9800000190734863;
            this.field_70181_x *= 0.9800000190734863;
            this.field_70179_y *= 0.9800000190734863;
            final BlockPos bp = new BlockPos((Entity)this);
            if (!this.field_70170_p.field_72995_K) {
                if (this.fallTime == 1) {
                    if (this.field_70170_p.func_180495_p(this.oldPos) != this.fallTile) {
                        this.func_70106_y();
                        return;
                    }
                    this.field_70170_p.func_175698_g(this.oldPos);
                }
                if (this.field_70122_E || this.field_70170_p.func_180495_p(bp.func_177977_b()) == BlocksTC.fluxGoo) {
                    this.field_70159_w *= 0.699999988079071;
                    this.field_70179_y *= 0.699999988079071;
                    this.field_70181_x *= -0.5;
                    if (this.field_70170_p.func_180495_p(bp).func_177230_c() != Blocks.field_150331_J && this.field_70170_p.func_180495_p(bp).func_177230_c() != Blocks.field_180384_M && this.field_70170_p.func_180495_p(bp).func_177230_c() != Blocks.field_150332_K) {
                        this.func_184185_a(SoundsTC.gore, 0.5f, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f + 1.0f) * 0.8f);
                        this.func_70106_y();
                        if (this.canPlace(bp) && !BlockTaint.canFallBelow(this.field_70170_p, bp.func_177977_b()) && this.field_70170_p.func_175656_a(bp, this.fallTile)) {}
                    }
                }
                else if ((this.fallTime > 100 && !this.field_70170_p.field_72995_K && (bp.func_177956_o() < 1 || bp.func_177956_o() > 256)) || this.fallTime > 600) {
                    this.func_70106_y();
                }
            }
            else if (this.field_70122_E || this.fallTime == 1) {
                for (int j = 0; j < 10; ++j) {
                    FXDispatcher.INSTANCE.taintLandFX(this);
                }
            }
        }
    }
    
    private boolean canPlace(final BlockPos pos) {
        return this.field_70170_p.func_180495_p(pos).func_177230_c() == BlocksTC.taintFibre || this.field_70170_p.func_180495_p(pos).func_177230_c() == BlocksTC.fluxGoo || this.field_70170_p.func_190527_a(this.fallTile.func_177230_c(), pos, true, EnumFacing.UP, (Entity)null);
    }
    
    public void func_180430_e(final float distance, final float damageMultiplier) {
    }
    
    protected void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        final Block block = (this.fallTile != null) ? this.fallTile.func_177230_c() : Blocks.field_150350_a;
        final ResourceLocation resourcelocation = (ResourceLocation)Block.field_149771_c.func_177774_c((Object)block);
        par1NBTTagCompound.func_74778_a("Block", (resourcelocation == null) ? "" : resourcelocation.toString());
        par1NBTTagCompound.func_74774_a("Data", (byte)block.func_176201_c(this.fallTile));
        par1NBTTagCompound.func_74774_a("Time", (byte)this.fallTime);
        par1NBTTagCompound.func_74776_a("FallHurtAmount", this.fallHurtAmount);
        par1NBTTagCompound.func_74768_a("FallHurtMax", this.fallHurtMax);
        par1NBTTagCompound.func_74772_a("Old", this.oldPos.func_177986_g());
    }
    
    protected void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        final int i = par1NBTTagCompound.func_74771_c("Data") & 0xFF;
        if (par1NBTTagCompound.func_150297_b("Block", 8)) {
            this.fallTile = Block.func_149684_b(par1NBTTagCompound.func_74779_i("Block")).func_176203_a(i);
        }
        else if (par1NBTTagCompound.func_150297_b("TileID", 99)) {
            this.fallTile = Block.func_149729_e(par1NBTTagCompound.func_74762_e("TileID")).func_176203_a(i);
        }
        else {
            this.fallTile = Block.func_149729_e(par1NBTTagCompound.func_74771_c("Tile") & 0xFF).func_176203_a(i);
        }
        this.fallTime = (par1NBTTagCompound.func_74771_c("Time") & 0xFF);
        this.oldPos = BlockPos.func_177969_a(par1NBTTagCompound.func_74763_f("Old"));
        if (par1NBTTagCompound.func_74764_b("HurtEntities")) {
            this.fallHurtAmount = par1NBTTagCompound.func_74760_g("FallHurtAmount");
            this.fallHurtMax = par1NBTTagCompound.func_74762_e("FallHurtMax");
        }
        if (this.fallTile == null) {
            this.fallTile = Blocks.field_150354_m.func_176223_P();
        }
    }
    
    public void func_85029_a(final CrashReportCategory par1CrashReportCategory) {
        super.func_85029_a(par1CrashReportCategory);
        par1CrashReportCategory.func_71507_a("Immitating block ID", (Object)Block.func_149682_b(this.fallTile.func_177230_c()));
        par1CrashReportCategory.func_71507_a("Immitating block data", (Object)this.fallTile.func_177230_c().func_176201_c(this.fallTile));
    }
    
    public SoundCategory func_184176_by() {
        return SoundCategory.BLOCKS;
    }
    
    @SideOnly(Side.CLIENT)
    public World getWorld() {
        return this.field_70170_p;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_90999_ad() {
        return false;
    }
}
