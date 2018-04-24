package thaumcraft.proxies;

import net.minecraft.client.*;
import net.minecraftforge.fml.client.registry.*;
import thaumcraft.common.entities.*;
import thaumcraft.client.renderers.entity.*;
import net.minecraft.client.model.*;
import thaumcraft.common.entities.monster.tainted.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.entities.monster.cult.*;
import thaumcraft.common.entities.monster.boss.*;
import thaumcraft.client.renderers.models.entity.*;
import thaumcraft.client.renderers.entity.mob.*;
import thaumcraft.api.items.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.client.renderers.entity.projectile.*;
import thaumcraft.common.entities.construct.*;
import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.client.renderers.entity.construct.*;

public class ProxyEntities
{
    public void setupEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySpecialItem.class, (Render)new RenderSpecialItem(Minecraft.func_71410_x().func_175598_ae(), Minecraft.func_71410_x().func_175599_af()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFollowingItem.class, (Render)new RenderEntityItem(Minecraft.func_71410_x().func_175598_ae(), Minecraft.func_71410_x().func_175599_af()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFallingTaint.class, (Render)new RenderFallingTaint(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBrainyZombie.class, (Render)new RenderBrainyZombie(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityInhabitedZombie.class, (Render)new RenderInhabitedZombie(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGiantBrainyZombie.class, (Render)new RenderBrainyZombie(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFireBat.class, (Render)new RenderFireBat(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySpellBat.class, (Render)new RenderSpellBat(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityPech.class, (Render)new RenderPech(Minecraft.func_71410_x().func_175598_ae(), new ModelPech(), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityWisp.class, (Render)new RenderWisp(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityThaumicSlime.class, (Render)new RenderThaumicSlime(Minecraft.func_71410_x().func_175598_ae(), (ModelBase)new ModelSlime(16), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintCrawler.class, (Render)new RenderTaintCrawler(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintSeed.class, (Render)new RenderTaintSeed(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintSeedPrime.class, (Render)new RenderTaintSeed(Minecraft.func_71410_x().func_175598_ae(), new ModelTaintSeed(), 0.6f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintacle.class, (Render)new RenderTaintacle(Minecraft.func_71410_x().func_175598_ae(), 0.6f, 10));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintacleSmall.class, (Render)new RenderTaintacle(Minecraft.func_71410_x().func_175598_ae(), 0.2f, 6));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintSwarm.class, (Render)new RenderTaintSwarm(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistKnight.class, (Render)new RenderCultist(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistLeader.class, (Render)new RenderCultistLeader(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistCleric.class, (Render)new RenderCultist(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchGuardian.class, (Render)new RenderEldritchGuardian(Minecraft.func_71410_x().func_175598_ae(), new ModelEldritchGuardian(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityMindSpider.class, (Render)new RenderMindSpider(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchCrab.class, (Render)new RenderEldritchCrab(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintacleGiant.class, (Render)new RenderTaintacle(Minecraft.func_71410_x().func_175598_ae(), 1.0f, 14));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistPortalGreater.class, (Render)new RenderCultistPortalGreater(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistPortalLesser.class, (Render)new RenderCultistPortalLesser(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchWarden.class, (Render)new RenderEldritchGuardian(Minecraft.func_71410_x().func_175598_ae(), new ModelEldritchGuardian(), 0.6f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchGolem.class, (Render)new RenderEldritchGolem(Minecraft.func_71410_x().func_175598_ae(), new ModelEldritchGolem(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFocusProjectile.class, (Render)new RenderNoProjectile(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFocusCloud.class, (Render)new RenderFocusCloud(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFocusMine.class, (Render)new RenderFocusMine(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityAlumentum.class, (Render)new RenderNoProjectile(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGolemOrb.class, (Render)new RenderElectricOrb(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGolemDart.class, (Render)new RenderDart(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBottleTaint.class, (Render)new RenderSnowball(Minecraft.func_71410_x().func_175598_ae(), ItemsTC.bottleTaint, Minecraft.func_71410_x().func_175599_af()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchOrb.class, (Render)new RenderEldritchOrb(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGrapple.class, (Render)new RenderGrapple(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTurretCrossbowAdvanced.class, (Render)new RenderTurretCrossbowAdvanced(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTurretCrossbow.class, (Render)new RenderTurretCrossbow(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityArcaneBore.class, (Render)new RenderArcaneBore(Minecraft.func_71410_x().func_175598_ae()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityThaumcraftGolem.class, (Render)new RenderThaumcraftGolem(Minecraft.func_71410_x().func_175598_ae()));
    }
}
