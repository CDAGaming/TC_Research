package thaumcraft.api.research;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import thaumcraft.api.*;

public class ScanEntity implements IScanThing
{
    String research;
    Class entityClass;
    ThaumcraftApi.EntityTagsNBT[] NBTData;
    boolean inheritedClasses;
    
    public ScanEntity(final String research, final Class entityClass, final boolean inheritedClasses) {
        this.inheritedClasses = false;
        this.research = research;
        this.entityClass = entityClass;
        this.inheritedClasses = inheritedClasses;
    }
    
    public ScanEntity(final String research, final Class entityClass, final boolean inheritedClasses, final ThaumcraftApi.EntityTagsNBT... nbt) {
        this.inheritedClasses = false;
        this.research = research;
        this.entityClass = entityClass;
        this.inheritedClasses = inheritedClasses;
        this.NBTData = nbt;
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        if (obj != null && ((!this.inheritedClasses && this.entityClass == obj.getClass()) || (this.inheritedClasses && this.entityClass.isInstance(obj)))) {
            if (this.NBTData != null && this.NBTData.length > 0) {
                final boolean b = true;
                final NBTTagCompound tc = new NBTTagCompound();
                ((Entity)obj).func_189511_e(tc);
                for (final ThaumcraftApi.EntityTagsNBT nbt : this.NBTData) {
                    if (!tc.func_74764_b(nbt.name) || !ThaumcraftApiHelper.getNBTDataFromId(tc, tc.func_150299_b(nbt.name), nbt.name).equals(nbt.value)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object object) {
        return this.research;
    }
}
