package com.lalameow.anticheat.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent.Post;

public class TextureCheck
{
  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void TextureStitchEvent(Post event)
  {
    ResourceLocation stoneBlocksTexture = new ResourceLocation("textures/blocks/stone.png");
    try {
      
      IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(stoneBlocksTexture);
      BufferedImage image = ImageIO.read(iresource.getInputStream());
      if (image.getRGB(image.getWidth() / 2, image.getHeight() / 2) >> 24 == 0) {
        // Minecraft.func_71410_x().func_147108_a(new GuiErrorScreen("121212", "123123123"));
        // Minecraft.func_71410_x().func_71404_a(new CrashReport("error", new Throwable("Don't allow Transparent Texture")));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
