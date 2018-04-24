package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.api.items.*;

public class ItemScribingTools extends ItemTCBase implements IScribeTools
{
    public ItemScribingTools() {
        super("scribing_tools", new String[0]);
        this.field_77777_bU = 1;
        this.func_77656_e(100);
        this.func_77627_a(false);
    }
}
