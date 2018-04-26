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
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySpecialItem.class, (Render)new RenderSpecialItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFollowingItem.class, (Render)new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFallingTaint.class, (Render)new RenderFallingTaint(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBrainyZombie.class, (Render)new RenderBrainyZombie(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityInhabitedZombie.class, (Render)new RenderInhabitedZombie(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGiantBrainyZombie.class, (Render)new RenderBrainyZombie(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFireBat.class, (Render)new RenderFireBat(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntitySpellBat.class, (Render)new RenderSpellBat(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityPech.class, (Render)new RenderPech(Minecraft.getMinecraft().getRenderManager(), new ModelPech(), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityWisp.class, (Render)new RenderWisp(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityThaumicSlime.class, (Render)new RenderThaumicSlime(Minecraft.getMinecraft().getRenderManager(), (ModelBase)new ModelSlime(16), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintCrawler.class, (Render)new RenderTaintCrawler(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintSeed.class, (Render)new RenderTaintSeed(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintSeedPrime.class, (Render)new RenderTaintSeed(Minecraft.getMinecraft().getRenderManager(), new ModelTaintSeed(), 0.6f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintacle.class, (Render)new RenderTaintacle(Minecraft.getMinecraft().getRenderManager(), 0.6f, 10));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintacleSmall.class, (Render)new RenderTaintacle(Minecraft.getMinecraft().getRenderManager(), 0.2f, 6));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintSwarm.class, (Render)new RenderTaintSwarm(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistKnight.class, (Render)new RenderCultist(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistLeader.class, (Render)new RenderCultistLeader(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistCleric.class, (Render)new RenderCultist(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchGuardian.class, (Render)new RenderEldritchGuardian(Minecraft.getMinecraft().getRenderManager(), new ModelEldritchGuardian(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityMindSpider.class, (Render)new RenderMindSpider(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchCrab.class, (Render)new RenderEldritchCrab(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTaintacleGiant.class, (Render)new RenderTaintacle(Minecraft.getMinecraft().getRenderManager(), 1.0f, 14));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistPortalGreater.class, (Render)new RenderCultistPortalGreater(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCultistPortalLesser.class, (Render)new RenderCultistPortalLesser(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchWarden.class, (Render)new RenderEldritchGuardian(Minecraft.getMinecraft().getRenderManager(), new ModelEldritchGuardian(), 0.6f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchGolem.class, (Render)new RenderEldritchGolem(Minecraft.getMinecraft().getRenderManager(), new ModelEldritchGolem(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFocusProjectile.class, (Render)new RenderNoProjectile(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFocusCloud.class, (Render)new RenderFocusCloud(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFocusMine.class, (Render)new RenderFocusMine(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityAlumentum.class, (Render)new RenderNoProjectile(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGolemOrb.class, (Render)new RenderElectricOrb(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGolemDart.class, (Render)new RenderDart(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBottleTaint.class, (Render)new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), ItemsTC.bottleTaint, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityEldritchOrb.class, (Render)new RenderEldritchOrb(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityGrapple.class, (Render)new RenderGrapple(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTurretCrossbowAdvanced.class, (Render)new RenderTurretCrossbowAdvanced(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityTurretCrossbow.class, (Render)new RenderTurretCrossbow(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityArcaneBore.class, (Render)new RenderArcaneBore(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityThaumcraftGolem.class, (Render)new RenderThaumcraftGolem(Minecraft.getMinecraft().getRenderManager()));
    }
}
