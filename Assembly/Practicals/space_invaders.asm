                ; ==============================
                ; Vector Initialization
                ; ==============================

                org         $0

vector_000      dc.l        VIDEO_START
Vector_001      dc.l        Main

                ; ==============================
                ; Main Program
                ; ==============================

                org         $500

Main            move.l      #20,d0
                jsr         WhiteSquare

                illegal

                ; ==============================
                ; Subroutines
                ; ==============================

HLines          ; Save registers on the stack.
                movem.l     d6/d7/a0,-(a7)
                ; A0 points to the video memory.
                lea         VIDEO_START,a0
                ; D7.W = Loop counter
                ; = Number of iterations - 1 (because DBRA is used).
                ; ------------------------------
                ; Number of iterations = Number of white and black stripes
                ; Height of a white stripe = 8 pixels
                ; Height of a black stripe = 8 pixels
                ; Number of white and black stripes = Height of the window / 2x8
                move.l      #VIDEO_HEIGHT/16-1,d7
\loop           ; Draw a white stripe (8 white lines).
                ; ------------------------------
                ; D6.W = Loop counter
                ; = Number of iterations - 1 (because DBRA is used).
                ; ------------------------------
                ; Number of iterations = Number of long words
                ; Number of long words = Number of bytes / 4
                ; Number of bytes = BYTE_PER_LINE x Height of a white stripe
                ; Height of a stripe = 8 pixels
                move.w      #BYTE_PER_LINE*8/4-1,d6
\white_loop     move.l      #$ffffffff,(a0)+
                dbra        d6,\white_loop

                ; Draw a black stripe (8 black lines).
                move.w      #BYTE_PER_LINE*8/4-1,d6
\black_loop     clr.l       (a0)+
                dbra        d6,\black_loop

                ; Branch to loop as long as there are
                ; white and black stripes to draw.
                dbra        d7,\loop

                ; Restore registers from the stack and return from subroutine.
                movem.l     (a7)+,d6/d7/a0
                rts

WhiteSquare32   movem.l     a0/d0,-(a7)
                
                move.l      #$ffd6dc,a0
                move.l      #32,d0
\loop           move.l      #$ffffffff,(a0)
                add.l       #BYTE_PER_LINE,a0
                dbra        d0,\loop

                movem.l     (a7)+,a0/d0
                rts

WhiteLine       movem.l     a0/d0,-(a7)
                
\loop           move.b      #$ff,(a0)+
                dbra        d0,\loop

                movem.l     (a7)+,a0/d0
                rts


WhiteSquare128  movem.l     a0/d0,-(a7)
                
                move.l      #$ffd6dc,a0
                move.l      #128,d0

\loop           jsr         WhiteLine

                add.l       #BYTE_PER_LINE,a0
                dbra        d0,\loop

                movem.l     (a7)+,a0/d0
                rts

WhiteSquare     movem.l     a0/d0/d1,-(a7)

                move.l      #(VIDEO_START+VIDEO_SIZE/2+BYTE_PER_LINE/2),a0
                move.l      d0,d1
                divu.w      #2,d1
                sub.w       d1,a0
                mulu.w      #BYTE_PER_LINE*8,d1
                sub.w       d1,a0
                
                move.l      d0,d1
                mulu.w      #8,d1
                subq.w      #1,d1
                subq.w      #1,d0

\loop           jsr         WhiteLine
                add.l       #BYTE_PER_LINE,a0
                dbra        d1,\loop

                movem.l     (a7)+,a0/d0/d1
                rts

                ; ==============================
                ; Data
                ; ==============================

                org         $700

VIDEO_START     equ         $ffb500 ; Starting address
VIDEO_WIDTH     equ         480 ; Width in pixels
VIDEO_HEIGHT    equ         320 ; Height in pixels
VIDEO_SIZE      equ         (VIDEO_WIDTH*VIDEO_HEIGHT/8) ; Size in bytes
BYTE_PER_LINE   equ         (VIDEO_WIDTH/8) ; Number of bytes per line

