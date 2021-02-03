export ZSH="$HOME/.oh-my-zsh"
export LANGUAGE=en_US.UTF-8
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8

ZSH_THEME="mrtazz"
HYPHEN_INSENSITIVE="true"

stty -ixon
source $ZSH/oh-my-zsh.sh

export GOROOT="$HOME/Programs/go"
export GOPATH="$HOME/Programs/gopackages"
export PATH="$PATH:$GOROOT/bin:$GOROOT/lib:$GOROOT/doc:$GOPATH/bin"
export PATH="$PATH:/opt/metasploit-framework/bin"
export PATH="$PATH:/usr/lib/postgresql/12/bin"
export PGDATA="$HOME/Programs/postgresql/data"
export PGHOST="/tmp"
export CS_HOST="beacon"
export EDITOR="vim"

# Aliases
alias vimrc="vim $HOME/.vimrc"
alias bashrc="vim $HOME/.bashrc"
alias zshrc="vim $HOME/.zshrc"
alias reload="source $HOME/.zshrc"
alias rm='trash'
alias update='sudo apt update && sudo apt upgrade && sudo apt autoremove'
alias gl='git log'
alias glo='gl --oneline'
alias glog='glo --graph'
alias gs='git status'
alias gd='git diff'
alias gc='git checkout'
alias gp='git pull'
alias decompress='tar -xvf $(ls -t $DOWNLOADS/*.tar | head -n1)'

# Stores all the things that will not be the same across all my machines
source $HOME/.zshrc_machine_dependant

# This function creates a new practical and does the base config
# If the practical name is not given, just goes to the practical folder
tp() {

    if [[ -z "$1" ]] ; then
        echo "Please provide a practical number"
        return
    fi

    # Goes the practicals folder
    cd $HOME/Code/Rust/TP_Prog
    # Clones if the practical doesn't exist
    [[ -d tp0$1-gabriel.jorge ]] || git clone git@git.cri.epita.net:p/2023-s4-tp/tp0$1-gabriel.jorge
    cd tp0$1-gabriel.jorge
    # Initialises the repo if the name was given
    if [[ ! -z "$2" ]] ; then
        # Creates the base folder
        mkdir pw_0$1_$2
        # Creates the AUTHORS file
        printf 'Gabriel\nJorge\ngabriel.jorge\ngabriel.jorge@epita.fr\n' > pw_0$1_$2/AUTHORS
    fi
    # Goes into the base folder
    cd *
    ls
}

# Usage: commit <message> [tag]
commit() {

    if [[ -n $(git diff --name-only --cached) ]]; then 
        read "response?Detected staged files. Redo add anyways ? [Y/n] "
        response=${response:l} #tolower
        if [[ $response =~ ^(yes|y| ) ]] || [[ -z $response ]]; then
            git reset HEAD .
            git add .
        fi
    else
        git add .
    fi

    git status

    # Asks the user if he wants to commit
    read "response?Commit ? [Y/n] "
    response=${response:l} #tolower
    if [[ $response =~ ^(yes|y| ) ]] || [[ -z $response ]]; then
        # Commits. Aborts if the command fails
        git commit -m $1 || return
        # If the tag is specified
        if [[ ! -z "$2" ]]; then
            i=0
            passed=0
            # Tries tags until it finds an unused one
            while [ $passed -lt 1 ]; do
                git tag -a $2-$i -m $1 && let passed=1
                let i=$i+1
            done
        fi
        # Pushes
        git push --follow-tags
    fi
}

# Goes to the Assembly folder
asm() {
   

    if [ $# -eq 0 ]
    then
        # No arguments provided: goes to the Assembly directory
        cd ~/Code/Assembly/
        ls
    else
        if ! test -f "$1.asm"; then
            # Argument provided: creates an asm file with given name and inits it
            touch $1.asm
            printf "                ; ==============================\n                ; Vector Initialization\n                ; ==============================\n\n                org         \$4\n\nVector_001      dc.l        Main\n\n                ; ==============================\n                ; Main Program\n                ; ==============================\n\n                org         \$500\n\nMain            \n\n                ; ==============================\n                ; Subroutines\n                ; ==============================\n\n\n\n                ; ==============================\n                ; Data\n                ; ==============================\n\n                org         \$700\n\n\n" >> $1.asm
            vim $1.asm
        else
            vim $1.asm
        fi
    fi
    

}

codingstyle() {
    
    files=("$@")
    
    if [[ -z "$1" ]] ; then
        files=($(find -name '*.c' -o -name '*.h' -o -name '*.cpp' -o -name '*.cc' -o -name '*.hh' -o -name '*.hxx'))
    fi
    
    for arg in "${files[@]}"; do

        if [[ ! -f $arg ]]; then
            echo -e "[\e[31mERROR\e[0m] No such file $arg"
            continue
        fi

        cp "$arg" /tmp/`basename "$arg"`
        clang-format -i "$arg"
        echo "Formatted $arg"
    
    done
}

# Automatically builds a c Makefile
# Usage: makefile [name]
# Name is used for the library name (libname.a) and the binary (name)
# Parent folder name by default
makefile() {

    if [[ -f Makefile ]]; then 
        read "response?Makefile already exists. Overwrite ? [Y/n] "
        response=${response:l} #tolower
        if [[ ! $response =~ ^(yes|y| ) ]] && [[ ! -z $response ]]; then
            return;
        fi
    fi

    SOURCES=`ls *.c | tr '\n' ' '`
    PROJECTNAME=${PWD##*/} # Parent directory

    if [[ ! -z $1 ]]; then
        PROJECTNAME=$1
    fi

    echo "\
CC = gcc
CFLAGS = -std=c99 -Wall -Wextra -Werror -pedantic

SRC = $SOURCES
OBJ = \$(SRC:.c=.o)
BIN = $PROJECTNAME
LIB = lib$PROJECTNAME.a

all: library bin
\$(BIN): bin
\$(LIB): library

bin: \$(OBJ)
\t\$(CC) \$(CFLAGS) \$^ -o \$(BIN)

debug: CFLAGS += -g -fsanitize=address
debug: clean all

library: \$(OBJ)
\tar -cvq  \$(LIB) \$(OBJ)

clean:
\t\$(RM) \$(LIB) \$(BIN) \$(OBJ)

.PHONY: all clean library debug" > Makefile

    echo "Makefile created!"

}

precommit() {
    cp $HOME/Code/Linux/Resources/precommit/.* ./
    pre-commit install 
}

shscript() {

    name="$1"
    if [ -z "$name" ]; then
        name="script"
    fi
    name+=".sh"

    if [ -f "$name" ]; then
        echo "$name already exists!"
        return
    fi

    echo "#!/bin/sh\n\n" > "$name"
    chmod +x "$name"
    vim "$name"
}

trimwhitespace() {
    sed -i 's/[ \t]*$//' "$1"
}

ccat() {
    if [[ -z "$1" ]] ; then
        echo "Please provide files to process"
        return
    fi
    for arg in "$@"; do
        echo "\n$arg:"
        pygmentize -g $arg
    done
}
