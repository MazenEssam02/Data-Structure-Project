# Data-Structure-Project
### 1. rror Handler :
#### ● Detecting MultiErrors in XML like missing closing tags or opening tags.
#### ● Correcting MultiErrors found in the previous point.

###  2. Formatting (Prettifying) the XML:
##### ●the XML file should be well formatted by keeping the indentation for each level.
##### ● Adjust the indentation of an XML file, to make it more readable.
##### ● If the file is not consistent, user cannot format it.

### 3. Minify
##### ● Since spaces and newlines (\n) are actually characters that can increase the size of an XML document.
##### ● This feature should aim at decreasing the size of an XML file (compressing it) by deleting the whitespaces and indentations

### 4. Huffman compression
#### Compressing the data in the XMLfile: Compression is based on using Huffman compression/encoding. We first minify the given file, generate binary codes to each character in the file (the most frequently used character has least number of binary digits). 
##### 1-Calculate the frequency of each character in the string.
##### 2-Sort the characters in increasing order of the frequency. These are stored in a priority queue. Characters sorted according to the frequency.
##### 3-Create an empty node p. Assign the minimum frequency to the left child of z and assign the second minimum frequency to the right child of p. Set the value of the p as the sum of the above two minimum frequencies. Getting the sum of  the above two minimum frequencies.
##### 4-Remove these two minimum frequencies from Q and add the sum into the list of frequencies 
##### 5-Insert node p into the tree.
##### 6-Repeat steps utill build Tree.
