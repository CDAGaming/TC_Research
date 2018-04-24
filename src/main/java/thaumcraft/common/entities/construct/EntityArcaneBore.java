package thaumcraft.common.entities.construct;

import thaumcraft.api.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.block.state.*;
import thaumcraft.common.lib.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.lib.utils.*;
import java.util.*;
import net.minecraftforge.items.*;
import net.minecraft.util.math.*;
import thaumcraft.common.world.aura.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import thaumcraft.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.datasync.*;

public class EntityArcaneBore extends EntityOwnedConstruct
{
    BlockPos digTarget;
    BlockPos digTargetPrev;
    float digCost;
    int paused;
    int maxPause;
    long soundDelay;
    Object beam1;
    double beamLength;
    int breakCounter;
    int digDelay;
    int digDelayMax;
    float radInc;
    public int spiral;
    public float currentRadius;
    private float charge;
    private static final DataParameter<EnumFacing> FACING;
    private static final DataParameter<Boolean> ACTIVE;
    public boolean clientDigging;
    
    public EntityArcaneBore(final World worldIn) {
        super(worldIn);
        this.digTarget = null;
        this.digTargetPrev = null;
        this.digCost = 0.25f;
        this.paused = 100;
        this.maxPause = 100;
        this.soundDelay = 0L;
        this.beam1 = null;
        this.beamLength = 0.0;
        this.breakCounter = 0;
        this.digDelay = 0;
        this.digDelayMax = 0;
        this.radInc = 0.0f;
        this.spiral = 0;
        this.currentRadius = 0.0f;
        this.charge = 0.0f;
        this.clientDigging = false;
        this.func_70105_a(0.9f, 0.9f);
    }
    
    public EntityArcaneBore(final World worldIn, final BlockPos pos, final EnumFacing facing) {
        this(worldIn);
        this.setFacing(facing);
        this.func_70080_a(pos.func_177958_n() + 0.5, (double)pos.func_177956_o(), pos.func_177952_p() + 0.5, 0.0f, 0.0f);
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0);
        this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0);
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K) {
            this.field_70177_z = this.field_70759_as;
            if (this.field_70173_aa % 50 == 0) {
                this.func_70691_i(1.0f);
            }
            if (this.field_70173_aa % 10 == 0 && this.getCharge() < 10.0f) {
                this.rechargeVis();
            }
            final int k = MathHelper.func_76128_c(this.field_70165_t);
            int l = MathHelper.func_76128_c(this.field_70163_u);
            final int i1 = MathHelper.func_76128_c(this.field_70161_v);
            if (BlockRailBase.func_176562_d(this.field_70170_p, new BlockPos(k, l - 1, i1))) {
                --l;
            }
            final BlockPos blockpos = new BlockPos(k, l, i1);
            final IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            if (BlockRailBase.func_176563_d(iblockstate)) {
                if (iblockstate.func_177230_c() == BlocksTC.activatorRail) {
                    final boolean ac = (boolean)iblockstate.func_177229_b((IProperty)BlockRailPowered.field_176569_M);
                    this.setActive(!ac);
                }
            }
            else {
                this.setActive(this.field_70170_p.func_175676_y(new BlockPos((Entity)this).func_177977_b()) > 0);
            }
            if (this.validInventory()) {
                try {
                    this.func_184614_ca().func_77945_a(this.field_70170_p, (Entity)this, 0, true);
                }
                catch (Exception ex) {}
            }
        }
        if (!this.isActive()) {
            this.digTarget = null;
            this.func_70671_ap().func_75650_a(this.field_70165_t + this.getFacing().func_82601_c(), this.field_70163_u, this.field_70161_v + this.getFacing().func_82599_e(), 10.0f, 33.0f);
        }
        if (this.digTarget != null && this.getCharge() >= this.digCost && !this.field_70170_p.field_72995_K) {
            this.func_70671_ap().func_75650_a(this.digTarget.func_177958_n() + 0.5, (double)this.digTarget.func_177956_o(), this.digTarget.func_177952_p() + 0.5, 10.0f, 90.0f);
            if (this.digDelay-- <= 0 && this.dig()) {
                this.setCharge((byte)(this.getCharge() - this.digCost));
                if (this.soundDelay < System.currentTimeMillis()) {
                    this.soundDelay = System.currentTimeMillis() + 1200L + this.field_70170_p.field_73012_v.nextInt(100);
                    this.func_184185_a(SoundsTC.rumble, 0.25f, 0.9f + this.field_70170_p.field_73012_v.nextFloat() * 0.2f);
                }
            }
        }
        if (!this.field_70170_p.field_72995_K && this.digTarget == null && this.isActive() && this.validInventory()) {
            this.findNextBlockToDig();
            if (this.digTarget != null) {
                this.field_70170_p.func_72960_a((Entity)this, (byte)16);
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBoreDig(this.digTarget, (Entity)this, this.digDelayMax), new NetworkRegistry.TargetPoint(this.field_70170_p.field_73011_w.getDimension(), (double)this.digTarget.func_177958_n(), (double)this.digTarget.func_177956_o(), (double)this.digTarget.func_177952_p(), 32.0));
            }
            else {
                this.field_70170_p.func_72960_a((Entity)this, (byte)17);
                this.func_70671_ap().func_75650_a(this.field_70165_t + this.getFacing().func_82601_c() * 2, this.field_70163_u + this.getFacing().func_96559_d() * 2 + this.func_70047_e(), this.field_70161_v + this.getFacing().func_82599_e() * 2, 10.0f, 33.0f);
            }
        }
    }
    
    public boolean validInventory() {
        boolean b = this.func_184614_ca() != null && this.func_184614_ca().func_77973_b() instanceof ItemPickaxe;
        if (b && this.func_184614_ca().func_77952_i() + 1 >= this.func_184614_ca().func_77958_k()) {
            b = false;
        }
        return b;
    }
    
    public int getDigRadius() {
        int r = 0;
        if (this.func_184614_ca() != null && this.func_184614_ca().func_77973_b() instanceof ItemPickaxe) {
            final ItemPickaxe pa = (ItemPickaxe)this.func_184614_ca().func_77973_b();
            r = pa.func_77619_b() / 3;
            r += EnumInfusionEnchantment.getInfusionEnchantmentLevel(this.func_184614_ca(), EnumInfusionEnchantment.DESTRUCTIVE) * 2;
        }
        return (r <= 1) ? 2 : r;
    }
    
    public int getDigDepth() {
        int r = this.getDigRadius() * 8;
        r += EnumInfusionEnchantment.getInfusionEnchantmentLevel(this.func_184614_ca(), EnumInfusionEnchantment.BURROWING) * 16;
        return r;
    }
    
    public int getFortune() {
        int r = 0;
        if (this.validInventory()) {
            r = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, this.func_184614_ca());
            r += EnumInfusionEnchantment.getInfusionEnchantmentLevel(this.func_184614_ca(), EnumInfusionEnchantment.SOUNDING);
        }
        return r;
    }
    
    public int getDigSpeed(final IBlockState blockState) {
        int speed = 0;
        if (this.validInventory()) {
            final ItemPickaxe pa = (ItemPickaxe)this.func_184614_ca().func_77973_b();
            speed += (int)(pa.func_150893_a(this.func_184614_ca(), blockState) / 2.0f);
            speed += EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, this.func_184614_ca());
        }
        return speed;
    }
    
    public int getRefining() {
        int refining = 0;
        if (this.func_184614_ca() != null) {
            refining = EnumInfusionEnchantment.getInfusionEnchantmentLevel(this.func_184614_ca(), EnumInfusionEnchantment.REFINING);
        }
        return refining;
    }
    
    public boolean hasSilkTouch() {
        return this.func_184614_ca() != null && EnchantmentHelper.func_77506_a(Enchantments.field_185306_r, this.func_184614_ca()) > 0;
    }
    
    private boolean canSilkTouch(final BlockPos pos, final IBlockState state) {
        return this.hasSilkTouch() && state.func_177230_c().canSilkHarvest(this.field_70170_p, pos, state, (EntityPlayer)null);
    }
    
    private boolean dig() {
        boolean b = false;
        if (this.digTarget != null && !this.field_70170_p.func_175623_d(this.digTarget)) {
            final IBlockState digBs = this.field_70170_p.func_180495_p(this.digTarget);
            if (!digBs.func_177230_c().isAir(digBs, (IBlockAccess)this.field_70170_p, this.digTarget)) {
                int tfortune = this.getFortune();
                boolean silktouch = false;
                if (this.canSilkTouch(this.digTarget, digBs)) {
                    silktouch = true;
                    tfortune = 0;
                }
                List<ItemStack> items = new ArrayList<ItemStack>();
                if (silktouch) {
                    final ItemStack dropped = BlockUtils.getSilkTouchDrop(digBs);
                    if (dropped != null && !dropped.func_190926_b()) {
                        items.add(dropped);
                    }
                }
                else {
                    items = (List<ItemStack>)digBs.func_177230_c().getDrops((IBlockAccess)this.field_70170_p, this.digTarget, digBs, tfortune);
                }
                final List<EntityItem> targets = (List<EntityItem>)this.field_70170_p.func_72872_a((Class)EntityItem.class, new AxisAlignedBB((double)this.digTarget.func_177958_n(), (double)this.digTarget.func_177956_o(), (double)this.digTarget.func_177952_p(), (double)(this.digTarget.func_177958_n() + 1), (double)(this.digTarget.func_177956_o() + 1), (double)(this.digTarget.func_177952_p() + 1)).func_72314_b(1.0, 1.0, 1.0));
                if (targets.size() > 0) {
                    for (final EntityItem e : targets) {
                        items.add(e.func_92059_d().func_77946_l());
                        e.func_70106_y();
                    }
                }
                final int refining = this.getRefining();
                if (items.size() > 0) {
                    for (final ItemStack is : items) {
                        ItemStack dropped2 = is.func_77946_l();
                        if (!silktouch && refining > 0) {
                            dropped2 = Utils.findSpecialMiningResult(is, (refining + 1) * 0.125f, this.field_70170_p.field_73012_v);
                        }
                        if (dropped2 != null && !dropped2.func_190926_b()) {
                            for (final EnumFacing f : EnumFacing.field_82609_l) {
                                final BlockPos p = this.func_180425_c().func_177972_a(f);
                                final IItemHandler inventory = InventoryUtils.getItemHandlerAt(this.func_130014_f_(), p, f);
                                if (inventory != null) {
                                    InventoryUtils.ejectStackAt(this.func_130014_f_(), p, f, dropped2);
                                }
                            }
                        }
                    }
                }
            }
            if (this.func_184614_ca() != null) {
                ++this.breakCounter;
                if (this.breakCounter >= 50) {
                    this.breakCounter = 0;
                    this.func_184614_ca().func_77972_a(1, (EntityLivingBase)this);
                }
                if (this.func_184614_ca().func_190916_E() <= 0) {
                    this.func_184611_a(this.func_184600_cs(), (ItemStack)null);
                }
            }
            b = this.field_70170_p.func_175698_g(this.digTarget);
        }
        this.digTarget = null;
        return b;
    }
    
    private void findNextBlockToDig() {
        if (this.digTargetPrev == null || this.func_174831_c(this.digTargetPrev) > (this.getDigRadius() + 1) * (this.getDigRadius() + 1)) {
            this.digTargetPrev = new BlockPos((Entity)this);
        }
        if (this.radInc == 0.0f) {
            this.radInc = 1.0f;
        }
        final int digRadius = this.getDigRadius();
        final int digDepth = this.getDigDepth();
        int x = this.digTargetPrev.func_177958_n();
        int z = this.digTargetPrev.func_177952_p();
        int y = this.digTargetPrev.func_177956_o();
        final int x2 = x + this.getFacing().func_82601_c() * digDepth;
        final int y2 = y + this.getFacing().func_96559_d() * digDepth;
        final int z2 = z + this.getFacing().func_82599_e() * digDepth;
        final BlockPos end = new BlockPos(x2, y2, z2);
        RayTraceResult mop = this.field_70170_p.func_147447_a(new Vec3d((Vec3i)this.digTargetPrev).func_72441_c(0.5, 0.5, 0.5), new Vec3d((Vec3i)end).func_72441_c(0.5, 0.5, 0.5), false, true, false);
        if (mop != null) {
            final Vec3d digger = new Vec3d(this.field_70165_t + this.getFacing().func_82601_c(), this.field_70163_u + this.func_70047_e() + this.getFacing().func_96559_d(), this.field_70161_v + this.getFacing().func_82599_e());
            mop = this.field_70170_p.func_147447_a(digger, new Vec3d((Vec3i)mop.func_178782_a()).func_72441_c(0.5, 0.5, 0.5), false, true, false);
            if (mop != null) {
                final IBlockState blockState = this.field_70170_p.func_180495_p(mop.func_178782_a());
                if (blockState.func_185887_b(this.field_70170_p, mop.func_178782_a()) > -1.0f && blockState.func_185890_d((IBlockAccess)this.field_70170_p, mop.func_178782_a()) != null) {
                    this.digDelay = Math.max(10 - this.getDigSpeed(blockState), (int)(blockState.func_185887_b(this.field_70170_p, mop.func_178782_a()) * 2.0f) - this.getDigSpeed(blockState) * 2);
                    if (this.digDelay < 1) {
                        this.digDelay = 1;
                    }
                    this.digDelayMax = this.digDelay;
                    if (!mop.func_178782_a().equals((Object)this.func_180425_c()) && !mop.func_178782_a().equals((Object)this.func_180425_c().func_177977_b())) {
                        this.digTarget = mop.func_178782_a();
                        return;
                    }
                }
            }
        }
        while (x == this.digTargetPrev.func_177958_n() && z == this.digTargetPrev.func_177952_p() && y == this.digTargetPrev.func_177956_o()) {
            if (Math.abs(this.currentRadius) > digRadius) {
                this.currentRadius = digRadius;
            }
            this.spiral += (int)(5.0f + (10.0f - Math.abs(this.currentRadius)) * 2.0f);
            if (this.spiral >= 360) {
                this.spiral -= 360;
                this.currentRadius += this.radInc;
                if (this.currentRadius > digRadius || this.currentRadius < -digRadius) {
                    this.currentRadius = 0.0f;
                }
            }
            final Vec3d vsource = new Vec3d(this.field_70165_t + this.getFacing().func_82601_c(), this.field_70163_u + this.getFacing().func_96559_d() + this.func_70047_e(), this.field_70161_v + this.getFacing().func_82599_e());
            Vec3d vtar = new Vec3d(0.0, (double)this.currentRadius, 0.0);
            vtar = Utils.rotateAroundZ(vtar, this.spiral / 180.0f * 3.1415927f);
            vtar = Utils.rotateAroundY(vtar, 1.5707964f * this.getFacing().func_82601_c());
            vtar = Utils.rotateAroundX(vtar, 1.5707964f * this.getFacing().func_96559_d());
            final Vec3d vres = vsource.func_72441_c(vtar.field_72450_a, vtar.field_72448_b, vtar.field_72449_c);
            x = MathHelper.func_76128_c(vres.field_72450_a);
            y = MathHelper.func_76128_c(vres.field_72448_b);
            z = MathHelper.func_76128_c(vres.field_72449_c);
        }
        this.digTargetPrev = new BlockPos(x, y, z);
    }
    
    public boolean func_70097_a(final DamageSource source, final float amount) {
        try {
            if (source.func_76346_g() != null && this.isOwner((EntityLivingBase)source.func_76346_g())) {
                final EnumFacing f = EnumFacing.func_190914_a(this.func_180425_c(), (EntityLivingBase)source.func_76346_g());
                if (f != EnumFacing.DOWN) {
                    this.setFacing(f);
                }
                return false;
            }
        }
        catch (Exception ex) {}
        this.field_70177_z += (float)(this.func_70681_au().nextGaussian() * 45.0);
        this.field_70125_A += (float)(this.func_70681_au().nextGaussian() * 20.0);
        return super.func_70097_a(source, amount);
    }
    
    protected void rechargeVis() {
        this.setCharge(this.getCharge() + AuraHandler.drainVis(this.field_70170_p, this.func_180425_c(), 10.0f, false));
    }
    
    public boolean func_70104_M() {
        return true;
    }
    
    public boolean func_70067_L() {
        return true;
    }
    
    @Override
    public void func_70645_a(final DamageSource cause) {
        super.func_70645_a(cause);
        if (!this.field_70170_p.field_72995_K) {
            this.dropStuff();
        }
    }
    
    protected void dropStuff() {
        if (this.func_184614_ca() != null) {
            this.func_70099_a(this.func_184614_ca(), 0.5f);
        }
    }
    
    @Override
    protected boolean func_184645_a(final EntityPlayer player, final EnumHand hand) {
        if (player.func_184586_b(hand).func_77973_b() instanceof ItemNameTag) {
            return false;
        }
        if (!this.field_70170_p.field_72995_K && this.isOwner((EntityLivingBase)player) && !this.field_70128_L) {
            if (player.func_70093_af()) {
                this.func_184185_a(SoundsTC.zap, 1.0f, 1.0f);
                this.dropStuff();
                this.func_70099_a(new ItemStack(ItemsTC.turretPlacer, 1, 2), 0.5f);
                this.func_70106_y();
                player.func_184609_a(hand);
            }
            else {
                player.openGui((Object)Thaumcraft.instance, 14, this.field_70170_p, this.func_145782_y(), 0, 0);
            }
            return true;
        }
        return super.func_184645_a(player, hand);
    }
    
    public void func_70653_a(final Entity p_70653_1_, final float p_70653_2_, final double p_70653_3_, final double p_70653_5_) {
        super.func_70653_a(p_70653_1_, p_70653_2_, p_70653_3_ / 10.0, p_70653_5_ / 10.0);
        if (this.field_70181_x > 0.1) {
            this.field_70181_x = 0.1;
        }
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityArcaneBore.FACING, (Object)EnumFacing.DOWN);
        this.field_70180_af.func_187214_a((DataParameter)EntityArcaneBore.ACTIVE, (Object)false);
    }
    
    public boolean isActive() {
        return (boolean)this.field_70180_af.func_187225_a((DataParameter)EntityArcaneBore.ACTIVE);
    }
    
    public void setActive(final boolean attacking) {
        this.field_70180_af.func_187227_b((DataParameter)EntityArcaneBore.ACTIVE, (Object)attacking);
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setCharge(nbt.func_74760_g("charge"));
        this.setFacing(EnumFacing.field_82609_l[nbt.func_74771_c("faceing")]);
        this.setActive(nbt.func_74767_n("active"));
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74776_a("charge", this.getCharge());
        nbt.func_74774_a("faceing", (byte)this.getFacing().func_176745_a());
        nbt.func_74757_a("active", this.isActive());
    }
    
    public EnumFacing getFacing() {
        return (EnumFacing)this.func_184212_Q().func_187225_a((DataParameter)EntityArcaneBore.FACING);
    }
    
    public void setFacing(final EnumFacing face) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityArcaneBore.FACING, (Object)face);
    }
    
    public float getCharge() {
        return this.charge;
    }
    
    public void setCharge(final float c) {
        this.charge = c;
    }
    
    public void func_70091_d(final MoverType mt, final double x, final double y, final double z) {
        super.func_70091_d(mt, x / 5.0, y, z / 5.0);
    }
    
    public void func_174812_G() {
        this.func_70097_a(DamageSource.field_76380_i, 400.0f);
    }
    
    protected void func_70628_a(final boolean p_70628_1_, final int treasure) {
        final float b = treasure * 0.15f;
        if (this.field_70146_Z.nextFloat() < 0.2f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.mind), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.2f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.morphicResonator), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.2f + b) {
            this.func_70099_a(new ItemStack(BlocksTC.crystalAir), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.2f + b) {
            this.func_70099_a(new ItemStack(BlocksTC.crystalEarth), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.5f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.mechanismSimple), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.5f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.plate), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.5f + b) {
            this.func_70099_a(new ItemStack(BlocksTC.plankGreatwood), 0.5f);
        }
    }
    
    public int func_70646_bf() {
        return 10;
    }
    
    @Override
    public Team func_96124_cp() {
        if (this.isOwned()) {
            final EntityLivingBase entitylivingbase = this.getOwnerEntity();
            if (entitylivingbase != null) {
                return entitylivingbase.func_96124_cp();
            }
        }
        return super.func_96124_cp();
    }
    
    public float func_70047_e() {
        return 0.8125f;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 16) {
            this.clientDigging = true;
        }
        else if (par1 == 17) {
            this.clientDigging = false;
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    static {
        FACING = EntityDataManager.func_187226_a((Class)EntityArcaneBore.class, DataSerializers.field_187202_l);
        ACTIVE = EntityDataManager.func_187226_a((Class)EntityArcaneBore.class, DataSerializers.field_187198_h);
    }
}
