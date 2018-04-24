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

public class CardSynthesis extends TheorycraftCard
{
    Aspect aspect1;
    Aspect aspect2;
    Aspect aspect3;
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74778_a("aspect1", this.aspect1.getTag());
        nbt.func_74778_a("aspect2", this.aspect2.getTag());
        nbt.func_74778_a("aspect3", this.aspect3.getTag());
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.aspect1 = Aspect.getAspect(nbt.func_74779_i("aspect1"));
        this.aspect2 = Aspect.getAspect(nbt.func_74779_i("aspect2"));
        this.aspect3 = Aspect.getAspect(nbt.func_74779_i("aspect3"));
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final Random r = new Random(this.getSeed());
        final int num = MathHelper.func_76136_a(r, 0, Aspect.getCompoundAspects().size() - 1);
        this.aspect3 = Aspect.getCompoundAspects().get(num);
        this.aspect1 = this.aspect3.getComponents()[0];
        this.aspect2 = this.aspect3.getComponents()[1];
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
        return new TextComponentTranslation("card.synthesis.name", new Object[0]).func_150260_c();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.synthesis.text", new Object[] { TextFormatting.BOLD + this.aspect1.getName() + TextFormatting.RESET, TextFormatting.BOLD + this.aspect2.getName() + TextFormatting.RESET }).func_150260_c();
    }
    
    @Override
    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { ThaumcraftApiHelper.makeCrystal(this.aspect1), ThaumcraftApiHelper.makeCrystal(this.aspect2) };
    }
    
    @Override
    public boolean[] getRequiredItemsConsumed() {
        return new boolean[] { true, true };
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        final ItemStack res = ThaumcraftApiHelper.makeCrystal(this.aspect3);
        data.addTotal(this.getResearchCategory(), 40);
        if (player.func_70681_au().nextFloat() < 0.33) {
            data.addInspiration(1);
        }
        if (!player.field_71071_by.func_70441_a(res)) {
            player.func_71019_a(res, true);
        }
        return true;
    }
}
