package thaumcraft.client.renderers.entity.projectile;

import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderFocusCloud extends Render
{
    private Random random;
    
    public RenderFocusCloud(final RenderManager rm) {
        super(rm);
        this.random = new Random();
        this.field_76989_e = 0.0f;
    }
    
    public void renderEntityAt(final Entity entity, final double x, final double y, final double z, final float fq, final float pticks) {
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return TextureMap.field_110575_b;
    }
}
