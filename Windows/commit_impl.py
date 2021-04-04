import os
import subprocess
import sys


# Executes the command and returns its exit code
def cmd_ret(cmd, silent=False):
    file = subprocess.DEVNULL if silent else None
    return subprocess.call(cmd, stdout= file, stderr=file)


# Executes the command and returns its output. Silent
def cmd_output(cmd):
    return os.popen(cmd).read()
    

def get_consent(msg):
    print(msg + " [Y/N] ", end='')
    res = input()
    return res == "" or res.lower() in ["yes", "y"]


def main():

    if len(sys.argv) == 1:
        print("Usage: commit <message> [tag]")
        return

    if cmd_output("git diff --name-only --cached").strip() != "":
        if get_consent("Detected staged files. Redo add anyways ?"):
            cmd_ret("git reset HEAD .")
            cmd_ret("git add .")
    else:
        cmd_ret("git add .")

    cmd_ret("git status")

    if not get_consent("Commit ?"):
        return
        
    if cmd_ret(f'git commit -m "{sys.argv[1]}"') != 0:
        return
    if len(sys.argv) >= 3:
        i = 0
        while cmd_ret(f'git tag -a "{sys.argv[2]}-{i}" -m "{sys.argv[1]}"', silent=True) != 0:
            i += 1
            
    cmd_ret("git push --follow-tags")


if __name__ == '__main__':
    main()
        
