package thaumcraft.common.lib.potions;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import thaumcraft.api.potions.*;
import java.util.*;

public class PotionInfectiousVisExhaust extends Potion
{
    public static Potion instance;
    private int statusIconIndex;
    static final ResourceLocation rl;
    
    public PotionInfectiousVisExhaust(final boolean par2, final int par3) {
        super(par2, par3);
        this.statusIconIndex = -1;
        this.func_76399_b(0, 0);
        this.func_76390_b("potion.infvisexhaust");
        this.func_76399_b(6, 1);
        this.func_76404_a(0.25);
    }
    
    public boolean func_76398_f() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_76392_e() {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(PotionInfectiousVisExhaust.rl);
        return super.func_76392_e();
    }
    
    public void func_76394_a(final EntityLivingBase target, final int par2) {
        final List<EntityLivingBase> targets = (List<EntityLivingBase>)target.field_70170_p.func_72872_a((Class)EntityLivingBase.class, target.func_174813_aQ().func_72314_b(4.0, 4.0, 4.0));
        if (targets.size() > 0) {
            for (final EntityLivingBase e : targets) {
                if (!e.func_70644_a(PotionInfectiousVisExhaust.instance)) {
                    if (par2 > 0) {
                        e.func_70690_d(new PotionEffect(PotionInfectiousVisExhaust.instance, 6000, par2 - 1, false, true));
                    }
                    else {
                        e.func_70690_d(new PotionEffect(PotionVisExhaust.instance, 6000, 0, false, true));
                    }
                }
            }
        }
    }
    
    public boolean func_76397_a(final int par1, final int par2) {
        return par1 % 40 == 0;
    }
    
    static {
        PotionInfectiousVisExhaust.instance = null;
        rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
    }
}
