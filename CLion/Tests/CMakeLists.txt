# cmake_minimum_required(VERSION <specify CMake version here>)
project(Tests C)

set(CMAKE_C_STANDARD 99)

add_executable(Tests TestsOCR/neuron.c TestsOCR/network.c TestsOCR/main.c TestsOCR/training_example.c TestsOCR/training_example.h)