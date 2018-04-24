package thaumcraft.common.lib.events;

import net.minecraftforge.fml.common.*;
import net.minecraft.client.settings.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.client.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.common.lib.network.misc.*;
import org.lwjgl.input.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class KeyHandler
{
    public static KeyBinding keyF;
    public static KeyBinding keyG;
    private static boolean keyPressedF;
    private boolean keyPressedH;
    private static boolean keyPressedG;
    public static boolean radialActive;
    public static boolean radialLock;
    public static long lastPressF;
    public static long lastPressH;
    public static long lastPressG;
    
    public KeyHandler() {
        this.keyPressedH = false;
        ClientRegistry.registerKeyBinding(KeyHandler.keyF);
        ClientRegistry.registerKeyBinding(KeyHandler.keyG);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void playerTick(final TickEvent.PlayerTickEvent event) {
        if (event.side == Side.SERVER) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            if (KeyHandler.keyF.func_151470_d()) {
                if (FMLClientHandler.instance().getClient().field_71415_G) {
                    final EntityPlayer player = event.player;
                    if (player != null) {
                        if (!KeyHandler.keyPressedF) {
                            KeyHandler.lastPressF = System.currentTimeMillis();
                            KeyHandler.radialLock = false;
                        }
                        if (!KeyHandler.radialLock && ((player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof ICaster) || (player.func_184592_cb() != null && player.func_184592_cb().func_77973_b() instanceof ICaster))) {
                            if (player.func_70093_af()) {
                                PacketHandler.INSTANCE.sendToServer((IMessage)new PacketFocusChangeToServer("REMOVE"));
                            }
                            else {
                                KeyHandler.radialActive = true;
                            }
                        }
                        else if (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof ItemGolemBell && !KeyHandler.keyPressedF) {
                            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketItemKeyToServer(0));
                        }
                    }
                    KeyHandler.keyPressedF = true;
                }
            }
            else {
                KeyHandler.radialActive = false;
                if (KeyHandler.keyPressedF) {
                    KeyHandler.lastPressF = System.currentTimeMillis();
                }
                KeyHandler.keyPressedF = false;
            }
            if (KeyHandler.keyG.func_151470_d()) {
                if (FMLClientHandler.instance().getClient().field_71415_G) {
                    final EntityPlayer player = event.player;
                    if (player != null && !KeyHandler.keyPressedG) {
                        KeyHandler.lastPressG = System.currentTimeMillis();
                        PacketHandler.INSTANCE.sendToServer((IMessage)new PacketItemKeyToServer(1, Keyboard.isKeyDown(29) ? 1 : (Keyboard.isKeyDown(42) ? 2 : 0)));
                    }
                    KeyHandler.keyPressedG = true;
                }
            }
            else {
                if (KeyHandler.keyPressedG) {
                    KeyHandler.lastPressG = System.currentTimeMillis();
                }
                KeyHandler.keyPressedG = false;
            }
        }
    }
    
    static {
        KeyHandler.keyF = new KeyBinding("Change Caster Focus", 33, "key.categories.thaumcraft");
        KeyHandler.keyG = new KeyBinding("Misc Caster Toggle", 34, "key.categories.thaumcraft");
        KeyHandler.keyPressedF = false;
        KeyHandler.keyPressedG = false;
        KeyHandler.radialActive = false;
        KeyHandler.radialLock = false;
        KeyHandler.lastPressF = 0L;
        KeyHandler.lastPressH = 0L;
        KeyHandler.lastPressG = 0L;
    }
}
