package com.lalameow.anticheat.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import java.util.EnumMap;

@ChannelHandler.Sharable
public class PacketHandler
  extends FMLIndexedMessageToMessageCodec<MPacket>
{
  public EnumMap<Side, FMLEmbeddedChannel> channels;
  
  public void encodeInto(ChannelHandlerContext ctx, MPacket msg, ByteBuf target)
    throws Exception
  {
    msg.writeBytes(target);
  }
  

  public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, MPacket msg)
  {
    msg.readBytes(source);
  }
  

  public void perInitialise() {
    this.channels = NetworkRegistry.INSTANCE.newChannel("AntiCheatListerC", new ChannelHandler[] { this });
  }
  
  public void Initialise() {
    FMLCommonHandler.instance().bus().register(new PacketLister());
    NetworkRegistry.INSTANCE.newEventDrivenChannel("AntiCheatListerC").register(new PacketLister());
  }
  
  public void sendMessage(MPacket mpack) {}
}