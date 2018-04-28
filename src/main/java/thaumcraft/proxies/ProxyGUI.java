package thaumcraft.proxies;

import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;
import thaumcraft.common.tiles.essentia.*;
import thaumcraft.common.entities.construct.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.entities.construct.golem.*;
import codechicken.lib.raytracer.*;
import net.minecraft.util.math.*;
import thaumcraft.client.gui.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import thaumcraft.common.container.*;

public class ProxyGUI
{
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (world instanceof WorldClient) {
            switch (ID) {
                case 13: {
                    return new GuiArcaneWorkbench(player.inventory, (TileArcaneWorkbench)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 12: {
                    return new GuiResearchBrowser();
                }
                case 10: {
                    return new GuiResearchTable(player, (TileResearchTable)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 9: {
                    return new GuiSmelter(player.inventory, (TileSmelter)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 16: {
                    return new GuiTurretBasic(player.inventory, world, (EntityTurretCrossbow) world.getEntityByID(x));
                }
                case 17: {
                    return new GuiTurretAdvanced(player.inventory, world, (EntityTurretCrossbowAdvanced) world.getEntityByID(x));
                }
                case 3: {
                    return new GuiThaumatorium(player.inventory, (TileThaumatorium)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 14: {
                    return new GuiArcaneBore(player.inventory, world, (EntityArcaneBore) world.getEntityByID(x));
                }
                case 4: {
                    return new GuiHandMirror(player.inventory, world, x, y, z);
                }
                case 5: {
                    return new GuiFocusPouch(player.inventory, world, x, y, z);
                }
                case 6: {
                    return new GuiSpa(player.inventory, (TileSpa)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 7: {
                    return new GuiFocalManipulator(player.inventory, (TileFocalManipulator)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 19: {
                    return new GuiGolemBuilder(player.inventory, (TileGolemBuilder)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 21: {
                    return new GuiPotionSprayer(player.inventory, (TilePotionSprayer)world.getTileEntity(new BlockPos(x, y, z)));
                }
                case 18: {
                    final ISealEntity se = ItemGolemBell.getSeal(player);
                    if (se != null) {
                        return se.getSeal().returnGui(world, player, new BlockPos(x, y, z), se.getSealPos().face, se);
                    }
                    break;
                }
                case 20: {
                    final RayTraceResult ray = RayTracer.retrace(player);
                    BlockPos target = null;
                    EnumFacing side = null;
                    if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
                        target = ray.getBlockPos();
                        side = ray.sideHit;
                    }
                    return new GuiLogistics(player.inventory, world, target, side);
                }
            }
        }
        return null;
    }
    
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        switch (ID) {
            case 13: {
                return new ContainerArcaneWorkbench(player.inventory, (TileArcaneWorkbench)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 10: {
                return new ContainerResearchTable(player.inventory, (TileResearchTable)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 9: {
                return new ContainerSmelter(player.inventory, (TileSmelter)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 16: {
                return new ContainerTurretBasic(player.inventory, world, (EntityTurretCrossbow) world.getEntityByID(x));
            }
            case 17: {
                return new ContainerTurretAdvanced(player.inventory, world, (EntityTurretCrossbowAdvanced) world.getEntityByID(x));
            }
            case 3: {
                return new ContainerThaumatorium(player.inventory, (TileThaumatorium)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 5: {
                return new ContainerFocusPouch(player.inventory, world, x, y, z);
            }
            case 14: {
                return new ContainerArcaneBore(player.inventory, world, (EntityArcaneBore) world.getEntityByID(x));
            }
            case 4: {
                return new ContainerHandMirror(player.inventory, world, x, y, z);
            }
            case 6: {
                return new ContainerSpa(player.inventory, (TileSpa)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 7: {
                return new ContainerFocalManipulator(player.inventory, (TileFocalManipulator)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 19: {
                return new ContainerGolemBuilder(player.inventory, (TileGolemBuilder)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 21: {
                return new ContainerPotionSprayer(player.inventory, (TilePotionSprayer)world.getTileEntity(new BlockPos(x, y, z)));
            }
            case 18: {
                final ISealEntity se = ItemGolemBell.getSeal(player);
                if (se != null) {
                    return se.getSeal().returnContainer(world, player, new BlockPos(x, y, z), se.getSealPos().face, se);
                }
                break;
            }
            case 20: {
                return new ContainerLogistics(player.inventory, world);
            }
        }
        return null;
    }
}
