" ------------------------------------------------------------------------------
" Compilation and running
" ------------------------------------------------------------------------------

" If you add compile.sh or run.sh files, dont forget to execute
" this command: chmod +x run/compile.sh

" F4: Compile
map <F4> <ESC> :w <CR> :call Compile()
:function! Compile(...)
    " Extension of the current file
:   let ext = expand('%:e')
    " Compilation arguments ("" if nothing given)
:   let arguments = a:0 >= 1 ? a:1 : ""
    " If compile.sh exists, executes it
:   if(filereadable("compile.sh"))
:       execute '!./compile.sh ' arguments ' && echo "Compilation successfull"'
    " else run the standard compilation command
    " C
:   elseif(ext == "c" || ext == "h")
:       execute '!gcc -Wall -Wextra -Werror -std=c99 -O1 -o a.out *.[co] -lm ' arguments ' && echo "Compilation successfull"'
    " Cpp
:   elseif(ext == "cpp" || ext == "cc" || ext == "hh")
:       execute '!g++ -Wall -Wextra -Werror -O1 -o a.out *.(cc|cpp|o) -lm ' arguments ' && echo "Compilation successfull"'
    " Assembly
:   elseif(ext == "asm")
:       !/home/kubby/68000/a68k % -o%:r.hex -s -n -rmal && echo "Compilation successfull"
:   endif
:endfunction

" F5: Run
map <F5> <ESC> :w <CR> :call Run()
:function! Run(...)
    " Extension of the current file
:   let ext = expand('%:e')
    " Arguments for the execution ("" if nothing given)
:   let arguments = a:0 >= 1 ? a:1 : ""
    " If run.sh exists, executes it
:   if(filereadable("run.sh"))
:       execute '!./run.sh ' arguments
    " else run the standard run command
    " C, Cpp and Cuda
:   elseif(ext == "c" || ext == "h" || ext == "cpp" || ext == "cc" || ext == "hh" || ext == "cu")
:       if(filereadable("a.out"))
:           execute '!./a.out ' arguments ' ; rm a.out'
:       else
:           !echo "Compiled file not found"
:       endif
    " Assembly
:   elseif(ext == "asm")
:       if(filereadable(expand("%:r") . ".hex"))
:           silent !(/home/kubby/68000/d68k.sh %:r.hex && rm %:r.hex) &
:       else
:           !echo "Compiled file not found"
:       endif
    " Bash scripts
:   elseif(ext == "sh")
:       execute '!bash ' expand('%:t') ' ' arguments
:   elseif(ext == "py")
:       execute '!python3 ' expand('%:t') ' ' arguments
:   endif
:endfunction

" F8: Compile and Run
map <F8> <ESC> :w <CR> :call CompileRun() <CR>
:function! CompileRun()
:   call Compile()
:   call Run()
:endfunction

" F9: Debug
map <F9> <ESC> :w <CR> :call Debug()
:function! Debug(...)
    " Extension of the current file
:   let ext = expand('%:e')
    " Arguments for the execution ("" if nothing given)
:   let runArgs = a:0 >= 1 ? a:1 : ""
    " Compilation arguments ("" if nothing given)
:   let compArgs = a:0 >= 2 ? a:2 : ""
    " C
:   if(ext == "c" || ext == "h")
:       execute '!gcc -Wall -Wextra -Werror -std=c99 -O0 -o a.out *.[co] -lm -g ' compArgs ' && gdb --args ./a.out ' runArgs ' ; rm ./a.out'
    " Cpp
:   elseif(ext == "cpp" || ext == "cc" || ext == "hh")
:       execute '!g++ -Wall -Wextra -Werror -O0 -o a.out *.(cc|cpp|o) -lm -g ' compArgs ' && gdb --args ./a.out ' runArgs ' ; rm ./a.out'
    " Assembly
:   elseif(ext == "asm")
:       call Compile()
:       call Run()
:   endif
:endfunction

" ------------------------------------------------------------------------------
" Plugins
" ------------------------------------------------------------------------------

set nocompatible
filetype off
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

Plugin 'VundleVim/Vundle.vim'
" Plugin 'Valloric/YouCompleteMe'
" Plugin 'joshdick/onedark.vim'
Plugin 'SirVer/ultisnips'
Plugin 'honza/vim-snippets'
Plugin 'justinmk/vim-syntax-extra'
Plugin 'vim-syntastic/syntastic'
Plugin 'Raimondi/delimitMate'
Plugin 'scrooloose/nerdtree'
Plugin 'Xuyuanp/nerdtree-git-plugin'
Plugin 'ntpeters/vim-better-whitespace'
Plugin 'powerline/powerline', {'rtp': 'powerline/bindings/vim'}

call vundle#end()
filetype plugin indent on

" Snippets
let g:UltiSnipsExpandTrigger="<tab>"
let g:UltiSnipsJumpForwardTrigger="<c-b>"
let g:UltiSnipsJumpBackwardTrigger="<c-z>"

" Syntastic
set statusline+=%#warningmsg#
set statusline+=%{SyntasticStatuslineFlag()}
set statusline+=%*
let g:syntastic_always_populate_loc_list = 1
let g:syntastic_auto_loc_list = 1
let g:syntastic_check_on_open = 1
let g:syntastic_check_on_wq = 0

" NERDtree
map <F2> :NERDTreeToggle<CR>
autocmd bufenter * if (winnr("$") == 1 && exists("b:NERDTree") && b:NERDTree.isTabTree()) | q | endif

" Powerline
set laststatus=2

" Better Whitespace
let g:better_whitespace_enabled=0 " Disables highlighting
let g:strip_whitespace_on_save=0
function EnableWSClean()
"    function WhiteSpaceClean(timer)
"        silent call StripWhitespace(0,2)
"    endfunction
"    let timer = timer_start(5000, 'WhiteSpaceClean', {'repeat': -1})
    let g:strip_whitespace_on_save=1
    let g:strip_whitelines_at_eof=1
    let g:strip_whitespace_confirm=0
endfunction

" ------------------------------------------------------------------------------
" File specific things
" ------------------------------------------------------------------------------

let ext = expand('%:e')
if(ext == "asm")
    " Turns Syntastic off for assembly files
    silent autocmd VimEnter * :SyntasticToggleMode
endif
if(ext == "c" || ext == "cpp" || ext == "cc" || ext == "cu")
    " Executes the header generation script for c and cpp files when F6 is pressed
    map <F6> <ESC> :w <CR> :silent !python ~/Code/Linux/Scripts/generate_header.py % <CR> <C-l>
endif
if(ext == "c" || ext == "cpp" || ext == "cc" || ext == "cu" || ext == "h" || ext == "hh" || ext == "asm" || ext == "py")
    " Enables the White space cleanup when the file is saved
    call EnableWSClean()
endif
if(ext == "c" || ext == "cpp" || ext == "cc" || ext == "h" || ext == "hh")
    " Swaps between source and header when F3 is pressed for c and cpp files
    let filename = expand('%:r')
    if(ext == "c")
        let filename .= ".h"
    elseif(ext == "cc" || ext == "cpp")
        let filename .= ".hh"
    elseif(ext == ".h")
        let filename .= ".c"
    elseif(ext == "hh")
        let filename .= ".cpp"
    endif
    map <F3> <ESC> execute '!vim filename &' <CR> :wq <CR>
endif

" ------------------------------------------------------------------------------
" Graphics and coloration
" ------------------------------------------------------------------------------

" Displays line numbers
set number
set cursorline

" Forces the tabs to be made of spaces
set tabstop=4 softtabstop=0 expandtab shiftwidth=4 smarttab

" Auto reloads the file if it's modified outside of vim
set autoread

" Enables mouse
set mouse=a

" Theme
" colorscheme onedark
colorscheme monokai

" Prevents line wrap
set nowrap

" Syntaxic coloration
syntax on
autocmd Filetype asm set syntax=asm68k
autocmd Filetype c set syntax=c
autocmd Filetype h set syntax=c

" Highlights text that goes over 80 charaters in red
highlight OverLength ctermfg=red guibg=#592929
match OverLength /\%81v.\+/

" Sets the background transparent
hi Normal guibg=NONE ctermbg=NONE
