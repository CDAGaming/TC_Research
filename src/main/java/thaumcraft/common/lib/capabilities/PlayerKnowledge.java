package thaumcraft.common.lib.capabilities;

import javax.annotation.*;
import net.minecraft.util.math.*;
import thaumcraft.api.research.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.playerdata.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import thaumcraft.api.capabilities.*;

public class PlayerKnowledge
{
    public static void preInit() {
        CapabilityManager.INSTANCE.register((Class)IPlayerKnowledge.class, (Capability.IStorage)new Capability.IStorage<IPlayerKnowledge>() {
            public NBTTagCompound writeNBT(final Capability<IPlayerKnowledge> capability, final IPlayerKnowledge instance, final EnumFacing side) {
                return (NBTTagCompound)instance.serializeNBT();
            }
            
            public void readNBT(final Capability<IPlayerKnowledge> capability, final IPlayerKnowledge instance, final EnumFacing side, final NBTBase nbt) {
                if (nbt instanceof NBTTagCompound) {
                    instance.deserializeNBT((NBTBase)nbt);
                }
            }
        }, () -> new DefaultImpl());
    }
    
    private static class DefaultImpl implements IPlayerKnowledge
    {
        private final HashSet<String> research;
        private final Map<String, Integer> stages;
        private final Map<String, HashSet<EnumResearchFlag>> flags;
        private final Map<String, Integer> knowledge;
        
        private DefaultImpl() {
            this.research = new HashSet<String>();
            this.stages = new HashMap<String, Integer>();
            this.flags = new HashMap<String, HashSet<EnumResearchFlag>>();
            this.knowledge = new HashMap<String, Integer>();
        }
        
        @Override
        public void clear() {
            this.research.clear();
            this.flags.clear();
            this.stages.clear();
            this.knowledge.clear();
        }
        
        @Override
        public EnumResearchStatus getResearchStatus(@Nonnull final String res) {
            if (!this.isResearchKnown(res)) {
                return EnumResearchStatus.UNKNOWN;
            }
            final ResearchEntry entry = ResearchCategories.getResearch(res);
            if (entry == null || entry.getStages() == null || this.getResearchStage(res) > entry.getStages().length) {
                return EnumResearchStatus.COMPLETE;
            }
            return EnumResearchStatus.IN_PROGRESS;
        }
        
        @Override
        public boolean isResearchKnown(final String res) {
            if (res == null) {
                return false;
            }
            if (res.equals("")) {
                return true;
            }
            final String[] ss = res.split("@");
            return (ss.length <= 1 || this.getResearchStage(ss[0]) >= MathHelper.func_82715_a(ss[1], 0)) && this.research.contains(ss[0]);
        }
        
        @Override
        public boolean isResearchComplete(final String res) {
            return this.getResearchStatus(res) == EnumResearchStatus.COMPLETE;
        }
        
        @Override
        public int getResearchStage(final String res) {
            if (res == null || !this.research.contains(res)) {
                return -1;
            }
            final Integer stage = this.stages.get(res);
            return (stage == null) ? 0 : stage;
        }
        
        @Override
        public boolean setResearchStage(final String res, final int stage) {
            if (res == null || !this.research.contains(res) || stage <= 0) {
                return false;
            }
            this.stages.put(res, stage);
            return true;
        }
        
        @Override
        public boolean addResearch(@Nonnull final String res) {
            if (!this.isResearchKnown(res)) {
                this.research.add(res);
                return true;
            }
            return false;
        }
        
        @Override
        public boolean removeResearch(@Nonnull final String res) {
            if (this.isResearchKnown(res)) {
                this.research.remove(res);
                return true;
            }
            return false;
        }
        
        @Nonnull
        @Override
        public Set<String> getResearchList() {
            return Collections.unmodifiableSet((Set<? extends String>)this.research);
        }
        
        @Override
        public boolean setResearchFlag(@Nonnull final String res, @Nonnull final EnumResearchFlag flag) {
            HashSet<EnumResearchFlag> list = this.flags.get(res);
            if (list == null) {
                list = new HashSet<EnumResearchFlag>();
                this.flags.put(res, list);
            }
            if (list.contains(flag)) {
                return false;
            }
            list.add(flag);
            return true;
        }
        
        @Override
        public boolean clearResearchFlag(@Nonnull final String res, @Nonnull final EnumResearchFlag flag) {
            final HashSet<EnumResearchFlag> list = this.flags.get(res);
            if (list != null) {
                final boolean b = list.remove(flag);
                if (list.isEmpty()) {
                    this.flags.remove(this.research);
                }
                return b;
            }
            return false;
        }
        
        @Override
        public boolean hasResearchFlag(@Nonnull final String res, @Nonnull final EnumResearchFlag flag) {
            return this.flags.get(res) != null && this.flags.get(res).contains(flag);
        }
        
        private String getKey(final EnumKnowledgeType type, final ResearchCategory category) {
            return type.getAbbreviation() + "_" + ((category == null) ? "" : category.key);
        }
        
        @Override
        public boolean addKnowledge(final EnumKnowledgeType type, final ResearchCategory category, final int amount) {
            final String key = this.getKey(type, category);
            int c = this.getKnowledgeRaw(type, category);
            if (c + amount < 0) {
                return false;
            }
            c += amount;
            this.knowledge.put(key, c);
            return true;
        }
        
        @Override
        public int getKnowledge(final EnumKnowledgeType type, final ResearchCategory category) {
            final String key = this.getKey(type, category);
            final int c = this.knowledge.containsKey(key) ? this.knowledge.get(key) : 0;
            return (int)Math.floor(c / type.getProgression());
        }
        
        @Override
        public int getKnowledgeRaw(final EnumKnowledgeType type, final ResearchCategory category) {
            final String key = this.getKey(type, category);
            return this.knowledge.containsKey(key) ? this.knowledge.get(key) : 0;
        }
        
        @Override
        public void sync(@Nonnull final EntityPlayerMP player) {
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketSyncKnowledge((EntityPlayer)player), player);
        }
        
        public NBTTagCompound serializeNBT() {
            final NBTTagCompound rootTag = new NBTTagCompound();
            final NBTTagList researchList = new NBTTagList();
            for (final String resKey : this.research) {
                final NBTTagCompound tag = new NBTTagCompound();
                tag.func_74778_a("key", resKey);
                if (this.stages.containsKey(resKey)) {
                    tag.func_74768_a("stage", (int)this.stages.get(resKey));
                }
                if (this.flags.containsKey(resKey)) {
                    final HashSet<EnumResearchFlag> list = this.flags.get(resKey);
                    if (list != null) {
                        String fs = "";
                        for (final EnumResearchFlag flag : list) {
                            if (fs.length() > 0) {
                                fs += ",";
                            }
                            fs += flag.name();
                        }
                        tag.func_74778_a("flags", fs);
                    }
                }
                researchList.func_74742_a((NBTBase)tag);
            }
            rootTag.func_74782_a("research", (NBTBase)researchList);
            final NBTTagList knowledgeList = new NBTTagList();
            for (final String key : this.knowledge.keySet()) {
                final Integer c = this.knowledge.get(key);
                if (c != null && c > 0 && key != null && !key.isEmpty()) {
                    final NBTTagCompound tag2 = new NBTTagCompound();
                    tag2.func_74778_a("key", key);
                    tag2.func_74768_a("amount", (int)c);
                    knowledgeList.func_74742_a((NBTBase)tag2);
                }
            }
            rootTag.func_74782_a("knowledge", (NBTBase)knowledgeList);
            return rootTag;
        }
        
        public void deserializeNBT(final NBTTagCompound rootTag) {
            if (rootTag == null) {
                return;
            }
            this.clear();
            final NBTTagList researchList = rootTag.func_150295_c("research", 10);
            for (int i = 0; i < researchList.func_74745_c(); ++i) {
                final NBTTagCompound tag = researchList.func_150305_b(i);
                final String know = tag.func_74779_i("key");
                if (know != null && !this.isResearchKnown(know)) {
                    this.research.add(know);
                    final int stage = tag.func_74762_e("stage");
                    if (stage > 0) {
                        this.stages.put(know, stage);
                    }
                    final String fs = tag.func_74779_i("flags");
                    if (fs.length() > 0) {
                        final String[] split;
                        final String[] ss = split = fs.split(",");
                        for (final String s : split) {
                            EnumResearchFlag flag = null;
                            try {
                                flag = EnumResearchFlag.valueOf(s);
                            }
                            catch (Exception ex) {}
                            if (flag != null) {
                                this.setResearchFlag(know, flag);
                            }
                        }
                    }
                }
            }
            final NBTTagList knowledgeList = rootTag.func_150295_c("knowledge", 10);
            for (int j = 0; j < knowledgeList.func_74745_c(); ++j) {
                final NBTTagCompound tag2 = knowledgeList.func_150305_b(j);
                final String key = tag2.func_74779_i("key");
                final int amount = tag2.func_74762_e("amount");
                this.knowledge.put(key, amount);
            }
            this.addAutoUnlockResearch();
        }
        
        private void addAutoUnlockResearch() {
            for (final ResearchCategory cat : ResearchCategories.researchCategories.values()) {
                for (final ResearchEntry ri : cat.research.values()) {
                    if (ri.hasMeta(ResearchEntry.EnumResearchMeta.AUTOUNLOCK)) {
                        this.addResearch(ri.getKey());
                    }
                }
            }
        }
    }
    
    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME;
        private final DefaultImpl knowledge;
        
        public Provider() {
            this.knowledge = new DefaultImpl();
        }
        
        public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
            return capability == ThaumcraftCapabilities.KNOWLEDGE;
        }
        
        public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
            if (capability == ThaumcraftCapabilities.KNOWLEDGE) {
                return (T)ThaumcraftCapabilities.KNOWLEDGE.cast((Object)this.knowledge);
            }
            return null;
        }
        
        public NBTTagCompound serializeNBT() {
            return this.knowledge.serializeNBT();
        }
        
        public void deserializeNBT(final NBTTagCompound nbt) {
            this.knowledge.deserializeNBT(nbt);
        }
        
        static {
            NAME = new ResourceLocation("thaumcraft", "knowledge");
        }
    }
}
