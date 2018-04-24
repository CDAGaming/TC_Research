package thaumcraft.common.blocks.essentia;

import thaumcraft.common.blocks.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.lib.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import thaumcraft.common.tiles.essentia.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import thaumcraft.api.items.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import thaumcraft.api.aura.*;
import thaumcraft.api.aspects.*;
import java.util.*;
import thaumcraft.client.fx.*;

public class BlockJar extends BlockTCTile implements ILabelable
{
    public BlockJar(final Class t, final String name) {
        super(Material.field_151592_s, t, name);
        this.func_149711_c(0.3f);
        this.func_149672_a(SoundsTC.JAR);
    }
    
    public SoundType func_185467_w() {
        return SoundsTC.JAR;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.75, 0.8125);
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.func_176203_a(meta);
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        BlockJar.spillEssentia = false;
        super.func_180663_b(worldIn, pos, state);
        BlockJar.spillEssentia = true;
    }
    
    public void func_180653_a(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileJarFillable) {
            this.spawnFilledJar(worldIn, pos, state, (TileJarFillable)te);
        }
        else if (te instanceof TileJarBrain) {
            this.spawnBrainJar(worldIn, pos, state, (TileJarBrain)te);
        }
        else {
            super.func_180653_a(worldIn, pos, state, chance, fortune);
        }
    }
    
    public void func_180657_a(final World worldIn, final EntityPlayer player, final BlockPos pos, final IBlockState state, final TileEntity te, final ItemStack stack) {
        if (te instanceof TileJarFillable) {
            this.spawnFilledJar(worldIn, pos, state, (TileJarFillable)te);
        }
        else if (te instanceof TileJarBrain) {
            this.spawnBrainJar(worldIn, pos, state, (TileJarBrain)te);
        }
        else {
            super.func_180657_a(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }
    
    private void spawnFilledJar(final World world, final BlockPos pos, final IBlockState state, final TileJarFillable te) {
        final ItemStack drop = new ItemStack((Block)this, 1, this.func_176201_c(state));
        if (te.amount > 0) {
            ((BlockJarItem)drop.func_77973_b()).setAspects(drop, new AspectList().add(te.aspect, te.amount));
        }
        if (te.aspectFilter != null) {
            if (!drop.func_77942_o()) {
                drop.func_77982_d(new NBTTagCompound());
            }
            drop.func_77978_p().func_74778_a("AspectFilter", te.aspectFilter.getTag());
        }
        if (te.blocked) {
            func_180635_a(world, pos, new ItemStack(ItemsTC.jarBrace));
        }
        func_180635_a(world, pos, drop);
    }
    
    private void spawnBrainJar(final World world, final BlockPos pos, final IBlockState state, final TileJarBrain te) {
        final ItemStack drop = new ItemStack((Block)this, 1, this.func_176201_c(state));
        if (te.xp > 0) {
            drop.func_77983_a("xp", (NBTBase)new NBTTagInt(te.xp));
        }
        func_180635_a(world, pos, drop);
    }
    
    public void func_180633_a(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase ent, final ItemStack stack) {
        final int l = MathHelper.func_76128_c(ent.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
        final TileEntity tile = world.func_175625_s(pos);
        if (tile instanceof TileJarFillable) {
            if (l == 0) {
                ((TileJarFillable)tile).facing = 2;
            }
            if (l == 1) {
                ((TileJarFillable)tile).facing = 5;
            }
            if (l == 2) {
                ((TileJarFillable)tile).facing = 3;
            }
            if (l == 3) {
                ((TileJarFillable)tile).facing = 4;
            }
        }
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileJarBrain) {
            ((TileJarBrain)te).eatDelay = 40;
            if (!world.field_72995_K) {
                final int var6 = world.field_73012_v.nextInt(Math.min(((TileJarBrain)te).xp + 1, 64));
                if (var6 > 0) {
                    final TileJarBrain tileJarBrain = (TileJarBrain)te;
                    tileJarBrain.xp -= var6;
                    int xp = var6;
                    while (xp > 0) {
                        final int var7 = EntityXPOrb.func_70527_a(xp);
                        xp -= var7;
                        world.func_72838_d((Entity)new EntityXPOrb(world, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, var7));
                    }
                    world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
                    te.func_70296_d();
                }
            }
            else {
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.2f, 1.0f, false);
            }
        }
        if (te != null && te instanceof TileJarFillable && !((TileJarFillable)te).blocked && player.func_184586_b(hand).func_77973_b() == ItemsTC.jarBrace) {
            ((TileJarFillable)te).blocked = true;
            player.func_184586_b(hand).func_190918_g(1);
            if (world.field_72995_K) {
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.key, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            }
            else {
                te.func_70296_d();
            }
        }
        else if (te != null && te instanceof TileJarFillable && player.func_70093_af() && ((TileJarFillable)te).aspectFilter != null && side.ordinal() == ((TileJarFillable)te).facing) {
            ((TileJarFillable)te).aspectFilter = null;
            if (world.field_72995_K) {
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.page, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            }
            else {
                world.func_72838_d((Entity)new EntityItem(world, (double)(pos.func_177958_n() + 0.5f + side.func_82601_c() / 3.0f), (double)(pos.func_177956_o() + 0.5f), (double)(pos.func_177952_p() + 0.5f + side.func_82599_e() / 3.0f), new ItemStack(ItemsTC.label)));
            }
        }
        else if (te != null && te instanceof TileJarFillable && player.func_70093_af() && player.func_184586_b(hand).func_190926_b()) {
            if (((TileJarFillable)te).aspectFilter == null) {
                ((TileJarFillable)te).aspect = null;
            }
            if (world.field_72995_K) {
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.4f, 1.0f, false);
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 0.5f, 1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.3f, false);
            }
            else {
                AuraHelper.polluteAura(world, pos, ((TileJarFillable)te).amount, true);
            }
            ((TileJarFillable)te).amount = 0;
            te.func_70296_d();
        }
        return true;
    }
    
    @Override
    public boolean applyLabel(final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ItemStack labelstack) {
        final TileEntity te = player.field_70170_p.func_175625_s(pos);
        if (te == null || !(te instanceof TileJarFillable) || ((TileJarFillable)te).aspectFilter != null) {
            return false;
        }
        if (((TileJarFillable)te).amount == 0 && ((IEssentiaContainerItem)labelstack.func_77973_b()).getAspects(labelstack) == null) {
            return false;
        }
        if (((TileJarFillable)te).amount == 0 && ((IEssentiaContainerItem)labelstack.func_77973_b()).getAspects(labelstack) != null) {
            ((TileJarFillable)te).aspect = ((IEssentiaContainerItem)labelstack.func_77973_b()).getAspects(labelstack).getAspects()[0];
        }
        this.func_180633_a(player.field_70170_p, pos, player.field_70170_p.func_180495_p(pos), (EntityLivingBase)player, null);
        ((TileJarFillable)te).aspectFilter = ((TileJarFillable)te).aspect;
        player.field_70170_p.markAndNotifyBlock(pos, player.field_70170_p.func_175726_f(pos), player.field_70170_p.func_180495_p(pos), player.field_70170_p.func_180495_p(pos), 3);
        te.func_70296_d();
        player.field_70170_p.func_184148_a((EntityPlayer)null, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.4f, 1.0f);
        return true;
    }
    
    public float getEnchantPowerBonus(final World world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileJarBrain) {
            return 5.0f;
        }
        return super.getEnchantPowerBonus(world, pos);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileJarBrain && ((TileJarBrain)tile).xp >= ((TileJarBrain)tile).xpMax) {
            FXDispatcher.INSTANCE.spark(pos.func_177958_n() + 0.5f, pos.func_177956_o() + 0.8f, pos.func_177952_p() + 0.5f, 3.0f, 0.2f + rand.nextFloat() * 0.2f, 1.0f, 0.3f + rand.nextFloat() * 0.2f, 0.5f);
        }
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileJarBrain) {
            final float r = ((TileJarBrain)tile).xp / ((TileJarBrain)tile).xpMax;
            return MathHelper.func_76141_d(r * 14.0f) + ((((TileJarBrain)tile).xp > 0) ? 1 : 0);
        }
        if (tile != null && tile instanceof TileJarFillable) {
            final float n = ((TileJarFillable)tile).amount;
            final TileJarFillable tileJarFillable = (TileJarFillable)tile;
            final float r = n / 250.0f;
            return MathHelper.func_76141_d(r * 14.0f) + ((((TileJarFillable)tile).amount > 0) ? 1 : 0);
        }
        return super.func_180641_l(state, world, pos);
    }
}
