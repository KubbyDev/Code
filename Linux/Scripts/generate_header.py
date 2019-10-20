import re # Regular expressions
import os

path = "test.c"
headerpath = "test.h"

# Finds function definitions: word word(word*) (add { or ; at the end)
regex = r'[^{};\n\r() ]+[ \n\r]+[^{};\n\r() ]+[ \n\r]*\([^{};()]*\)[ \n\r]*' 

if(os.path.exists(headerpath)):
    # If the file exists, removes the function definitions
    file = open(headerpath, "r")
    withoutFunctions = re.sub(regex+';\n', '', file.read())
    print(withoutFunctions)
    file.close()
    file = open(headerpath, "w")
    file.write(withoutFunctions)
    file.close()
else:
    # If the file doesn't exist, creates it and inits it
    # Calculates the define string
    define = "TEST_H"
    # Creates the file
    file = open(headerpath, "w+")
    file.write('#ifndef ' + define + '\n' 
             + '#define ' + define + '\n' 
             + '\n\n\n'
             + '#endif //' + define)
    file.close()
    
# Writes the function definitions


