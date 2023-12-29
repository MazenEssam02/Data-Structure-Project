import java.util.ArrayList;


public class NetworkHelper {
    private User [] users ;
 
    public  String search(String word){
        boolean found = false;
        String result = "";
        boolean firstFound = false;


        for(User u : users){

            int indexx=0;
            for(Post x : u.Posts){
                found = false;
                if(x.body.contains(word)){
                    found = true;
                    firstFound = true;
                    indexx++;
                }
                if(x.topics.contains(word)){
                    found = true;
                    firstFound = true;
                    indexx++;
                }
                if(found){
                    if(indexx==1){
                        result += "Name is: " + u.name + "\n" + "ID is: " + u.ID + "\n";
                    }
                    result += "Body is: " + x.body + "\n" + "Topics is: "+ x.topics + "\n";
                }
            }
        }
        if(!firstFound){
            result += "Word isn't Found";
        }
        return result;
    }
    public  User mostActive(){
        Graph graph= CreateNetwork();
        int maxActive = 0 ;

        int index = 0 ;
        for(int i=0;i< graph.getVertices();i++){
            int counter=0 ;
            for(int j =0;j<graph.getVertices();j++){
                if(graph.isConnected(j,i)){
            counter++;
                }

                }


            if(maxActive <counter){
                maxActive = counter;
                index = i ;
            }
        }
return users[index];
}
    
}