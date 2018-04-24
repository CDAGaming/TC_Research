package thaumcraft.common.lib.research;

import java.util.concurrent.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.config.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.api.research.*;
import net.minecraft.util.text.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.internal.*;
import net.minecraft.util.*;
import thaumcraft.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class ResearchManager
{
    public static ConcurrentHashMap<String, Boolean> syncList;
    public static boolean noFlags;
    public static LinkedHashSet<Integer> craftingReferences;
    
    public static boolean addKnowledge(final EntityPlayer player, final IPlayerKnowledge.EnumKnowledgeType type, ResearchCategory category, final int amount) {
        final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
        if (!type.hasFields()) {
            category = null;
        }
        final int kp = knowledge.getKnowledge(type, category);
        knowledge.addKnowledge(type, category, amount);
        final int kr = knowledge.getKnowledge(type, category) - kp;
        if (amount > 0) {
            for (int a = 0; a < kr; ++a) {
                PacketHandler.INSTANCE.sendTo((IMessage)new PacketKnowledgeGain((byte)type.ordinal(), (category == null) ? null : category.key), (EntityPlayerMP)player);
            }
        }
        ResearchManager.syncList.put(player.func_70005_c_(), true);
        return true;
    }
    
    public static boolean completeResearch(final EntityPlayer player, final String researchkey, final boolean sync) {
        boolean b = false;
        while (progressResearch(player, researchkey, sync)) {
            b = true;
        }
        return b;
    }
    
    public static boolean completeResearch(final EntityPlayer player, final String researchkey) {
        boolean b = false;
        while (progressResearch(player, researchkey, true)) {
            b = true;
        }
        return b;
    }
    
    public static boolean startResearchWithPopup(final EntityPlayer player, final String researchkey) {
        final boolean b = progressResearch(player, researchkey, true);
        if (b) {
            final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
            knowledge.setResearchFlag(researchkey, IPlayerKnowledge.EnumResearchFlag.POPUP);
            knowledge.setResearchFlag(researchkey, IPlayerKnowledge.EnumResearchFlag.RESEARCH);
        }
        return b;
    }
    
    public static boolean progressResearch(final EntityPlayer player, final String researchkey) {
        return progressResearch(player, researchkey, true);
    }
    
    public static boolean progressResearch(final EntityPlayer player, final String researchkey, final boolean sync) {
        final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
        if (!knowledge.isResearchComplete(researchkey) && doesPlayerHaveRequisites(player, researchkey)) {
            if (!knowledge.isResearchKnown(researchkey)) {
                knowledge.addResearch(researchkey);
            }
            final ResearchEntry re = ResearchCategories.getResearch(researchkey);
            if (re != null) {
                boolean popups = true;
                if (re.getStages() != null) {
                    int cs = knowledge.getResearchStage(researchkey);
                    ResearchStage currentStage = null;
                    if (cs > 0) {
                        currentStage = re.getStages()[cs - 1];
                    }
                    if (re.getStages().length == 1 && cs == 0 && re.getStages()[0].getCraft() == null && re.getStages()[0].getObtain() == null && re.getStages()[0].getKnow() == null && re.getStages()[0].getResearch() == null) {
                        ++cs;
                    }
                    else if (re.getStages().length > 1 && re.getStages().length <= cs + 1 && cs < re.getStages().length && re.getStages()[cs].getCraft() == null && re.getStages()[cs].getObtain() == null && re.getStages()[cs].getKnow() == null && re.getStages()[cs].getResearch() == null) {
                        ++cs;
                    }
                    knowledge.setResearchStage(researchkey, Math.min(re.getStages().length + 1, cs + 1));
                    popups = (cs >= re.getStages().length);
                    if (currentStage != null) {
                        final int warp = currentStage.getWarp();
                        if (warp > 0 && !ModConfig.CONFIG_MISC.wussMode && !player.field_70170_p.field_72995_K) {
                            if (warp > 1) {
                                final IPlayerWarp pw = ThaumcraftCapabilities.getWarp(player);
                                final int w2 = warp / 2;
                                if (warp - w2 > 0) {
                                    ThaumcraftApi.internalMethods.addWarpToPlayer(player, warp - w2, IPlayerWarp.EnumWarpType.PERMANENT);
                                }
                                if (w2 > 0) {
                                    ThaumcraftApi.internalMethods.addWarpToPlayer(player, w2, IPlayerWarp.EnumWarpType.NORMAL);
                                }
                            }
                            else {
                                ThaumcraftApi.internalMethods.addWarpToPlayer(player, warp, IPlayerWarp.EnumWarpType.PERMANENT);
                            }
                        }
                    }
                }
                if (sync && popups) {
                    knowledge.setResearchFlag(researchkey, IPlayerKnowledge.EnumResearchFlag.POPUP);
                    if (!ResearchManager.noFlags) {
                        knowledge.setResearchFlag(researchkey, IPlayerKnowledge.EnumResearchFlag.RESEARCH);
                    }
                    else {
                        ResearchManager.noFlags = false;
                    }
                    if (re.getRewardItem() != null) {
                        for (final ItemStack rs : re.getRewardItem()) {
                            if (!player.field_71071_by.func_70441_a(rs.func_77946_l())) {
                                player.func_70099_a(rs.func_77946_l(), 1.0f);
                            }
                        }
                    }
                    if (re.getRewardKnow() != null) {
                        for (final ResearchStage.Knowledge rk : re.getRewardKnow()) {
                            addKnowledge(player, rk.type, rk.category, rk.type.getProgression() * rk.amount);
                        }
                    }
                }
            }
            if (re != null && re.getSiblings() != null) {
                for (final String sibling : re.getSiblings()) {
                    if (!knowledge.isResearchComplete(sibling) && doesPlayerHaveRequisites(player, sibling)) {
                        completeResearch(player, sibling, sync);
                    }
                }
            }
            for (final String rc : ResearchCategories.researchCategories.keySet()) {
                for (final ResearchEntry ri : ResearchCategories.getResearchCategory(rc).research.values()) {
                    if (ri != null && ri.getAddenda() != null) {
                        if (!knowledge.isResearchComplete(ri.getKey())) {
                            continue;
                        }
                        for (final ResearchAddendum addendum : ri.getAddenda()) {
                            if (addendum.getResearch() != null && Arrays.asList(addendum.getResearch()).contains(researchkey)) {
                                final ITextComponent text = (ITextComponent)new TextComponentTranslation("tc.addaddendum", new Object[] { ri.getLocalizedName() });
                                player.func_146105_b(text, true);
                                knowledge.setResearchFlag(ri.getKey(), IPlayerKnowledge.EnumResearchFlag.PAGE);
                                break;
                            }
                        }
                    }
                }
            }
            if (sync) {
                ResearchManager.syncList.put(player.func_70005_c_(), true);
                if (re != null) {
                    player.func_71023_q(5);
                }
            }
            return true;
        }
        return false;
    }
    
    public static boolean doesPlayerHaveRequisites(final EntityPlayer player, final String key) {
        final ResearchEntry ri = ResearchCategories.getResearch(key);
        if (ri == null) {
            return true;
        }
        final String[] parents = ri.getParentsStripped();
        return parents == null || ThaumcraftCapabilities.knowsResearchStrict(player, parents);
    }
    
    public static Aspect getCombinationResult(final Aspect aspect1, final Aspect aspect2) {
        final Collection<Aspect> aspects = Aspect.aspects.values();
        for (final Aspect aspect3 : aspects) {
            if (aspect3.getComponents() != null && ((aspect3.getComponents()[0] == aspect1 && aspect3.getComponents()[1] == aspect2) || (aspect3.getComponents()[0] == aspect2 && aspect3.getComponents()[1] == aspect1))) {
                return aspect3;
            }
        }
        return null;
    }
    
    public static void parseAllResearch() {
        final JsonParser parser = new JsonParser();
        for (final ResourceLocation loc : CommonInternals.jsonLocs.values()) {
            String s = "/assets/" + loc.func_110624_b() + "/" + loc.func_110623_a();
            if (!s.endsWith(".json")) {
                s += ".json";
            }
            final InputStream stream = ResearchManager.class.getResourceAsStream(s);
            if (stream != null) {
                try {
                    final InputStreamReader reader = new InputStreamReader(stream);
                    final JsonObject obj = parser.parse((Reader)reader).getAsJsonObject();
                    final JsonArray entries = obj.get("entries").getAsJsonArray();
                    int a = 0;
                    for (final JsonElement element : entries) {
                        ++a;
                        try {
                            final JsonObject entry = element.getAsJsonObject();
                            final ResearchEntry researchEntry = parseResearchJson(entry);
                            addResearchToCategory(researchEntry);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Thaumcraft.log.warn("Invalid research entry [" + a + "] found in " + loc.toString());
                            --a;
                        }
                    }
                    Thaumcraft.log.info("Loaded " + a + " research entries from " + loc.toString());
                }
                catch (Exception e2) {
                    Thaumcraft.log.warn("Invalid research file: " + loc.toString());
                }
            }
            else {
                Thaumcraft.log.warn("Research file not found: " + loc.toString());
            }
        }
    }
    
    private static ResearchEntry parseResearchJson(final JsonObject obj) throws Exception {
        final ResearchEntry entry = new ResearchEntry();
        entry.setKey(obj.getAsJsonPrimitive("key").getAsString());
        if (entry.getKey() == null) {
            throw new Exception("Invalid key in research JSon");
        }
        entry.setName(obj.getAsJsonPrimitive("name").getAsString());
        entry.setCategory(obj.getAsJsonPrimitive("category").getAsString());
        if (entry.getCategory() == null) {
            throw new Exception("Invalid category in research JSon");
        }
        if (obj.has("icons")) {
            final String[] icons = arrayJsonToString(obj.get("icons").getAsJsonArray());
            if (icons != null && icons.length > 0) {
                final Object[] ir = new Object[icons.length];
                for (int a = 0; a < icons.length; ++a) {
                    final ItemStack stack = parseJSONtoItemStack(icons[a]);
                    if (stack != null && !stack.func_190926_b()) {
                        ir[a] = stack;
                    }
                    else if (icons[a].startsWith("focus")) {
                        ir[a] = icons[a];
                    }
                    else {
                        ir[a] = new ResourceLocation(icons[a]);
                    }
                }
                entry.setIcons(ir);
            }
        }
        if (obj.has("parents")) {
            entry.setParents(arrayJsonToString(obj.get("parents").getAsJsonArray()));
        }
        if (obj.has("siblings")) {
            entry.setSiblings(arrayJsonToString(obj.get("siblings").getAsJsonArray()));
        }
        if (obj.has("meta")) {
            final String[] meta = arrayJsonToString(obj.get("meta").getAsJsonArray());
            if (meta != null && meta.length > 0) {
                final ArrayList<ResearchEntry.EnumResearchMeta> metas = new ArrayList<ResearchEntry.EnumResearchMeta>();
                for (final String s : meta) {
                    final ResearchEntry.EnumResearchMeta en = ResearchEntry.EnumResearchMeta.valueOf(s.toUpperCase());
                    if (en == null) {
                        throw new Exception("Illegal metadata in research JSon");
                    }
                    metas.add(en);
                }
                entry.setMeta(metas.toArray(new ResearchEntry.EnumResearchMeta[metas.size()]));
            }
        }
        if (obj.has("location")) {
            final Integer[] location = arrayJsonToInt(obj.get("location").getAsJsonArray());
            if (location != null && location.length == 2) {
                entry.setDisplayColumn(location[0]);
                entry.setDisplayRow(location[1]);
            }
        }
        if (obj.has("reward_item")) {
            entry.setRewardItem(parseJsonItemList(entry.getKey(), arrayJsonToString(obj.get("reward_item").getAsJsonArray())));
        }
        if (obj.has("reward_knowledge")) {
            final String[] sl = arrayJsonToString(obj.get("reward_knowledge").getAsJsonArray());
            if (sl != null && sl.length > 0) {
                final ArrayList<ResearchStage.Knowledge> kl = new ArrayList<ResearchStage.Knowledge>();
                for (final String s : sl) {
                    final ResearchStage.Knowledge k = ResearchStage.Knowledge.parse(s);
                    if (k != null) {
                        kl.add(k);
                    }
                }
                if (kl.size() > 0) {
                    entry.setRewardKnow(kl.toArray(new ResearchStage.Knowledge[kl.size()]));
                }
            }
        }
        final JsonArray stagesJson = obj.get("stages").getAsJsonArray();
        final ArrayList<ResearchStage> stages = new ArrayList<ResearchStage>();
        for (final JsonElement element : stagesJson) {
            final JsonObject stageObj = element.getAsJsonObject();
            final ResearchStage stage = new ResearchStage();
            stage.setText(stageObj.getAsJsonPrimitive("text").getAsString());
            if (stage.getText() == null) {
                throw new Exception("Illegal stage text in research JSon");
            }
            if (stageObj.has("recipes")) {
                stage.setRecipes(arrayJsonToResourceLocations(stageObj.get("recipes").getAsJsonArray()));
            }
            if (stageObj.has("required_item")) {
                stage.setObtain(parseJsonItemList(entry.getKey(), arrayJsonToString(stageObj.get("required_item").getAsJsonArray())));
            }
            if (stageObj.has("required_craft")) {
                final String[] s2 = arrayJsonToString(stageObj.get("required_craft").getAsJsonArray());
                stage.setCraft(parseJsonItemList(entry.getKey(), s2));
                if (stage.getCraft() != null && stage.getCraft().length > 0) {
                    final int[] refs = new int[stage.getCraft().length];
                    int q = 0;
                    for (final ItemStack stack2 : stage.getCraft()) {
                        final int code = createItemStackHash(stack2);
                        ResearchManager.craftingReferences.add(code);
                        refs[q] = code;
                        ++q;
                    }
                    stage.setCraftReference(refs);
                }
            }
            if (stageObj.has("required_knowledge")) {
                final String[] sl2 = arrayJsonToString(stageObj.get("required_knowledge").getAsJsonArray());
                if (sl2 != null && sl2.length > 0) {
                    final ArrayList<ResearchStage.Knowledge> kl2 = new ArrayList<ResearchStage.Knowledge>();
                    for (final String s3 : sl2) {
                        final ResearchStage.Knowledge i = ResearchStage.Knowledge.parse(s3);
                        if (i != null) {
                            kl2.add(i);
                        }
                    }
                    if (kl2.size() > 0) {
                        stage.setKnow(kl2.toArray(new ResearchStage.Knowledge[kl2.size()]));
                    }
                }
            }
            if (stageObj.has("required_research")) {
                stage.setResearch(arrayJsonToString(stageObj.get("required_research").getAsJsonArray()));
            }
            if (stageObj.has("warp")) {
                stage.setWarp(stageObj.getAsJsonPrimitive("warp").getAsInt());
            }
            stages.add(stage);
        }
        if (stages.size() > 0) {
            entry.setStages(stages.toArray(new ResearchStage[stages.size()]));
        }
        if (obj.get("addenda") != null) {
            final JsonArray addendaJson = obj.get("addenda").getAsJsonArray();
            final ArrayList<ResearchAddendum> addenda = new ArrayList<ResearchAddendum>();
            for (final JsonElement element2 : addendaJson) {
                final JsonObject addendumObj = element2.getAsJsonObject();
                final ResearchAddendum addendum = new ResearchAddendum();
                addendum.setText(addendumObj.getAsJsonPrimitive("text").getAsString());
                if (addendum.getText() == null) {
                    throw new Exception("Illegal addendum text in research JSon");
                }
                if (addendumObj.has("recipes")) {
                    addendum.setRecipes(arrayJsonToResourceLocations(addendumObj.get("recipes").getAsJsonArray()));
                }
                if (addendumObj.has("required_research")) {
                    addendum.setResearch(arrayJsonToString(addendumObj.get("required_research").getAsJsonArray()));
                }
                addenda.add(addendum);
            }
            if (addenda.size() > 0) {
                entry.setAddenda(addenda.toArray(new ResearchAddendum[addenda.size()]));
            }
        }
        return entry;
    }
    
    public static int createItemStackHash(final ItemStack stack) {
        if (stack == null || stack.func_190926_b()) {
            return 0;
        }
        stack.func_190920_e(1);
        return stack.serializeNBT().toString().hashCode();
    }
    
    private static String[] arrayJsonToString(final JsonArray jsonArray) {
        final ArrayList<String> out = new ArrayList<String>();
        for (final JsonElement element : jsonArray) {
            out.add(element.getAsString());
        }
        return (String[])((out.size() == 0) ? null : ((String[])out.toArray(new String[out.size()])));
    }
    
    private static ResourceLocation[] arrayJsonToResourceLocations(final JsonArray jsonArray) {
        final ArrayList<ResourceLocation> out = new ArrayList<ResourceLocation>();
        for (final JsonElement element : jsonArray) {
            out.add(new ResourceLocation(element.getAsString()));
        }
        return (ResourceLocation[])((out.size() == 0) ? null : ((ResourceLocation[])out.toArray(new ResourceLocation[out.size()])));
    }
    
    private static Integer[] arrayJsonToInt(final JsonArray jsonArray) {
        final ArrayList<Integer> out = new ArrayList<Integer>();
        for (final JsonElement element : jsonArray) {
            out.add(element.getAsInt());
        }
        return (Integer[])((out.size() == 0) ? null : ((Integer[])out.toArray(new Integer[out.size()])));
    }
    
    private static ItemStack[] parseJsonItemList(final String key, final String[] stacks) {
        if (stacks == null || stacks.length == 0) {
            return null;
        }
        final ItemStack[] work = new ItemStack[stacks.length];
        int idx = 0;
        for (String s : stacks) {
            s = s.replace("'", "\"");
            final ItemStack stack = parseJSONtoItemStack(s);
            if (stack != null && !stack.func_190926_b()) {
                work[idx] = stack;
                ++idx;
            }
        }
        ItemStack[] out = null;
        if (idx > 0) {
            out = Arrays.copyOf(work, idx);
        }
        return out;
    }
    
    public static ItemStack parseJSONtoItemStack(final String entry) {
        if (entry == null) {
            return null;
        }
        final String[] split = entry.split(";");
        final String name = split[0];
        int num = -1;
        int dam = -1;
        String nbt = null;
        for (int a = 1; a < split.length; ++a) {
            if (split[a].startsWith("{")) {
                nbt = split[a];
                nbt.replaceAll("'", "\"");
                break;
            }
            int q = -1;
            try {
                q = Integer.parseInt(split[a]);
            }
            catch (NumberFormatException e) {
                continue;
            }
            if (q >= 0 && num < 0) {
                num = q;
            }
            else if (q >= 0 && dam < 0) {
                dam = q;
            }
        }
        if (num < 0) {
            num = 1;
        }
        if (dam < 0) {
            dam = 0;
        }
        ItemStack stack = ItemStack.field_190927_a;
        try {
            final Item it = Item.func_111206_d(name);
            if (it != null) {
                stack = new ItemStack(it, num, dam);
                if (nbt != null) {
                    stack.func_77982_d(JsonToNBT.func_180713_a(nbt));
                }
            }
        }
        catch (Exception ex) {}
        return stack;
    }
    
    private static void addResearchToCategory(final ResearchEntry ri) {
        final ResearchCategory rl = ResearchCategories.getResearchCategory(ri.getCategory());
        if (rl != null && !rl.research.containsKey(ri.getKey())) {
            for (final ResearchEntry rr : rl.research.values()) {
                if (rr.getDisplayColumn() == ri.getDisplayColumn() && rr.getDisplayRow() == ri.getDisplayRow()) {
                    Thaumcraft.log.warn("Research [" + ri.getKey() + "] not added as it overlaps with existing research [" + rr.getKey() + "] at " + ri.getDisplayColumn() + "," + rr.getDisplayRow());
                    return;
                }
            }
            rl.research.put(ri.getKey(), ri);
            if (ri.getDisplayColumn() < rl.minDisplayColumn) {
                rl.minDisplayColumn = ri.getDisplayColumn();
            }
            if (ri.getDisplayRow() < rl.minDisplayRow) {
                rl.minDisplayRow = ri.getDisplayRow();
            }
            if (ri.getDisplayColumn() > rl.maxDisplayColumn) {
                rl.maxDisplayColumn = ri.getDisplayColumn();
            }
            if (ri.getDisplayRow() > rl.maxDisplayRow) {
                rl.maxDisplayRow = ri.getDisplayRow();
            }
        }
        else {
            Thaumcraft.log.warn("Could not add invalid research entry " + ri.getKey());
        }
    }
    
    static {
        ResearchManager.syncList = new ConcurrentHashMap<String, Boolean>();
        ResearchManager.noFlags = false;
        ResearchManager.craftingReferences = new LinkedHashSet<Integer>();
    }
}
