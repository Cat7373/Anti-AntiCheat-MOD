package org.cat73.antianticheat.bukkit.handle;

import org.cat73.antianticheat.bukkit.pack.ModPackImpl;
import org.cat73.antianticheat.bukkit.pack.ModsPack;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class AntiCheatHandle extends FMLIndexedMessageToMessageCodec<ModsPack> {
    public AntiCheatHandle() {
        addDiscriminator(0, ModPackImpl.class);
    }

    public void encodeInto(ChannelHandlerContext ctx, ModsPack msg, ByteBuf target) throws Exception {
        msg.write(target);
    }

    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, ModsPack msg) {
        msg.read(source);
    }
}
