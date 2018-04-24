package thaumcraft.api.research.theorycraft;

import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import java.util.*;
import thaumcraft.api.research.*;
import thaumcraft.api.capabilities.*;

public class ResearchTableData
{
    public TileEntity table;
    public String player;
    public int inspiration;
    public int inspirationStart;
    public int bonusDraws;
    public int placedCards;
    public int aidsChosen;
    public int penaltyStart;
    public ArrayList<Long> savedCards;
    public ArrayList<String> aidCards;
    public TreeMap<String, Integer> categoryTotals;
    public ArrayList<String> categoriesBlocked;
    public ArrayList<CardChoice> cardChoices;
    public CardChoice lastDraw;
    
    public ResearchTableData(final TileEntity tileResearchTable) {
        this.savedCards = new ArrayList<Long>();
        this.aidCards = new ArrayList<String>();
        this.categoryTotals = new TreeMap<String, Integer>();
        this.categoriesBlocked = new ArrayList<String>();
        this.cardChoices = new ArrayList<CardChoice>();
        this.table = tileResearchTable;
    }
    
    public ResearchTableData(final EntityPlayer player2, final TileEntity tileResearchTable) {
        this.savedCards = new ArrayList<Long>();
        this.aidCards = new ArrayList<String>();
        this.categoryTotals = new TreeMap<String, Integer>();
        this.categoriesBlocked = new ArrayList<String>();
        this.cardChoices = new ArrayList<CardChoice>();
        this.player = player2.func_70005_c_();
        this.table = tileResearchTable;
    }
    
    public boolean isComplete() {
        return this.inspiration <= 0;
    }
    
    public boolean hasTotal(final String cat) {
        return this.categoryTotals.containsKey(cat);
    }
    
    public int getTotal(final String cat) {
        return this.categoryTotals.containsKey(cat) ? this.categoryTotals.get(cat) : 0;
    }
    
    public void addTotal(final String cat, final int amt) {
        int current = this.categoryTotals.containsKey(cat) ? this.categoryTotals.get(cat) : 0;
        current += amt;
        if (current <= 0) {
            this.categoryTotals.remove(cat);
        }
        else {
            this.categoryTotals.put(cat, current);
        }
    }
    
    public void addInspiration(final int amt) {
        this.inspiration += amt;
        if (this.inspiration > this.inspirationStart) {
            this.inspirationStart = this.inspiration;
        }
    }
    
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.func_74778_a("player", this.player);
        nbt.func_74768_a("inspiration", this.inspiration);
        nbt.func_74768_a("inspirationStart", this.inspirationStart);
        nbt.func_74768_a("placedCards", this.placedCards);
        nbt.func_74768_a("bonusDraws", this.bonusDraws);
        nbt.func_74768_a("aidsChosen", this.aidsChosen);
        nbt.func_74768_a("penaltyStart", this.penaltyStart);
        final NBTTagList savedTag = new NBTTagList();
        for (final Long card : this.savedCards) {
            final NBTTagCompound gt = new NBTTagCompound();
            gt.func_74772_a("card", (long)card);
            savedTag.func_74742_a((NBTBase)gt);
        }
        nbt.func_74782_a("savedCards", (NBTBase)savedTag);
        final NBTTagList categoriesBlockedTag = new NBTTagList();
        for (final String category : this.categoriesBlocked) {
            final NBTTagCompound gt2 = new NBTTagCompound();
            gt2.func_74778_a("category", category);
            categoriesBlockedTag.func_74742_a((NBTBase)gt2);
        }
        nbt.func_74782_a("categoriesBlocked", (NBTBase)categoriesBlockedTag);
        final NBTTagList categoryTotalsTag = new NBTTagList();
        for (final String category2 : this.categoryTotals.keySet()) {
            final NBTTagCompound gt3 = new NBTTagCompound();
            gt3.func_74778_a("category", category2);
            gt3.func_74768_a("total", (int)this.categoryTotals.get(category2));
            categoryTotalsTag.func_74742_a((NBTBase)gt3);
        }
        nbt.func_74782_a("categoryTotals", (NBTBase)categoryTotalsTag);
        final NBTTagList aidCardsTag = new NBTTagList();
        for (final String mc : this.aidCards) {
            final NBTTagCompound gt4 = new NBTTagCompound();
            gt4.func_74778_a("aidCard", mc);
            aidCardsTag.func_74742_a((NBTBase)gt4);
        }
        nbt.func_74782_a("aidCards", (NBTBase)aidCardsTag);
        final NBTTagList cardChoicesTag = new NBTTagList();
        for (final CardChoice mc2 : this.cardChoices) {
            final NBTTagCompound gt5 = this.serializeCardChoice(mc2);
            cardChoicesTag.func_74742_a((NBTBase)gt5);
        }
        nbt.func_74782_a("cardChoices", (NBTBase)cardChoicesTag);
        if (this.lastDraw != null) {
            nbt.func_74782_a("lastDraw", (NBTBase)this.serializeCardChoice(this.lastDraw));
        }
        return nbt;
    }
    
    public NBTTagCompound serializeCardChoice(final CardChoice mc) {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.func_74778_a("cardChoice", mc.key);
        nbt.func_74757_a("aid", mc.fromAid);
        nbt.func_74757_a("select", mc.selected);
        try {
            nbt.func_74782_a("cardNBT", (NBTBase)mc.card.serialize());
        }
        catch (Exception ex) {}
        return nbt;
    }
    
    public void deserialize(final NBTTagCompound nbt) {
        if (nbt == null) {
            return;
        }
        this.inspiration = nbt.func_74762_e("inspiration");
        this.inspirationStart = nbt.func_74762_e("inspirationStart");
        this.placedCards = nbt.func_74762_e("placedCards");
        this.bonusDraws = nbt.func_74762_e("bonusDraws");
        this.aidsChosen = nbt.func_74762_e("aidsChosen");
        this.penaltyStart = nbt.func_74762_e("penaltyStart");
        this.player = nbt.func_74779_i("player");
        final NBTTagList savedTag = nbt.func_150295_c("savedCards", 10);
        this.savedCards = new ArrayList<Long>();
        for (int x = 0; x < savedTag.func_74745_c(); ++x) {
            final NBTTagCompound nbtdata = savedTag.func_150305_b(x);
            this.savedCards.add(nbtdata.func_74763_f("card"));
        }
        final NBTTagList categoriesBlockedTag = nbt.func_150295_c("categoriesBlocked", 10);
        this.categoriesBlocked = new ArrayList<String>();
        for (int x2 = 0; x2 < categoriesBlockedTag.func_74745_c(); ++x2) {
            final NBTTagCompound nbtdata2 = categoriesBlockedTag.func_150305_b(x2);
            this.categoriesBlocked.add(nbtdata2.func_74779_i("category"));
        }
        final NBTTagList categoryTotalsTag = nbt.func_150295_c("categoryTotals", 10);
        this.categoryTotals = new TreeMap<String, Integer>();
        for (int x3 = 0; x3 < categoryTotalsTag.func_74745_c(); ++x3) {
            final NBTTagCompound nbtdata3 = categoryTotalsTag.func_150305_b(x3);
            this.categoryTotals.put(nbtdata3.func_74779_i("category"), nbtdata3.func_74762_e("total"));
        }
        final NBTTagList aidCardsTag = nbt.func_150295_c("aidCards", 10);
        this.aidCards = new ArrayList<String>();
        for (int x4 = 0; x4 < aidCardsTag.func_74745_c(); ++x4) {
            final NBTTagCompound nbtdata4 = aidCardsTag.func_150305_b(x4);
            this.aidCards.add(nbtdata4.func_74779_i("aidCard"));
        }
        EntityPlayer pe = null;
        if (this.table != null && this.table.func_145831_w() != null && !this.table.func_145831_w().field_72995_K) {
            pe = this.table.func_145831_w().func_72924_a(this.player);
        }
        final NBTTagList cardChoicesTag = nbt.func_150295_c("cardChoices", 10);
        this.cardChoices = new ArrayList<CardChoice>();
        for (int x5 = 0; x5 < cardChoicesTag.func_74745_c(); ++x5) {
            final NBTTagCompound nbtdata5 = cardChoicesTag.func_150305_b(x5);
            final CardChoice cc = this.deserializeCardChoice(nbtdata5);
            if (cc != null) {
                this.cardChoices.add(cc);
            }
        }
        this.lastDraw = this.deserializeCardChoice(nbt.func_74775_l("lastDraw"));
    }
    
    public CardChoice deserializeCardChoice(final NBTTagCompound nbt) {
        if (nbt == null) {
            return null;
        }
        final String key = nbt.func_74779_i("cardChoice");
        final TheorycraftCard tc = this.generateCardWithNBT(nbt.func_74779_i("cardChoice"), nbt.func_74775_l("cardNBT"));
        if (tc == null) {
            return null;
        }
        return new CardChoice(key, tc, nbt.func_74767_n("aid"), nbt.func_74767_n("select"));
    }
    
    private boolean isCategoryBlocked(final String cat) {
        return this.categoriesBlocked.contains(cat);
    }
    
    public void drawCards(int draw, final EntityPlayer pe) {
        if (draw == 3) {
            if (this.bonusDraws > 0) {
                --this.bonusDraws;
            }
            else {
                draw = 2;
            }
        }
        this.cardChoices.clear();
        this.player = pe.func_70005_c_();
        final ArrayList<String> availCats = this.getAvailableCategories(pe);
        final ArrayList<String> drawnCards = new ArrayList<String>();
        final boolean aidDrawn = false;
        int failsafe = 0;
        while (draw > 0 && failsafe < 10000) {
            ++failsafe;
            if (!aidDrawn && !this.aidCards.isEmpty() && pe.func_70681_au().nextFloat() <= 0.25) {
                final int idx = pe.func_70681_au().nextInt(this.aidCards.size());
                final String key = this.aidCards.get(idx);
                final TheorycraftCard card = this.generateCard(key, -1L, pe);
                if (card == null || card.getInspirationCost() > this.inspiration) {
                    continue;
                }
                if (this.isCategoryBlocked(card.getResearchCategory())) {
                    continue;
                }
                if (drawnCards.contains(key)) {
                    continue;
                }
                drawnCards.add(key);
                this.cardChoices.add(new CardChoice(key, card, true, false));
                this.aidCards.remove(idx);
            }
            else {
                try {
                    final String[] cards = TheorycraftManager.cards.keySet().toArray(new String[0]);
                    final int idx2 = pe.func_70681_au().nextInt(cards.length);
                    final TheorycraftCard card = this.generateCard(cards[idx2], -1L, pe);
                    if (card == null || card.isAidOnly() || card.getInspirationCost() > this.inspiration) {
                        continue;
                    }
                    if (card.getResearchCategory() != null) {
                        boolean found = false;
                        for (final String cn : availCats) {
                            if (cn.equals(card.getResearchCategory())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            continue;
                        }
                    }
                    if (drawnCards.contains(cards[idx2])) {
                        continue;
                    }
                    drawnCards.add(cards[idx2]);
                    this.cardChoices.add(new CardChoice(cards[idx2], card, false, false));
                }
                catch (Exception e) {
                    continue;
                }
            }
            --draw;
        }
    }
    
    private TheorycraftCard generateCard(final String key, final long seed, final EntityPlayer pe) {
        if (key == null) {
            return null;
        }
        final Class<TheorycraftCard> tcc = TheorycraftManager.cards.get(key);
        if (tcc == null) {
            return null;
        }
        TheorycraftCard tc = null;
        try {
            tc = tcc.newInstance();
            if (seed < 0L) {
                if (pe != null) {
                    tc.setSeed(pe.func_70681_au().nextLong());
                }
                else {
                    tc.setSeed(System.nanoTime());
                }
            }
            else {
                tc.setSeed(seed);
            }
            if (pe != null && !tc.initialize(pe, this)) {
                return null;
            }
        }
        catch (Exception ex) {}
        return tc;
    }
    
    private TheorycraftCard generateCardWithNBT(final String key, final NBTTagCompound nbt) {
        if (key == null) {
            return null;
        }
        final Class<TheorycraftCard> tcc = TheorycraftManager.cards.get(key);
        if (tcc == null) {
            return null;
        }
        TheorycraftCard tc = null;
        try {
            tc = tcc.newInstance();
            tc.deserialize(nbt);
        }
        catch (Exception ex) {}
        return tc;
    }
    
    public void initialize(final EntityPlayer player1, final Set<String> aids) {
        this.inspirationStart = getAvailableInspiration(player1);
        this.inspiration = this.inspirationStart - aids.size();
        for (final String muk : aids) {
            final ITheorycraftAid mu = TheorycraftManager.aids.get(muk);
            if (mu != null) {
                for (final Class clazz : mu.getCards()) {
                    this.aidCards.add(clazz.getName());
                }
            }
        }
    }
    
    public ArrayList<String> getAvailableCategories(final EntityPlayer player) {
        final ArrayList<String> cats = new ArrayList<String>();
        for (final String rck : ResearchCategories.researchCategories.keySet()) {
            final ResearchCategory rc = ResearchCategories.getResearchCategory(rck);
            if (rc != null) {
                if (this.isCategoryBlocked(rck)) {
                    continue;
                }
                if (rc.researchKey != null && !ThaumcraftCapabilities.knowsResearchStrict(player, rc.researchKey)) {
                    continue;
                }
                cats.add(rck);
            }
        }
        return cats;
    }
    
    public static int getAvailableInspiration(final EntityPlayer player) {
        float tot = 5.0f;
        final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
        for (final String s : knowledge.getResearchList()) {
            if (ThaumcraftCapabilities.knowsResearchStrict(player, s)) {
                final ResearchEntry re = ResearchCategories.getResearch(s);
                if (re == null) {
                    continue;
                }
                if (re.hasMeta(ResearchEntry.EnumResearchMeta.SPIKY)) {
                    tot += 0.5f;
                }
                if (!re.hasMeta(ResearchEntry.EnumResearchMeta.HIDDEN)) {
                    continue;
                }
                tot += 0.1f;
            }
        }
        return Math.min(15, Math.round(tot));
    }
    
    public class CardChoice
    {
        public TheorycraftCard card;
        public String key;
        public boolean fromAid;
        public boolean selected;
        
        public CardChoice(final String key, final TheorycraftCard card, final boolean aid, final boolean selected) {
            this.key = key;
            this.card = card;
            this.fromAid = aid;
            this.selected = selected;
        }
        
        @Override
        public String toString() {
            return "key:" + this.key + " card:" + this.card.getSeed() + " fromAid:" + this.fromAid + " selected:" + this.selected;
        }
    }
}
