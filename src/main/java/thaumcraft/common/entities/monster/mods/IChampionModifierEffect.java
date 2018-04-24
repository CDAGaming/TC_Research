package thaumcraft.common.entities.monster.mods;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;

public interface IChampionModifierEffect
{
    float performEffect(final EntityLivingBase p0, final EntityLivingBase p1, final DamageSource p2, final float p3);
    
    @SideOnly(Side.CLIENT)
    void showFX(final EntityLivingBase p0);
    
    void preRender(final EntityLivingBase p0, final RenderLivingBase p1);
}
