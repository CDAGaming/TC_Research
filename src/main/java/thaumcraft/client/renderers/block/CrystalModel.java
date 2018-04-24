package thaumcraft.client.renderers.block;

import net.minecraft.client.renderer.texture.*;
import thaumcraft.client.lib.obj.*;
import java.io.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import thaumcraft.common.blocks.world.ore.*;
import net.minecraftforge.common.property.*;
import java.util.*;
import thaumcraft.codechicken.lib.vec.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;

public class CrystalModel implements IBakedModel
{
    ResourceLocation model;
    MeshModel sourceMesh;
    TextureAtlasSprite tex;
    
    public CrystalModel(final TextureAtlasSprite tex2) {
        this.model = new ResourceLocation("thaumcraft", "models/obj/crystal.obj");
        this.tex = tex2;
        try {
            this.sourceMesh = new MeshLoader().loadFromResource(this.model);
            for (final MeshPart mp : this.sourceMesh.parts) {
                mp.tintIndex = 0;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<BakedQuad> func_188616_a(final IBlockState state, final EnumFacing side, final long rand) {
        if (side == null && state instanceof IExtendedBlockState) {
            final List<BakedQuad> ret = new ArrayList<BakedQuad>();
            final IExtendedBlockState es = (IExtendedBlockState)state;
            final int m = ((BlockCrystal)state.func_177230_c()).getGrowth(state) + 1;
            final MeshModel mm = this.sourceMesh.clone();
            try {
                if (es != null) {
                    if (!(boolean)es.getValue((IUnlistedProperty)BlockCrystal.UP) || !(boolean)es.getValue((IUnlistedProperty)BlockCrystal.DOWN) || !(boolean)es.getValue((IUnlistedProperty)BlockCrystal.EAST) || !(boolean)es.getValue((IUnlistedProperty)BlockCrystal.WEST) || !(boolean)es.getValue((IUnlistedProperty)BlockCrystal.NORTH) || !(boolean)es.getValue((IUnlistedProperty)BlockCrystal.SOUTH)) {
                        if (es.getValue((IUnlistedProperty)BlockCrystal.UP)) {
                            final List<Integer> c = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                            Collections.shuffle(c, new Random(rand));
                            mm.parts.clear();
                            for (int a = 0; a < m; ++a) {
                                mm.parts.add(this.sourceMesh.parts.get(c.get(a)));
                            }
                            final MeshModel mod = mm.clone();
                            mod.rotate(Math.toRadians(180.0), new Vector3(1.0, 0.0, 0.0), new Vector3(0.0, 1.0, 1.0));
                            for (final BakedQuad quad : mod.bakeModel(this.func_177554_e())) {
                                ret.add(quad);
                            }
                        }
                        if (es.getValue((IUnlistedProperty)BlockCrystal.DOWN)) {
                            final List<Integer> c = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                            Collections.shuffle(c, new Random(rand + 5L));
                            mm.parts.clear();
                            for (int a = 0; a < m; ++a) {
                                mm.parts.add(this.sourceMesh.parts.get(c.get(a)));
                            }
                            for (final BakedQuad quad2 : mm.bakeModel(this.func_177554_e())) {
                                ret.add(quad2);
                            }
                        }
                        if (es.getValue((IUnlistedProperty)BlockCrystal.EAST)) {
                            final List<Integer> c = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                            Collections.shuffle(c, new Random(rand + 10L));
                            mm.parts.clear();
                            for (int a = 0; a < m; ++a) {
                                mm.parts.add(this.sourceMesh.parts.get(c.get(a)));
                            }
                            final MeshModel mod = mm.clone();
                            mod.rotate(Math.toRadians(90.0), new Vector3(1.0, 0.0, 0.0), new Vector3(0.0, 0.0, 0.0));
                            mod.rotate(Math.toRadians(270.0), new Vector3(0.0, 1.0, 0.0), new Vector3(1.0, 1.0, 0.0));
                            for (final BakedQuad quad : mod.bakeModel(this.func_177554_e())) {
                                ret.add(quad);
                            }
                        }
                        if (es.getValue((IUnlistedProperty)BlockCrystal.WEST)) {
                            final List<Integer> c = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                            Collections.shuffle(c, new Random(rand + 15L));
                            mm.parts.clear();
                            for (int a = 0; a < m; ++a) {
                                mm.parts.add(this.sourceMesh.parts.get(c.get(a)));
                            }
                            final MeshModel mod = mm.clone();
                            mod.rotate(Math.toRadians(90.0), new Vector3(1.0, 0.0, 0.0), new Vector3(0.0, 0.0, 0.0));
                            mod.rotate(Math.toRadians(90.0), new Vector3(0.0, 1.0, 0.0), new Vector3(0.0, 1.0, 1.0));
                            for (final BakedQuad quad : mod.bakeModel(this.func_177554_e())) {
                                ret.add(quad);
                            }
                        }
                        if (es.getValue((IUnlistedProperty)BlockCrystal.NORTH)) {
                            final List<Integer> c = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                            Collections.shuffle(c, new Random(rand + 20L));
                            mm.parts.clear();
                            for (int a = 0; a < m; ++a) {
                                mm.parts.add(this.sourceMesh.parts.get(c.get(a)));
                            }
                            final MeshModel mod = mm.clone();
                            mod.rotate(Math.toRadians(90.0), new Vector3(1.0, 0.0, 0.0), new Vector3(0.0, 1.0, 0.0));
                            for (final BakedQuad quad : mod.bakeModel(this.func_177554_e())) {
                                ret.add(quad);
                            }
                        }
                        if (es.getValue((IUnlistedProperty)BlockCrystal.SOUTH)) {
                            final List<Integer> c = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                            Collections.shuffle(c, new Random(rand + 25L));
                            mm.parts.clear();
                            for (int a = 0; a < m; ++a) {
                                mm.parts.add(this.sourceMesh.parts.get(c.get(a)));
                            }
                            final MeshModel mod = mm.clone();
                            mod.rotate(Math.toRadians(90.0), new Vector3(1.0, 0.0, 0.0), new Vector3(0.0, 0.0, 0.0));
                            mod.rotate(Math.toRadians(180.0), new Vector3(0.0, 1.0, 0.0), new Vector3(1.0, 1.0, 1.0));
                            for (final BakedQuad quad : mod.bakeModel(this.func_177554_e())) {
                                ret.add(quad);
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {}
            return ret;
        }
        return (List<BakedQuad>)ImmutableList.of();
    }
    
    public boolean func_177555_b() {
        return true;
    }
    
    public boolean func_177556_c() {
        return false;
    }
    
    public boolean func_188618_c() {
        return false;
    }
    
    public TextureAtlasSprite func_177554_e() {
        return this.tex;
    }
    
    public ItemCameraTransforms func_177552_f() {
        return ItemCameraTransforms.field_178357_a;
    }
    
    public ItemOverrideList func_188617_f() {
        return ItemOverrideList.field_188022_a;
    }
}
