package thaumcraft.client.fx.other;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.lib.obj.*;
import net.minecraft.entity.monster.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.vertex.*;

public class FXShieldRunes extends Particle
{
    ResourceLocation[] tex1;
    ResourceLocation[] tex2;
    Entity target;
    float yaw;
    float pitch;
    private IModelCustom model;
    private static final ResourceLocation MODEL;
    
    public FXShieldRunes(final World world, final double d, final double d1, final double d2, final Entity target, final int age, final float yaw, final float pitch) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.tex1 = new ResourceLocation[15];
        this.tex2 = new ResourceLocation[15];
        this.target = null;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.field_70552_h = 1.0f;
        this.field_70553_i = 1.0f;
        this.field_70551_j = 1.0f;
        this.field_70545_g = 0.0f;
        final double field_187129_i = 0.0;
        this.field_187131_k = field_187129_i;
        this.field_187130_j = field_187129_i;
        this.field_187129_i = field_187129_i;
        this.field_70547_e = age + this.field_187136_p.nextInt(age / 2);
        this.func_187115_a(0.01f, 0.01f);
        this.field_70544_f = 1.0f;
        this.target = target;
        this.yaw = yaw;
        this.pitch = pitch;
        final double field_70165_t = target.field_70165_t;
        this.field_187126_f = field_70165_t;
        this.field_187123_c = field_70165_t;
        final double n = (target.func_174813_aQ().field_72338_b + target.func_174813_aQ().field_72337_e) / 2.0;
        this.field_187127_g = n;
        this.field_187124_d = n;
        final double field_70161_v = target.field_70161_v;
        this.field_187128_h = field_70161_v;
        this.field_187125_e = field_70161_v;
        for (int a = 0; a < 15; ++a) {
            this.tex1[a] = new ResourceLocation("thaumcraft", "textures/models/ripple" + (a + 1) + ".png");
            this.tex2[a] = new ResourceLocation("thaumcraft", "textures/models/hemis" + (a + 1) + ".png");
        }
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity p_180434_2_, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        if (this.model == null) {
            this.model = AdvancedModelLoader.loadModel(FXShieldRunes.MODEL);
        }
        final float fade = (this.field_70546_d + f) / this.field_70547_e;
        final float xx = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXShieldRunes.field_70556_an);
        final float yy = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXShieldRunes.field_70554_ao);
        final float zz = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXShieldRunes.field_70555_ap);
        GL11.glTranslated((double)xx, (double)yy, (double)zz);
        float b = 1.0f;
        final int frame = Math.min(15, (int)(14.0f * fade) + 1);
        if (this.target instanceof EntityMob && !(this.target instanceof EntityCultist)) {
            Minecraft.func_71410_x().field_71446_o.func_110577_a(this.tex1[frame - 1]);
            b = 0.5f;
        }
        else {
            Minecraft.func_71410_x().field_71446_o.func_110577_a(this.tex2[frame - 1]);
        }
        final int i = 220;
        final int j = i % 65536;
        final int k = i / 65536;
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0f, k / 1.0f);
        GL11.glRotatef(180.0f - this.yaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.pitch, 1.0f, 0.0f, 0.0f);
        GL11.glScaled(0.2 * this.target.field_70131_O, 0.2 * this.target.field_70131_O, 0.2 * this.target.field_70131_O);
        GL11.glColor4f(b, b, b, Math.min(1.0f, (1.0f - fade) * 3.0f));
        this.model.renderAll();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        Minecraft.func_71410_x().field_71446_o.func_110577_a(ParticleManager.field_110737_b);
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
        this.field_187126_f = this.target.field_70165_t;
        this.field_187127_g = (this.target.func_174813_aQ().field_72338_b + this.target.func_174813_aQ().field_72337_e) / 2.0;
        this.field_187128_h = this.target.field_70161_v;
    }
    
    static {
        MODEL = new ResourceLocation("thaumcraft", "models/obj/hemis.obj");
    }
}
