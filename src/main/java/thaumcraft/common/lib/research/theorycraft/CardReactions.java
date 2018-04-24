package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.aspects.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import thaumcraft.api.*;

public class CardReactions extends TheorycraftCard
{
    Aspect aspect1;
    Aspect aspect2;
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74778_a("aspect1", this.aspect1.getTag());
        nbt.func_74778_a("aspect2", this.aspect2.getTag());
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.aspect1 = Aspect.getAspect(nbt.func_74779_i("aspect1"));
        this.aspect2 = Aspect.getAspect(nbt.func_74779_i("aspect2"));
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final Random r = new Random(this.getSeed());
        final int num = MathHelper.func_76136_a(r, 0, Aspect.getCompoundAspects().size() - 1);
        this.aspect1 = Aspect.getCompoundAspects().get(num);
        int num2;
        for (num2 = num; num2 == num; num2 = MathHelper.func_76136_a(r, 0, Aspect.getCompoundAspects().size() - 1)) {}
        this.aspect2 = Aspect.getCompoundAspects().get(num2);
        return true;
    }
    
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getResearchCategory() {
        return "ALCHEMY";
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.reactions.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.reactions.text", new Object[] { TextFormatting.BOLD + this.aspect1.getName() + TextFormatting.RESET, TextFormatting.BOLD + this.aspect2.getName() + TextFormatting.RESET }).func_150254_d();
    }
    
    @Override
    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { ThaumcraftApiHelper.makeCrystal(this.aspect1), ThaumcraftApiHelper.makeCrystal(this.aspect2) };
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal(this.getResearchCategory(), 25);
        if (player.func_70681_au().nextFloat() < 0.33) {
            data.addInspiration(1);
        }
        return true;
    }
}
