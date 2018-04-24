package thaumcraft.common.entities.construct;

import com.google.common.base.*;
import java.util.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.server.management.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.passive.*;
import net.minecraft.network.datasync.*;

public class EntityOwnedConstruct extends EntityCreature implements IEntityOwnable
{
    protected static final DataParameter<Byte> TAMED;
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID;
    boolean validSpawn;
    
    public EntityOwnedConstruct(final World worldIn) {
        super(worldIn);
        this.validSpawn = false;
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityOwnedConstruct.TAMED, (Object)(byte)0);
        this.func_184212_Q().func_187214_a((DataParameter)EntityOwnedConstruct.OWNER_UNIQUE_ID, (Object)Optional.absent());
    }
    
    public boolean isOwned() {
        return ((byte)this.func_184212_Q().func_187225_a((DataParameter)EntityOwnedConstruct.TAMED) & 0x4) != 0x0;
    }
    
    public void setOwned(final boolean tamed) {
        final byte b0 = (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityOwnedConstruct.TAMED);
        if (tamed) {
            this.func_184212_Q().func_187227_b((DataParameter)EntityOwnedConstruct.TAMED, (Object)(byte)(b0 | 0x4));
        }
        else {
            this.func_184212_Q().func_187227_b((DataParameter)EntityOwnedConstruct.TAMED, (Object)(byte)(b0 & 0xFFFFFFFB));
        }
    }
    
    public UUID func_184753_b() {
        return (UUID)((Optional)this.func_184212_Q().func_187225_a((DataParameter)EntityOwnedConstruct.OWNER_UNIQUE_ID)).orNull();
    }
    
    public void setOwnerId(final UUID p_184754_1_) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityOwnedConstruct.OWNER_UNIQUE_ID, (Object)Optional.fromNullable((Object)p_184754_1_));
    }
    
    protected int func_70682_h(final int air) {
        return air;
    }
    
    public boolean func_70648_aU() {
        return true;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.clack;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundsTC.clack;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.tool;
    }
    
    public int func_70627_aG() {
        return 240;
    }
    
    protected boolean func_70692_ba() {
        return false;
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.func_70638_az() != null && this.func_184191_r((Entity)this.func_70638_az())) {
            this.func_70624_b((EntityLivingBase)null);
        }
        if (!this.field_70170_p.field_72995_K && !this.validSpawn) {
            this.func_70106_y();
        }
    }
    
    public void setValidSpawn() {
        this.validSpawn = true;
    }
    
    public void func_70014_b(final NBTTagCompound tagCompound) {
        super.func_70014_b(tagCompound);
        tagCompound.func_74757_a("v", this.validSpawn);
        if (this.func_184753_b() == null) {
            tagCompound.func_74778_a("OwnerUUID", "");
        }
        else {
            tagCompound.func_74778_a("OwnerUUID", this.func_184753_b().toString());
        }
    }
    
    public void func_70037_a(final NBTTagCompound tagCompound) {
        super.func_70037_a(tagCompound);
        this.validSpawn = tagCompound.func_74767_n("v");
        String s = "";
        if (tagCompound.func_150297_b("OwnerUUID", 8)) {
            s = tagCompound.func_74779_i("OwnerUUID");
        }
        else {
            final String s2 = tagCompound.func_74779_i("Owner");
            s = PreYggdrasilConverter.func_187473_a(this.func_184102_h(), s2);
        }
        if (!s.isEmpty()) {
            try {
                this.setOwnerId(UUID.fromString(s));
                this.setOwned(true);
            }
            catch (Throwable var4) {
                this.setOwned(false);
            }
        }
    }
    
    public EntityLivingBase getOwnerEntity() {
        try {
            final UUID uuid = this.func_184753_b();
            return (EntityLivingBase)((uuid == null) ? null : this.field_70170_p.func_152378_a(uuid));
        }
        catch (IllegalArgumentException var2) {
            return null;
        }
    }
    
    public boolean isOwner(final EntityLivingBase entityIn) {
        return entityIn == this.getOwnerEntity();
    }
    
    public Team func_96124_cp() {
        if (this.isOwned()) {
            final EntityLivingBase entitylivingbase = this.getOwnerEntity();
            if (entitylivingbase != null) {
                return entitylivingbase.func_96124_cp();
            }
        }
        return super.func_96124_cp();
    }
    
    public boolean func_184191_r(final Entity otherEntity) {
        if (this.isOwned()) {
            final EntityLivingBase entitylivingbase1 = this.getOwnerEntity();
            if (otherEntity == entitylivingbase1) {
                return true;
            }
            if (entitylivingbase1 != null) {
                return entitylivingbase1.func_184191_r(otherEntity);
            }
        }
        return super.func_184191_r(otherEntity);
    }
    
    public void func_70645_a(final DamageSource cause) {
        if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_82736_K().func_82766_b("showDeathMessages") && this.func_145818_k_() && this.getOwnerEntity() instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.getOwnerEntity()).func_145747_a(this.func_110142_aN().func_151521_b());
        }
        super.func_70645_a(cause);
    }
    
    public Entity func_70902_q() {
        return (Entity)this.getOwnerEntity();
    }
    
    protected boolean func_184645_a(final EntityPlayer player, final EnumHand hand) {
        if (player.func_70093_af() || (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof ItemNameTag)) {
            return false;
        }
        if (!this.field_70170_p.field_72995_K && !this.isOwner((EntityLivingBase)player)) {
            player.func_146105_b((ITextComponent)new TextComponentTranslation("§5§o" + I18n.func_74838_a("tc.notowned"), new Object[0]), true);
            return true;
        }
        return super.func_184645_a(player, hand);
    }
    
    static {
        TAMED = EntityDataManager.func_187226_a((Class)EntityTameable.class, DataSerializers.field_187191_a);
        OWNER_UNIQUE_ID = EntityDataManager.func_187226_a((Class)EntityTameable.class, DataSerializers.field_187203_m);
    }
}
