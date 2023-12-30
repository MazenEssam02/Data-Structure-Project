import java.util.ArrayList;


public class NetworkHelper {
    private User [] users ;

 public NetworkHelper(String multilineInput){
        HandleXML handleXML =new HandleXML();
        ArrayList<String> linesList = HandleXML.convertToArrayList(multilineInput);

        // Print the result
        for (int i =0;i<linesList.size() ;i++) {
            linesList.set(i,linesList.get(i).trim());

            //System.out.println(linesList.get(i).trim());
        }

        this.users = handleXML.getUsers(linesList);

    }

public MapF createUserIndexMap() {
        MapF userIndexMapF = new MapF();

        for (int i = 0; i < users.length; i++) {
            User user = users[i];
            String id = user.ID;
            userIndexMapF.put(id, i); // Map each user's ID to their index in the array
        }

        return userIndexMapF;
    }


 public  Graph CreateNetwork(){
        MapF userIndexMapF = createUserIndexMap();
Graph graph = new Graph(users.length);
        for(int i =0;i<users.length;i++){
           for(int j=0;j<users[i].Followers.size();j++){
               int index = userIndexMapF.get(users[i].Followers.get(j));
               graph.addEdge(i,index);
           }
        }
return graph;
}
public User[] getUsers() {
        return users;
    }
    
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


public User[][] suggest(){

        Graph graph= this.CreateNetwork();

        User[][] suggestionUser =new User[graph.getVertices()][];
        for(int i=0 ;i<graph.getVertices();i++){
            ArrayList<Integer> followersOfFollowers = new ArrayList<>();
            for(int j=0;j<graph.getVertices();j++){
                if(graph.isConnected(i,j)){
                    for(int k=0;k<graph.getVertices();k++){
                        if((!graph.isConnected(i,k))&& graph.isConnected(j,k) && i!=k){
                            followersOfFollowers.add(k);}}}}

            suggestionUser[i]= new User[followersOfFollowers.size()];
            for (int j = 0; j < followersOfFollowers.size(); j++) {
                // Note: Assuming you have a way to create User objects from IDs
                suggestionUser[i][j] =users[followersOfFollowers.get(j)];
            }

        }

return suggestionUser;   }



public  ArrayList<User> mutualFollowers(String ID1 , String ID2 ){
        MapF mapF = createUserIndexMap();
        Graph graph = CreateNetwork();
        int user1 = mapF.get(ID1);
        int user2 = mapF.get(ID2);

        ArrayList<User> mutualUsers=new ArrayList<>();

        for(int i=0;i<users.length;i++){
            if((graph.isConnected(user1,i) && graph.isConnected(user2,i))){
                mutualUsers.add(users[i]);
            }
        }

 return mutualUsers;
    }
    
public User mostFollowed() {
    int maxFollowers = users[0].Followers.size();
    int index = 0;
    for (int i = 1; i < users.length; i++) {
        if (maxFollowers < users[i].Followers.size()) {
            maxFollowers = users[i].Followers.size();
            index = i;
        }

    }
    return users[index];}

    
    
}
