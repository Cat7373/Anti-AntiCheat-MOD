package org.cat73.antianticheat.bukkit.pack;

import io.netty.buffer.ByteBuf;

public abstract interface ModsPack {
    public abstract void read(ByteBuf paramByteBuf);
  
    public abstract void write(ByteBuf paramByteBuf);
}
