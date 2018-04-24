package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class AIFollowOwner extends EntityAIBase
{
    private EntityOwnedConstruct thePet;
    private EntityLivingBase theOwner;
    World theWorld;
    private double field_75336_f;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private float field_75344_i;
    
    public AIFollowOwner(final EntityOwnedConstruct p_i1625_1_, final double p_i1625_2_, final float p_i1625_4_, final float p_i1625_5_) {
        this.thePet = p_i1625_1_;
        this.theWorld = p_i1625_1_.field_70170_p;
        this.field_75336_f = p_i1625_2_;
        this.petPathfinder = p_i1625_1_.func_70661_as();
        this.minDist = p_i1625_4_;
        this.maxDist = p_i1625_5_;
        this.func_75248_a(3);
        if (!(p_i1625_1_.func_70661_as() instanceof PathNavigateGround) && !(p_i1625_1_.func_70661_as() instanceof PathNavigateGolemAir)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }
    
    public boolean func_75250_a() {
        final EntityLivingBase entitylivingbase = this.thePet.getOwnerEntity();
        if (entitylivingbase == null) {
            return false;
        }
        if (this.thePet.func_70068_e((Entity)entitylivingbase) < this.minDist * this.minDist) {
            return false;
        }
        this.theOwner = entitylivingbase;
        return true;
    }
    
    public boolean func_75253_b() {
        return !this.petPathfinder.func_75500_f() && this.thePet.func_70068_e((Entity)this.theOwner) > this.maxDist * this.maxDist;
    }
    
    public void func_75249_e() {
        this.field_75343_h = 0;
        this.field_75344_i = this.thePet.func_184643_a(PathNodeType.WATER);
        this.thePet.func_184644_a(PathNodeType.WATER, 0.0f);
    }
    
    public void func_75251_c() {
        this.theOwner = null;
        this.petPathfinder.func_75499_g();
        this.thePet.func_184644_a(PathNodeType.WATER, this.field_75344_i);
    }
    
    private boolean func_181065_a(final BlockPos p_181065_1_) {
        final IBlockState iblockstate = this.theWorld.func_180495_p(p_181065_1_);
        final Block block = iblockstate.func_177230_c();
        return block == Blocks.field_150350_a || !iblockstate.func_185917_h();
    }
    
    public void func_75246_d() {
        this.thePet.func_70671_ap().func_75651_a((Entity)this.theOwner, 10.0f, (float)this.thePet.func_70646_bf());
        final int field_75343_h = this.field_75343_h - 1;
        this.field_75343_h = field_75343_h;
        if (field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (!this.petPathfinder.func_75497_a((Entity)this.theOwner, this.field_75336_f) && !this.thePet.func_110167_bD() && this.thePet.func_70068_e((Entity)this.theOwner) >= 144.0) {
                final int i = MathHelper.func_76128_c(this.theOwner.field_70165_t) - 2;
                final int j = MathHelper.func_76128_c(this.theOwner.field_70161_v) - 2;
                final int k = MathHelper.func_76128_c(this.theOwner.func_174813_aQ().field_72338_b);
                for (int l = 0; l <= 4; ++l) {
                    for (int i2 = 0; i2 <= 4; ++i2) {
                        if ((l < 1 || i2 < 1 || l > 3 || i2 > 3) && this.theWorld.func_180495_p(new BlockPos(i + l, k - 1, j + i2)).func_185917_h() && this.func_181065_a(new BlockPos(i + l, k, j + i2)) && this.func_181065_a(new BlockPos(i + l, k + 1, j + i2))) {
                            this.thePet.func_70012_b((double)(i + l + 0.5f), (double)k, (double)(j + i2 + 0.5f), this.thePet.field_70177_z, this.thePet.field_70125_A);
                            this.petPathfinder.func_75499_g();
                            return;
                        }
                    }
                }
            }
        }
    }
}
