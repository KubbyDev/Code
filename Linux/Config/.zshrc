export ZSH="$HOME/.oh-my-zsh"
export LANGUAGE=en_US.UTF-8
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8

ZSH_THEME="mrtazz"
HYPHEN_INSENSITIVE="true"

source $ZSH/oh-my-zsh.sh

export GOROOT=$HOME/Programs/go
export GOPATH=$HOME/Programs/gopackages
export PATH=$PATH:$GOROOT/bin:$GOROOT/lib:$GOROOT/doc:$GOPATH/bin
export PATH=$PATH:/opt/metasploit-framework/bin

# Aliases
alias vimrc="vim $HOME/.vimrc"
alias bashrc="vim $HOME/.bashrc"
alias zshrc="vim $HOME/.zshrc"
alias reload="source $HOME/.zshrc"
alias rm='trash'
alias update='sudo apt update && sudo apt upgrade && sudo apt autoremove'

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

# Git commits and pushes
commit() {

    git add .
    git status

    read "response?Commit ? [Y/n] "
    response=${response:l} #tolower
    if [[ $response =~ ^(yes|y| ) ]] || [[ -z $response ]]; then
        git commit -m $1
        git push
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

# Creates a C file with its header
c() {

    touch $1.h
    input=$1
    uppercase=${input^^}
    printf "#ifndef ${uppercase}\n#define ${uppercase}\n\n    \n\n#endif" > $1.h
    vim $1.c

}

drive() {
    cd /media/kubby/$1
}

data() {
    drive Data
}

