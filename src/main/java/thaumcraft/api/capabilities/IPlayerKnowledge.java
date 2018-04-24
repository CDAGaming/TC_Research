package thaumcraft.api.capabilities;

import net.minecraftforge.common.util.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import java.util.*;
import thaumcraft.api.research.*;
import net.minecraft.entity.player.*;

public interface IPlayerKnowledge extends INBTSerializable<NBTTagCompound>
{
    void clear();
    
    EnumResearchStatus getResearchStatus(@Nonnull final String p0);
    
    boolean isResearchComplete(final String p0);
    
    boolean isResearchKnown(final String p0);
    
    int getResearchStage(@Nonnull final String p0);
    
    boolean addResearch(@Nonnull final String p0);
    
    boolean setResearchStage(@Nonnull final String p0, final int p1);
    
    boolean removeResearch(@Nonnull final String p0);
    
    @Nonnull
    Set<String> getResearchList();
    
    boolean setResearchFlag(@Nonnull final String p0, @Nonnull final EnumResearchFlag p1);
    
    boolean clearResearchFlag(@Nonnull final String p0, @Nonnull final EnumResearchFlag p1);
    
    boolean hasResearchFlag(@Nonnull final String p0, @Nonnull final EnumResearchFlag p1);
    
    boolean addKnowledge(@Nonnull final EnumKnowledgeType p0, final ResearchCategory p1, final int p2);
    
    int getKnowledge(@Nonnull final EnumKnowledgeType p0, final ResearchCategory p1);
    
    int getKnowledgeRaw(@Nonnull final EnumKnowledgeType p0, final ResearchCategory p1);
    
    void sync(final EntityPlayerMP p0);
    
    public enum EnumResearchStatus
    {
        UNKNOWN, 
        COMPLETE, 
        IN_PROGRESS;
    }
    
    public enum EnumKnowledgeType
    {
        THEORY(32, true, "T"), 
        OBSERVATION(16, true, "O");
        
        private short progression;
        private boolean hasFields;
        private String abbr;
        
        private EnumKnowledgeType(final int progression, final boolean hasFields, final String abbr) {
            this.progression = (short)progression;
            this.hasFields = hasFields;
            this.abbr = abbr;
        }
        
        public int getProgression() {
            return this.progression;
        }
        
        public boolean hasFields() {
            return this.hasFields;
        }
        
        public String getAbbreviation() {
            return this.abbr;
        }
    }
    
    public enum EnumResearchFlag
    {
        PAGE, 
        RESEARCH, 
        POPUP;
    }
}
