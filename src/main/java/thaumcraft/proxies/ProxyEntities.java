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
        RenderingRegistry.registerEntityRenderingHandler(EntitySpecialItem.class, new RenderSpecialItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFollowingItem.class, new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingTaint.class, new RenderFallingTaint(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityBrainyZombie.class, new RenderBrainyZombie(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityInhabitedZombie.class, new RenderInhabitedZombie(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGiantBrainyZombie.class, new RenderBrainyZombie(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFireBat.class, new RenderFireBat(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntitySpellBat.class, new RenderSpellBat(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityPech.class, new RenderPech(Minecraft.getMinecraft().getRenderManager(), new ModelPech(), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityWisp.class, new RenderWisp(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityThaumicSlime.class, new RenderThaumicSlime(Minecraft.getMinecraft().getRenderManager(), new ModelSlime(16), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityTaintCrawler.class, new RenderTaintCrawler(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTaintSeed.class, new RenderTaintSeed(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTaintSeedPrime.class, new RenderTaintSeed(Minecraft.getMinecraft().getRenderManager(), new ModelTaintSeed(), 0.6f));
        RenderingRegistry.registerEntityRenderingHandler(EntityTaintacle.class, new RenderTaintacle(Minecraft.getMinecraft().getRenderManager(), 0.6f, 10));
        RenderingRegistry.registerEntityRenderingHandler(EntityTaintacleSmall.class, new RenderTaintacle(Minecraft.getMinecraft().getRenderManager(), 0.2f, 6));
        RenderingRegistry.registerEntityRenderingHandler(EntityTaintSwarm.class, new RenderTaintSwarm(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCultistKnight.class, new RenderCultist(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCultistLeader.class, new RenderCultistLeader(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCultistCleric.class, new RenderCultist(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEldritchGuardian.class, new RenderEldritchGuardian(Minecraft.getMinecraft().getRenderManager(), new ModelEldritchGuardian(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(EntityMindSpider.class, new RenderMindSpider(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEldritchCrab.class, new RenderEldritchCrab(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTaintacleGiant.class, new RenderTaintacle(Minecraft.getMinecraft().getRenderManager(), 1.0f, 14));
        RenderingRegistry.registerEntityRenderingHandler(EntityCultistPortalGreater.class, new RenderCultistPortalGreater(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCultistPortalLesser.class, new RenderCultistPortalLesser(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEldritchWarden.class, new RenderEldritchGuardian(Minecraft.getMinecraft().getRenderManager(), new ModelEldritchGuardian(), 0.6f));
        RenderingRegistry.registerEntityRenderingHandler(EntityEldritchGolem.class, new RenderEldritchGolem(Minecraft.getMinecraft().getRenderManager(), new ModelEldritchGolem(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(EntityFocusProjectile.class, new RenderNoProjectile(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFocusCloud.class, new RenderFocusCloud(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFocusMine.class, new RenderFocusMine(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityAlumentum.class, new RenderNoProjectile(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGolemOrb.class, new RenderElectricOrb(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGolemDart.class, new RenderDart(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityBottleTaint.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), ItemsTC.bottleTaint, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEldritchOrb.class, new RenderEldritchOrb(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGrapple.class, new RenderGrapple(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTurretCrossbowAdvanced.class, new RenderTurretCrossbowAdvanced(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTurretCrossbow.class, new RenderTurretCrossbow(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityArcaneBore.class, new RenderArcaneBore(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityThaumcraftGolem.class, new RenderThaumcraftGolem(Minecraft.getMinecraft().getRenderManager()));
    }
}
