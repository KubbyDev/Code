# If you come from bash you might have to change your $PATH.
# export PATH=$HOME/bin:/usr/local/bin:$PATH

# Path to your oh-my-zsh installation.
export ZSH="/home/kubby/.oh-my-zsh"

# Set name of the theme to load --- if set to "random", it will
# load a random theme each time oh-my-zsh is loaded, in which case,
# to know which specific one was loaded, run: echo $RANDOM_THEME
# See https://github.com/robbyrussell/oh-my-zsh/wiki/Themes
ZSH_THEME="mrtazz"

# Set list of themes to pick from when loading at random
# Setting this variable when ZSH_THEME=random will cause zsh to load
# a theme from this variable instead of looking in ~/.oh-my-zsh/themes/
# If set to an empty array, this variable will have no effect.
# ZSH_THEME_RANDOM_CANDIDATES=( "robbyrussell" "agnoster" )

# Uncomment the following line to use case-sensitive completion.
# CASE_SENSITIVE="true"

# Uncomment the following line to use hyphen-insensitive completion.
# Case-sensitive completion must be off. _ and - will be interchangeable.
# HYPHEN_INSENSITIVE="true"

# Uncomment the following line to disable bi-weekly auto-update checks.
# DISABLE_AUTO_UPDATE="true"

# Uncomment the following line to automatically update without prompting.
# DISABLE_UPDATE_PROMPT="true"

# Uncomment the following line to change how often to auto-update (in days).
# export UPDATE_ZSH_DAYS=13

# Uncomment the following line if pasting URLs and other text is messed up.
# DISABLE_MAGIC_FUNCTIONS=true

# Uncomment the following line to disable colors in ls.
# DISABLE_LS_COLORS="true"

# Uncomment the following line to disable auto-setting terminal title.
# DISABLE_AUTO_TITLE="true"

# Uncomment the following line to enable command auto-correction.
# ENABLE_CORRECTION="true"

# Uncomment the following line to display red dots whilst waiting for completion.
# COMPLETION_WAITING_DOTS="true"

# Uncomment the following line if you want to disable marking untracked files
# under VCS as dirty. This makes repository status check for large repositories
# much, much faster.
# DISABLE_UNTRACKED_FILES_DIRTY="true"

# Uncomment the following line if you want to change the command execution time
# stamp shown in the history command output.
# You can set one of the optional three formats:
# "mm/dd/yyyy"|"dd.mm.yyyy"|"yyyy-mm-dd"
# or set a custom format using the strftime function format specifications,
# see 'man strftime' for details.
# HIST_STAMPS="mm/dd/yyyy"

# Would you like to use another custom folder than $ZSH/custom?
# ZSH_CUSTOM=/path/to/new-custom-folder

# Which plugins would you like to load?
# Standard plugins can be found in ~/.oh-my-zsh/plugins/*
# Custom plugins may be added to ~/.oh-my-zsh/custom/plugins/
# Example format: plugins=(rails git textmate ruby lighthouse)
# Add wisely, as too many plugins slow down shell startup.
# plugins=(git)

source $ZSH/oh-my-zsh.sh

# User configuration

# export MANPATH="/usr/local/man:$MANPATH"

# You may need to manually set your language environment
# export LANG=en_US.UTF-8

# Preferred editor for local and remote sessions
# if [[ -n $SSH_CONNECTION ]]; then
#   export EDITOR='vim'
# else
#   export EDITOR='mvim'
# fi

# Compilation flags
# export ARCHFLAGS="-arch x86_64"

# Set personal aliases, overriding those provided by oh-my-zsh libs,
# plugins, and themes. Aliases can be placed here, though oh-my-zsh
# users are encouraged to define aliases within the ZSH_CUSTOM folder.
# For a full list of active aliases, run `alias`.

# Aliases
alias zshconfig="mate ~/.zshrc"
alias ohmyzsh="mate ~/.oh-my-zsh"
alias vimrc='vim /home/kubby/.vimrc'
alias bashrc='vim /home/kubby/.bashrc'
alias zshrc="vim /home/kubby/.zshrc"
alias rm='trash'
alias reload='bash'

# This function creates a new practical and does the base config
createpractical() {

    cd /home/kubby/Code/C/TP_Prog
    git clone git@git.cri.epita.net:p/2023-s3-tp/tp0$1-gabriel.jorge

    cd tp0$1-gabriel.jorge
    printf 'Gabriel\nJorge\ngabriel.jorge\ngabriel.jorge@epita.fr\n' > AUTHORS
    
    cd *
}

# Git commits and pushes
commit() {

    git add *
    git status
    git commit -m $1
    git push

}

# Goes the the folder of the nth tp
tp() {
    
    cd /home/kubby/Code/C/TP_Prog/tp0$1-gabriel.jorge
    cd *
    ls

}

# Goes to the Assembly folder
asm() {
   

    if [ $# -eq 0 ]
    then
        # No arguments provided: goes to the Assembly directory
        cd /home/kubby/Code/Assembly/
        ls
    else
        # Argument provided: creates an asm file with given name and inis it
        touch $1.asm
        printf "                ; ==============================\n                ; Vector Initialization\n                ; ==============================\n\n                org         \$4\n\nVector_001      dc.l        Main\n\n                ; ==============================\n                ; Main Program\n                ; ==============================\n\n                org         \$500\n\nMain            \n\n                ; ==============================\n                ; Subroutines\n                ; ==============================\n\n\n\n                ; ==============================\n                ; Data\n                ; ==============================\n\n                org         \$700\n\n\n" > $1.asm
        vim $1.asm
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

