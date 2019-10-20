#!/bin/bash

# Finds the name of the header file


# If the file exists, removes all the functions


# If the file doesn't exist, initialises it


# Copies all the functions from the source file and writes it in the header file



while IFS= read -r line
do
  echo "$line"
done < "$1"
