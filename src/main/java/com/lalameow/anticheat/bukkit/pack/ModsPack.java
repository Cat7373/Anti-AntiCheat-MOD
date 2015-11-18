package com.lalameow.anticheat.bukkit.pack;

import io.netty.buffer.ByteBuf;

public abstract interface ModsPack
{
  public abstract void read(ByteBuf paramByteBuf);
  
  public abstract void write(ByteBuf paramByteBuf);
}
