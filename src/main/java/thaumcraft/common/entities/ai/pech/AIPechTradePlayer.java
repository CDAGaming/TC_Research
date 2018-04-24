package thaumcraft.common.entities.ai.pech;

import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.monster.*;

public class AIPechTradePlayer extends EntityAIBase
{
    private EntityPech villager;
    
    public AIPechTradePlayer(final EntityPech par1EntityVillager) {
        this.villager = par1EntityVillager;
        this.func_75248_a(5);
    }
    
    public boolean func_75250_a() {
        return this.villager.func_70089_S() && !this.villager.func_70090_H() && this.villager.isTamed() && this.villager.field_70122_E && !this.villager.field_70133_I && this.villager.trading;
    }
    
    public void func_75249_e() {
        this.villager.func_70661_as().func_75499_g();
    }
    
    public void func_75251_c() {
        this.villager.trading = false;
    }
}
