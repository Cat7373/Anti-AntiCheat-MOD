package org.cat73.antianticheat;

import io.netty.channel.ChannelHandler;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.lalameow.anticheat.bukkit.handle.AntiCheatHandle;
import com.lalameow.anticheat.bukkit.listern.ModsPackLister;
import com.lalameow.anticheat.network.PacketHandler;
import com.lalameow.anticheat.network.TextureCheck;
import com.lalameow.anticheat.util.MD5Util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
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
    
    public static AntiAntiCheat self;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        self = this;
        
        channelmap = NetworkRegistry.INSTANCE.newChannel("LALAACM", new ChannelHandler[] { new AntiCheatHandle() });
        
        packetHandler.perInitialise();
        this.config = new Configuration(new File(String.format("%s/%s.cfg", new Object[] { event.getModConfigurationDirectory(), "antiantiCheat" })));
        MinecraftForge.EVENT_BUS.register(new TextureCheck());
        
        FMLLog.info("preInit over");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLEventChannel networkEvent = NetworkRegistry.INSTANCE.newEventDrivenChannel("LALAACM");
        
        networkEvent.register(new ModsPackLister());
        packetHandler.Initialise();
        
        FMLLog.info("Init over");
    }
    
    public List<String> md5List() {
        // 读取配置
        config.load();
        
        // 获取MOD列表
        List<ModContainer> mods = Loader.instance().getModList();
        
        // 获取MOD的MD5列表
        List<String> md5List = new ArrayList<String>();
        MD5Util md5Util = new MD5Util();
        for (ModContainer mod : mods) {
            try {
                if (!mod.getModId().equalsIgnoreCase(AntiAntiCheat.MODID)) {
                    File modFile = mod.getSource();
                    String modMD5 = md5Util.getMd5(modFile);
                    FMLLog.log(Level.INFO, "%s modMd5:%s", new Object[] { mod.getName(), modMD5 });
                    md5List.add(modMD5);
                    break;
                }
            } catch (NoSuchAlgorithmException e) {
                FMLLog.log(Level.ERROR, "%s:%s", new Object[] { mod.getName(), e });
            } catch (IOException e) {
                md5List.add(mod.getName());
            }
        }
        md5List.add(this.selfMd5);
        
        // 如果配置里没有MD5列表则存入配置
        String[] md5String = config.getStringList("MD5List", "ClientConfig", md5List.toArray(new String[md5List.size()]), "MD5 list");
        this.config.save();
        
        // 重新读MD5列表
        md5List.clear();
        for (int i = 0; i < md5String.length; i++) {
            md5List.add(md5String[i]);
        }
        
        // 输出MD5列表
        if (md5String != null) {
            FMLLog.info("client md5Lis :");
            for (String string : md5List) {
                FMLLog.info("%s", new Object[] { string });
            }
        }
        
        return md5List;
    }
}
