package thaumcraft.client.lib.ender;

import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import java.io.*;

public final class ShaderHelper
{
    private static final int VERT = 35633;
    private static final int FRAG = 35632;
    private static final String PREFIX = "/assets/thaumcraft/shader/";
    public static int endShader;
    
    public static void initShaders() {
        if (!useShaders()) {
            return;
        }
        ShaderHelper.endShader = createProgram("ender.vert", "ender.frag");
    }
    
    public static void useShader(final int shader, final ShaderCallback callback) {
        if (!useShaders()) {
            return;
        }
        ARBShaderObjects.glUseProgramObjectARB(shader);
        if (shader != 0) {
            final int time = ARBShaderObjects.glGetUniformLocationARB(shader, (CharSequence)"time");
            ARBShaderObjects.glUniform1iARB(time, Minecraft.func_71410_x().func_175606_aa().field_70173_aa);
            if (callback != null) {
                callback.call(shader);
            }
        }
    }
    
    public static void useShader(final int shader) {
        useShader(shader, null);
    }
    
    public static void releaseShader() {
        useShader(0);
    }
    
    public static boolean useShaders() {
        return !ModConfig.CONFIG_GRAPHICS.disableShaders && OpenGlHelper.field_148824_g;
    }
    
    private static int createProgram(final String vert, final String frag) {
        int vertId = 0;
        int fragId = 0;
        int program = 0;
        if (vert != null) {
            vertId = createShader("/assets/thaumcraft/shader/" + vert, 35633);
        }
        if (frag != null) {
            fragId = createShader("/assets/thaumcraft/shader/" + frag, 35632);
        }
        program = ARBShaderObjects.glCreateProgramObjectARB();
        if (program == 0) {
            return 0;
        }
        if (vert != null) {
            ARBShaderObjects.glAttachObjectARB(program, vertId);
        }
        if (frag != null) {
            ARBShaderObjects.glAttachObjectARB(program, fragId);
        }
        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, 35714) == 0) {
            return 0;
        }
        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, 35715) == 0) {
            return 0;
        }
        return program;
    }
    
    private static int createShader(final String filename, final int shaderType) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            if (shader == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB(shader, (CharSequence)readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);
            if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
            }
            return shader;
        }
        catch (Exception e) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            e.printStackTrace();
            return -1;
        }
    }
    
    private static String getLogInfo(final int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
    }
    
    private static String readFileAsString(final String filename) throws Exception {
        final StringBuilder source = new StringBuilder();
        final InputStream in = ShaderHelper.class.getResourceAsStream(filename);
        Exception exception = null;
        if (in == null) {
            return "";
        }
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Exception innerExc = null;
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    source.append(line).append('\n');
                }
            }
            catch (Exception exc) {
                exception = exc;
                try {
                    reader.close();
                }
                catch (Exception exc) {
                    if (innerExc == null) {
                        innerExc = exc;
                    }
                    else {
                        exc.printStackTrace();
                    }
                }
            }
            finally {
                try {
                    reader.close();
                }
                catch (Exception exc2) {
                    if (innerExc == null) {
                        innerExc = exc2;
                    }
                    else {
                        exc2.printStackTrace();
                    }
                }
            }
            if (innerExc != null) {
                throw innerExc;
            }
        }
        catch (Exception exc3) {
            exception = exc3;
        }
        finally {
            try {
                in.close();
            }
            catch (Exception exc4) {
                if (exception == null) {
                    exception = exc4;
                }
                else {
                    exc4.printStackTrace();
                }
            }
            if (exception != null) {
                throw exception;
            }
        }
        return source.toString();
    }
    
    static {
        ShaderHelper.endShader = 0;
    }
}
