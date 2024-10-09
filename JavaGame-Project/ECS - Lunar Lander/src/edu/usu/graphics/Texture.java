package edu.usu.graphics;

import org.lwjgl.system.MemoryStack;

import java.nio.*;

import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private final int textureId;
    private final int width;
    private final int height;

    public Texture(String texturePath) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer buffer = stbi_load(texturePath, w, h, channels, 4);
            if (buffer == null) {
                throw new RuntimeException("Image file [" + texturePath + "] not loaded: " + stbi_failure_reason());
            }

            this.width = w.get();
            this.height = h.get();

            this.textureId = createTexture(buffer);
            stbi_image_free(buffer);
        }
    }

    // --------------------------------------------------------------
    //
    // Builds a texture from a ByteBuffer.  The purpose for this constructor
    // is for the font rendering.  The FontTexture generates a ByteBuffer of
    // data based on the font being used, and this constructor then takes that
    // data and build a texture that can be used for text rendering.
    //
    // --------------------------------------------------------------
    public Texture(ByteBuffer imageBuffer) throws Exception {
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer buffer = stbi_load_from_memory(imageBuffer, w, h, channels, 4);
            if (buffer == null) {
                throw new Exception("Image file not loaded: " + stbi_failure_reason());
            }

            this.width = w.get();
            this.height = h.get();

            this.textureId = createTexture(buffer);
            stbi_image_free(buffer);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

    private int createTexture(ByteBuffer buffer) {
        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        return textureId;
    }

}
