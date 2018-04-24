package thaumcraft.client.gui;

import thaumcraft.api.research.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.toasts.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.text.translation.*;

public class ResearchToast implements IToast
{
    ResearchEntry entry;
    private long firstDrawTime;
    private boolean newDisplay;
    ResourceLocation tex;
    
    public ResearchToast(final ResearchEntry entry) {
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/hud.png");
        this.entry = entry;
    }
    
    public IToast.Visibility func_193653_a(final GuiToast toastGui, final long delta) {
        if (this.newDisplay) {
            this.firstDrawTime = delta;
            this.newDisplay = false;
        }
        toastGui.func_192989_b().func_110434_K().func_110577_a(this.tex);
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
        toastGui.func_73729_b(0, 0, 0, 224, 160, 32);
        GuiResearchBrowser.drawResearchIcon(this.entry, 6, 8, 0.0f, false);
        toastGui.func_192989_b().field_71466_p.func_78276_b(I18n.func_74838_a("research.complete"), 30, 7, 10631665);
        final String s = this.entry.getLocalizedName();
        float w = toastGui.func_192989_b().field_71466_p.func_78256_a(s);
        if (w > 124.0f) {
            w = 124.0f / w;
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(30.0f, 18.0f, 0.0f);
            GlStateManager.func_179152_a(w, w, w);
            toastGui.func_192989_b().field_71466_p.func_78276_b(s, 0, 0, 16755465);
            GlStateManager.func_179121_F();
        }
        else {
            toastGui.func_192989_b().field_71466_p.func_78276_b(s, 30, 18, 16755465);
        }
        return (delta - this.firstDrawTime < 5000L) ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
    }
}
