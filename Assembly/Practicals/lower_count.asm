

 ; ==============================
 ; Vector Initialization
 ; ==============================
 org $4
vector_001 dc.l Main
 ; ==============================
 ; Main Program
 ; ==============================
 org $500
Main movea.l #String1,a0
 jsr LowerCount
 movea.l #String2,a0
 jsr LowerCount
 illegal
 ; ==============================
 ; Subroutines
 ; ==============================

                org             $4

Vector_001      dc.l            Main

                org             $500
            
Main            movea.l     #String1,a0
                jsr         LowerCount
            
LowerCount      clr.l       d0
            
                ; Checks if the end of the string is reached
Loop            tst.b       (a0)            
                beq     Done
            
                ; Checks if the character is in lower case
                cmp.b       #'a',(a0)
                blo         NextChar    ; Goes to loop if char < 'a'
                cmp.b       #'z',(a0)
                bhi         NextChar    ; Goes to loop if char > 'z'
            
                ; If the character is in lower case, increments the counter
                add.l       #1,d0

NextChar        adda.l      #1,a0
                bra         Loop

Done        
            
                illegal
            
                org $550
            
STRING          dc.b        "This string contains 29 small letters.",0
