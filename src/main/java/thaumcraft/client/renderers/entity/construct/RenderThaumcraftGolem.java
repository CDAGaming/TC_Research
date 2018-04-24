package thaumcraft.client.renderers.entity.construct;

import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.golems.parts.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import thaumcraft.client.lib.obj.*;
import thaumcraft.common.entities.construct.golem.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import thaumcraft.api.golems.*;
import org.apache.logging.log4j.*;

@SideOnly(Side.CLIENT)
public class RenderThaumcraftGolem extends RenderBiped
{
    private static final Logger logger;
    private HashMap<String, IModelCustom> models;
    private HashMap<Integer, HashMap<PartModel.EnumAttachPoint, ArrayList<PartModel>>> partsCache;
    private IModelCustom baseModel;
    float swingProgress;
    
    public RenderThaumcraftGolem(final RenderManager p_i46127_1_) {
        super(p_i46127_1_, new ModelBiped(), 0.3f);
        this.models = new HashMap<String, IModelCustom>();
        this.partsCache = new HashMap<Integer, HashMap<PartModel.EnumAttachPoint, ArrayList<PartModel>>>();
        this.swingProgress = 0.0f;
        this.field_177097_h.clear();
        this.baseModel = AdvancedModelLoader.loadModel(new ResourceLocation("thaumcraft", "models/obj/golem_base.obj"));
    }
    
    private void renderModel(final EntityThaumcraftGolem entity, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float partialTicks) {
        final boolean flag = !entity.func_82150_aj();
        final boolean flag2 = !flag && !entity.func_98034_c((EntityPlayer)Minecraft.func_71410_x().field_71439_g);
        if (flag || flag2) {
            if (flag2) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.func_179132_a(false);
                GlStateManager.func_179147_l();
                GlStateManager.func_179112_b(770, 771);
                GlStateManager.func_179092_a(516, 0.003921569f);
            }
            this.renderParts(entity, p1, p2, p3, p4, p5, p6, partialTicks);
            if (flag2) {
                GlStateManager.func_179084_k();
                GlStateManager.func_179092_a(516, 0.1f);
                GlStateManager.func_179121_F();
                GlStateManager.func_179132_a(true);
            }
        }
    }
    
    private void renderParts(final EntityThaumcraftGolem entity, final float limbSwing, final float prevLimbSwing, final float rotFloat, final float headPitch, final float headYaw, final float p_78087_6_, final float partialTicks) {
        final ResourceLocation matTexture = entity.getProperties().getMaterial().texture;
        final boolean holding = !((EntityLiving)entity).func_184614_ca().func_190926_b();
        final boolean aflag = entity.getProperties().hasTrait(EnumGolemTrait.WHEELED) || entity.getProperties().hasTrait(EnumGolemTrait.FLYER);
        final Vec3d v1 = new Vec3d(entity.field_70165_t, 0.0, entity.field_70161_v);
        final Vec3d v2 = new Vec3d(entity.field_70169_q, 0.0, entity.field_70166_s);
        final double speed = v1.func_72436_e(v2);
        if (!this.partsCache.containsKey(entity.func_145782_y())) {
            this.createPartsCache(entity);
        }
        float f1 = 0.0f;
        float bry = 0.0f;
        final float rx = (float)Math.toDegrees(MathHelper.func_76126_a(rotFloat * 0.067f) * 0.03f);
        final float rz = (float)Math.toDegrees(MathHelper.func_76134_b(rotFloat * 0.09f) * 0.05f + 0.05f);
        float rrx = 0.0f;
        float rry = 0.0f;
        float rrz = 0.0f;
        float rlx = 0.0f;
        float rly = 0.0f;
        float rlz = 0.0f;
        if (holding) {
            rrx = 90.0f - rz / 2.0f;
            rrz = -2.0f;
            rlx = 90.0f - rz / 2.0f;
            rlz = 2.0f;
        }
        else {
            if (aflag) {
                rrx = rx * 2.0f;
                rlx = -rx * 2.0f;
            }
            else {
                f1 = MathHelper.func_76134_b(limbSwing * 0.6662f + 3.1415927f) * 2.0f * prevLimbSwing * 0.5f;
                rrx = (float)(Math.toDegrees(f1) + rx);
                f1 = MathHelper.func_76134_b(limbSwing * 0.6662f) * 2.0f * prevLimbSwing * 0.5f;
                rlx = (float)(Math.toDegrees(f1) - rx);
            }
            rrz += rz + 2.0f;
            rlz -= rz + 2.0f;
        }
        if (this.swingProgress > 0.0f) {
            final float wiggle = -MathHelper.func_76126_a(MathHelper.func_76129_c(this.swingProgress) * 3.1415927f * 2.0f) * 0.2f;
            bry = (float)Math.toDegrees(wiggle);
            rrz = -(float)Math.toDegrees(MathHelper.func_76126_a(wiggle) * 3.0f);
            rrx = (float)Math.toDegrees(-MathHelper.func_76134_b(wiggle) * 5.0f);
            rry += bry;
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_179114_b(bry, 0.0f, 1.0f, 0.0f);
        float lean = 25.0f;
        if (aflag) {
            lean = 75.0f;
        }
        GlStateManager.func_179114_b((float)(speed * lean), -1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b((float)(speed * lean * 0.06 * (entity.field_70177_z - entity.field_70126_B)), 0.0f, 0.0f, -1.0f);
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(0.0, 0.5, 0.0);
        this.func_110776_a(matTexture);
        this.baseModel.renderPart("chest");
        this.baseModel.renderPart("waist");
        if (entity.getGolemColor() > 0) {
            final Color c = new Color(EnumDyeColor.func_176764_b(entity.getGolemColor() - 1).func_193350_e());
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
            this.baseModel.renderPart("flag");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        for (final PartModel part : this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.BODY)) {
            this.renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.MIDDLE);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(0.0, 0.75, -0.03125);
        GlStateManager.func_179114_b(headPitch, 0.0f, -1.0f, 0.0f);
        GlStateManager.func_179114_b(headYaw, -1.0f, 0.0f, 0.0f);
        for (final PartModel part : this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.HEAD)) {
            this.renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.MIDDLE);
        }
        this.func_110776_a(matTexture);
        this.baseModel.renderPart("head");
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(0.20625, 0.6875, 0.0);
        final Iterator<PartModel> iterator3 = this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.ARMS).iterator();
        if (iterator3.hasNext()) {
            final PartModel part = iterator3.next();
            rrx = part.preRenderArmRotationX(entity, partialTicks, PartModel.EnumLimbSide.RIGHT, rrx);
            rry = part.preRenderArmRotationY(entity, partialTicks, PartModel.EnumLimbSide.RIGHT, rry);
            rrz = part.preRenderArmRotationZ(entity, partialTicks, PartModel.EnumLimbSide.RIGHT, rrz);
        }
        GlStateManager.func_179114_b(rrx, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(rry, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(rrz, 0.0f, 0.0f, 1.0f);
        this.func_110776_a(matTexture);
        this.baseModel.renderPart("arm");
        final Iterator<PartModel> iterator4 = this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.ARMS).iterator();
        while (iterator4.hasNext()) {
            final PartModel part = iterator4.next();
            this.renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.RIGHT);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(-0.20625, 0.6875, 0.0);
        final Iterator<PartModel> iterator5 = this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.ARMS).iterator();
        if (iterator5.hasNext()) {
            final PartModel part = iterator5.next();
            rlx = part.preRenderArmRotationX(entity, partialTicks, PartModel.EnumLimbSide.LEFT, rlx);
            rly = part.preRenderArmRotationY(entity, partialTicks, PartModel.EnumLimbSide.LEFT, rly);
            rlz = part.preRenderArmRotationZ(entity, partialTicks, PartModel.EnumLimbSide.LEFT, rlz);
        }
        GlStateManager.func_179114_b(rlx, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(rly + 180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(rlz, 0.0f, 0.0f, -1.0f);
        this.func_110776_a(matTexture);
        this.baseModel.renderPart("arm");
        final Iterator<PartModel> iterator6 = this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.ARMS).iterator();
        while (iterator6.hasNext()) {
            final PartModel part = iterator6.next();
            this.renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.LEFT);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(0.09375, 0.375, 0.0);
        f1 = MathHelper.func_76134_b(limbSwing * 0.6662f) * prevLimbSwing;
        GlStateManager.func_179114_b((float)Math.toDegrees(f1), 1.0f, 0.0f, 0.0f);
        final Iterator<PartModel> iterator7 = this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.LEGS).iterator();
        while (iterator7.hasNext()) {
            final PartModel part = iterator7.next();
            this.renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.RIGHT);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(-0.09375, 0.375, 0.0);
        f1 = MathHelper.func_76134_b(limbSwing * 0.6662f + 3.1415927f) * prevLimbSwing;
        GlStateManager.func_179114_b((float)Math.toDegrees(f1), 1.0f, 0.0f, 0.0f);
        final Iterator<PartModel> iterator8 = this.partsCache.get(entity.func_145782_y()).get(PartModel.EnumAttachPoint.LEGS).iterator();
        while (iterator8.hasNext()) {
            final PartModel part = iterator8.next();
            this.renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.LEFT);
        }
        GlStateManager.func_179121_F();
        GL11.glDisable(3042);
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(0.0, 0.625, 0.0);
        GlStateManager.func_179114_b(90.0f - rz * 0.5f, 1.0f, 0.0f, 0.0f);
        this.drawHeldItem(entity);
        GlStateManager.func_179121_F();
    }
    
    private void drawHeldItem(final EntityThaumcraftGolem entity) {
        final ItemStack itemstack = entity.func_184614_ca();
        if (itemstack != null && !itemstack.func_190926_b()) {
            GlStateManager.func_179094_E();
            final Item item = itemstack.func_77973_b();
            final Minecraft minecraft = Minecraft.func_71410_x();
            GlStateManager.func_179114_b(90.0f, -1.0f, 0.0f, 0.0f);
            GlStateManager.func_179139_a(0.375, 0.375, 0.375);
            GlStateManager.func_179109_b(0.0f, 0.0f, -1.5f);
            if (!(item instanceof ItemBlock)) {
                GlStateManager.func_179109_b(0.0f, -0.6f, 0.0f);
            }
            minecraft.func_175597_ag().func_178099_a((EntityLivingBase)entity, itemstack, ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.func_179121_F();
        }
    }
    
    private void renderPart(final EntityThaumcraftGolem golem, final String partName, final PartModel part, final ResourceLocation matTexture, final float partialTicks, final PartModel.EnumLimbSide side) {
        IModelCustom model = this.models.get(partName);
        if (model == null) {
            model = AdvancedModelLoader.loadModel(part.getObjModel());
            if (model == null) {
                return;
            }
            this.models.put(partName, model);
        }
        for (final String op : model.getPartNames()) {
            GL11.glPushMatrix();
            if (part.useMaterialTextureForObjectPart(op)) {
                this.func_110776_a(matTexture);
            }
            else {
                this.func_110776_a(part.getTexture());
            }
            part.preRenderObjectPart(op, golem, partialTicks, side);
            model.renderPart(op);
            part.postRenderObjectPart(op, golem, partialTicks, side);
            GL11.glPopMatrix();
        }
    }
    
    private void doRender(final EntityThaumcraftGolem entity, final double x, final double y, final double z, final float p_76986_8_, final float partialTicks) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre((EntityLivingBase)entity, (RenderLivingBase)this, x, y, z))) {
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179129_p();
        this.swingProgress = this.func_77040_d((EntityLivingBase)entity, partialTicks);
        try {
            float f2 = this.func_77034_a(entity.field_70760_ar, entity.field_70761_aq, partialTicks);
            final float f3 = this.func_77034_a(entity.field_70758_at, entity.field_70759_as, partialTicks);
            float f4 = f3 - f2;
            if (entity.func_184218_aH() && entity.func_184187_bx() instanceof EntityLivingBase) {
                final EntityLivingBase entitylivingbase1 = (EntityLivingBase)entity.func_184187_bx();
                f2 = this.func_77034_a(entitylivingbase1.field_70760_ar, entitylivingbase1.field_70761_aq, partialTicks);
                f4 = f3 - f2;
                float f5 = MathHelper.func_76142_g(f4);
                if (f5 < -85.0f) {
                    f5 = -85.0f;
                }
                if (f5 >= 85.0f) {
                    f5 = 85.0f;
                }
                f2 = f3 - f5;
                if (f5 * f5 > 2500.0f) {
                    f2 += f5 * 0.2f;
                }
            }
            final float f6 = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
            this.func_77039_a((EntityLivingBase)entity, x, y, z);
            float f5 = this.func_77044_a((EntityLivingBase)entity, partialTicks);
            this.func_77043_a((EntityLivingBase)entity, f5, f2, partialTicks);
            GlStateManager.func_179091_B();
            this.func_77041_b((EntityLivingBase)entity, partialTicks);
            float f7 = entity.field_184618_aE + (entity.field_70721_aZ - entity.field_184618_aE) * partialTicks;
            final float f8 = entity.field_184619_aG - entity.field_70721_aZ * (1.0f - partialTicks);
            if (f7 > 1.0f) {
                f7 = 1.0f;
            }
            GlStateManager.func_179141_d();
            if (this.field_188301_f) {
                final boolean flag = this.func_177088_c((EntityLivingBase)entity);
                this.renderModel(entity, f8, f7, f5, f4, f6, 0.0625f, partialTicks);
                if (flag) {
                    this.func_180565_e();
                }
            }
            else {
                final boolean flag = this.func_177090_c((EntityLivingBase)entity, partialTicks);
                this.renderModel(entity, f8, f7, f5, f4, f6, 0.0625f, partialTicks);
                if (flag) {
                    this.func_177091_f();
                }
                GlStateManager.func_179132_a(true);
                this.func_177093_a((EntityLivingBase)entity, f8, f7, partialTicks, f5, f4, f6, 0.0625f);
            }
            GlStateManager.func_179101_C();
        }
        catch (Exception exception) {
            RenderThaumcraftGolem.logger.error("Couldn't render entity", (Throwable)exception);
        }
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179098_w();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        GlStateManager.func_179089_o();
        GlStateManager.func_179121_F();
        if (!this.field_188301_f) {
            this.func_177067_a((EntityLivingBase)entity, x, y, z);
        }
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post((EntityLivingBase)entity, (RenderLivingBase)this, x, y, z));
        this.func_110827_b((EntityLiving)entity, x, y, z, p_76986_8_, partialTicks);
    }
    
    private void createPartsCache(final EntityThaumcraftGolem golem) {
        final HashMap<PartModel.EnumAttachPoint, ArrayList<PartModel>> pl = new HashMap<PartModel.EnumAttachPoint, ArrayList<PartModel>>();
        pl.put(PartModel.EnumAttachPoint.BODY, new ArrayList<PartModel>());
        pl.put(PartModel.EnumAttachPoint.HEAD, new ArrayList<PartModel>());
        pl.put(PartModel.EnumAttachPoint.ARMS, new ArrayList<PartModel>());
        pl.put(PartModel.EnumAttachPoint.LEGS, new ArrayList<PartModel>());
        final IGolemProperties props = golem.getProperties();
        if (props.getHead().model != null) {
            pl.get(props.getHead().model.getAttachPoint()).add(props.getHead().model);
        }
        if (props.getArms().model != null) {
            pl.get(props.getArms().model.getAttachPoint()).add(props.getArms().model);
        }
        if (props.getLegs().model != null) {
            pl.get(props.getLegs().model.getAttachPoint()).add(props.getLegs().model);
        }
        if (props.getAddon().model != null) {
            pl.get(props.getAddon().model.getAttachPoint()).add(props.getAddon().model);
        }
        this.partsCache.put(golem.func_145782_y(), pl);
    }
    
    public void func_76986_a(final EntityLiving entity, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityThaumcraftGolem)entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation func_110775_a(final EntityLiving p_110775_1_) {
        return null;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
