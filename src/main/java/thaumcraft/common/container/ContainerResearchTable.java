package thaumcraft.common.container;

import thaumcraft.common.tiles.crafting.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.items.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import thaumcraft.common.container.slot.*;
import thaumcraft.common.lib.utils.*;
import java.util.*;
import thaumcraft.api.research.theorycraft.*;

public class ContainerResearchTable extends Container
{
    public TileResearchTable tileEntity;
    String[] aspects;
    EntityPlayer player;
    
    public ContainerResearchTable(final InventoryPlayer iinventory, final TileResearchTable iinventory1) {
        this.player = iinventory.field_70458_d;
        this.tileEntity = iinventory1;
        this.aspects = Aspect.aspects.keySet().toArray(new String[0]);
        this.func_75146_a((Slot)new SlotLimitedByClass(IScribeTools.class, (IInventory)iinventory1, 0, 16, 15));
        this.func_75146_a((Slot)new SlotLimitedByItemstack(new ItemStack(Items.field_151121_aF), (IInventory)iinventory1, 1, 224, 16));
        this.bindPlayerInventory(iinventory);
    }
    
    protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, j + i * 9 + 9, 77 + j * 18, 190 + i * 18));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, i + j * 3, 20 + i * 18, 190 + j * 18));
            }
        }
    }
    
    public boolean func_75140_a(final EntityPlayer par1EntityPlayer, final int button) {
        if (button == 1) {
            if (this.tileEntity.data.lastDraw != null) {
                this.tileEntity.data.savedCards.add(this.tileEntity.data.lastDraw.card.getSeed());
            }
            for (final ResearchTableData.CardChoice cc : this.tileEntity.data.cardChoices) {
                if (cc.selected) {
                    this.tileEntity.data.lastDraw = cc;
                    break;
                }
            }
            this.tileEntity.data.cardChoices.clear();
            this.tileEntity.syncTile(false);
            return true;
        }
        Label_0410: {
            if (button != 4 && button != 5) {
                if (button != 6) {
                    break Label_0410;
                }
            }
            try {
                final TheorycraftCard card = this.tileEntity.data.cardChoices.get(button - 4).card;
                if (card.getRequiredItems() != null) {
                    for (final ItemStack stack : card.getRequiredItems()) {
                        if (stack != null && !stack.func_190926_b() && !InventoryUtils.isPlayerCarryingAmount(this.player, stack, true)) {
                            return false;
                        }
                    }
                    if (card.getRequiredItemsConsumed() != null && card.getRequiredItemsConsumed().length == card.getRequiredItems().length) {
                        for (int a = 0; a < card.getRequiredItems().length; ++a) {
                            if (card.getRequiredItemsConsumed()[a] && card.getRequiredItems()[a] != null && !card.getRequiredItems()[a].func_190926_b()) {
                                InventoryUtils.consumePlayerItem(this.player, card.getRequiredItems()[a], true, true);
                            }
                        }
                    }
                }
                if (card.activate(par1EntityPlayer, this.tileEntity.data)) {
                    this.tileEntity.consumeInkFromTable();
                    this.tileEntity.data.cardChoices.get(button - 4).selected = true;
                    this.tileEntity.data.addInspiration(-card.getInspirationCost());
                    this.tileEntity.syncTile(false);
                    return true;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (button == 7 && this.tileEntity.data.isComplete()) {
            this.tileEntity.finishTheory(par1EntityPlayer);
            this.tileEntity.syncTile(false);
            return true;
        }
        if (button == 9 && !this.tileEntity.data.isComplete()) {
            this.tileEntity.data = null;
            this.tileEntity.syncTile(false);
            return true;
        }
        if (button == 2 || button == 3) {
            if (this.tileEntity.data != null && !this.tileEntity.data.isComplete() && this.tileEntity.consumepaperFromTable()) {
                this.tileEntity.data.drawCards(button, par1EntityPlayer);
                this.tileEntity.syncTile(false);
            }
            return true;
        }
        return false;
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d()) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot < 2) {
                if (!this.tileEntity.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 2, this.field_75151_b.size(), true)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.tileEntity.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 0, 2, false)) {
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
    
    public boolean func_75145_c(final EntityPlayer player) {
        return this.tileEntity.func_70300_a(player);
    }
}
