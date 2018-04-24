package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.function.*;
import java.util.stream.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.api.*;
import thaumcraft.api.research.*;
import thaumcraft.api.research.theorycraft.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.block.state.*;
import java.util.*;
import thaumcraft.api.items.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;

public class TileResearchTable extends TileThaumcraftInventory
{
    public ResearchTableData data;
    
    public TileResearchTable() {
        super(2);
        this.data = null;
        this.syncedSlots = new int[] { 0, 1 };
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        super.readSyncNBT(nbttagcompound);
        if (nbttagcompound.func_74764_b("note")) {
            (this.data = new ResearchTableData(this)).deserialize(nbttagcompound.func_74775_l("note"));
        }
        else {
            this.data = null;
        }
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        if (this.data != null) {
            nbttagcompound.func_74782_a("note", (NBTBase)this.data.serialize());
        }
        else {
            nbttagcompound.func_82580_o("note");
        }
        return super.writeSyncNBT(nbttagcompound);
    }
    
    protected void func_190201_b(final World worldIn) {
        super.func_190201_b(worldIn);
        if (!this.func_145830_o()) {
            this.func_145834_a(worldIn);
        }
    }
    
    public void startNewTheory(final EntityPlayer player, final Set<String> mutators) {
        (this.data = new ResearchTableData(player, this)).initialize(player, mutators);
        this.syncTile(false);
        this.func_70296_d();
    }
    
    public void finishTheory(final EntityPlayer player) {
        final Comparator<Map.Entry<String, Integer>> valueComparator = (e1, e2) -> e2.getValue().compareTo(e1.getValue());
        final Map<String, Integer> sortedMap = this.data.categoryTotals.entrySet().stream().sorted((Comparator<? super Object>)valueComparator).collect((Collector<? super Object, ?, Map<String, Integer>>)Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue, (e1, e2) -> e1, (Supplier<R>)LinkedHashMap::new));
        int i = 0;
        for (final String cat : sortedMap.keySet()) {
            int tot = Math.round(sortedMap.get(cat) / 100.0f * IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression());
            if (i > this.data.penaltyStart) {
                tot *= 0;
            }
            final ResearchCategory rc = ResearchCategories.getResearchCategory(cat);
            ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc, tot);
            ++i;
        }
        this.data = null;
    }
    
    public Set<String> checkSurroundingAids() {
        final HashMap<String, ITheorycraftAid> mutators = new HashMap<String, ITheorycraftAid>();
        for (int y = -1; y <= 1; ++y) {
            for (int x = -4; x <= 4; ++x) {
                for (int z = -4; z <= 4; ++z) {
                    for (final String muk : TheorycraftManager.aids.keySet()) {
                        final ITheorycraftAid mu = TheorycraftManager.aids.get(muk);
                        final IBlockState state = this.field_145850_b.func_180495_p(this.func_174877_v().func_177982_a(x, y, z));
                        if (mu.getAidObject() instanceof Block) {
                            if (state.func_177230_c() != mu.getAidObject()) {
                                continue;
                            }
                            mutators.put(muk, mu);
                        }
                        else {
                            if (!(mu.getAidObject() instanceof ItemStack)) {
                                continue;
                            }
                            final ItemStack is = state.func_177230_c().func_185473_a(this.func_145831_w(), this.func_174877_v().func_177982_a(x, y, z), state);
                            if (is == null || is.func_190926_b() || !is.func_185136_b((ItemStack)mu.getAidObject())) {
                                continue;
                            }
                            mutators.put(muk, mu);
                        }
                    }
                }
            }
        }
        final List<Entity> l = EntityUtils.getEntitiesInRange(this.func_145831_w(), this.func_174877_v(), (Entity)null, (Class<? extends Entity>)Entity.class, 5.0);
        if (l != null && !l.isEmpty()) {
            for (final Entity e : l) {
                for (final String muk : TheorycraftManager.aids.keySet()) {
                    final ITheorycraftAid mu = TheorycraftManager.aids.get(muk);
                    if (mu.getAidObject() instanceof Class && e.getClass().isAssignableFrom((Class<?>)mu.getAidObject())) {
                        mutators.put(muk, mu);
                    }
                }
            }
        }
        return mutators.keySet();
    }
    
    public boolean consumeInkFromTable() {
        if (this.func_70301_a(0).func_77973_b() instanceof IScribeTools && this.func_70301_a(0).func_77952_i() < this.func_70301_a(0).func_77958_k()) {
            this.func_70301_a(0).func_77964_b(this.func_70301_a(0).func_77952_i() + 1);
            this.syncTile(false);
            this.func_70296_d();
            return true;
        }
        return false;
    }
    
    public boolean consumepaperFromTable() {
        if (this.func_70301_a(1).func_77973_b() == Items.field_151121_aF && this.func_70301_a(1).func_190916_E() > 0) {
            this.func_70298_a(1, 1);
            this.syncTile(false);
            this.func_70296_d();
            return true;
        }
        return false;
    }
    
    @Override
    public String func_70005_c_() {
        return "Research Table";
    }
    
    @Override
    public boolean func_94041_b(final int i, final ItemStack itemstack) {
        switch (i) {
            case 0: {
                if (itemstack.func_77973_b() instanceof IScribeTools) {
                    return true;
                }
                break;
            }
            case 1: {
                if (itemstack.func_77973_b() == Items.field_151121_aF && itemstack.func_77952_i() == 0) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    @Override
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if (this.field_145850_b != null && this.field_145850_b.field_72995_K) {
            this.syncTile(false);
        }
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 1) {
            if (this.field_145850_b.field_72995_K) {
                this.field_145850_b.func_184134_a((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), SoundsTC.learn, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
}
