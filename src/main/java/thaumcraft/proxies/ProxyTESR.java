package thaumcraft.proxies;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.common.tiles.essentia.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.tiles.misc.*;
import thaumcraft.client.renderers.tile.*;
import net.minecraftforge.fml.client.registry.*;

public class ProxyTESR
{
    public void setupTESR() {
        this.registerTESR(TileCrucible.class, new TileCrucibleRenderer());
        this.registerTESR(TileDioptra.class, new TileDioptraRenderer());
        this.registerTESR(TilePedestal.class, new TilePedestalRenderer());
        this.registerTESR(TileRechargePedestal.class, new TileRechargePedestalRenderer());
        this.registerTESR(TileFocalManipulator.class, new TileFocalManipulatorRenderer());
        this.registerTESR(TileHungryChest.class, new TileHungryChestRenderer());
        this.registerTESR(TileTubeOneway.class, new TileTubeOnewayRenderer());
        this.registerTESR(TileTubeValve.class, new TileTubeValveRenderer());
        this.registerTESR(TileTubeBuffer.class, new TileTubeBufferRenderer());
        this.registerTESR(TileJar.class, new TileJarRenderer());
        this.registerTESR(TileBellows.class, new TileBellowsRenderer());
        this.registerTESR(TileAlembic.class, new TileAlembicRenderer());
        this.registerTESR(TileInfusionMatrix.class, new TileInfusionMatrixRenderer());
        this.registerTESR(TileResearchTable.class, new TileResearchTableRenderer());
        this.registerTESR(TileThaumatorium.class, new TileThaumatoriumRenderer());
        this.registerTESR(TileCentrifuge.class, new TileCentrifugeRenderer());
        final TileMirrorRenderer tmr = new TileMirrorRenderer();
        this.registerTESR(TileMirror.class, tmr);
        this.registerTESR(TileMirrorEssentia.class, tmr);
        this.registerTESR(TileGolemBuilder.class, new TileGolemBuilderRenderer());
        this.registerTESR(TilePatternCrafter.class, new TilePatternCrafterRenderer());
        this.registerTESR(TileBanner.class, new TileBannerRenderer());
        this.registerTESR(TileHole.class, new TileHoleRenderer());
    }
    
    private void registerTESR(final Class tile, final TileEntitySpecialRenderer renderer) {
        ClientRegistry.bindTileEntitySpecialRenderer(tile, renderer);
    }
}
