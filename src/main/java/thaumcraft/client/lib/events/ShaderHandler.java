package thaumcraft.client.lib.events;

import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import com.google.gson.*;
import java.io.*;
import thaumcraft.common.lib.potions.*;
import net.minecraft.client.renderer.*;

public class ShaderHandler
{
    public static int warpVignette;
    public static final int SHADER_DESAT = 0;
    public static final int SHADER_BLUR = 1;
    public static final int SHADER_HUNGER = 2;
    public static final int SHADER_SUNSCORNED = 3;
    public static ResourceLocation[] shader_resources;
    
    protected void checkShaders(final TickEvent.PlayerTickEvent event, final Minecraft mc) {
        if (event.player.func_70644_a(PotionDeathGaze.instance)) {
            ShaderHandler.warpVignette = 10;
            if (!RenderEventHandler.shaderGroups.containsKey(0)) {
                try {
                    this.setShader(new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), mc.func_147110_a(), ShaderHandler.shader_resources[0]), 0);
                }
                catch (JsonSyntaxException ex) {}
                catch (IOException ex2) {}
            }
        }
        else if (RenderEventHandler.shaderGroups.containsKey(0)) {
            this.deactivateShader(0);
        }
        if (event.player.func_70644_a(PotionBlurredVision.instance)) {
            if (!RenderEventHandler.shaderGroups.containsKey(1)) {
                try {
                    this.setShader(new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), mc.func_147110_a(), ShaderHandler.shader_resources[1]), 1);
                }
                catch (JsonSyntaxException ex3) {}
                catch (IOException ex4) {}
            }
        }
        else if (RenderEventHandler.shaderGroups.containsKey(1)) {
            this.deactivateShader(1);
        }
        if (event.player.func_70644_a(PotionUnnaturalHunger.instance)) {
            if (!RenderEventHandler.shaderGroups.containsKey(2)) {
                try {
                    this.setShader(new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), mc.func_147110_a(), ShaderHandler.shader_resources[2]), 2);
                }
                catch (JsonSyntaxException ex5) {}
                catch (IOException ex6) {}
            }
        }
        else if (RenderEventHandler.shaderGroups.containsKey(2)) {
            this.deactivateShader(2);
        }
        if (event.player.func_70644_a(PotionSunScorned.instance)) {
            if (!RenderEventHandler.shaderGroups.containsKey(3)) {
                try {
                    this.setShader(new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), mc.func_147110_a(), ShaderHandler.shader_resources[3]), 3);
                }
                catch (JsonSyntaxException ex7) {}
                catch (IOException ex8) {}
            }
        }
        else if (RenderEventHandler.shaderGroups.containsKey(3)) {
            this.deactivateShader(3);
        }
    }
    
    void setShader(final ShaderGroup target, final int shaderId) {
        if (OpenGlHelper.field_148824_g) {
            if (RenderEventHandler.shaderGroups.containsKey(shaderId)) {
                RenderEventHandler.shaderGroups.get(shaderId).func_148021_a();
                RenderEventHandler.shaderGroups.remove(shaderId);
            }
            try {
                if (target == null) {
                    this.deactivateShader(shaderId);
                }
                else {
                    RenderEventHandler.resetShaders = true;
                    RenderEventHandler.shaderGroups.put(shaderId, target);
                }
            }
            catch (Exception ioexception) {
                RenderEventHandler.shaderGroups.remove(shaderId);
            }
        }
    }
    
    public void deactivateShader(final int shaderId) {
        if (RenderEventHandler.shaderGroups.containsKey(shaderId)) {
            RenderEventHandler.shaderGroups.get(shaderId).func_148021_a();
        }
        RenderEventHandler.shaderGroups.remove(shaderId);
    }
    
    static {
        ShaderHandler.warpVignette = 0;
        ShaderHandler.shader_resources = new ResourceLocation[] { new ResourceLocation("shaders/post/desaturatetc.json"), new ResourceLocation("shaders/post/blurtc.json"), new ResourceLocation("shaders/post/hunger.json"), new ResourceLocation("shaders/post/sunscorned.json") };
    }
}
