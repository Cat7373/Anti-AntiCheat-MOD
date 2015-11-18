package com.lalameow.anticheat.common;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


@SideOnly(Side.CLIENT)
public class ClientProxy
  implements IGuiHandler
{
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    return null;
  }
  


  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    return null;
  }
}
