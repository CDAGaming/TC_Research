package thaumcraft.common.lib.capabilities;

import net.minecraft.nbt.*;
import javax.annotation.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.playerdata.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import thaumcraft.api.capabilities.*;

public class PlayerWarp
{
    public static void preInit() {
        CapabilityManager.INSTANCE.register((Class)IPlayerWarp.class, (Capability.IStorage)new Capability.IStorage<IPlayerWarp>() {
            public NBTTagCompound writeNBT(final Capability<IPlayerWarp> capability, final IPlayerWarp instance, final EnumFacing side) {
                return (NBTTagCompound)instance.serializeNBT();
            }
            
            public void readNBT(final Capability<IPlayerWarp> capability, final IPlayerWarp instance, final EnumFacing side, final NBTBase nbt) {
                if (nbt instanceof NBTTagCompound) {
                    instance.deserializeNBT((NBTBase)nbt);
                }
            }
        }, () -> new DefaultImpl());
    }
    
    private static class DefaultImpl implements IPlayerWarp
    {
        private int[] warp;
        private int counter;
        
        private DefaultImpl() {
            this.warp = new int[EnumWarpType.values().length];
        }
        
        @Override
        public void clear() {
            this.warp = new int[EnumWarpType.values().length];
            this.counter = 0;
        }
        
        @Override
        public int get(@Nonnull final EnumWarpType type) {
            return this.warp[type.ordinal()];
        }
        
        @Override
        public void set(final EnumWarpType type, final int amount) {
            this.warp[type.ordinal()] = MathHelper.func_76125_a(amount, 0, 500);
        }
        
        @Override
        public int add(@Nonnull final EnumWarpType type, final int amount) {
            return this.warp[type.ordinal()] = MathHelper.func_76125_a(this.warp[type.ordinal()] + amount, 0, 500);
        }
        
        @Override
        public int reduce(@Nonnull final EnumWarpType type, final int amount) {
            return this.warp[type.ordinal()] = MathHelper.func_76125_a(this.warp[type.ordinal()] - amount, 0, 500);
        }
        
        @Override
        public void sync(@Nonnull final EntityPlayerMP player) {
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketSyncWarp((EntityPlayer)player), player);
        }
        
        public NBTTagCompound serializeNBT() {
            final NBTTagCompound properties = new NBTTagCompound();
            properties.func_74783_a("warp", this.warp);
            properties.func_74768_a("counter", this.getCounter());
            return properties;
        }
        
        public void deserializeNBT(final NBTTagCompound properties) {
            if (properties == null) {
                return;
            }
            this.clear();
            final int[] ba = properties.func_74759_k("warp");
            if (ba != null) {
                int l = EnumWarpType.values().length;
                if (ba.length < l) {
                    l = ba.length;
                }
                for (int a = 0; a < l; ++a) {
                    this.warp[a] = ba[a];
                }
            }
            this.setCounter(properties.func_74762_e("counter"));
        }
        
        @Override
        public int getCounter() {
            return this.counter;
        }
        
        @Override
        public void setCounter(final int amount) {
            this.counter = amount;
        }
    }
    
    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        public static final ResourceLocation NAME;
        private final DefaultImpl warp;
        
        public Provider() {
            this.warp = new DefaultImpl();
        }
        
        public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
            return capability == ThaumcraftCapabilities.WARP;
        }
        
        public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
            if (capability == ThaumcraftCapabilities.WARP) {
                return (T)ThaumcraftCapabilities.WARP.cast((Object)this.warp);
            }
            return null;
        }
        
        public NBTTagCompound serializeNBT() {
            return this.warp.serializeNBT();
        }
        
        public void deserializeNBT(final NBTTagCompound nbt) {
            this.warp.deserializeNBT(nbt);
        }
        
        static {
            NAME = new ResourceLocation("thaumcraft", "warp");
        }
    }
}
