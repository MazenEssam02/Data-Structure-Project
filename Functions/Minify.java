import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Minify {
    
    Minify() throws FileNotFoundException {

    }

    public static String to_Minify(String s)
    {
        String x = "";
        s=s.replaceAll(">\n",">");
        s=s.replaceAll("\n<","<");
        s=s.replaceAll("\n","");
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' ')
                x += s.charAt(i);
            
        }
        return x;
    }

    
    public static String minifyKeepingSpaces(String file) {
        StringBuilder file_after_minify= new StringBuilder();
        String[] lines = splite_the_XML_file(file);
        for (int i =0 ; i<lines.length ; i++){
            
    if (lines[i].trim().length()==0)
    {
       continue;
    }
      lines[i] = lines[i].trim();
      file_after_minify.append(lines[i].trim());
    }
    return file_after_minify.toString();
        
    }

    public static String[] splite_the_XML_file(String File){
        String[] lines;
        File =File.trim().replaceAll("<", "\n<").replaceAll(">", ">\n");
        lines = File.split("\n");
        return lines;
    }
public static void main(String[]args){
        String d="<users>\n" + //
                "    <user>\n" + //
                "        <id>1</id>\n" + //
                "        <name>Ahmed Ali</name>\n" + //
                "        <posts>\n" + //
                "            <post>\n" + //
                "                <body>\n" + //
                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" + //
                "                </body>\n" + //
                "                <topics>\n" + //
                "                    <topic>\n" + //
                "                        economy\n" + //
                "                    </topic>\n" + //
                "                    <topic>\n" + //
                "                        finance\n" + //
                "                    </topic>\n" + //
                "                </topics>\n" + //
                "            </post>\n" + //
                "            <post>\n" + //
                "                <body>\n" + //
                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" + //
                "                </body>\n" + //
                "                <topics>\n" + //
                "                    <topic>\n" + //
                "                        solar_energy\n" + //
                "                    </topic>\n" + //
                "                </topics>\n" + //
                "            </post>\n" + //
                "        </posts>\n" + //
                "        <followers>\n" + //
                "            <follower>\n" + //
                "                <id>2</id>\n" + //
                "            </follower>\n" + //
                "            <follower>\n" + //
                "                <id>3</id>\n" + //
                "            </follower>\n" + //
                "        </followers>\n" + //
                "    </user>\n" + //
                "    <user>\n" + //
                "        <id>2</id>\n" + //
                "        <name>Yasser Ahmed</name>\n" + //
                "        <posts>\n" + //
                "            <post>\n" + //
                "                <body>\n" + //
                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" + //
                "                </body>\n" + //
                "                <topics>\n" + //
                "                    <topic>\n" + //
                "                        education\n" + //
                "                    </topic>\n" + //
                "                </topics>\n" + //
                "            </post>\n" + //
                "        </posts>\n" + //
                "        <followers>\n" + //
                "            <follower>\n" + //
                "                <id>1</id>\n" + //
                "            </follower>\n" + //
                "        </followers>\n" + //
                "    </user>\n" + //
                "    <user>\n" + //
                "        <id>3</id>\n" + //
                "        <name>Mohamed Sherif</name>\n" + //
                "        <posts>\n" + //
                "            <post>\n" + //
                "                <body>\n" + //
                "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" + //
                "                </body>\n" + //
                "                <topics>\n" + //
                "                    <topic>\n" + //
                "                        sports\n" + //
                "                    </topic>\n" + //
                "                </topics>\n" + //
                "            </post>\n" + //
                "        </posts>\n" + //
                "        <followers>\n" + //
                "            <follower>\n" + //
                "                <id>1</id>\n" + //
                "            </follower>\n" + //
                "        </followers>\n" + //
                "    </user>\n" + //
                "</users>";
         System.out.println(to_Minify(d));

          System.out.println("---------------------------------------------------------------------");
          
        System.out.println(minifyKeepingSpaces(d));
}

}
