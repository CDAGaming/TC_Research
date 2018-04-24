package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.aspects.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import java.util.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import thaumcraft.common.items.consumables.*;

public class CardChannel extends TheorycraftCard
{
    Aspect aspect;
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74778_a("aspect", this.aspect.getTag());
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.aspect = Aspect.getAspect(nbt.func_74779_i("aspect"));
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final Random r = new Random(this.getSeed());
        final int num = r.nextInt(Aspect.getCompoundAspects().size());
        this.aspect = Aspect.getCompoundAspects().get(num);
        return true;
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
        return new TextComponentTranslation("card.channel.name", new Object[] { TextFormatting.DARK_BLUE + this.aspect.getName() + TextFormatting.RESET + "" + TextFormatting.BOLD }).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.channel.text", new Object[] { TextFormatting.BOLD + this.aspect.getName() + TextFormatting.RESET }).func_150254_d();
    }
    
    @Override
    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { ItemPhial.makeFilledPhial(this.aspect) };
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal(this.getResearchCategory(), 25);
        return true;
    }
}
