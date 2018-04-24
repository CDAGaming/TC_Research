package thaumcraft.common.lib.research;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import thaumcraft.api.research.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.item.*;

public class ScanPotion implements IScanThing
{
    Potion potion;
    
    public ScanPotion(final Potion potion) {
        this.potion = potion;
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        return this.getPotionEffect(player, obj) != null;
    }
    
    private PotionEffect getPotionEffect(final EntityPlayer player, final Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof EntityLivingBase) {
            final EntityLivingBase e = (EntityLivingBase)obj;
            for (final PotionEffect potioneffect : e.func_70651_bq()) {
                if (potioneffect.func_188419_a() == this.potion) {
                    return potioneffect;
                }
            }
        }
        else {
            final ItemStack is = ScanningManager.getItemFromParms(player, obj);
            if (is != null && !is.func_190926_b()) {
                for (final PotionEffect potioneffect : PotionUtils.func_185189_a(is)) {
                    if (potioneffect.func_188419_a() == this.potion) {
                        return potioneffect;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object obj) {
        return "!" + this.potion.func_76393_a();
    }
}
