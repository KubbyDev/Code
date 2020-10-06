cd ~/Code/Linux/Config

# Puts the current files in the trash to avoid
# deleting them by accident
trash ~/.vimrc
trash ~/.bash_aliases
trash ~/.bashrc
trash ~/.zshrc

dos2unix .vimrc
dos2unix .zshrc
dos2unix .bash_aliases
dos2unix .bashrc

cp .vimrc ~/
cp .bash_aliases ~/
cp .bashrc ~/
cp .zshrc ~/
