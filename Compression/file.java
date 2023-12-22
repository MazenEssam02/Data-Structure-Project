package Compression;
import java.nio.file.Path;
import java.io.*;
import java.nio.file.Path;

public class file {

    public static void writeBinaryToFile(String binary, String filePath) throws IOException {
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
            // everytime you form 8 bits write them as a byte to the file
            if (count == 0) {
                dataOS.writeByte(toByte);
                toByte = 0;
                count = 8;
            }
            count--;
        }
        // the last byte will be padded by zeros equal to the flag value
        dataOS.writeByte(toByte);
        dataOS.close();
    }
    public static void main(String[] args) {
        System.out.println("sara");
    }
}