package thaumcraft.common.lib.network.fx;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.particles.*;
import java.awt.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.*;

public class PacketFXScanSource implements IMessage, IMessageHandler<PacketFXScanSource, IMessage>
{
    private long loc;
    private int size;
    final int C_QUARTZ = 15064789;
    final int C_IRON = 14200723;
    final int C_LAPIS = 1328572;
    final int C_GOLD = 16576075;
    final int C_DIAMOND = 6155509;
    final int C_EMERALD = 1564002;
    final int C_REDSTONE = 16711680;
    final int C_COAL = 1052688;
    final int C_SILVER = 14342653;
    final int C_TIN = 15724539;
    final int C_COPPER = 16620629;
    final int C_AMBER = 16626469;
    final int C_CINNABAR = 10159368;
    
    public PacketFXScanSource() {
    }
    
    public PacketFXScanSource(final BlockPos pos, final int size) {
        this.loc = pos.func_177986_g();
        this.size = size;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeLong(this.loc);
        buffer.writeByte(this.size);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.loc = buffer.readLong();
        this.size = buffer.readByte();
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketFXScanSource message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                PacketFXScanSource.this.startScan(Minecraft.func_71410_x().field_71439_g.field_70170_p, BlockPos.func_177969_a(message.loc), message.size);
            }
        });
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void startScan(final World world, final BlockPos pos, final int r) {
        final int range = 4 + r * 4;
        final ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
        for (int xx = -range; xx <= range; ++xx) {
            for (int yy = -range; yy <= range; ++yy) {
                for (int zz = -range; zz <= range; ++zz) {
                    final BlockPos p = pos.func_177982_a(xx, yy, zz);
                    if (Utils.isOreBlock(world, p)) {
                        positions.add(p);
                    }
                }
            }
        }
        while (!positions.isEmpty()) {
            final BlockPos start = positions.get(0);
            final ArrayList<BlockPos> coll = new ArrayList<BlockPos>();
            coll.add(start);
            positions.remove(0);
            this.calcGroup(world, start, coll, positions);
            if (!coll.isEmpty()) {
                final int c = this.getOreColor(world, start);
                double x = 0.0;
                double y = 0.0;
                double z = 0.0;
                for (final BlockPos p2 : coll) {
                    x += p2.func_177958_n() + 0.5;
                    y += p2.func_177956_o() + 0.5;
                    z += p2.func_177952_p() + 0.5;
                }
                x /= coll.size();
                y /= coll.size();
                z /= coll.size();
                final double dis = Math.sqrt(pos.func_177957_d(x, y, z));
                final FXGeneric fb = new FXGeneric(world, x, y, z, 0.0, 0.0, 0.0);
                fb.func_187114_a(44);
                final Color cc = new Color(c);
                fb.func_70538_b(cc.getRed() / 255.0f, cc.getGreen() / 255.0f, cc.getBlue() / 255.0f);
                final float q = (cc.getRed() / 255.0f + cc.getGreen() / 255.0f + cc.getBlue() / 255.0f) / 3.0f;
                fb.setAlphaF(0.0f, 1.0f, 0.8f, 0.0f);
                fb.setParticles(240, 15, 1);
                fb.setGridSize(16);
                fb.setLoop(true);
                fb.setScale(9.0f);
                fb.setLayer((q < 0.25f) ? 3 : 2);
                fb.setRotationSpeed(0.0f);
                ParticleEngine.addEffectWithDelay(world, fb, (int)(dis * 3.0));
            }
        }
    }
    
    private void calcGroup(final World world, final BlockPos start, final ArrayList<BlockPos> coll, final ArrayList<BlockPos> positions) {
        final IBlockState bs = world.func_180495_p(start);
    Label_0132:
        for (int x = -1; x <= 1; ++x) {
            for (int y = -1; y <= 1; ++y) {
                for (int z = -1; z <= 1; ++z) {
                    final BlockPos t = new BlockPos((Vec3i)start).func_177982_a(x, y, z);
                    final IBlockState ts = world.func_180495_p(t);
                    if (ts.equals(bs) && positions.contains(t)) {
                        positions.remove(t);
                        coll.add(t);
                        if (positions.isEmpty()) {
                            break Label_0132;
                        }
                        this.calcGroup(world, t, coll, positions);
                    }
                }
            }
        }
    }
    
    private int getOreColor(final World world, final BlockPos pos) {
        final IBlockState bi = world.func_180495_p(pos);
        if (bi.func_177230_c() != Blocks.field_150350_a && bi.func_177230_c() != Blocks.field_150357_h) {
            ItemStack is = BlockUtils.getSilkTouchDrop(bi);
            if (is == null || is.func_190926_b()) {
                final int md = bi.func_177230_c().func_176201_c(bi);
                is = new ItemStack(bi.func_177230_c(), 1, md);
            }
            if (is == null || is.func_190926_b() || is.func_77973_b() == null) {
                return 12632256;
            }
            final int[] od = OreDictionary.getOreIDs(is);
            if (od != null && od.length > 0) {
                for (final int id : od) {
                    if (OreDictionary.getOreName(id) != null) {
                        if (OreDictionary.getOreName(id).toUpperCase().contains("IRON")) {
                            return 14200723;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("COAL")) {
                            return 1052688;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("REDSTONE")) {
                            return 16711680;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("GOLD")) {
                            return 16576075;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("LAPIS")) {
                            return 1328572;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("DIAMOND")) {
                            return 6155509;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("EMERALD")) {
                            return 1564002;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("QUARTZ")) {
                            return 15064789;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("SILVER")) {
                            return 14342653;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("TIN")) {
                            return 15724539;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("COPPER")) {
                            return 16620629;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("AMBER")) {
                            return 16626469;
                        }
                        if (OreDictionary.getOreName(id).toUpperCase().contains("CINNABAR")) {
                            return 10159368;
                        }
                    }
                }
            }
        }
        return 12632256;
    }
}
