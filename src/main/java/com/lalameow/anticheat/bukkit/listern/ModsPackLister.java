package com.lalameow.anticheat.bukkit.listern;

import com.lalameow.anticheat.bukkit.pack.ModPackImpl;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.relauncher.Side;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.cat73.antianticheat.AntiAntiCheat;

public class ModsPackLister {
    protected static final char[] flag = "0123456789ABCDEF".toCharArray();

    public static String a(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[(j * 2)] = flag[(v >>> 4)];
            hexChars[(j * 2 + 1)] = flag[(v & 0xF)];
        }
        return new String(hexChars);
    }

    @SubscribeEvent
    public void connectionSever(ClientCustomPacketEvent event) {
        String md5sString = "";
        for (String md5 : AntiAntiCheat.getInstance().md5List()) {
            md5sString = md5sString + md5 + ",";
        }
        byte[] k = new byte[8];
        int j = 0;
        for (byte b : event.packet.payload().array()) {
            if (b != 0) {
                k[j] = b;
                j++;
            }
        }

        SecretKey secretKey = null;
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(k);
            _generator.init(128, secureRandom);
            secretKey = _generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        IvParameterSpec ivSpec = new IvParameterSpec(event.packet.payload().array(), 0, 8);

        AntiAntiCheat.channelmap.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        AntiAntiCheat.channelmap.get(Side.CLIENT).writeOutbound(new ModPackImpl(md5sString, secretKey, ivSpec));
  }
}
