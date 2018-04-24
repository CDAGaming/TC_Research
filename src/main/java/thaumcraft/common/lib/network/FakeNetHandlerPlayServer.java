package thaumcraft.common.lib.network;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public class FakeNetHandlerPlayServer extends NetHandlerPlayServer
{
    public FakeNetHandlerPlayServer(final MinecraftServer server, final NetworkManager networkManagerIn, final EntityPlayerMP playerIn) {
        super(server, networkManagerIn, playerIn);
    }
    
    public void func_73660_a() {
    }
    
    public void func_147358_a(final CPacketInput packetIn) {
    }
    
    public void func_147347_a(final CPacketPlayer packetIn) {
    }
    
    public void func_147359_a(final Packet packetIn) {
    }
    
    public void func_147340_a(final CPacketUseEntity packetIn) {
    }
    
    public void func_147342_a(final CPacketClientStatus packetIn) {
    }
    
    public void func_147356_a(final CPacketCloseWindow packetIn) {
    }
    
    public void func_147351_a(final CPacketClickWindow packetIn) {
    }
    
    public void func_147338_a(final CPacketEnchantItem packetIn) {
    }
    
    public void func_147344_a(final CPacketCreativeInventoryAction packetIn) {
    }
    
    public void func_147339_a(final CPacketConfirmTransaction packetIn) {
    }
    
    public void func_147343_a(final CPacketUpdateSign packetIn) {
    }
    
    public void func_147353_a(final CPacketKeepAlive packetIn) {
    }
    
    public void func_147341_a(final CPacketTabComplete packetIn) {
    }
    
    public void func_147352_a(final CPacketClientSettings packetIn) {
    }
    
    public void func_147349_a(final CPacketCustomPayload packetIn) {
    }
}
