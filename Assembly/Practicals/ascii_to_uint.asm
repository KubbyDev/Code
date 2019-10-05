
            org         $4

Vector_001  dc.l        Main

            org         $500

Main        move.l      #str1,a0
            jsr         Atoui

            move.l      #str2,a0
            jsr         Atoui

            move.l      #str3,a0
            jsr         Atoui

            move.l      #str4,a0
            jsr         Atoui

            move.l      #str5,a0
            jsr         Atoui

            illegal

Atoui       movem.l     d1/a0,-(a7)
            clr.l       d0
            clr.l       d1           

\loop       move.b      (a0)+,d1
            beq         \quit       ; If it encounters the \0 char, quits
            
            ; If there is a number
            mulu.w      #$A,d0
            subi.b      #'0',d1
            add.w       d1,d0

            bra         \loop

\quit       movem.l     (a7)+,d1/a0
            rts

            org         $700

str1        dc.b        "54893",0
str2        dc.b        "0",0
str3        dc.b        "5",0
str4        dc.b        "65535",0
str5        dc.b        "",0
