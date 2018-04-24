package thaumcraft.api.potions;

import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import thaumcraft.api.*;
import thaumcraft.api.entities.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.damagesource.*;
import net.minecraft.entity.ai.attributes.*;

public class PotionFluxTaint extends Potion
{
    public static Potion instance;
    private int statusIconIndex;
    static final ResourceLocation rl;
    
    public PotionFluxTaint(final boolean par2, final int par3) {
        super(par2, par3);
        this.statusIconIndex = -1;
        this.func_76399_b(3, 1);
        this.func_76404_a(0.25);
        this.func_76390_b("potion.flux_taint");
    }
    
    public boolean func_76398_f() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_76392_e() {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(PotionFluxTaint.rl);
        return super.func_76392_e();
    }
    
    public void func_76394_a(final EntityLivingBase target, final int strength) {
        final IAttributeInstance cai = target.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD);
        if (target instanceof ITaintedMob || (cai != null && (int)cai.func_111126_e() == 13)) {
            target.func_70691_i(1.0f);
        }
        else if (!target.func_70662_br() && !(target instanceof EntityPlayer)) {
            target.func_70097_a(DamageSourceThaumcraft.taint, 1.0f);
        }
        else if (!target.func_70662_br() && (target.func_110138_aP() > 1.0f || target instanceof EntityPlayer)) {
            target.func_70097_a(DamageSourceThaumcraft.taint, 1.0f);
        }
    }
    
    public boolean func_76397_a(final int par1, final int par2) {
        final int k = 40 >> par2;
        return k <= 0 || par1 % k == 0;
    }
    
    static {
        PotionFluxTaint.instance = null;
        rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
    }
}
