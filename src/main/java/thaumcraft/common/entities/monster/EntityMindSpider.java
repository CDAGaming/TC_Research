package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.datasync.*;

public class EntityMindSpider extends EntitySpider implements IEldritchMob
{
    private int lifeSpan;
    private static final DataParameter<Boolean> HARMLESS;
    private static final DataParameter<String> VIEWER;
    
    public EntityMindSpider(final World par1World) {
        super(par1World);
        this.lifeSpan = Integer.MAX_VALUE;
        this.func_70105_a(0.7f, 0.5f);
        this.field_70728_aV = 1;
    }
    
    @Nullable
    protected ResourceLocation func_184647_J() {
        return null;
    }
    
    public float func_70047_e() {
        return 0.45f;
    }
    
    protected int func_70693_a(final EntityPlayer p_70693_1_) {
        return this.isHarmless() ? 0 : super.func_70693_a(p_70693_1_);
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0);
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityMindSpider.HARMLESS, (Object)false);
        this.func_184212_Q().func_187214_a((DataParameter)EntityMindSpider.VIEWER, (Object)String.valueOf(""));
    }
    
    public String getViewer() {
        return (String)this.func_184212_Q().func_187225_a((DataParameter)EntityMindSpider.VIEWER);
    }
    
    public void setViewer(final String player) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityMindSpider.VIEWER, (Object)String.valueOf(player));
    }
    
    public boolean isHarmless() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityMindSpider.HARMLESS);
    }
    
    public void setHarmless(final boolean h) {
        if (h) {
            this.lifeSpan = 1200;
        }
        this.func_184212_Q().func_187227_b((DataParameter)EntityMindSpider.HARMLESS, (Object)h);
    }
    
    protected float func_70647_i() {
        return 0.7f;
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K && this.field_70173_aa > this.lifeSpan) {
            this.func_70106_y();
        }
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean p_70628_1_, final int p_70628_2_) {
    }
    
    public boolean func_145773_az() {
        return true;
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public boolean func_70652_k(final Entity p_70652_1_) {
        return !this.isHarmless() && super.func_70652_k(p_70652_1_);
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setHarmless(nbt.func_74767_n("harmless"));
        this.setViewer(nbt.func_74779_i("viewer"));
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74757_a("harmless", this.isHarmless());
        nbt.func_74778_a("viewer", this.getViewer());
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        return p_180482_2_;
    }
    
    static {
        HARMLESS = EntityDataManager.func_187226_a((Class)EntityMindSpider.class, DataSerializers.field_187198_h);
        VIEWER = EntityDataManager.func_187226_a((Class)EntityMindSpider.class, DataSerializers.field_187194_d);
    }
}
