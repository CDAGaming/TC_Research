package thaumcraft.api.golems.seals;

public interface ISealConfigToggles
{
    SealToggle[] getToggles();
    
    void setToggle(final int p0, final boolean p1);
    
    public static class SealToggle
    {
        public boolean value;
        public String key;
        public String name;
        
        public SealToggle(final boolean value, final String key, final String name) {
            this.value = value;
            this.key = key;
            this.name = name;
        }
        
        public boolean getValue() {
            return this.value;
        }
        
        public void setValue(final boolean value) {
            this.value = value;
        }
        
        public String getKey() {
            return this.key;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
