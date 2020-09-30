#!/usr/bin/python3

import os
import sys

downloaded_location = "/media/kubby/Data/Tmp"

if len(sys.argv) < 2:
    cwd = os.getcwd()
    name = cwd[cwd.rindex('/')+1:]
    print("No practical name given, using folder name (%s)" % name)
else:
    name = sys.argv[1]

print("Extracting files...")
os.system('cd .. && tar -xvf %s/piscine-2023-c%s-*' % (downloaded_location, name))

print("Testing...")
os.system('make all check')

print("Cleaning")
os.system("make clean")
os.system("rm tests -rf")
os.system("rm Makefile")
