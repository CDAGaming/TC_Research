package thaumcraft.client.fx.particles;

import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class FXGenericP2P extends FXGeneric
{
    private double targetX;
    private double targetY;
    private double targetZ;
    
    public FXGenericP2P(final World world, final double x, final double y, final double z, final double xx, final double yy, final double zz) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.func_187115_a(0.1f, 0.1f);
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.targetX = xx;
        this.targetY = yy;
        this.targetZ = zz;
        final double dx = xx - this.field_187126_f;
        final double dy = yy - this.field_187127_g;
        final double dz = zz - this.field_187128_h;
        int base = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 3.0f);
        if (base < 1) {
            base = 1;
        }
        this.field_70547_e = base / 2 + this.field_187136_p.nextInt(base);
        this.field_187123_c = x;
        this.field_187124_d = y;
        this.field_187125_e = z;
        this.field_70548_b = 0.0f;
        this.field_70549_c = 0.0f;
        final float f3 = 0.01f;
        this.field_187129_i = (float)world.field_73012_v.nextGaussian() * f3;
        this.field_187130_j = (float)world.field_73012_v.nextGaussian() * f3;
        this.field_187131_k = (float)world.field_73012_v.nextGaussian() * f3;
        this.field_70545_g = 0.2f;
    }
    
    @Override
    public void func_189213_a() {
        super.func_189213_a();
        double dx = this.targetX - this.field_187126_f;
        double dy = this.targetY - this.field_187127_g;
        double dz = this.targetZ - this.field_187128_h;
        double d13 = 0.3;
        final double d14 = MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz);
        if (d14 < 4.0) {
            this.field_70544_f *= 0.9f;
            d13 = 0.6;
        }
        dx /= d14;
        dy /= d14;
        dz /= d14;
        this.field_187129_i += dx * d13;
        this.field_187130_j += dy * d13;
        this.field_187131_k += dz * d13;
        this.field_187129_i = MathHelper.func_76131_a((float)this.field_187129_i, -0.35f, 0.35f);
        this.field_187130_j = MathHelper.func_76131_a((float)this.field_187130_j, -0.35f, 0.35f);
        this.field_187131_k = MathHelper.func_76131_a((float)this.field_187131_k, -0.35f, 0.35f);
    }
}
