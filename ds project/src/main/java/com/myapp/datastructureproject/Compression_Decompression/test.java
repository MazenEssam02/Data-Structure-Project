package com.myapp.datastructureproject.Compression_Decompression;

import java.io.IOException;

public class test {
     public static void main(String[] args) throws IOException {

Huffman ob1= new Huffman("C:\\Users\\MBR\\Documents\\DS\\test.txt");
Huffman ob2 = new Huffman();

Node c=ob1.Compress("<users>\r\n" + //
        "    <user>\r\n" + //
        "        <id>1</id>\r\n" + //
        "        <name>Ahmed Ali</name>\r\n" + //
        "        <posts>\r\n" + //
        "            <post>\r\n" + //
        "                <body>\r\n" + //
        "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n" + //
        "                </body>\r\n" + //
        "                <topics>\r\n" + //
        "                    <topic>\r\n" + //
        "                        economy\r\n" + //
        "                    </topic>\r\n" + //
        "                    <topic>\r\n" + //
        "                        finance\r\n" + //
        "                    </topic>\r\n" + //
        "                </topics>\r\n" + //
        "            </post>\r\n" + //
        "            <post>\r\n" + //
        "                <body>\r\n" + //
        "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n" + //
        "                </body>\r\n" + //
        "                <topics>\r\n" + //
        "                    <topic>\r\n" + //
        "                        solar_energy\r\n" + //
        "                    </topic>\r\n" + //
        "                </topics>\r\n" + //
        "            </post>\r\n" + //
        "        </posts>\r\n" + //
        "        <followers>\r\n" + //
        "            <follower>\r\n" + //
        "                <id>2</id>\r\n" + //
        "            </follower>\r\n" + //
        "            <follower>\r\n" + //
        "                <id>3</id>\r\n" + //
        "            </follower>\r\n" + //
        "        </followers>\r\n" + //
        "    </user>\r\n" + //
        "    <user>\r\n" + //
        "        <id>2</id>\r\n" + //
        "        <name>Yasser Ahmed</name>\r\n" + //
        "        <posts>\r\n" + //
        "            <post>\r\n" + //
        "                <body>\r\n" + //
        "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n" + //
        "                </body>\r\n" + //
        "                <topics>\r\n" + //
        "                    <topic>\r\n" + //
        "                        education\r\n" + //
        "                    </topic>\r\n" + //
        "                </topics>\r\n" + //
        "            </post>\r\n" + //
        "        </posts>\r\n" + //
        "        <followers>\r\n" + //
        "            <follower>\r\n" + //
        "                <id>1</id>\r\n" + //
        "            </follower>\r\n" + //
        "        </followers>\r\n" + //
        "    </user>\r\n" + //
        "    <user>\r\n" + //
        "        <id>3</id>\r\n" + //
        "        <name>Mohamed Sherif</name>\r\n" + //
        "        <posts>\r\n" + //
        "            <post>\r\n" + //
        "                <body>\r\n" + //
        "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\r\n" + //
        "                </body>\r\n" + //
        "                <topics>\r\n" + //
        "                    <topic>\r\n" + //
        "                        sports\r\n" + //
        "                    </topic>\r\n" + //
        "                </topics>\r\n" + //
        "            </post>\r\n" + //
        "        </posts>\r\n" + //
        "        <followers>\r\n" + //
        "            <follower>\r\n" + //
        "                <id>1</id>\r\n" + //
        "            </follower>\r\n" + //
        "        </followers>\r\n" + //
        "    </user>\r\n" + //
        "</users>");
        String s =ob2.decompress(c, "C:\\Users\\MBR\\Documents\\DS\\test.txt");
        System.out.println(s);
    }

}