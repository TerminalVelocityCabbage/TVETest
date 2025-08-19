package com.terminalvelocitycabbage.game.client;

import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import static org.lwjgl.util.freetype.FreeType.*;

/**
 * LWJGL translation of FreeType tutorial example3.cpp (bitmap path)
 *
 * Key points:
 *  - Uses real LWJGL struct wrappers: FT_Face, FT_GlyphSlot, FT_Bitmap, FT_Size_Metrics, FT_Vector.
 *  - Create struct views with FT_Face.create(pointer), etc.
 *  - Renders by FT_Load_Glyph(..., FT_LOAD_RENDER) and composites the FT_Bitmap into a grayscale image.
 *  - Handles kerning with FT_Get_Kerning and 26.6 fixed-point conversion.
 */
public class FTExample3Java {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java FTExample3Java <font-file> [text] [pxSize] [out.png]");
            return;
        }
        String fontPath = args[0];
        String text = args.length > 1 ? args[1] : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`1234567890-=~!@#$%^&*()_+{}[]:\";',./<>?'";
        int pixelSize = args.length > 2 ? Integer.parseInt(args[2]) : 256;
        String outPath = args.length > 3 ? args[3] : "example3-lwjgl.png";

        Configuration.HARFBUZZ_LIBRARY_NAME.set(FreeType.getLibrary());

        File fontFile = new File(fontPath);
        if (!fontFile.exists()) {
            throw new IllegalArgumentException("Font file not found: " + fontFile.getAbsolutePath());
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Init FreeType
            var libPtr = stack.mallocPointer(1);
            check(FT_Init_FreeType(libPtr), "FT_Init_FreeType");
            long library = libPtr.get(0);

            // Load face (use MemoryUtil to pass a native string)
            var facePtr = stack.mallocPointer(1);
            ByteBuffer fontPathBuffer = MemoryUtil.memUTF8(fontPath);
            check(FT_New_Face(library, fontPathBuffer, 0, facePtr), "FT_New_Face");
            FT_Face face = FT_Face.create(facePtr.get(0)); // <-- struct view from pointer

            // Set pixel size (y pixels)
            check(FT_Set_Pixel_Sizes(face, 0, pixelSize), "FT_Set_Pixel_Sizes");

            // Metrics (26.6 fixed point)
            FT_Size_Metrics m = face.size().metrics();
            int ascenderPx = frac26_6_to_px((int) m.ascender());
            int descenderPx = frac26_6_to_px((int) -m.descender()); // descender is negative
            int lineHeightPx = Math.max(1, frac26_6_to_px((int) m.height()));

            // Measure text width (with kerning)
            int widthPx = 0;
            int prevGlyph = 0;
            boolean hasKerning = FT_HAS_KERNING(face);
            for (int i = 0; i < text.length(); i++) {
                int cp = text.codePointAt(i);
                if (Character.isHighSurrogate(text.charAt(i))) i++;
                int glyphIndex = FT_Get_Char_Index(face, cp);
                if (glyphIndex == 0) continue; // missing glyph, skip

                if (hasKerning && prevGlyph != 0) {
                    FT_Vector kern = FT_Vector.malloc(stack);
                    check(FT_Get_Kerning(face, prevGlyph, glyphIndex, FT_KERNING_DEFAULT, kern), "FT_Get_Kerning");
                    widthPx += frac26_6_to_px((int) kern.x());
                }

                check(FT_Load_Glyph(face, glyphIndex, FT_LOAD_DEFAULT), "FT_Load_Glyph");
                FT_GlyphSlot slot = face.glyph();
                widthPx += frac26_6_to_px((int) slot.advance().x());
                prevGlyph = glyphIndex;
            }

            int margin = Math.max(4, pixelSize / 4);
            int imgW = Math.max(1, widthPx + 2 * margin);
            int imgH = Math.max(1, lineHeightPx + 2 * margin);

            byte[] img = new byte[imgW * imgH]; // grayscale target
            int originX = margin;
            int originY = margin + ascenderPx; // baseline from top

            // Render & composite
            prevGlyph = 0;
            int penX = 0;
            for (int i = 0; i < text.length(); i++) {
                int cp = text.codePointAt(i);
                if (Character.isHighSurrogate(text.charAt(i))) i++;
                int glyphIndex = FT_Get_Char_Index(face, cp);
                if (glyphIndex == 0) continue;

                if (hasKerning && prevGlyph != 0) {
                    FT_Vector kern = FT_Vector.malloc(stack);
                    check(FT_Get_Kerning(face, prevGlyph, glyphIndex, FT_KERNING_DEFAULT, kern), "FT_Get_Kerning");
                    penX += frac26_6_to_px((int) kern.x());
                }

                check(FT_Load_Glyph(face, glyphIndex, FT_LOAD_RENDER), "FT_Load_Glyph");
                FT_GlyphSlot slot = face.glyph();
                FT_Bitmap bmp = slot.bitmap();

                int bw = bmp.width();
                int bh = bmp.rows();
                int pitch = Math.abs(bmp.pitch());
                int bufSize = pitch * bh;
                ByteBuffer gbuf = bmp.buffer(bufSize);

                int left = slot.bitmap_left();
                int top = slot.bitmap_top();

                for (int y = 0; y < bh; y++) {
                    int dstY = originY - top + y;
                    if (dstY < 0 || dstY >= imgH) continue;
                    int rowOfs = dstY * imgW;
                    int srcRow = y * pitch;
                    for (int x = 0; x < bw; x++) {
                        int dstX = originX + penX + left + x;
                        if (dstX < 0 || dstX >= imgW) continue;
                        int src = gbuf.get(srcRow + x) & 0xFF;
                        if (src == 0) continue;
                        int idx = rowOfs + dstX;
                        int dst = img[idx] & 0xFF;
                        int blended = dst + src;
                        img[idx] = (byte) (blended > 255 ? 255 : blended);
                    }
                }

                penX += frac26_6_to_px((int) slot.advance().x());
                prevGlyph = glyphIndex;
            }

            // Write PNG
            BufferedImage bi = new BufferedImage(imgW, imgH, BufferedImage.TYPE_BYTE_GRAY);
            int k = 0;
            for (int y = 0; y < imgH; y++) {
                for (int x = 0; x < imgW; x++) {
                    int v = img[k++] & 0xFF;
                    int rgb = (v << 16) | (v << 8) | v;
                    bi.setRGB(x, y, rgb);
                }
            }
            ImageIO.write(bi, "PNG", new File(outPath));
            System.out.printf("Wrote %s (%,dx%d) using %s at %dpx\n", outPath, imgW, imgH, new File(fontPath).getName(), pixelSize);

            FT_Done_Face(face);
            FT_Done_FreeType(library);
        }
    }

    private static int frac26_6_to_px(int v26_6) {
        return (v26_6 + 32) >> 6;
    }

    private static void check(int error, String where) {
        if (error != 0) throw new RuntimeException(where + " failed: FT_Error=" + error);
    }
}

