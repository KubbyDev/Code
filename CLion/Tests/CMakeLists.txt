# cmake_minimum_required(VERSION <specify CMake version here>)
project(Tests C)

set(CMAKE_C_STANDARD 99)

add_executable(Tests Pendu/pendu.c Pendu/pendu.h)