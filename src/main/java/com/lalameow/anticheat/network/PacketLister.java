package com.lalameow.anticheat.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;

import org.cat73.antianticheat.AntiAntiCheat;

import net.minecraft.util.StringUtils;

public class PacketLister {
    @SubscribeEvent
    public void clientCustomPacketEvent(ClientCustomPacketEvent e) {
        String packet = "";
        for (String string : AntiAntiCheat.self.md5List()) {
            packet = packet + string + ",";
        }
        if (!StringUtils.isNullOrEmpty(packet)) {
            packet = packet.substring(0, packet.length() - 1);
        }
        MPacketImpl mppacket = new MPacketImpl(packet);
        mppacket.sendClientMessage(packet);
    }
}
