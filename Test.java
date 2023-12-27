import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static ArrayList<String> ReadXML(String path){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            ArrayList<String> LinesArray = new ArrayList<String>();
            while((line=reader.readLine()) != null){
                LinesArray.add(line);
            }
            reader.close();
            return LinesArray;

        }
        catch(IOException c){
            c.printStackTrace();
        }
        return null;
    }

    //Test Code
    public static void main(String[] args) {
        ArrayList<String> d=ReadXML("sample.xml");
        MainFiles obj = new MainFiles(d);
        Object [] resulttest=new Object[4];
        Object [] resulttest2=new Object[4];
        resulttest2= obj.validation();
        //Check before correction 
        if((Integer)resulttest2[1]!=-1 && (Integer)resulttest2[3]!=-1){
        System.out.println("The Tag \""+resulttest2[0]+"\" in line ("+resulttest2[1] + ") isn't opened and the tag \""+ resulttest2[2]+"\" in line ("+resulttest2[3]+") isn't closed");
        }
        //Output:The Tag "id" in line (2) isn't opened and the tag "name" in line (3) isn't closed
        
        String s=obj.correction();
        resulttest= obj.validation();
        //check after correction
        if((Integer)resulttest2[1]==-1 && (Integer)resulttest2[3]==-1){
        System.out.println(resulttest[0]+" "+resulttest[1]+" "+resulttest[2]+" "+resulttest[3]);
        }
        // Output:-1 -1 -1 -1


//       String  s=obj.Minify();
//        System.out.println(s);
//        System.out.println("-------------------");
//        System.out.println(obj.getStringData());
//        s=obj.Prettify();
//        System.out.println("-------------------");
//        System.out.println(s);
//        System.out.println("-------------------");
//        System.out.println(obj.getStringData());



    }

}
