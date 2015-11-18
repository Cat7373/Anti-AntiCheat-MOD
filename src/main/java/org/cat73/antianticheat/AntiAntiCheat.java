package org.cat73.antianticheat;

import io.netty.channel.ChannelHandler;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.lalameow.anticheat.bukkit.handle.AntiCheatHandle;
import com.lalameow.anticheat.bukkit.listern.ModsPackLister;
import com.lalameow.anticheat.network.PacketHandler;
import com.lalameow.anticheat.util.MD5Util;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = AntiAntiCheat.MODID, version = AntiAntiCheat.VERSION)
public class AntiAntiCheat {
    public static final String MODID = "AntiCheat";
    public static final String VERSION = "1.0";

    public static EnumMap<Side, FMLEmbeddedChannel> channelmap;
    public static final PacketHandler packetHandler = new PacketHandler();
    private Configuration config;
    private String selfMd5 = "b004abfb2a019a6563b51bfae6456a92";

    private static AntiAntiCheat instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;

        channelmap = NetworkRegistry.INSTANCE.newChannel("LALAACM", new ChannelHandler[] { new AntiCheatHandle() });

        packetHandler.perInitialise();
        this.config = new Configuration(new File(String.format("%s/%s.cfg", event.getModConfigurationDirectory(), "antiantiCheat")));

        FMLLog.info("preInit over");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLEventChannel networkEvent = NetworkRegistry.INSTANCE.newEventDrivenChannel("LALAACM");

        networkEvent.register(new ModsPackLister());
        packetHandler.Initialise();

        FMLLog.info("Init over");
    }

    public String[] md5List() {
        // 获取MOD的MD5列表
        HashMap<String, String> mods = getMods();
        String[] md5List = mods.values().toArray(new String[mods.size() + 1]);
        md5List[md5List.length - 1] = this.selfMd5;

        // 读取配置
        config.load();

        // 保存配置
        md5List = config.getStringList("MD5List", "ClientConfig", md5List, "MD5 list");
        this.config.save();

        // 输出MD5列表
        if (md5List != null) {
            FMLLog.info("client md5Lis :");
            for (String string : md5List) {
                FMLLog.info("%s", string);
            }
        }

        return md5List;
    }

    private HashMap<String, String> getMods() {
        HashMap<String, String> md5Map = new HashMap<String, String>();
        MD5Util md5Util = new MD5Util();
        try {
            Class<?> minecraftClass = Class.forName("net.minecraft.client.main.Main");

            String path = minecraftClass.getProtectionDomain().getCodeSource().getLocation().getPath().split("!")[0];
            path = URLDecoder.decode(path, "UTF-8");
            path = path.substring(6);
            FMLLog.info("ClassPath---%s", path);
            File jarFile = new File(path);
            String md5 = md5Util.getMd5(jarFile);
            FMLLog.info("jar---%s:%s", jarFile.getName(), md5);
            md5Map.put(jarFile.getName(), md5);
        } catch (ClassNotFoundException e) {
            FMLLog.log(Level.ERROR, "%s:%s", "GameJarERROR", e);
        } catch (NoSuchAlgorithmException e) {
            FMLLog.log(Level.ERROR, "%s:%s", "GameJarERROR", e);
        } catch (IOException e) {
            FMLLog.log(Level.ERROR, "%s:%s", "GameJarERROR", e);
        }

        List<ModContainer> mods = Loader.instance().getModList();
        for (ModContainer mod : mods) {
            File modFile = mod.getSource();
            try {
                if ((mod.getName().equals("Forge Mod Loader")) || (mod.getName().equals("Minecraft Forge")) || (mod.getName().equals("Minecraft Coder Pack"))) {
                    continue;
                }
                String modMD5 = md5Util.getMd5(modFile);
                FMLLog.info("modjar----%s:%s", mod.getName(), modMD5);
                md5Map.put(mod.getName(), modMD5);
            } catch (NoSuchAlgorithmException e) {
                FMLLog.log(Level.ERROR, "%s:%s", mod.getName(), e);
                continue;
            } catch (IOException e) {
                FMLLog.log(Level.ERROR, "%s:%s", mod.getName(), e);
                md5Util.researchfile(modFile, false);
            }
        }
        return md5Map;
    }
    
    public static AntiAntiCheat getInstance() {
        return instance;
    }
}
