import re # Regular expressions
import os
import sys

if(len(sys.argv) < 2):
    raise Exception("You must provide a c file path as argument")

path = sys.argv[1]  # Gets the c file name
filename =  path[path.rfind('/')+1:path.rfind('.')] # Gets the file name without extension
headerpath = filename + '.h' # Gets the header file name

# Finds function definitions: word word(word*) (add { or ; at the end)
regex = r'[^{};\n\r() ]+[ \n\r]+[^{};\n\r() ]+[ \n\r]*\([^{};()]*\)[ \n\r]*' 

if(os.path.exists(headerpath)):
    # If the file exists, removes the function definitions
    file = open(headerpath, "r")
    withoutFunctions = re.sub(regex+';\n', '', file.read())
    file.close()
    file = open(headerpath, "w")
    file.write(withoutFunctions)
    file.close()
else:
    # If the file doesn't exist, creates it and inits it
    # Calculates the define string
    define = filename.upper() + "_H"
    # Creates the file
    file = open(headerpath, "w+")
    file.write('#ifndef ' + define + '\n' 
             + '#define ' + define + '\n'
             + '\n\n'
             + '#endif //' + define)
    file.close()

# Gets all the functions in the c file
file = open(path, "r")
cfiledata = file.read()
file.close()
functions = re.findall(regex+'{', cfiledata)

# Adds the include
if cfiledata.find('#include "' + filename + '.h"') == -1:
    cfiledata = '#include "' + filename + '.h"\n' + cfiledata
    file = open(path, "w")
    file.write(cfiledata)
    file.close()

# Converts the functions to prototypes
for i in range(len(functions)):
    functions[i] = functions[i][:-1] + ';'

# Writes the prototypes in the header file
file = open(headerpath, "r")
data = re.sub(regex+';\n', '', file.read())
file.close()
endifIndex = data.rfind('\n#endif')
endif = data[endifIndex:]
data = data[:endifIndex]
for function in functions:
    data += function + "\n"
data += endif
file = open(headerpath, "w")
file.write(data)
file.close()