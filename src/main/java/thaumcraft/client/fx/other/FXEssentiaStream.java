package thaumcraft.client.fx.other;

import net.minecraft.client.particle.*;
import com.sasmaster.glelwjgl.java.*;
import thaumcraft.codechicken.lib.vec.*;
import net.minecraft.world.*;
import thaumcraft.common.blocks.essentia.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.math.*;
import java.awt.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import thaumcraft.client.fx.*;
import java.util.*;

public class FXEssentiaStream extends Particle
{
    private double targetX;
    private double targetY;
    private double targetZ;
    private double startX;
    private double startY;
    private double startZ;
    private int count;
    public int length;
    private String key;
    private BlockPos startPos;
    private BlockPos endPos;
    static HashMap<String, FXEssentiaStream> pt;
    CoreGLE gle;
    private static final ResourceLocation TEX0;
    int layer;
    double[][] points;
    float[][] colours;
    double[] radii;
    int growing;
    ArrayList<Quat> vecs;
    
    public FXEssentiaStream(final World w, final double par2, final double par4, final double par6, final double tx, final double ty, final double tz, final int count, final int color, final float scale, final int extend, final double my) {
        super(w, par2, par4, par6, 0.0, 0.0, 0.0);
        this.count = 0;
        this.length = 20;
        this.key = "";
        this.startPos = null;
        this.endPos = null;
        this.gle = new CoreGLE();
        this.layer = 1;
        this.growing = -1;
        this.vecs = new ArrayList<Quat>();
        this.field_70544_f = (float)(scale * (1.0 + this.field_187136_p.nextGaussian() * 0.15000000596046448));
        this.length = Math.max(20, extend);
        this.count = count;
        this.targetX = tx;
        this.targetY = ty;
        this.targetZ = tz;
        final BlockPos bp1 = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
        final BlockPos bp2 = new BlockPos(this.targetX, this.targetY, this.targetZ);
        final IBlockState bs = w.func_180495_p(bp1);
        if (bs.func_177230_c() instanceof BlockEssentiaTransport) {
            final EnumFacing f = BlockStateUtils.getFacing(bs);
            this.field_187126_f += f.func_82601_c() * 0.05f;
            this.field_187127_g += f.func_96559_d() * 0.05f;
            this.field_187128_h += f.func_82599_e() * 0.05f;
        }
        final double dx = tx - this.field_187126_f;
        final double dy = ty - this.field_187127_g;
        final double dz = tz - this.field_187128_h;
        int base = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 21.0f);
        if (base < 1) {
            base = 1;
        }
        this.field_70547_e = base;
        final String k = bp1.func_177986_g() + "" + bp2.func_177986_g() + "" + color;
        if (FXEssentiaStream.pt.containsKey(k)) {
            final FXEssentiaStream trail2 = FXEssentiaStream.pt.get(k);
            if (!trail2.field_187133_m && trail2.vecs.size() < trail2.length) {
                final FXEssentiaStream fxEssentiaStream = trail2;
                fxEssentiaStream.length += Math.max(extend, 5);
                final FXEssentiaStream fxEssentiaStream2 = trail2;
                fxEssentiaStream2.field_70547_e += Math.max(extend, 5);
                this.field_70547_e = 0;
            }
        }
        if (this.field_70547_e > 0) {
            FXEssentiaStream.pt.put(k, this);
            this.key = k;
        }
        this.field_187129_i = MathHelper.func_76126_a(count / 4.0f) * 0.015f;
        this.field_187130_j = my + MathHelper.func_76126_a(count / 3.0f) * 0.015f;
        this.field_187131_k = MathHelper.func_76126_a(count / 2.0f) * 0.015f;
        final Color c = new Color(color);
        this.field_70552_h = c.getRed() / 255.0f;
        this.field_70553_i = c.getGreen() / 255.0f;
        this.field_70551_j = c.getBlue() / 255.0f;
        this.field_70545_g = 0.2f;
        this.vecs.add(new Quat(0.0, 0.0, 0.0, 0.001));
        this.vecs.add(new Quat(0.0, 0.0, 0.0, 0.001));
        this.startX = this.field_187126_f;
        this.startY = this.field_187127_g;
        this.startZ = this.field_187128_h;
        this.startPos = new BlockPos(this.startX, this.startY, this.startZ);
        this.endPos = bp2;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        final double ePX = this.startX - FXEssentiaStream.field_70556_an;
        final double ePY = this.startY - FXEssentiaStream.field_70554_ao;
        final double ePZ = this.startZ - FXEssentiaStream.field_70555_ap;
        GL11.glTranslated(ePX, ePY, ePZ);
        if (this.points != null && this.points.length > 2) {
            Minecraft.func_71410_x().field_71446_o.func_110577_a(FXEssentiaStream.TEX0);
            this.gle.set_POLYCYL_TESS(8);
            this.gle.set__ROUND_TESS_PIECES(1);
            this.gle.gleSetJoinStyle(1042);
            this.gle.glePolyCone(this.points.length, this.points, this.colours, this.radii, 0.075f, (this.growing < 0) ? 0.0f : (0.075f * (this.field_70546_d - this.growing + f)));
        }
        GL11.glPopMatrix();
        Minecraft.func_71410_x().field_71446_o.func_110577_a(ParticleEngine.particleTexture);
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
    }
    
    public void setFXLayer(final int l) {
        this.layer = l;
    }
    
    public int func_70537_b() {
        return this.layer;
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        if (this.field_70546_d++ >= this.field_70547_e || this.length < 1) {
            this.func_187112_i();
            if (FXEssentiaStream.pt.containsKey(this.key) && FXEssentiaStream.pt.get(this.key).field_187133_m) {
                FXEssentiaStream.pt.remove(this.key);
            }
            return;
        }
        this.field_187130_j += 0.01 * this.field_70545_g;
        this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
        this.field_187129_i *= 0.985;
        this.field_187130_j *= 0.985;
        this.field_187131_k *= 0.985;
        this.field_187129_i = MathHelper.func_76131_a((float)this.field_187129_i, -0.05f, 0.05f);
        this.field_187130_j = MathHelper.func_76131_a((float)this.field_187130_j, -0.05f, 0.05f);
        this.field_187131_k = MathHelper.func_76131_a((float)this.field_187131_k, -0.05f, 0.05f);
        double dx = this.targetX - this.field_187126_f;
        double dy = this.targetY - this.field_187127_g;
        double dz = this.targetZ - this.field_187128_h;
        final double d13 = 0.01;
        final double d14 = MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz);
        dx /= d14;
        dy /= d14;
        dz /= d14;
        this.field_187129_i += dx * (d13 / Math.min(1.0, d14));
        this.field_187130_j += dy * (d13 / Math.min(1.0, d14));
        this.field_187131_k += dz * (d13 / Math.min(1.0, d14));
        float scale = this.field_70544_f * (0.75f + MathHelper.func_76126_a((this.count + this.field_70546_d) / 2.0f) * 0.25f);
        if (d14 < 1.0) {
            final float f = MathHelper.func_76126_a((float)(d14 * 1.5707963267948966));
            scale *= f;
            this.field_70544_f *= f;
        }
        if (this.field_70544_f > 0.001) {
            this.vecs.add(new Quat(scale, this.field_187126_f - this.startX, this.field_187127_g - this.startY, this.field_187128_h - this.startZ));
        }
        else {
            if (this.growing < 0) {
                this.growing = this.field_70546_d;
            }
            --this.length;
            FXDispatcher.INSTANCE.essentiaDropFx(this.targetX + this.field_187136_p.nextGaussian() * 0.07500000298023224, this.targetY + this.field_187136_p.nextGaussian() * 0.07500000298023224, this.targetZ + this.field_187136_p.nextGaussian() * 0.07500000298023224, this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.5f);
        }
        if (this.vecs.size() > this.length) {
            this.vecs.remove(0);
        }
        this.points = new double[this.vecs.size()][3];
        this.colours = new float[this.vecs.size()][4];
        this.radii = new double[this.vecs.size()];
        int c = this.vecs.size();
        for (final Quat v : this.vecs) {
            --c;
            final float variance = 1.0f + MathHelper.func_76126_a((c + this.field_70546_d) / 3.0f) * 0.2f;
            final float xx = MathHelper.func_76126_a((c + this.field_70546_d) / 6.0f) * 0.03f;
            final float yy = MathHelper.func_76126_a((c + this.field_70546_d) / 7.0f) * 0.03f;
            final float zz = MathHelper.func_76126_a((c + this.field_70546_d) / 8.0f) * 0.03f;
            this.points[c][0] = v.x + xx;
            this.points[c][1] = v.y + yy;
            this.points[c][2] = v.z + zz;
            this.radii[c] = v.s * variance;
            if (c > this.vecs.size() - 10) {
                final double[] radii = this.radii;
                final int n = c;
                radii[n] *= MathHelper.func_76134_b((float)((c - (this.vecs.size() - 12)) / 10.0f * 1.5707963267948966));
            }
            if (c == 0) {
                this.radii[c] = 0.0;
            }
            else if (c == 1) {
                this.radii[c] = 0.0;
            }
            else if (c == 2) {
                this.radii[c] = (this.field_70544_f * 0.5 + this.radii[c]) / 2.0;
            }
            else if (c == 3) {
                this.radii[c] = (this.field_70544_f + this.radii[c]) / 2.0;
            }
            else if (c == 4) {
                this.radii[c] = (this.field_70544_f + this.radii[c] * 2.0) / 3.0;
            }
            final float v2 = 1.0f - MathHelper.func_76126_a((c + this.field_70546_d) / 2.0f) * 0.1f;
            this.colours[c][0] = this.field_70552_h * v2;
            this.colours[c][1] = this.field_70553_i * v2;
            this.colours[c][2] = this.field_70551_j * v2;
            this.colours[c][3] = 1.0f;
        }
        if (this.vecs.size() > 2 && this.field_187136_p.nextBoolean()) {
            int q = this.field_187136_p.nextInt(3);
            if (this.field_187136_p.nextBoolean()) {
                q = this.vecs.size() - 2;
            }
            FXDispatcher.INSTANCE.essentiaDropFx(this.vecs.get(q).x + this.startX, this.vecs.get(q).y + this.startY, this.vecs.get(q).z + this.startZ, this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.5f);
        }
    }
    
    public void setGravity(final float value) {
        this.field_70545_g = value;
    }
    
    static {
        FXEssentiaStream.pt = new HashMap<String, FXEssentiaStream>();
        TEX0 = new ResourceLocation("thaumcraft", "textures/misc/essentia.png");
    }
}
