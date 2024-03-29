package com.myapp.datastructureproject.Compression_Decompression;

import java.io.*;
public  class File {

    public static void writeToFile(String binary, String filePath) throws IOException {
        FileOutputStream fileOS = new FileOutputStream(filePath );
        DataOutputStream dataOS = new DataOutputStream(fileOS);
        byte flag = 0;
        byte toByte = 0;
        byte count = 7;
        byte remainder = (byte) (binary.length() % 8);
        if (remainder != 0) {
            flag = (byte) (8 - remainder);
        }
        dataOS.writeByte(flag);
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                toByte |= (1 << count);
            }
            if (count == 0) {
                dataOS.writeByte(toByte);
                toByte = 0;
                count = 8;
            }
            count--;
        }
        dataOS.writeByte(toByte);
        dataOS.close();
    }
    public static String readFromFile(String filePath) throws IOException {
        FileInputStream fileIS = new FileInputStream(filePath);
        DataInputStream dataIS = new DataInputStream(fileIS);
        StringBuilder encoded = new StringBuilder();
        char[] read = new char[dataIS.available()];
        int count = 0;
        while (dataIS.available() > 0) {
            read[count++] = (char) dataIS.readByte();
        }
        dataIS.close();
        char flag = read[0];
        for (int i = 1; i < count; i++) {
            encoded.append(charToBinaryString(read[i]));
        }
        if (flag != 0) {

            encoded.delete(encoded.length() - flag, encoded.length());
        }
        return encoded.toString();
    }
    public static StringBuilder charToBinaryString(char c) {
        StringBuilder result = new StringBuilder();
        String binary = Integer.toBinaryString(c & 0xFF);
        for (int i = 0; i < (8 - binary.length()); i++) {
            result.append('0');
        }
        result.append(binary);
        return result;
    }

 
}