package thaumcraft.common.container;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.container.slot.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;

public class ContainerTurretAdvanced extends Container
{
    private EntityTurretCrossbowAdvanced turret;
    private EntityPlayer player;
    private final World theWorld;
    
    public ContainerTurretAdvanced(final InventoryPlayer par1InventoryPlayer, final World par3World, final EntityTurretCrossbowAdvanced ent) {
        this.turret = ent;
        this.theWorld = par3World;
        this.player = par1InventoryPlayer.field_70458_d;
        this.func_75146_a((Slot)new SlotTurretBasic(this.turret, 0, 42, 29));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }
    
    public boolean func_75140_a(final EntityPlayer par1EntityPlayer, final int par2) {
        if (par2 == 1) {
            this.turret.setTargetAnimal(!this.turret.getTargetAnimal());
            return true;
        }
        if (par2 == 2) {
            this.turret.setTargetMob(!this.turret.getTargetMob());
            return true;
        }
        if (par2 == 3) {
            this.turret.setTargetPlayer(!this.turret.getTargetPlayer());
            return true;
        }
        if (par2 == 4) {
            this.turret.setTargetFriendly(!this.turret.getTargetFriendly());
            return true;
        }
        return super.func_75140_a(par1EntityPlayer, par2);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_75137_b(final int par1, final int par2) {
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return true;
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d()) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot == 0) {
                if (!this.func_75135_a(stackInSlot, 1, this.field_75151_b.size(), true)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.func_75135_a(stackInSlot, 0, 1, false)) {
                return ItemStack.field_190927_a;
            }
            if (stackInSlot.func_190916_E() == 0) {
                slotObject.func_75215_d(ItemStack.field_190927_a);
            }
            else {
                slotObject.func_75218_e();
            }
        }
        return stack;
    }
}
