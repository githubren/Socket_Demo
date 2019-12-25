package com.example.y.socket_demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class StreamTool {

    public static void save(File file,byte[] data) throws Exception{
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data);
        outputStream.close();
    }

    public static String readLine(PushbackInputStream in) throws IOException{
        char buff[] = new char[128];
        int room = buff.length;
        int offset = 0;
        int c;
        while (true){
            switch (c = in.read()){
                case  -1:
                case '\n':
                    break;
                case '\r':
                    int c2 = in.read();
                    if ((c2 != '\n') && (c2 != -1))
                        in.unread(c2);
                    break;
                default:
                    if (--room < 0) {
                        char[] lineBuffer = buff;
                        buff = new char[offset+128];
                        room = buff.length - offset - 1;
                        System.arraycopy(lineBuffer,0,buff,0,offset);
                    }
                    buff[offset++] = (char) c;
                    break;
            }
            if ((c == -1) && (offset == 0)) return null;
            return String.copyValueOf(buff,0,offset);
        }
    }

    public static byte[] readStream(InputStream inStream)throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        inStream.close();
        return outputStream.toByteArray();
    }
}
