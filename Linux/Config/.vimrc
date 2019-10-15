" ----------------------------------------------------------
" Compilation and running
" ----------------------------------------------------------

" If you add compile.sh or run.sh files, dont forget to execute
" this command: chmod +x run/compile.sh

" F4: Compile
map <F4> <ESC> :w <CR> :call Compile(
:function! Compile(...)
    " Compilation arguments ("" if nothing given)
:   let arguments = a:0 >= 1 ? a:1 : ""
    " If compile.sh exists, executes it
:   if(filereadable("compile.sh"))
:       execute '!./compile.sh ' arguments ' && echo "Compilation successfull"'
    " else run the standard compilation command
    " C
:   elseif(&filetype == "c" || &filetype == "h")
:       execute '!gcc -Wall -Wextra -Werror -std=c99 -O1 -o a.out *.[co] ' arguments ' && echo "Compilation successfull"'
    " Assembly
:   elseif(&filetype == "asm")
:       !/home/kubby/68000/a68k % -o%:r.hex -s -n -rmal && echo "Compilation successfull"
:   endif
:endfunction

" F5: Run
map <F5> <ESC> :call Run(
:function! Run(...)
    " Arguments for the execution ("" if nothing given)
:   let arguments = a:0 >= 1 ? a:1 : ""
    " If run.sh exists, executes it
:   if(filereadable("run.sh"))
:       execute '!./run.sh ' arguments
    " else run the standard run command
    " C
:   elseif((&filetype == "c" || &filetype == "h"))
:       if(filereadable("a.out"))
:           execute '!./a.out ' arguments ' & rm a.out'
:       else
:           !echo "Compiled file not found"
:       endif
    " Assembly
:   elseif(&filetype == "asm")
:       if(filereadable(expand("%:r") . ".hex"))
:           silent !(/home/kubby/68000/d68k.sh %:r.hex && rm %:r.hex) &          
:       else
:           !echo "Compiled file not found"
:       endif
:   endif
:endfunction

" F8: Compile and Run
map <F8> <ESC> :w <CR> :call CompileRun() <CR>
:function! CompileRun()
:   call Compile()
:   call Run()
:endfunction

" F9: Debug
map <F9> <ESC> :w <CR> :call Debug(
:function! Debug(...)
    " Arguments for the execution ("" if nothing given)
:   let runArgs = a:0 >= 1 ? a:1 : ""
    " Compilation arguments ("" if nothing given)
:   let compArgs = a:0 >= 2 ? a:2 : ""
    " C
:   if(&filetype == "c" || &filetype == "h")
:       call Compile('-g '.compArgs)
:       execute '!gdb --args ./a.out ' runArgs ' && rm ./a.out'
    " Assembly
:   elseif(&filetype == "asm")
:       call Compile()
:       call Run()
:   endif
:endfunction

" ----------------------------------------------------------
" Plugins
" ----------------------------------------------------------

set nocompatible
filetype off
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

Plugin 'VundleVim/Vundle.vim'
" Plugin 'Valloric/YouCompleteMe'
Plugin 'joshdick/onedark.vim'
Plugin 'SirVer/ultisnips'
Plugin 'honza/vim-snippets'
Plugin 'justinmk/vim-syntax-extra'

call vundle#end()
filetype plugin indent on

" Snippets
let g:UltiSnipsExpandTrigger="<tab>"
let g:UltiSnipsJumpForwardTrigger="<c-b>"
let g:UltiSnipsJumpBackwardTrigger="<c-z>"

" ----------------------------------------------------------
" Graphics and coloration
" ----------------------------------------------------------

" Displays line numbers
set number

" Forces the tabs to be made of spaces
set tabstop=4 softtabstop=0 expandtab shiftwidth=4 smarttab

" Enables mouse
set mouse=a

" Theme
colorscheme onedark

" Syntaxic coloration
syntax on
autocmd Filetype asm set syntax=asm68k
autocmd Filetype c set syntax=c
autocmd Filetype h set syntax=c

" Sets the background transparent
hi Normal guibg=NONE ctermbg=NONE
