cmake_minimum_required(VERSION 3.9)
project(GameSDL C)
set(CMAKE_C_STANDARD 99)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wextra")
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${PROJECT_SOURCE_DIR}/SDL2/cmake")

set(SDL2_INCLUDE_DIR "${PROJECT_SOURCE_DIR}/SDL2/include")
set(SDL2_IMAGE_INCLUDE_DIR "${PROJECT_SOURCE_DIR}/SDL2/include")
set(SDL2_LIBRARY "${PROJECT_SOURCE_DIR}/SDL2/lib/SDL2.lib")
set(SDL2_IMAGE_LIBRARY "${PROJECT_SOURCE_DIR}/SDL2/lib/SDL2_image.lib")

find_package(SDL2 REQUIRED)
find_package(SDL2_Image REQUIRED)

#set(INCLUDES header/constantes.h header/editeur.h header/fichier.h header/jeu.h header/deplacerJoueur.h)
#set(SOURCES src/main.c src/jeu.c src/chargerNiveau.c src/deplacerJoueur.c src/affichageMario.c)
set(SOURCES src/main.c)

add_executable(GameSDL ${INCLUDES} ${SOURCES})

include_directories(${PROJECT_SOURCE_DIR}/include ${SDL2_IMAGE_INCLUDE_DIR})
target_link_libraries(GameSDL ${SDL2_LIBRARY} ${SDL2_IMAGE_LIBRARY})