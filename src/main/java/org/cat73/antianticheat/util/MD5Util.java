package org.cat73.antianticheat.util;

import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.Level;

public class MD5Util {
    private boolean isSearch = false;

    public String getMd5(File file) throws IOException, NoSuchAlgorithmException {
        FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
        MappedByteBuffer byteBuffer = inputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(byteBuffer);
        BigInteger bi = new BigInteger(1, md5.digest());
        String value = bi.toString(16);
        inputStream.close();
        return value;
    }

    public void researchfile(File file, boolean d) {
        if (d) {
            return;
        }
        if (file.isDirectory()) {
            File[] filearry = file.listFiles();
            for (File f : filearry) {
                if (f.getName().endsWith(".class")) {
                    FMLLog.log(Level.ERROR, "[%s] ,don't allow unpack mods jar ", f.getName());
                    this.isSearch = true;
                    return;
                }
                researchfile(f, this.isSearch);
            }
        }
    }

    public boolean isSearch() {
        return this.isSearch;
    }
}
