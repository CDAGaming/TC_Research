package thaumcraft.common.entities.monster.boss;

import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.tiles.misc.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.lib.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import thaumcraft.common.lib.utils.*;
import java.util.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;

public class EntityCultistPortalGreater extends EntityMob
{
    protected final BossInfoServer bossInfo;
    int stage;
    int stagecounter;
    public int pulse;
    
    public EntityCultistPortalGreater(final World par1World) {
        super(par1World);
        this.bossInfo = (BossInfoServer)new BossInfoServer(this.func_145748_c_(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6).func_186741_a(true);
        this.stage = 0;
        this.stagecounter = 200;
        this.pulse = 0;
        this.field_70178_ae = true;
        this.field_70728_aV = 30;
        this.func_70105_a(1.5f, 3.0f);
    }
    
    public int func_70658_aO() {
        return 5;
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74768_a("stage", this.stage);
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.stage = nbt.func_74762_e("stage");
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(500.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(0.0);
        this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0);
    }
    
    public boolean func_70067_L() {
        return true;
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    public void func_70091_d(final MoverType mt, final double par1, final double par3, final double par5) {
    }
    
    public void func_70636_d() {
    }
    
    public boolean func_70112_a(final double par1) {
        return par1 < 4096.0;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_70070_b() {
        return 15728880;
    }
    
    public float func_70013_c() {
        return 1.0f;
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K) {
            if (this.stagecounter > 0) {
                --this.stagecounter;
                if (this.stagecounter == 160 && this.stage == 0) {
                    this.field_70170_p.func_72960_a((Entity)this, (byte)16);
                    for (final EnumFacing dir : EnumFacing.field_176754_o) {
                        final BlockPos bp = new BlockPos((int)this.field_70165_t - dir.func_82601_c() * 6, (int)this.field_70163_u, (int)this.field_70161_v + dir.func_82599_e() * 6);
                        this.field_70170_p.func_180501_a(bp, BlocksTC.bannerCrimsonCult.func_176223_P(), 3);
                        final TileEntity te = this.field_70170_p.func_175625_s(new BlockPos((int)this.field_70165_t - dir.func_82601_c() * 6, (int)this.field_70163_u, (int)this.field_70161_v + dir.func_82599_e() * 6));
                        if (te != null && te instanceof TileBanner) {
                            int face = 0;
                            switch (dir.ordinal()) {
                                case 2: {
                                    face = 8;
                                    break;
                                }
                                case 3: {
                                    face = 0;
                                    break;
                                }
                                case 4: {
                                    face = 12;
                                    break;
                                }
                                case 5: {
                                    face = 4;
                                    break;
                                }
                            }
                            ((TileBanner)te).setBannerFacing((byte)face);
                            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockArc(new BlockPos((int)this.field_70165_t - dir.func_82601_c() * 6, (int)this.field_70163_u, (int)this.field_70161_v + dir.func_82599_e() * 6), (Entity)this, 0.5f + this.field_70146_Z.nextFloat() * 0.2f, 0.0f, 0.0f), new NetworkRegistry.TargetPoint(this.field_70170_p.field_73011_w.getDimension(), this.field_70165_t, this.field_70163_u, this.field_70161_v, 32.0));
                            this.func_184185_a(SoundsTC.wandfail, 1.0f, 1.0f);
                        }
                    }
                }
                if (this.stagecounter > 20 && this.stagecounter < 150 && this.stage == 0 && this.stagecounter % 13 == 0) {
                    final int a = (int)this.field_70165_t + this.field_70146_Z.nextInt(5) - this.field_70146_Z.nextInt(5);
                    final int b = (int)this.field_70161_v + this.field_70146_Z.nextInt(5) - this.field_70146_Z.nextInt(5);
                    final BlockPos bp2 = new BlockPos(a, (int)this.field_70163_u, b);
                    if (a != (int)this.field_70165_t && b != (int)this.field_70161_v && this.field_70170_p.func_175623_d(bp2)) {
                        this.field_70170_p.func_72960_a((Entity)this, (byte)16);
                        final float rr = this.field_70170_p.field_73012_v.nextFloat();
                        final int md = (rr < 0.05f) ? 2 : ((rr < 0.2f) ? 1 : 0);
                        Block bb = BlocksTC.lootCrateCommon;
                        switch (md) {
                            case 1: {
                                bb = BlocksTC.lootCrateUncommon;
                                break;
                            }
                            case 2: {
                                bb = BlocksTC.lootCrateRare;
                                break;
                            }
                        }
                        this.field_70170_p.func_175656_a(bp2, bb.func_176223_P());
                        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockArc(new BlockPos(a, (int)this.field_70163_u, b), (Entity)this, 0.5f + this.field_70146_Z.nextFloat() * 0.2f, 0.0f, 0.0f), new NetworkRegistry.TargetPoint(this.field_70170_p.field_73011_w.getDimension(), this.field_70165_t, this.field_70163_u, this.field_70161_v, 32.0));
                        this.func_184185_a(SoundsTC.wandfail, 1.0f, 1.0f);
                    }
                }
            }
            else if (this.field_70170_p.func_72890_a((Entity)this, 48.0) != null) {
                this.field_70170_p.func_72960_a((Entity)this, (byte)16);
                switch (this.stage) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4: {
                        this.stagecounter = 15 + this.field_70146_Z.nextInt(10 - this.stage) - this.stage;
                        this.spawnMinions();
                        break;
                    }
                    case 12: {
                        this.stagecounter = 50 + this.getTiming() * 2 + this.field_70146_Z.nextInt(50);
                        this.spawnBoss();
                        break;
                    }
                    default: {
                        final int t = this.getTiming();
                        this.stagecounter = t + this.field_70146_Z.nextInt(5 + t / 3);
                        this.spawnMinions();
                        break;
                    }
                }
                ++this.stage;
            }
            else {
                this.stagecounter = 30 + this.field_70146_Z.nextInt(30);
            }
            if (this.stage < 12) {
                this.func_70691_i(1.0f);
            }
        }
        if (this.pulse > 0) {
            --this.pulse;
        }
    }
    
    int getTiming() {
        final List<EntityCultist> l = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends EntityCultist>)EntityCultist.class, 32.0);
        return l.size() * 20;
    }
    
    void spawnMinions() {
        EntityCultist cultist = null;
        if (this.field_70146_Z.nextFloat() > 0.33) {
            cultist = new EntityCultistKnight(this.field_70170_p);
        }
        else {
            cultist = new EntityCultistCleric(this.field_70170_p);
        }
        cultist.func_70107_b(this.field_70165_t + this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat(), this.field_70163_u + 0.25, this.field_70161_v + this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat());
        cultist.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Vec3i)cultist.func_180425_c())), null);
        cultist.func_175449_a(this.func_180425_c(), 32);
        this.field_70170_p.func_72838_d((Entity)cultist);
        cultist.func_70656_aK();
        cultist.func_184185_a(SoundsTC.wandfail, 1.0f, 1.0f);
        if (this.stage > 12) {
            this.func_70097_a(DamageSource.field_76380_i, (float)(5 + this.field_70146_Z.nextInt(5)));
        }
    }
    
    void spawnBoss() {
        final EntityCultistLeader cultist = new EntityCultistLeader(this.field_70170_p);
        cultist.func_70107_b(this.field_70165_t + this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat(), this.field_70163_u + 0.25, this.field_70161_v + this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat());
        cultist.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Vec3i)cultist.func_180425_c())), null);
        cultist.func_175449_a(this.func_180425_c(), 32);
        this.field_70170_p.func_72838_d((Entity)cultist);
        cultist.func_70656_aK();
        cultist.func_184185_a(SoundsTC.wandfail, 1.0f, 1.0f);
    }
    
    public void func_70100_b_(final EntityPlayer p) {
        if (this.func_70068_e((Entity)p) < 3.0 && p.func_70097_a(DamageSource.func_76354_b((Entity)this, (Entity)this), 8.0f)) {
            this.func_184185_a(SoundsTC.zap, 1.0f, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1f + 1.0f);
        }
    }
    
    protected float func_70599_aP() {
        return 0.75f;
    }
    
    public int func_70627_aG() {
        return 540;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.monolith;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundsTC.zap;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.shock;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int fortune) {
        EntityUtils.entityDropSpecialItem((Entity)this, new ItemStack(ItemsTC.primordialPearl), this.field_70131_O / 2.0f);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte msg) {
        if (msg == 16) {
            this.pulse = 10;
        }
        else {
            super.func_70103_a(msg);
        }
    }
    
    public void func_70690_d(final PotionEffect p_70690_1_) {
    }
    
    public void func_180430_e(final float distance, final float damageMultiplier) {
    }
    
    public void func_70645_a(final DamageSource p_70645_1_) {
        if (!this.field_70170_p.field_72995_K) {
            this.field_70170_p.func_72885_a((Entity)this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0f, false, false);
        }
        super.func_70645_a(p_70645_1_);
    }
    
    public void func_184203_c(final EntityPlayerMP player) {
        super.func_184203_c(player);
        this.bossInfo.func_186761_b(player);
    }
    
    public void func_184178_b(final EntityPlayerMP player) {
        super.func_184178_b(player);
        this.bossInfo.func_186760_a(player);
    }
    
    public boolean func_184222_aU() {
        return false;
    }
    
    protected void func_70619_bc() {
        super.func_70619_bc();
        this.bossInfo.func_186735_a(this.func_110143_aJ() / this.func_110138_aP());
    }
}
