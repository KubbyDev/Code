                ; ==============================
                ; Vector Initialization
                ; ==============================         

                org         $4

Vector_001      dc.l        Main

                ; ==============================
                ; Main Program
                ; ==============================

                org         $500

Main            movea.l     #String3,a0
                
                jsr         NextOp 

                illegal                

                ; ==============================
                ; Subroutines
                ; ==============================

; Removes all spaces of the string pointed by a0
RemoveSpace     movem.l     a0/a1,-(a7)
                movea.l     a0,a1                                

\loop           cmp.b       #' ',(a1)

                ; if the character is a space, goes to the next one
                beq         \nextchar

                ; moves the character on a0 and moves a0 by 1
                move.b      (a1),(a0)+

                ; Quits if the character is \0
                tst.b       (a1)
                beq         \quit           

                ; to go to the next char, a1 moves by 1
\nextchar       add.l       #1,a1
                bra         \loop

\quit           movem.l     (a7)+,a0/a1
                rts

; Returns true if at least one character in the string pointed by a0 is not a number
; Stores the result in the Z flag
IsCharError     move.l     a0,-(a7)   
                
                ; If the character is \0, returns true
\loop           tst.b       (a0) 
                beq         \false                
                
                ; If the character is not a digit, returns false 
                cmp.b       #'0',(a0)
                blo         \true
                cmp.b       #'9',(a0)
                bhi         \true
               
                adda.l      #1,a0 
                bra         \loop

                ; Returns false
\false          andi.b      #%11111011,ccr
                bra         \quit

                ; Returns true
\true           ori.b       #%00000100,ccr

\quit           move.l     (a7)+,a0
                rts

; Returns true if the string pointed by a0 represents an int larger than 32767
; Stores the result in the Z flag
IsMaxError      movem.l     d0/a0,-(a7)
                
                ; If the length is > 5 => return true, < 5 => return false
                jsr         StrLen
                cmp.l       #5,d0
                blo         \false
                bhi         \true
                
                ; If the length is 5, compares each character
                cmp.b       #'3',(a0)+
                bhi         \true 
                blo         \false
                cmp.b       #'2',(a0)+
                bhi         \true
                blo         \false
                cmp.b       #'7',(a0)+
                bhi         \true
                blo         \false
                cmp.b       #'6',(a0)+
                bhi         \true               
                blo         \false
                cmp.b       #'7',(a0)
                bhi         \true

                ; Returns false
\false          andi.b      #%11111011,ccr
                bra         \quit 

                ; Returns true
\true           ori.b       #%00000100,ccr

\quit           movem.l     (a7)+,d0/a0
                rts

; Returns the length of the string pointed by a0 (on d0)
StrLen          move.l      a0,-(a7) ; Save A0 on the stack.
                clr.l       d0

\loop           tst.b       (a0)+
                beq         \quit
                addq.l      #1,d0
                bra         \loop

\quit           movea.l     (a7)+,a0 ; Restore AO from the stack.
                rts

; Returns the 15-bit unsigned integer represented by the string pointed by a0
; Sets the Z flag to 1 if there is no error, 0 if there is
Convert         ; Checks if all the characters are digits
                jsr         IsCharError
                beq         \quit

                ; Checks if the integer is less than the maximum
                jsr         IsMaxError
                beq         \quit
                
                jsr         Atoui

                ; Inverts the Z flag
\quit           eori.b      #%00000100,ccr
                rts

; Returns the 16-bit unsigned integer represented by the string pointed by a0
Atoui           movem.l     d1/a0,-(a7)
                clr.l       d0
                clr.l       d1           

\loop           move.b      (a0)+,d1
                beq         \quit       ; If it encounters the \0 char, quits
            
                ; If there is a number
                mulu.w      #10,d0
                subi.b      #'0',d1
                add.w       d1,d0

                bra         \loop

\quit           movem.l     (a7)+,d1/a0
                rts

; Prints a string in the video output. a0 is the string to display
; d1 is the x position of the first char, d2 is the y position
Print           movem.l      a0/d0/d1,-(a7)
                
                ; Stops if the character is a null terminator
\loop           tst.b       (a0)
                beq         \quit

                ; Draws the character
                move.b      (a0),d0
                jsr         PrintChar
                
                ; Goes to the next character
                add.b       #1,d1
                adda.l      #1,a0

                bra         \loop

\quit           movem.l      (a7)+,a0/d0/d1
                rts

; Prints a char in the video output. d0 is the char to display
; d1 is the x position of the char, d2 is the y position
PrintChar       incbin      "../Given/PrintChar.bin"

; Returns (in a0) the position of the first operator or null terminator
NextOp          
\loop           tst.b       (a0)
                beq         \quit

                cmp.b       #'+',(a0)
                beq         \quit
                
                cmp.b       #'-',(a0)
                beq         \quit
                
                cmp.b       #'*',(a0)
                beq         \quit
                
                cmp.b       #'/',(a0)
                beq         \quit
                
                ; Next char
                adda.l      #1,a0
                bra         \loop

\quit           rts
               
                ; ==============================
                ; Data
                ; ==============================

                org         $700

String1         dc.b        "32767",0
String2         dc.b        "Ceci est un message",0
String3         dc.b        "43+59",0
