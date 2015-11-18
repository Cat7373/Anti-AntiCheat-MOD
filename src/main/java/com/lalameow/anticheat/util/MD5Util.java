package com.lalameow.anticheat.util;

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
  public String getMd5(File file)
    throws IOException, NoSuchAlgorithmException
  {
    FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
    MappedByteBuffer byteBuffer = inputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
    
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(byteBuffer);
    BigInteger bi = new BigInteger(1, md5.digest());
    String value = bi.toString(16);
    inputStream.close();
    return value; }
  
  private boolean isSearch = false;
  
  public void researchfile(File file, boolean d)
  {
    if (d) return;
    if (file.isDirectory()) {
      File[] filearry = file.listFiles();
      for (File f : filearry) {
        if (f.getName().endsWith(".class")) {
          FMLLog.log(Level.ERROR, "[%s] ,don't allow unpack mods jar ", new Object[] { f.getName() });
          this.isSearch = true;
          return;
        }
        researchfile(f, this.isSearch);
      }
    }
  }
  
  public boolean isSearch() { return this.isSearch; }
}
