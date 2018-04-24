package thaumcraft.proxies;

import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;
import thaumcraft.common.tiles.essentia.*;
import thaumcraft.common.entities.construct.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.codechicken.lib.raytracer.*;
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
                    return new GuiArcaneWorkbench(player.field_71071_by, (TileArcaneWorkbench)world.func_175625_s(new BlockPos(x, y, z)));
                }
                case 12: {
                    return new GuiResearchBrowser();
                }
                case 10: {
                    return new GuiResearchTable(player, (TileResearchTable)world.func_175625_s(new BlockPos(x, y, z)));
                }
                case 9: {
                    return new GuiSmelter(player.field_71071_by, (TileSmelter)world.func_175625_s(new BlockPos(x, y, z)));
                }
                case 16: {
                    return new GuiTurretBasic(player.field_71071_by, world, (EntityTurretCrossbow)((WorldClient)world).func_73045_a(x));
                }
                case 17: {
                    return new GuiTurretAdvanced(player.field_71071_by, world, (EntityTurretCrossbowAdvanced)((WorldClient)world).func_73045_a(x));
                }
                case 3: {
                    return new GuiThaumatorium(player.field_71071_by, (TileThaumatorium)world.func_175625_s(new BlockPos(x, y, z)));
                }
                case 14: {
                    return new GuiArcaneBore(player.field_71071_by, world, (EntityArcaneBore)((WorldClient)world).func_73045_a(x));
                }
                case 4: {
                    return new GuiHandMirror(player.field_71071_by, world, x, y, z);
                }
                case 5: {
                    return new GuiFocusPouch(player.field_71071_by, world, x, y, z);
                }
                case 6: {
                    return new GuiSpa(player.field_71071_by, (TileSpa)world.func_175625_s(new BlockPos(x, y, z)));
                }
                case 7: {
                    return new GuiFocalManipulator(player.field_71071_by, (TileFocalManipulator)world.func_175625_s(new BlockPos(x, y, z)));
                }
                case 19: {
                    return new GuiGolemBuilder(player.field_71071_by, (TileGolemBuilder)world.func_175625_s(new BlockPos(x, y, z)));
                }
                case 21: {
                    return new GuiPotionSprayer(player.field_71071_by, (TilePotionSprayer)world.func_175625_s(new BlockPos(x, y, z)));
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
                    if (ray != null && ray.field_72313_a == RayTraceResult.Type.BLOCK) {
                        target = ray.func_178782_a();
                        side = ray.field_178784_b;
                    }
                    return new GuiLogistics(player.field_71071_by, world, target, side);
                }
            }
        }
        return null;
    }
    
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        switch (ID) {
            case 13: {
                return new ContainerArcaneWorkbench(player.field_71071_by, (TileArcaneWorkbench)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 10: {
                return new ContainerResearchTable(player.field_71071_by, (TileResearchTable)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 9: {
                return new ContainerSmelter(player.field_71071_by, (TileSmelter)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 16: {
                return new ContainerTurretBasic(player.field_71071_by, world, (EntityTurretCrossbow)((WorldServer)world).func_73045_a(x));
            }
            case 17: {
                return new ContainerTurretAdvanced(player.field_71071_by, world, (EntityTurretCrossbowAdvanced)((WorldServer)world).func_73045_a(x));
            }
            case 3: {
                return new ContainerThaumatorium(player.field_71071_by, (TileThaumatorium)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 5: {
                return new ContainerFocusPouch(player.field_71071_by, world, x, y, z);
            }
            case 14: {
                return new ContainerArcaneBore(player.field_71071_by, world, (EntityArcaneBore)((WorldServer)world).func_73045_a(x));
            }
            case 4: {
                return new ContainerHandMirror(player.field_71071_by, world, x, y, z);
            }
            case 6: {
                return new ContainerSpa(player.field_71071_by, (TileSpa)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 7: {
                return new ContainerFocalManipulator(player.field_71071_by, (TileFocalManipulator)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 19: {
                return new ContainerGolemBuilder(player.field_71071_by, (TileGolemBuilder)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 21: {
                return new ContainerPotionSprayer(player.field_71071_by, (TilePotionSprayer)world.func_175625_s(new BlockPos(x, y, z)));
            }
            case 18: {
                final ISealEntity se = ItemGolemBell.getSeal(player);
                if (se != null) {
                    return se.getSeal().returnContainer(world, player, new BlockPos(x, y, z), se.getSealPos().face, se);
                }
                break;
            }
            case 20: {
                return new ContainerLogistics(player.field_71071_by, world);
            }
        }
        return null;
    }
}
