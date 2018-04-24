package thaumcraft.common.lib.potions;

import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import thaumcraft.api.blocks.*;

public class PotionThaumarhia extends Potion
{
    public static Potion instance;
    private int statusIconIndex;
    static final ResourceLocation rl;
    
    public PotionThaumarhia(final boolean par2, final int par3) {
        super(par2, par3);
        this.statusIconIndex = -1;
        this.func_76399_b(0, 0);
        this.func_76390_b("potion.thaumarhia");
        this.func_76399_b(7, 2);
        this.func_76404_a(0.25);
    }
    
    public boolean func_76398_f() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_76392_e() {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(PotionThaumarhia.rl);
        return super.func_76392_e();
    }
    
    public void func_76394_a(final EntityLivingBase target, final int par2) {
        if (!target.field_70170_p.field_72995_K && target.field_70170_p.field_73012_v.nextInt(15) == 0 && target.field_70170_p.func_175623_d(new BlockPos((Entity)target))) {
            target.field_70170_p.func_175656_a(new BlockPos((Entity)target), BlocksTC.fluxGoo.func_176223_P());
        }
    }
    
    public boolean func_76397_a(final int par1, final int par2) {
        return par1 % 20 == 0;
    }
    
    static {
        PotionThaumarhia.instance = null;
        rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
    }
}
