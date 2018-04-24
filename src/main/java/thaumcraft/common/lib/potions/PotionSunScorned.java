package thaumcraft.common.lib.potions;

import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class PotionSunScorned extends Potion
{
    public static Potion instance;
    private int statusIconIndex;
    static final ResourceLocation rl;
    
    public PotionSunScorned(final boolean par2, final int par3) {
        super(par2, par3);
        this.statusIconIndex = -1;
        this.func_76399_b(0, 0);
        this.func_76390_b("potion.sunscorned");
        this.func_76399_b(6, 2);
        this.func_76404_a(0.25);
    }
    
    public boolean func_76398_f() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_76392_e() {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(PotionSunScorned.rl);
        return super.func_76392_e();
    }
    
    public void func_76394_a(final EntityLivingBase target, final int par2) {
        if (!target.field_70170_p.field_72995_K) {
            final float f = target.func_70013_c();
            if (f > 0.5f && target.field_70170_p.field_73012_v.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && target.field_70170_p.func_175710_j(new BlockPos(MathHelper.func_76128_c(target.field_70165_t), MathHelper.func_76128_c(target.field_70163_u), MathHelper.func_76128_c(target.field_70161_v)))) {
                target.func_70015_d(4);
            }
            else if (f < 0.25f && target.field_70170_p.field_73012_v.nextFloat() > f * 2.0f) {
                target.func_70691_i(1.0f);
            }
        }
    }
    
    public boolean func_76397_a(final int par1, final int par2) {
        return par1 % 40 == 0;
    }
    
    static {
        PotionSunScorned.instance = null;
        rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
    }
}
