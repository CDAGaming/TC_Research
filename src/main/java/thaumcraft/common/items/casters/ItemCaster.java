package thaumcraft.common.items.casters;

import thaumcraft.common.items.*;
import thaumcraft.api.items.*;
import java.text.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.world.aura.*;
import net.minecraft.world.chunk.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.init.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import thaumcraft.codechicken.lib.math.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.casters.*;
import net.minecraft.item.*;

public class ItemCaster extends ItemTCBase implements IArchitect, ICaster
{
    int area;
    DecimalFormat myFormatter;
    ArrayList<BlockPos> checked;
    
    public ItemCaster(final String name, final int area) {
        super(name, new String[0]);
        this.area = 0;
        this.myFormatter = new DecimalFormat("#######.#");
        this.checked = new ArrayList<BlockPos>();
        this.area = area;
        this.field_77777_bU = 1;
        this.func_77656_e(0);
        this.func_185043_a(new ResourceLocation("focus"), (IItemPropertyGetter)new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float func_185085_a(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                final ItemFocus f = ((ItemCaster)stack.func_77973_b()).getFocus(stack);
                if (stack.func_77973_b() instanceof ItemCaster && f != null) {
                    return 1.0f;
                }
                return 0.0f;
            }
        });
    }
    
    public boolean shouldCauseReequipAnimation(final ItemStack oldStack, final ItemStack newStack, final boolean slotChanged) {
        if (oldStack.func_77973_b() != null && oldStack.func_77973_b() == this && newStack.func_77973_b() != null && newStack.func_77973_b() == this) {
            final ItemFocus oldf = ((ItemCaster)oldStack.func_77973_b()).getFocus(oldStack);
            final ItemFocus newf = ((ItemCaster)newStack.func_77973_b()).getFocus(newStack);
            int s1 = 0;
            int s2 = 0;
            if (oldf != null && oldf.getSortingHelper(((ItemCaster)oldStack.func_77973_b()).getFocusStack(oldStack)) != null) {
                s1 = oldf.getSortingHelper(((ItemCaster)oldStack.func_77973_b()).getFocusStack(oldStack)).hashCode();
            }
            if (newf != null && newf.getSortingHelper(((ItemCaster)newStack.func_77973_b()).getFocusStack(newStack)) != null) {
                s2 = newf.getSortingHelper(((ItemCaster)newStack.func_77973_b()).getFocusStack(newStack)).hashCode();
            }
            return s1 != s2;
        }
        return newStack.func_77973_b() != oldStack.func_77973_b();
    }
    
    public boolean func_77645_m() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_77662_d() {
        return true;
    }
    
    private float getAuraPool(final EntityPlayer player) {
        float tot = 0.0f;
        switch (this.area) {
            default: {
                tot = AuraHandler.getVis(player.field_70170_p, player.func_180425_c());
                break;
            }
            case 1: {
                tot = AuraHandler.getVis(player.field_70170_p, player.func_180425_c());
                for (final EnumFacing face : EnumFacing.field_176754_o) {
                    tot += AuraHandler.getVis(player.field_70170_p, player.func_180425_c().func_177967_a(face, 16));
                }
                break;
            }
            case 2: {
                tot = 0.0f;
                for (int xx = -1; xx <= 1; ++xx) {
                    for (int zz = -1; zz <= 1; ++zz) {
                        tot += AuraHandler.getVis(player.field_70170_p, player.func_180425_c().func_177982_a(xx * 16, 0, zz * 16));
                    }
                }
                break;
            }
        }
        return tot;
    }
    
    @Override
    public boolean consumeVis(final ItemStack is, final EntityPlayer player, float amount, final boolean crafting, final boolean sim) {
        amount *= this.getConsumptionModifier(is, player, crafting);
        final float tot = this.getAuraPool(player);
        if (tot < amount) {
            return false;
        }
        if (sim) {
            return true;
        }
        Label_0309: {
            switch (this.area) {
                default: {
                    amount -= AuraHandler.drainVis(player.field_70170_p, player.func_180425_c(), amount, sim);
                    break;
                }
                case 1: {
                    float i = amount / 5.0f;
                    while (amount > 0.0f) {
                        if (i > amount) {
                            i = amount;
                        }
                        amount -= AuraHandler.drainVis(player.field_70170_p, player.func_180425_c(), i, sim);
                        if (amount <= 0.0f) {
                            break;
                        }
                        if (i > amount) {
                            i = amount;
                        }
                        for (final EnumFacing face : EnumFacing.field_176754_o) {
                            amount -= AuraHandler.drainVis(player.field_70170_p, player.func_180425_c().func_177967_a(face, 16), i, sim);
                            if (amount <= 0.0f) {
                                break Label_0309;
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    float i = amount / 9.0f;
                    while (amount > 0.0f) {
                        if (i > amount) {
                            i = amount;
                        }
                        for (int xx = -1; xx <= 1; ++xx) {
                            for (int zz = -1; zz <= 1; ++zz) {
                                amount -= AuraHandler.drainVis(player.field_70170_p, player.func_180425_c().func_177982_a(xx * 16, 0, zz * 16), i, sim);
                                if (amount <= 0.0f) {
                                    break Label_0309;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return amount <= 0.0f;
    }
    
    @Override
    public float getConsumptionModifier(final ItemStack is, final EntityPlayer player, final boolean crafting) {
        float consumptionModifier = 1.0f;
        if (player != null) {
            consumptionModifier -= CasterManager.getTotalVisDiscount(player);
        }
        return Math.max(consumptionModifier, 0.1f);
    }
    
    @Override
    public ItemFocus getFocus(final ItemStack stack) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("focus")) {
            final NBTTagCompound nbt = stack.func_77978_p().func_74775_l("focus");
            final ItemStack fs = new ItemStack(nbt);
            if (fs != null && !fs.func_190926_b()) {
                return (ItemFocus)fs.func_77973_b();
            }
        }
        return null;
    }
    
    @Override
    public ItemStack getFocusStack(final ItemStack stack) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("focus")) {
            final NBTTagCompound nbt = stack.func_77978_p().func_74775_l("focus");
            return new ItemStack(nbt);
        }
        return null;
    }
    
    @Override
    public void setFocus(final ItemStack stack, final ItemStack focus) {
        if (focus == null || focus.func_190926_b()) {
            stack.func_77978_p().func_82580_o("focus");
        }
        else {
            stack.func_77983_a("focus", (NBTBase)focus.func_77955_b(new NBTTagCompound()));
        }
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o()) {
            String text = "";
            final ItemStack focus = this.getFocusStack(stack);
            if (focus != null && !focus.func_190926_b()) {
                final float amt = ((ItemFocus)focus.func_77973_b()).getVisCost(focus);
                if (amt > 0.0f) {
                    text = "§r" + this.myFormatter.format(amt) + " " + I18n.func_74838_a("item.Focus.cost1");
                }
            }
            tooltip.add(TextFormatting.ITALIC + "" + TextFormatting.AQUA + I18n.func_74838_a("tc.vis.cost") + " " + text);
        }
        if (this.getFocus(stack) != null) {
            tooltip.add(TextFormatting.BOLD + "" + TextFormatting.ITALIC + "" + TextFormatting.GREEN + this.getFocus(stack).func_77653_i(this.getFocusStack(stack)));
            this.getFocus(stack).addFocusInformation(this.getFocusStack(stack), worldIn, tooltip, flagIn);
        }
    }
    
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
    }
    
    public void func_77663_a(final ItemStack is, final World w, final Entity e, final int slot, final boolean currentItem) {
        if (!w.field_72995_K && e.field_70173_aa % 10 == 0 && e instanceof EntityPlayerMP) {
            for (final ItemStack h : e.func_184214_aD()) {
                if (h != null && !h.func_190926_b() && h.func_77973_b() instanceof ICaster) {
                    this.updateAura(is, w, (EntityPlayerMP)e);
                    break;
                }
            }
        }
    }
    
    private void updateAura(final ItemStack stack, final World world, final EntityPlayerMP player) {
        float cv = 0.0f;
        float cf = 0.0f;
        short bv = 0;
        switch (this.area) {
            default: {
                final AuraChunk ac = AuraHandler.getAuraChunk(world.field_73011_w.getDimension(), (int)player.field_70165_t >> 4, (int)player.field_70161_v >> 4);
                cv = ac.getVis();
                cf = ac.getFlux();
                bv = ac.getBase();
                break;
            }
            case 1: {
                AuraChunk ac = AuraHandler.getAuraChunk(world.field_73011_w.getDimension(), (int)player.field_70165_t >> 4, (int)player.field_70161_v >> 4);
                cv = ac.getVis();
                cf = ac.getFlux();
                bv = ac.getBase();
                for (final EnumFacing face : EnumFacing.field_176754_o) {
                    ac = AuraHandler.getAuraChunk(world.field_73011_w.getDimension(), ((int)player.field_70165_t >> 4) + face.func_82601_c(), ((int)player.field_70161_v >> 4) + face.func_82599_e());
                    cv += ac.getVis();
                    cf += ac.getFlux();
                    bv += ac.getBase();
                }
                break;
            }
            case 2: {
                for (int xx = -1; xx <= 1; ++xx) {
                    for (int zz = -1; zz <= 1; ++zz) {
                        final AuraChunk ac = AuraHandler.getAuraChunk(world.field_73011_w.getDimension(), ((int)player.field_70165_t >> 4) + xx, ((int)player.field_70161_v >> 4) + zz);
                        cv += ac.getVis();
                        cf += ac.getFlux();
                        bv += ac.getBase();
                    }
                }
                break;
            }
        }
        PacketHandler.INSTANCE.sendTo((IMessage)new PacketAuraToClient(new AuraChunk(null, bv, cv, cf)), player);
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        final IBlockState bs = world.func_180495_p(pos);
        if (bs.func_177230_c() instanceof IInteractWithCaster && ((IInteractWithCaster)bs.func_177230_c()).onCasterRightClick(world, player.func_184586_b(hand), player, pos, side, hand)) {
            return EnumActionResult.SUCCESS;
        }
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof IInteractWithCaster && ((IInteractWithCaster)tile).onCasterRightClick(world, player.func_184586_b(hand), player, pos, side, hand)) {
            return EnumActionResult.SUCCESS;
        }
        if (CasterTriggerRegistry.hasTrigger(bs)) {
            return CasterTriggerRegistry.performTrigger(world, player.func_184586_b(hand), player, pos, side, bs) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
        }
        final ItemStack fb = this.getFocusStack(player.func_184586_b(hand));
        if (fb != null && !fb.func_190926_b()) {
            final FocusPackage core = ItemFocus.getPackage(fb);
            for (final IFocusElement fe : core.nodes) {
                if (fe instanceof IFocusBlockPicker && player.func_70093_af() && world.func_175625_s(pos) == null) {
                    if (!world.field_72995_K) {
                        ItemStack isout = new ItemStack(bs.func_177230_c(), 1, bs.func_177230_c().func_176201_c(bs));
                        try {
                            if (bs != Blocks.field_150350_a) {
                                final ItemStack is = BlockUtils.getSilkTouchDrop(bs);
                                if (is != null && !is.func_190926_b()) {
                                    isout = is.func_77946_l();
                                }
                            }
                        }
                        catch (Exception ex) {}
                        this.storePickedBlock(player.func_184586_b(hand), isout);
                        return EnumActionResult.SUCCESS;
                    }
                    player.func_184609_a(hand);
                    return EnumActionResult.PASS;
                }
            }
        }
        return EnumActionResult.PASS;
    }
    
    private RayTraceResult generateSourceVector(final Entity e) {
        Vec3d v = e.func_174791_d();
        boolean mainhand = true;
        if (e instanceof EntityPlayer) {
            if (((EntityPlayer)e).func_184614_ca() != null && ((EntityPlayer)e).func_184614_ca().func_77973_b() instanceof ICaster) {
                mainhand = true;
            }
            else if (((EntityPlayer)e).func_184592_cb() != null && ((EntityPlayer)e).func_184592_cb().func_77973_b() instanceof ICaster) {
                mainhand = false;
            }
        }
        final double posX = -MathHelper.cos((e.field_70177_z - 0.5f) / 180.0f * 3.141593f) * 0.20000000298023224 * (mainhand ? 1 : -1);
        final double posZ = -MathHelper.sin((e.field_70177_z - 0.5f) / 180.0f * 3.141593f) * 0.30000001192092896 * (mainhand ? 1 : -1);
        final Vec3d vl = e.func_70040_Z();
        v = v.func_72441_c(posX, e.func_70047_e() - 0.4000000014901161, posZ);
        v = v.func_178787_e(vl);
        return new RayTraceResult(e, v);
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer player, final EnumHand hand) {
        final ItemStack focusStack = this.getFocusStack(player.func_184586_b(hand));
        final ItemFocus focus = this.getFocus(player.func_184586_b(hand));
        if (focus == null || CasterManager.isOnCooldown((EntityLivingBase)player)) {
            return (ActionResult<ItemStack>)super.func_77659_a(world, player, hand);
        }
        CasterManager.setCooldown((EntityLivingBase)player, focus.getActivationTime(focusStack));
        final FocusPackage core = ItemFocus.getPackage(focusStack);
        if (player.func_70093_af()) {
            for (final IFocusElement fe : core.nodes) {
                if (fe instanceof IFocusBlockPicker && player.func_70093_af()) {
                    return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.PASS, (Object)player.func_184586_b(hand));
                }
            }
        }
        if (world.field_72995_K) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
        }
        if (this.consumeVis(player.func_184586_b(hand), player, focus.getVisCost(focusStack), false, false)) {
            FocusEngine.castFocusPackage((EntityLivingBase)player, core);
            player.func_184609_a(hand);
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, (Object)player.func_184586_b(hand));
    }
    
    public int func_77626_a(final ItemStack itemstack) {
        return 72000;
    }
    
    public EnumAction func_77661_b(final ItemStack stack1) {
        return EnumAction.BOW;
    }
    
    @Override
    public ArrayList<BlockPos> getArchitectBlocks(final ItemStack stack, final World world, final BlockPos pos, final EnumFacing side, final EntityPlayer player) {
        final ItemFocus focus = this.getFocus(stack);
        if (focus != null) {
            final FocusPackage fp = ItemFocus.getPackage(this.getFocusStack(stack));
            if (fp != null) {
                for (final IFocusElement fe : fp.nodes) {
                    if (fe instanceof IArchitect) {
                        return ((IArchitect)fe).getArchitectBlocks(stack, world, pos, side, player);
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean showAxis(final ItemStack stack, final World world, final EntityPlayer player, final EnumFacing side, final EnumAxis axis) {
        final ItemFocus focus = this.getFocus(stack);
        if (focus != null) {
            final FocusPackage fp = ItemFocus.getPackage(this.getFocusStack(stack));
            if (fp != null) {
                for (final IFocusElement fe : fp.nodes) {
                    if (fe instanceof IArchitect) {
                        return ((IArchitect)fe).showAxis(stack, world, player, side, axis);
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public RayTraceResult getArchitectMOP(final ItemStack stack, final World world, final EntityLivingBase player) {
        final ItemFocus focus = this.getFocus(stack);
        if (focus != null) {
            final FocusPackage fp = ItemFocus.getPackage(this.getFocusStack(stack));
            if (fp != null && FocusEngine.doesPackageContainElement(fp, "thaumcraft.PLAN")) {
                return ((IArchitect)FocusEngine.getElement("thaumcraft.PLAN")).getArchitectMOP(this.getFocusStack(stack), world, player);
            }
        }
        return null;
    }
    
    @Override
    public boolean useBlockHighlight(final ItemStack stack) {
        return false;
    }
    
    public void storePickedBlock(final ItemStack stack, final ItemStack stackout) {
        final NBTTagCompound item = new NBTTagCompound();
        stack.func_77983_a("picked", (NBTBase)stackout.func_77955_b(item));
    }
    
    @Override
    public ItemStack getPickedBlock(final ItemStack stack) {
        if (stack == null || stack.func_190926_b()) {
            return ItemStack.field_190927_a;
        }
        ItemStack out = null;
        final ItemFocus focus = this.getFocus(stack);
        if (focus != null && stack.func_77942_o() && stack.func_77978_p().func_74764_b("picked")) {
            final FocusPackage fp = ItemFocus.getPackage(this.getFocusStack(stack));
            if (fp != null) {
                for (final IFocusElement fe : fp.nodes) {
                    if (fe instanceof IFocusBlockPicker) {
                        out = new ItemStack(Blocks.field_150350_a);
                        try {
                            out = new ItemStack(stack.func_77978_p().func_74775_l("picked"));
                        }
                        catch (Exception ex) {}
                        break;
                    }
                }
            }
        }
        return out;
    }
}
