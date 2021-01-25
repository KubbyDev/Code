cd ~/Code/Linux/Config

# Puts the current files in tmp to avoid
# deleting them by accident
mv ~/.vimrc /tmp
mv ~/.bash_aliases /tmp
mv ~/.bashrc /tmp
mv ~/.zshrc /tmp

dos2unix .vimrc
dos2unix .zshrc
dos2unix .bash_aliases
dos2unix .bashrc

cp .vimrc ~/
cp .bash_aliases ~/
cp .bashrc ~/
cp .zshrc ~/
