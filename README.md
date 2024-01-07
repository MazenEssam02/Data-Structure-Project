# Data-Structure-Project
## The features implemented :
###  1. Error Detection
- Detecting MultiErrors in XML like missing closing tags or opening tags.
###   2. Error Correction
- Correcting MultiErrors found in the previous point.

###   3. Formatting (Prettifying) the XML
   - The XML file should be well formatted by keeping the indentation for each level.
   - Adjust the indentation of an XML file, to make it more readable.
   - If the file is not consistent, user cannot format it.

###   4. Minify
- Since spaces and newlines (\n) are actually characters that can increase the size of an XML document.
- This feature should aim at decreasing the size of an XML file (compressing it) by deleting the whitespaces and indentations

###   5. Huffman compression
####     Compressing the data in the XMLfile: Compression is based on using Huffman compression/encoding. We first minify the given file, generate binary codes to each character in the file (the most frequently   used character has least number of binary digits). 
    1-Calculate the frequency of each character in the string.
    2-Sort the characters in increasing order of the frequency. These are stored in a priority queue. Characters sorted according to the frequency.
    3-Create an empty node p. Assign the minimum frequency to the left child of z and assign the second minimum frequency to the right child of p. Set the value of the p as the sum of the above two minimum frequencies. Getting the sum of  the above two minimum frequencies.
    4-Remove these two minimum frequencies from Q and add the sum into the list of frequencies 
    5-Insert node p into the tree.
    6-Repeat steps until the Tree is built.
###   6. Converting XML to JSON
   - This function takes an XML string as input and converts it into  a JSON-formatted string. It does so by parsing through the XML string  character by character, identifying tags, and constructing the  equivalent JSON structure.
###  7. Network Analysis:
#### We use this analysis to extract important data like:
-  who is the most influencer user (has the most followers)
-  who is the most active user (connected to lots of users)
-  the mutual followers between 2 users
-  for each user, suggest a list of users to follow (the followers of his followers)
###  8. Post Search 
- given a specific word or topic, we can get the posts where this  word or topic was mentioned.
## Link for the Report, EXE file and The Illustration Video:


