package com.lalameow.anticheat.network;

import io.netty.buffer.ByteBuf;

public abstract interface MPacket
{
  public abstract void readBytes(ByteBuf paramByteBuf);
  
  public abstract void writeBytes(ByteBuf paramByteBuf);
}
