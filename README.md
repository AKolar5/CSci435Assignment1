# CSci435Assignment1
The initial assignment for CSCI 435, testing programming ability.

How to run:

I tested this by downloading the repo as a zip on a Windows machine. Once you extract the files, you can see the 
already generated images in the \CSci435Assignment1-main\CSci435Assignment1-main\Programming_Assigment_435\src\PNGOutputs folder. 
Delete these to before running the program to see that the folder actually get populated.
Navigate to the assignment_main package within the srcfolder. On the command line, run "javac Main.java XML_parser.java". 
Then navigate back to the src folder and run java assignment_main.Main path/to/Resources. For my machine the command was:
"java assignment_main.Main C:\Users\acmck\Documents\CSci435Assignment1-main\CSci435Assignment1-main\Programming_Assigment_435\src\Resources"
The Resources folder holds the .xml and .png files that need to be parsed and updated.
After that, you can look back in the PNGOutputs folder and it should be updated with new images.

Technique:

I used object oriented programming in java to accomplish the assignment. The XML_parser class is responsible for manipulating a 
.xml file to obtain the nodes that are leaves and the bounds attributes of those nodes. The Main class uses the Graphics2D library to
draw rectangles on the .png files at the locations specified by the bounds attribute. Each updated png is then saved to the PNGOutputs
folder.
