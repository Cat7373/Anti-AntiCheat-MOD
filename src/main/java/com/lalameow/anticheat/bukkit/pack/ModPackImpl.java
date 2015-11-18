package com.lalameow.anticheat.bukkit.pack;

import io.netty.buffer.ByteBuf;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ModPackImpl implements ModsPack {
    private String modsMd5;
    private SecretKey secretKey;

    public ModPackImpl(String md5s, SecretKey key, IvParameterSpec ivP) {
        this.modsMd5 = md5s;
        this.secretKey = key;
    }

    public void read(ByteBuf paramByteBuf) {
    }

    public void write(ByteBuf paramByteBuf) {
        try {
            byte[] enCodeFormat = this.secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, key);
            byte[] result = cipher.doFinal(this.modsMd5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                String hex = Integer.toHexString(result[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            for (byte b : sb.toString().getBytes()) {
                paramByteBuf.writeByte(b);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }
}
