package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import thaumcraft.common.items.curios.*;
import java.util.*;
import thaumcraft.api.research.*;
import net.minecraft.util.math.*;

public class CardCurio extends TheorycraftCard
{
    ItemStack curio;
    
    public CardCurio() {
        this.curio = ItemStack.field_190927_a;
    }
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74782_a("stack", (NBTBase)this.curio.serializeNBT());
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.curio = new ItemStack(nbt.func_74775_l("stack"));
    }
    
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.curio.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.curio.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { this.curio };
    }
    
    @Override
    public boolean[] getRequiredItemsConsumed() {
        return new boolean[] { true };
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final Random r = new Random(this.getSeed());
        final ArrayList<ItemStack> curios = new ArrayList<ItemStack>();
        for (final ItemStack stack : player.field_71071_by.field_70462_a) {
            if (stack != null && !stack.func_190926_b() && stack.func_77973_b() instanceof ItemCurio) {
                final ItemStack c = stack.func_77946_l();
                c.func_190920_e(1);
                curios.add(c);
            }
        }
        if (!curios.isEmpty()) {
            this.curio = curios.get(r.nextInt(curios.size()));
        }
        return !this.curio.func_190926_b();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal("BASICS", 5);
        final String[] s = ResearchCategories.researchCategories.keySet().toArray(new String[0]);
        data.addTotal(s[player.func_70681_au().nextInt(s.length)], 5);
        final String s2;
        final String type = s2 = ((ItemCurio)this.getRequiredItems()[0].func_77973_b()).getVariantNames()[this.getRequiredItems()[0].func_77952_i()];
        switch (s2) {
            case "arcane": {
                data.addTotal("AUROMANCY", MathHelper.func_76136_a(player.func_70681_au(), 25, 35));
                break;
            }
            case "preserved": {
                data.addTotal("ALCHEMY", MathHelper.func_76136_a(player.func_70681_au(), 25, 35));
                break;
            }
            case "ancient": {
                data.addTotal("GOLEMANCY", MathHelper.func_76136_a(player.func_70681_au(), 25, 35));
                break;
            }
            case "eldritch": {
                data.addTotal("ELDRITCH", MathHelper.func_76136_a(player.func_70681_au(), 25, 35));
                break;
            }
            case "knowledge": {
                data.addTotal("INFUSION", MathHelper.func_76136_a(player.func_70681_au(), 25, 35));
                break;
            }
            case "twisted": {
                data.addTotal("ARTIFICE", MathHelper.func_76136_a(player.func_70681_au(), 25, 35));
                break;
            }
            case "rites": {
                data.addTotal("ELDRITCH", MathHelper.func_76136_a(player.func_70681_au(), 15, 20));
                data.addTotal("AUROMANCY", MathHelper.func_76136_a(player.func_70681_au(), 10, 15));
                break;
            }
            default: {
                data.addTotal("BASICS", MathHelper.func_76136_a(player.func_70681_au(), 25, 35));
                break;
            }
        }
        if (player.func_70681_au().nextBoolean()) {
            ++data.bonusDraws;
        }
        if (player.func_70681_au().nextBoolean()) {
            ++data.bonusDraws;
        }
        return true;
    }
}
