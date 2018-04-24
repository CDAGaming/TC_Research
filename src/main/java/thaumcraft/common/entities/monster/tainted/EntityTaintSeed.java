package thaumcraft.common.entities.monster.tainted;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import thaumcraft.common.lib.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import thaumcraft.common.blocks.world.taint.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.api.aura.*;
import net.minecraft.util.math.*;
import thaumcraft.api.potions.*;
import net.minecraft.potion.*;
import thaumcraft.client.fx.*;
import java.util.*;
import thaumcraft.common.world.biomes.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.blocks.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import thaumcraft.common.config.*;
import net.minecraft.entity.*;

public class EntityTaintSeed extends EntityMob implements ITaintedMob
{
    boolean firstRun;
    public float attackAnim;
    
    public EntityTaintSeed(final World par1World) {
        super(par1World);
        this.firstRun = false;
        this.attackAnim = 0.0f;
        this.func_70105_a(1.5f, 1.25f);
        this.field_70728_aV = 8;
    }
    
    protected int getArea() {
        return 1;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
    }
    
    public boolean func_70652_k(final Entity p_70652_1_) {
        this.field_70170_p.func_72960_a((Entity)this, (byte)16);
        this.func_184185_a(SoundsTC.tentacle, this.func_70599_aP(), this.func_70647_i());
        return super.func_70652_k(p_70652_1_);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 16) {
            this.attackAnim = 0.5f;
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    public boolean func_70686_a(final Class clazz) {
        return !ITaintedMob.class.isAssignableFrom(clazz);
    }
    
    public boolean func_184191_r(final Entity otherEntity) {
        return otherEntity instanceof ITaintedMob || super.func_184191_r(otherEntity);
    }
    
    public boolean func_70601_bi() {
        return this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL && this.func_70058_J() && EntityUtils.getEntitiesInRange(this.func_130014_f_(), this.func_180425_c(), (Entity)null, (Class<? extends Entity>)EntityTaintSeed.class, ModConfig.CONFIG_WORLD.taintSpreadArea * 0.8).size() <= 0;
    }
    
    public boolean func_70058_J() {
        return !this.field_70170_p.func_72953_d(this.func_174813_aQ()) && this.field_70170_p.func_72917_a(this.func_174813_aQ(), (Entity)this);
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(75.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0);
    }
    
    public void func_70645_a(final DamageSource cause) {
        TaintHelper.removeTaintSeed(this.func_130014_f_(), this.func_180425_c());
        super.func_70645_a(cause);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K) {
            if (!this.firstRun || this.field_70173_aa % 1200 == 0) {
                TaintHelper.removeTaintSeed(this.func_130014_f_(), this.func_180425_c());
                TaintHelper.addTaintSeed(this.func_130014_f_(), this.func_180425_c());
                this.firstRun = true;
            }
            if (this.field_70173_aa % 20 == 0) {
                final float mod = AuraHandler.getFluxSaturation(this.field_70170_p, this.func_180425_c());
                if (mod <= 0.0f) {
                    this.func_70097_a(DamageSource.field_76366_f, 0.5f);
                    AuraHelper.polluteAura(this.func_130014_f_(), this.func_180425_c(), 0.1f, false);
                }
                else {
                    TaintHelper.spreadFibres(this.field_70170_p, this.func_180425_c().func_177982_a(MathHelper.func_76136_a(this.func_70681_au(), -this.getArea() * 3, this.getArea() * 3), MathHelper.func_76136_a(this.func_70681_au(), -this.getArea(), this.getArea()), MathHelper.func_76136_a(this.func_70681_au(), -this.getArea() * 3, this.getArea() * 3)), true);
                }
                if (this.func_70638_az() != null && this.func_70068_e((Entity)this.func_70638_az()) < this.getArea() * 256 && this.func_70635_at().func_75522_a((Entity)this.func_70638_az())) {
                    this.spawnTentacles((Entity)this.func_70638_az());
                }
                final List<EntityLivingBase> list = EntityUtils.getEntitiesInRange(this.func_130014_f_(), this.func_180425_c(), (Entity)this, (Class<? extends EntityLivingBase>)EntityLivingBase.class, (double)(this.getArea() * 4));
                for (final EntityLivingBase elb : list) {
                    elb.func_70690_d(new PotionEffect(PotionFluxTaint.instance, 200, this.getArea(), false, true));
                }
            }
        }
        else {
            if (this.attackAnim > 0.0f) {
                this.attackAnim *= 0.75f;
            }
            if (this.attackAnim < 0.001) {
                this.attackAnim = 0.0f;
            }
            final float xx = 1.0f * MathHelper.func_76126_a(this.field_70173_aa * 0.05f - 0.5f) / 5.0f;
            final float zz = 1.0f * MathHelper.func_76126_a(this.field_70173_aa * 0.06f - 0.5f) / 5.0f + this.field_70737_aN / 200.0f + this.attackAnim;
            if (this.field_70146_Z.nextFloat() < 0.033) {
                FXDispatcher.INSTANCE.drawLightningFlash((float)this.field_70165_t + xx, (float)this.field_70163_u + this.field_70131_O + 0.25f, (float)this.field_70161_v + zz, 0.7f, 0.1f, 0.9f, 0.5f, 1.5f + this.field_70146_Z.nextFloat());
            }
            else {
                FXDispatcher.INSTANCE.drawTaintParticles((float)this.field_70165_t + xx, (float)this.field_70163_u + this.field_70131_O + 0.25f, (float)this.field_70161_v + zz, (float)this.field_70146_Z.nextGaussian() * 0.05f, 0.1f + 0.01f * this.field_70146_Z.nextFloat(), (float)this.field_70146_Z.nextGaussian() * 0.05f, 2.0f);
            }
        }
    }
    
    protected void spawnTentacles(final Entity entity) {
        if (this.field_70170_p.func_180494_b(entity.func_180425_c()) == BiomeHandler.ELDRITCH || this.field_70170_p.func_180495_p(entity.func_180425_c()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT || this.field_70170_p.func_180495_p(entity.func_180425_c().func_177977_b()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT) {
            final EntityTaintacleSmall taintlet = new EntityTaintacleSmall(this.field_70170_p);
            taintlet.func_70012_b(entity.field_70165_t + this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat(), entity.field_70163_u, entity.field_70161_v + this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat(), 0.0f, 0.0f);
            this.field_70170_p.func_72838_d((Entity)taintlet);
            this.func_184185_a(SoundsTC.tentacle, this.func_70599_aP(), this.func_70647_i());
            if (this.field_70170_p.func_180494_b(entity.func_180425_c()) == BiomeHandler.ELDRITCH && this.field_70170_p.func_175623_d(entity.func_180425_c()) && BlockUtils.isAdjacentToSolidBlock(this.field_70170_p, entity.func_180425_c())) {
                this.field_70170_p.func_175656_a(entity.func_180425_c(), BlocksTC.taintFibre.func_176223_P());
            }
        }
    }
    
    public int func_70627_aG() {
        return 200;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundEvents.field_187540_ab;
    }
    
    protected float func_70647_i() {
        return 1.3f - this.field_70131_O / 10.0f;
    }
    
    protected float func_70599_aP() {
        return this.field_70131_O / 8.0f;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundsTC.tentacle;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.tentacle;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    public boolean func_70067_L() {
        return true;
    }
    
    public void func_191958_b(final float strafe, final float forward, final float friction, final float g) {
    }
    
    public void func_70091_d(final MoverType mt, double par1, double par3, double par5) {
        par1 = 0.0;
        par5 = 0.0;
        if (par3 > 0.0) {
            par3 = 0.0;
        }
        super.func_70091_d(mt, par1, par3, par5);
    }
    
    protected int func_70682_h(final int air) {
        return air;
    }
    
    public boolean func_70648_aU() {
        return true;
    }
    
    protected boolean func_70692_ba() {
        return false;
    }
}
