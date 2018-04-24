package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import java.util.*;
import thaumcraft.common.lib.crafting.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;
import thaumcraft.api.items.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class CardTinker extends TheorycraftCard
{
    ItemStack stack;
    static ItemStack[] options;
    
    public CardTinker() {
        this.stack = ItemStack.field_190927_a;
    }
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74782_a("stack", (NBTBase)this.stack.serializeNBT());
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.stack = new ItemStack(nbt.func_74775_l("stack"));
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final Random r = new Random(this.getSeed());
        this.stack = CardTinker.options[r.nextInt(CardTinker.options.length)].func_77946_l();
        return this.stack != null;
    }
    
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getResearchCategory() {
        return "ARTIFICE";
    }
    
    private int getVal() {
        int q = 0;
        try {
            q += (int)Math.sqrt(ThaumcraftCraftingManager.getObjectTags(this.stack).visSize());
        }
        catch (Exception ex) {}
        return q;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.tinker.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        final int a = this.getVal() * 2;
        final int b = a + 10;
        return new TextComponentTranslation("card.tinker.text", new Object[] { a, b }).func_150254_d();
    }
    
    @Override
    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { this.stack };
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        final int q = this.getVal() * 2;
        data.addTotal(this.getResearchCategory(), MathHelper.func_76136_a(player.func_70681_au(), q, q + 10));
        return true;
    }
    
    static {
        CardTinker.options = new ItemStack[] { new ItemStack(ItemsTC.visResonator), new ItemStack(ItemsTC.thaumometer), new ItemStack(Blocks.field_150467_bQ), new ItemStack(Blocks.field_150408_cc), new ItemStack(Blocks.field_150367_z), new ItemStack(Blocks.field_150409_cd), new ItemStack(Blocks.field_150381_bn), new ItemStack(Blocks.field_150477_bB), new ItemStack(Blocks.field_150421_aI), new ItemStack((Block)Blocks.field_150453_bW), new ItemStack((Block)Blocks.field_150331_J), new ItemStack((Block)Blocks.field_150438_bZ), new ItemStack((Block)Blocks.field_150320_F), new ItemStack((Item)Items.field_151148_bJ), new ItemStack(Items.field_151111_aL), new ItemStack(Items.field_151142_bV), new ItemStack(Items.field_151132_bS), new ItemStack(Items.field_151113_aN) };
    }
}
