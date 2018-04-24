package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.aspects.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import java.util.*;
import net.minecraft.util.text.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.common.items.consumables.*;
import thaumcraft.api.items.*;
import net.minecraft.block.*;
import thaumcraft.api.blocks.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class CardInfuse extends TheorycraftCard
{
    Aspect aspect;
    ItemStack stack;
    static ItemStack[] options;
    
    public CardInfuse() {
        this.stack = ItemStack.field_190927_a;
    }
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74778_a("aspect", this.aspect.getTag());
        nbt.func_74782_a("stack", (NBTBase)this.stack.serializeNBT());
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.aspect = Aspect.getAspect(nbt.func_74779_i("aspect"));
        this.stack = new ItemStack(nbt.func_74775_l("stack"));
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final Random r = new Random(this.getSeed());
        final int num = r.nextInt(Aspect.getCompoundAspects().size());
        this.aspect = Aspect.getCompoundAspects().get(num);
        this.stack = CardInfuse.options[r.nextInt(CardInfuse.options.length)].func_77946_l();
        return this.aspect != null && this.stack != null;
    }
    
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getResearchCategory() {
        return "INFUSION";
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.infuse.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.infuse.text", new Object[] { TextFormatting.BOLD + this.aspect.getName() + TextFormatting.RESET, this.stack.func_82833_r(), this.getVal() }).func_150254_d();
    }
    
    private int getVal() {
        int q = 10;
        try {
            q += (int)(Math.sqrt(ThaumcraftCraftingManager.getObjectTags(this.stack).visSize()) * 1.5);
        }
        catch (Exception ex) {}
        return q;
    }
    
    @Override
    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { this.stack, ItemPhial.makeFilledPhial(this.aspect) };
    }
    
    @Override
    public boolean[] getRequiredItemsConsumed() {
        return new boolean[] { true, true };
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal(this.getResearchCategory(), this.getVal());
        return true;
    }
    
    static {
        CardInfuse.options = new ItemStack[] { new ItemStack(ItemsTC.alumentum), new ItemStack((Block)BlocksTC.nitor.get(EnumDyeColor.YELLOW)), new ItemStack(ItemsTC.amber), new ItemStack(ItemsTC.brain), new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.salisMundus), new ItemStack(ItemsTC.ingots, 1, 0), new ItemStack(ItemsTC.ingots, 1, 2), new ItemStack(ItemsTC.quicksilver), new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151042_j), new ItemStack(Items.field_151045_i), new ItemStack(Items.field_151166_bC), new ItemStack(Items.field_151072_bj), new ItemStack(Items.field_151116_aA), new ItemStack(Blocks.field_150325_L), new ItemStack(Items.field_151118_aC), new ItemStack(Items.field_151032_g), new ItemStack(Items.field_151110_aK), new ItemStack(Items.field_151008_G), new ItemStack(Items.field_151114_aO), new ItemStack(Items.field_151137_ax), new ItemStack(Items.field_151073_bk), new ItemStack(Items.field_151016_H), new ItemStack((Item)Items.field_151031_f), new ItemStack(Items.field_151010_B), new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151035_b), new ItemStack(Items.field_151005_D), new ItemStack(Items.field_151128_bU), new ItemStack(Items.field_151034_e) };
    }
}
