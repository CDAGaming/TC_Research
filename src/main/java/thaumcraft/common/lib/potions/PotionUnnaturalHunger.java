package thaumcraft.common.lib.potions;

import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class PotionUnnaturalHunger extends Potion
{
    public static Potion instance;
    private int statusIconIndex;
    static final ResourceLocation rl;
    
    public PotionUnnaturalHunger(final boolean par2, final int par3) {
        super(par2, par3);
        this.statusIconIndex = -1;
        this.func_76399_b(0, 0);
        this.func_76390_b("potion.unhunger");
        this.func_76399_b(7, 1);
        this.func_76404_a(0.25);
    }
    
    public boolean func_76398_f() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_76392_e() {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(PotionUnnaturalHunger.rl);
        return super.func_76392_e();
    }
    
    public void func_76394_a(final EntityLivingBase target, final int par2) {
        if (!target.field_70170_p.field_72995_K && target instanceof EntityPlayer) {
            ((EntityPlayer)target).func_71020_j(0.025f * (par2 + 1));
        }
    }
    
    public boolean func_76397_a(final int par1, final int par2) {
        return true;
    }
    
    static {
        PotionUnnaturalHunger.instance = null;
        rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
    }
}
