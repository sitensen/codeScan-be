package com.codescan.admin.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class ZipUtils {
    @Value("${zip.filepath}")
    private String filePath;

    public void unzipFile(String file) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file),Charset.forName("GBK"));
        ZipEntry entry;
        while((entry = zipInputStream.getNextEntry()) != null){
            if(!entry.isDirectory()){
                FileOutputStream out = new FileOutputStream( filePath + entry.getName());
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int len;
                byte[] buf = new byte[1024];
                while((len = zipInputStream.read(buf)) != -1){
                    bos.write(buf,0,len);
                }
                bos.close();
                out.close();
            }
        }
    }
}
