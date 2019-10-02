" ----------------------------------------------------------
" Compilation and running
" ----------------------------------------------------------

" F4: Compile
map <F4> :w <CR> :call Compile() <CR> 
:function! Compile()
" If compile.sh exists, executes it
:   if(filereadable("compile.sh"))
:       !./compile.sh
" else run the standard compilation command
" C
:   elseif(&filetype == "c" || &filetype == "h")
:       !gcc -Wall -Wextra -Werror -std=c99 -O1 -o a.out *.c
" Assembly
:   elseif(&filetype == "asm")
:       !/home/kubby/68000/a68k % -o%:r.hex -s -n -rmal
:   endif
:endfunction

" F5: Run
map <F5> :call Run() <CR>
:function! Run()
" If run.sh exists, executes it
:   if(filereadable("run.sh"))
:       !./run.sh
" else run the standard run command
" C
:   elseif(&filetype == "c" || &filetype == "h")
:       !./a.out && rm a.out 
" Assembly
:   elseif(&filetype == "asm")
:       !/home/kubby/68000/d68k.sh %:r.hex && rm %:r.hex
:   endif
:endfunction

" F8: Compile and Run
map <F8> :w <CR> :call Compile() <CR> :call Run() <CR>

" ----------------------------------------------------------
" Plugins
" ----------------------------------------------------------

set nocompatible
filetype off
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()
Plugin 'VundleVim/Vundle.vim'

Plugin 'Valloric/YouCompleteMe'
Plugin 'joshdick/onedark.vim'

call vundle#end()
filetype plugin indent on

" ----------------------------------------------------------
" Graphics and coloration
" ----------------------------------------------------------

" Displays line numbers
set number

" Forces the tabs to be made of spaces
set tabstop=4 softtabstop=0 expandtab shiftwidth=4 smarttab

" Theme
colorscheme onedark

" Syntaxic coloration
syntax on
autocmd Filetype asm set syntax=asm68k
autocmd Filetype c set syntax=c

" Sets the background transparent
hi Normal guibg=NONE ctermbg=NONE
