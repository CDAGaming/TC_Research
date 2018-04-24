package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderInhabitedZombie extends RenderZombie
{
    private static final ResourceLocation t1;
    private ModelBiped field_82434_o;
    private ModelZombieVillager field_82432_p;
    private int field_82431_q;
    
    public RenderInhabitedZombie(final RenderManager p_i46127_1_) {
        super(p_i46127_1_);
        this.field_82431_q = 1;
    }
    
    protected ResourceLocation func_110775_a(final EntityZombie entity) {
        return RenderInhabitedZombie.t1;
    }
    
    static {
        t1 = new ResourceLocation("thaumcraft", "textures/entity/czombie.png");
    }
}
