package org.cat73.antianticheat.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;

import org.cat73.antianticheat.AntiAntiCheat;

public class PacketLister {
    @SubscribeEvent
    public void clientCustomPacketEvent(ClientCustomPacketEvent e) {
        String packet = AntiAntiCheat.getInstance().getMD5String();
        MPacketImpl mppacket = new MPacketImpl(packet);
        mppacket.sendClientMessage(packet);
    }
}
