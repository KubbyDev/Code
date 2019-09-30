# cmake_minimum_required(VERSION <specify CMake version here>)
project(Tests C)

set(CMAKE_C_STANDARD 99)

add_executable(Tests ChainedList/Main.c ChainedList/ChainedList.c ChainedList/ChainedList.h TestsOCR/neuron.c TestsOCR/network.c TestsOCR/neuron.h TestsOCR/network.h)