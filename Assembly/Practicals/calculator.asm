                ; ==============================
                ; Vector Initialization
                ; ==============================         

                org         $4

Vector_001      dc.l        Main

                ; ==============================
                ; Main Program
                ; ==============================

                org         $500

Main            movea.l     #String1,a0
                jsr         RemoveSpace
                jsr         IsCharError
                jsr         IsMaxError

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

                ; Returns true
\false           andi.b      #%11111011,ccr
                bra         \quit

                ; Returns false
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
                
                ; ==============================
                ; Data
                ; ==============================

                org         $700

String1         dc.b        "32767",0
