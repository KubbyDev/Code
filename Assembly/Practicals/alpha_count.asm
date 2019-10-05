            ; ==============================
            ; Vector Initialization
            ; ==============================

            org         $4

vector_001  dc.l        Main

            ; ==============================
            ; Main Program
            ; ==============================

            org $500

Main        movea.l     #String1,a0
            jsr         AlphaCount
            movea.l     #String2,a0
            jsr         AlphaCount
            
            illegal
            
            ; ==============================
            ; Subroutines
            ; ==============================

            ; The return value is in d0, the bounds are in d1 and d2
CharsCount  movem.l     d3/a0,-(a7) ; Save registers on the stack.
            clr.l       d0

\loop       move.b      (a0)+,d3    ; The move instruction also updates the flags
            beq         \quit
            
            ; If the character is outside the bounds, skips it
            cmp.b       d1,d3
            blo         \loop
            cmp.b       d2,d3
            bhi         \loop
            
            ; If the character is in the bounds
            addq.l      #1,d0
            bra         \loop

\quit       movem.l     (a7)+,d3/a0 ; Restore registers from the stack.
            rts

LowerCount  movem.l     d1/d2,-(a7)
            move.b      #'a',d1
            move.b      #'z',d2
            jsr         CharsCount
            movem.l     (a7)+,d1/d2
            rts

UpperCount  movem.l     d1/d2,-(a7)
            move.b      #'A',d1
            move.b      #'Z',d2
            jsr         CharsCount
            movem.l     (a7)+,d1/d2
            rts

DigitCount  movem.l     d1/d2,-(a7)
            move.b      #'0',d1
            move.b      #'9',d2
            jsr         CharsCount
            movem.l     (a7)+,d1/d2
            rts

            ; d1 stores the sum, d0 stores the return values
AlphaCount  move.l      d1,-(a7)
            
            jsr         LowerCount
            move.l      d0,d1
            jsr         UpperCount
            add.l       d0,d1
            jsr         DigitCount
            add.l       d1,d0       ; Moves the sum to d0
            
            move.l      (a7)+,d1
            rts

            ; ==============================
            ; Data
            ; ==============================

String1     dc.b        "This string contains 42 alphanumeric characters.",0
String2     dc.b        "This one only 13.",0
