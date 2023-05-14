# Program Two for Team IJ

### Members
* Gage Fringer (gwfringe) 
* Amey Meher (avmeher)
* Deep Mehta (dmmehta2)

### Algorithms Implemented
* Prim-Jarnik (Edge Version) - This is an implementation utilizing a binary heap that selects a minimum edge from vertices in an MST to add to the tree
* Kruskal's - Adds the lowest weight edges not in the MST to the MST
* Boruvka's - Follows the in-class model of working to find edges that connect connected components

### Compilation instructions
During development, the main workflow for compilation and execution was as follows:
* Navigate into the ProgramTwo directory
* Select an algorithm to run and compile that file
```
$ javac [target_algorithm]
```
* After compilation is successful, the code can be executed by reading a file from the **inputs/** directory and writing to the **outputs/** directory
```
$ java [algorithm] < ./inputs/input_file_name.gph > ./outputs/input_file_name.out 2> ./outputs/input_file_name.err
```
* After this is done, standard output (the .out file) should include a .gph representation of the constructed MST, and standard error (the .err file) should have experimental statistics (i.e. total weight, runtime, number of comparisons)