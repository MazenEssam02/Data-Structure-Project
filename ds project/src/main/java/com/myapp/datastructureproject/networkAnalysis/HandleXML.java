package com.myapp.datastructureproject.networkAnalysis;
import java.util.ArrayList;
class HandleXML {

    String userOpen = "<user>";
    String userClose = "</user>";
    String IDOpen = "<id>";
    String IDClose = "</id>";
    String nameOpen = "<name>";
    String nameClose = "</name>";
    String postsOpen = "<posts>";
    String postsClose = "</posts>";
    String postOpen = "<post>";
    String postClose = "</post>";
    String bodyOpen = "<body>";
    String bodyClose = "</body>";
    String topicsOpen = "<topics>";
    String topicsClose = "</topics>";
    String topicOpen = "<topic>";
    String topicClose = "</topic>";
    String followersOpen = "<followers>";
    String followersClose = "</followers>";
    String followerOpen = "<follower>";
    String followerClose = "</follower>";
    String usersOpen = "<users>";
    String usersClose = "</users>";



    public static ArrayList<String> concatenateLinesForID(ArrayList<String> linesNoSpace) {
        ArrayList<String> modifiedLines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        boolean isIdTagOpen = false;

        for (String line : linesNoSpace) {
            if (line.contains("<id>")) {
                isIdTagOpen = true;
            }

            if (isIdTagOpen) {
                currentLine.append(line);
                if (line.contains("</id>")) {
                    isIdTagOpen = false;
                    modifiedLines.add(currentLine.toString());
                    currentLine.setLength(0); // Clear StringBuilder for the next line
                }
            } else {
                modifiedLines.add(line);
            }
        }

        //  System.out.println(modifiedLines);
        return modifiedLines;
    }



    public static ArrayList<String> concatenateLinesForName(ArrayList<String> linesNoSpace) {
        ArrayList<String> modifiedLines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        boolean isNameTagOpen = false;

        for (String line : linesNoSpace) {
            if (line.contains("<name>")) {
                isNameTagOpen = true;
            }

            if (isNameTagOpen) {
                currentLine.append(line);
                if (line.contains("</name>")) {
                    isNameTagOpen = false;
                    modifiedLines.add(currentLine.toString());
                    currentLine.setLength(0); // Clear StringBuilder for the next line
                }
            } else {
                modifiedLines.add(line);
            }
        }

        //     System.out.println(modifiedLines);
        return modifiedLines;
    }








    public User[] getUsers(ArrayList<String> modifiedLines) {

        ArrayList<String> modifiedLines_2 = concatenateLinesForID(modifiedLines);
        ArrayList<String> LinesNoSpace = concatenateLinesForName(modifiedLines_2);

        char[] LineChar;
        boolean isFollower = false;
        int NoUser = 0;
        int indexUser = -1;
        int i = 0;
        for (String s : LinesNoSpace) {
            LineChar = s.toCharArray();
            if (s.contains(userOpen)) {
                NoUser++;
            }
        }

        User[] users = new User[NoUser];

        for (String s : LinesNoSpace) {
            LineChar = s.toCharArray();
            String data = "";
            if (s.contains(userOpen)) {
                indexUser++;
                users[indexUser] = new User();
                isFollower = false;
            }


            if (s.contains(IDOpen)) {
                int index = s.indexOf(">");
                for (index++; index < s.length(); index++) {
                    if (LineChar[index] == '\0' ) {
                        continue;
                    } else if (LineChar[index] == '<'||LineChar[index] == '\n') {
                        break;
                    } else {

                        data += LineChar[index];}

                }
                if (isFollower) {
                    users[indexUser].Followers.add(data.trim());
                } else {
                    users[indexUser].ID = data.trim();
                }
            }

            if (s.contains(IDClose)) {
                int index = s.indexOf("<");
                for (index--; index >=0; index--) {
                    if (LineChar[index] == ' ' ) {
                        continue;
                    } else if (LineChar[index] == '>') {
                        break;
                    } else {
                        data += LineChar[index];
                    }
                }
                if (isFollower) {
                    users[indexUser].Followers.add(data.trim());
                } else {
                    users[indexUser].ID = data.trim();
                }
            }






            if (s.contains(nameOpen)) {
                int index = s.indexOf(">");
                for (index++; index < s.length(); index++) {
                    if (LineChar[index] == '\0') {
                        continue;
                    } else if (LineChar[index] == '<') {
                        break;
                    } else {
                        data += LineChar[index];
                    }

                }
                users[indexUser].name = data;
            }


            if (s.contains(nameClose)) {

                for (int index=0; index <s.indexOf("<"); index++) {
                    if (LineChar[index] == '>') {
                        break;
                    } else {
                        data += LineChar[index];
                    }
                }


                users[indexUser].name = data ;

            }






            if (s.contains(followerOpen)) {
                isFollower = true;
            }
            if (s.contains(postOpen)) {
                Post post = new Post();
                String y;

                for (int x = i + 1; x < LinesNoSpace.size(); x++) {
                    y = LinesNoSpace.get(x);
                    if (y.contains(postClose)) {
                        break;
                    }
                    if (y.contains(bodyOpen) && y.contains(bodyClose)) {
                        int index = s.indexOf(">");
                        for (index++; index < s.length(); index++) {
                            if (LineChar[index] == '\0') {
                                continue;
                            } else if (LineChar[index] == '<') {
                                break;
                            } else {
                                data += LineChar[index];
                            }
                        }
                        post.body = data;
                    }
                    if (y.contains(bodyOpen) && !(y.contains(bodyClose))) {

                        int k = x + 1;
                        for (; k < LinesNoSpace.size(); k++) {
                            if (LinesNoSpace.get(k).contains(bodyClose)) {
                                break;
                            }
                            data += LinesNoSpace.get(k);
                        }
                        post.body = data;
                    }

                    if (y.contains(topicOpen) && y.contains(topicClose)) {
                        int index = s.indexOf(">");
                        for (index++; index < s.length(); index++) {
                            if (LineChar[index] == '\0') {
                                continue;
                            } else if (LineChar[index] == '<') {
                                break;
                            } else {
                                data += LineChar[index];
                            }
                        }
                        post.topics.add(data);
                    }

                    if (y.contains(topicOpen) && !(y.contains(topicClose))) {
                        data = LinesNoSpace.get(x + 1);
                        post.topics.add(data);
                    }
                }
                users[indexUser].Posts.add(post);
            }
            i++;
        }
        return users;
    }

    public static ArrayList<String> convertToArrayList(String multilineInput) {
        ArrayList<String> result = new ArrayList<>();

        // Split the multiline string into an array of lines
        String[] lines = multilineInput.split("\\r?\\n");


        // Add each line to the ArrayList
        for (String line : lines) {
            result.add(line);
        }

        return result;
    }
}