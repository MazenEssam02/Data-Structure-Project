import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class Formatting {

//function to format the XML file

public static String format_xml_file(String file ){

StringBuilder file_after_format= new StringBuilder();

int n=0; 
//file is empty 
if (file.trim().length()==0)
 {
    return "file is Empty";
 }
String[] lines = splite_the_XML_file(file);

for (int i =0 ; i<lines.length ; i++){
    
    // check the line is empty

    if (lines[i].trim().length()==0)
 {
    continue;
 }
  if (lines[i].startsWith("</"))
  {
    n--;
    String indent = generateIndentation(n);
    file_after_format.append(indent).append(lines[i]).append("\n");
  }
  else if(lines[i].startsWith("<"))
  {
    String indent = generateIndentation(n);
    file_after_format.append(indent).append(lines[i]).append("\n");
    n++;
  }
  else if (lines[i].startsWith("<?"))
  {
     file_after_format.append(lines[i]).append("\n");
  }
  // if the line is data 
  else
  {
    String indent = generateIndentation(n);
   file_after_format.append(indent).append(lines[i]).append("\n");
  }
}
return  file_after_format.toString();
}
    
//Function to read the XML file from path

public static String Read_File(String path)throws IOException{
 String line ;  
 StringBuilder file = new StringBuilder();
 FileReader file_R= new FileReader(path);
 BufferedReader reader = new BufferedReader(file_R);
 while((line=reader.readLine())!= null){
    file.append(line).append("\n");
 }
  reader.close();

 return file.toString();

}

//  public static void WriteFile(String inputFile, String outputFilePath) throws IOException
//     {
//         /* check if the inputPath file is empty */
//         if ((inputFile == null) || (inputFile.trim().length() == 0))
//             return;

//         BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

//         writer.write(inputFile);
//         writer.close();
//     }

//Function to splite the XML file into lines as it make format easy
public static String[] splite_the_XML_file(String File){
    String[] lines;
    File =File.trim().replaceAll("<", "\n<").replaceAll(">", ">\n");
    lines = File.split("\n");
    return lines;
}

// function to generate Indentation;
public static String generateIndentation(int level)
    {
        StringBuilder indention = new StringBuilder();
        for (int i = 0; i < level; i++)
        {
            indention.append("  ");
        }
        return indention.toString();
    }

    // main function 
    public static void main(String[] args) throws IOException {

        String file_path = "C:\\Users\\MBR\\Desktop\\ds project\\file.xml";

        
        String formattedFile = format_xml_file(Read_File(file_path));

        System.out.println(formattedFile);
        
        //WriteFile(formattedFile,"formatted_" + file_path);

    }



}
