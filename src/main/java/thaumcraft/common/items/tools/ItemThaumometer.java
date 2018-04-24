package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.research.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.math.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.common.lib.research.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.world.aura.*;

public class ItemThaumometer extends ItemTCBase
{
    public ItemThaumometer() {
        super("thaumometer", new String[0]);
        this.func_77625_d(1);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer p, final EnumHand hand) {
        if (world.field_72995_K) {
            this.drawFX(world, p);
            p.field_70170_p.func_184134_a(p.field_70165_t, p.field_70163_u, p.field_70161_v, SoundsTC.scan, SoundCategory.PLAYERS, 0.5f, 1.0f, false);
        }
        else {
            this.doScan(world, p);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)p.func_184586_b(hand));
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (isSelected && !world.field_72995_K && entity.field_70173_aa % 20 == 0 && entity instanceof EntityPlayerMP) {
            this.updateAura(stack, world, (EntityPlayerMP)entity);
        }
        if (isSelected && world.field_72995_K && entity.field_70173_aa % 3 == 0 && entity instanceof EntityPlayer) {
            final Entity target = EntityUtils.getPointedEntity(world, entity, 1.0, 16.0, 5.0f, true);
            if (target != null && ScanningManager.isThingStillScannable((EntityPlayer)entity, target)) {
                FXDispatcher.INSTANCE.scanHighlight(target);
            }
            final RayTraceResult mop = this.getRayTraceResultFromPlayerWild(world, (EntityPlayer)entity, true);
            if (mop != null && mop.func_178782_a() != null && ScanningManager.isThingStillScannable((EntityPlayer)entity, mop.func_178782_a())) {
                FXDispatcher.INSTANCE.scanHighlight(mop.func_178782_a());
            }
        }
    }
    
    protected RayTraceResult getRayTraceResultFromPlayerWild(final World worldIn, final EntityPlayer playerIn, final boolean useLiquids) {
        final float f = playerIn.field_70127_C + (playerIn.field_70125_A - playerIn.field_70127_C) + worldIn.field_73012_v.nextInt(33) - worldIn.field_73012_v.nextInt(33);
        final float f2 = playerIn.field_70126_B + (playerIn.field_70177_z - playerIn.field_70126_B) + worldIn.field_73012_v.nextInt(33) - worldIn.field_73012_v.nextInt(33);
        final double d0 = playerIn.field_70169_q + (playerIn.field_70165_t - playerIn.field_70169_q);
        final double d2 = playerIn.field_70167_r + (playerIn.field_70163_u - playerIn.field_70167_r) + playerIn.func_70047_e();
        final double d3 = playerIn.field_70166_s + (playerIn.field_70161_v - playerIn.field_70166_s);
        final Vec3d vec3 = new Vec3d(d0, d2, d3);
        final float f3 = MathHelper.func_76134_b(-f2 * 0.017453292f - 3.1415927f);
        final float f4 = MathHelper.func_76126_a(-f2 * 0.017453292f - 3.1415927f);
        final float f5 = -MathHelper.func_76134_b(-f * 0.017453292f);
        final float f6 = MathHelper.func_76126_a(-f * 0.017453292f);
        final float f7 = f4 * f5;
        final float f8 = f3 * f5;
        final double d4 = 16.0;
        final Vec3d vec4 = vec3.func_72441_c(f7 * d4, f6 * d4, f8 * d4);
        return worldIn.func_147447_a(vec3, vec4, useLiquids, !useLiquids, false);
    }
    
    private void updateAura(final ItemStack stack, final World world, final EntityPlayerMP player) {
        final AuraChunk ac = AuraHandler.getAuraChunk(world.field_73011_w.getDimension(), (int)player.field_70165_t >> 4, (int)player.field_70161_v >> 4);
        if (ac != null) {
            if ((ac.getFlux() > ac.getVis() || ac.getFlux() > ac.getBase() / 3) && !ThaumcraftCapabilities.knowsResearch((EntityPlayer)player, "FLUX")) {
                ResearchManager.startResearchWithPopup((EntityPlayer)player, "FLUX");
                player.func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("research.FLUX.warn")), true);
            }
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketAuraToClient(ac), player);
        }
    }
    
    private void drawFX(final World worldIn, final EntityPlayer playerIn) {
        final Entity target = EntityUtils.getPointedEntity(worldIn, (Entity)playerIn, 1.0, 9.0, 0.0f, true);
        if (target != null) {
            for (int a = 0; a < 10; ++a) {
                FXDispatcher.INSTANCE.blockRunes(target.field_70165_t - 0.5, target.field_70163_u + target.func_70047_e() / 2.0f, target.field_70161_v - 0.5, 0.3f + worldIn.field_73012_v.nextFloat() * 0.7f, 0.0f, 0.3f + worldIn.field_73012_v.nextFloat() * 0.7f, (int)(target.field_70131_O * 15.0f), 0.03f);
            }
        }
        else {
            final RayTraceResult mop = this.func_77621_a(worldIn, playerIn, true);
            if (mop != null && mop.func_178782_a() != null) {
                for (int a2 = 0; a2 < 10; ++a2) {
                    FXDispatcher.INSTANCE.blockRunes(mop.func_178782_a().func_177958_n(), mop.func_178782_a().func_177956_o() + 0.25, mop.func_178782_a().func_177952_p(), 0.3f + worldIn.field_73012_v.nextFloat() * 0.7f, 0.0f, 0.3f + worldIn.field_73012_v.nextFloat() * 0.7f, 15, 0.03f);
                }
            }
        }
    }
    
    public void doScan(final World worldIn, final EntityPlayer playerIn) {
        if (!worldIn.field_72995_K) {
            final Entity target = EntityUtils.getPointedEntity(worldIn, (Entity)playerIn, 1.0, 9.0, 0.0f, true);
            if (target != null) {
                ScanningManager.scanTheThing(playerIn, target);
            }
            else {
                final RayTraceResult mop = this.func_77621_a(worldIn, playerIn, true);
                if (mop != null && mop.func_178782_a() != null) {
                    ScanningManager.scanTheThing(playerIn, mop.func_178782_a());
                }
                else {
                    ScanningManager.scanTheThing(playerIn, null);
                }
            }
        }
    }
}
