			org		$4
			
Vector_001  dc.l    Main

			org		$500
			
Main        movea.l	#TAB,a0		; Initialize A0 with the address of the tab.
			move.b	(a0)+,d0	; Number 1        -> D0.B ; A0 + 1 -> A0
			add.b	(a0)+,d0	; Number 2 + D0.B -> D0.B ; A0 + 1 -> A0
			add.b	(a0)+,d0	; Number 3 + D0.B -> D0.B ; A0 + 1 -> A0
			add.b	(a0)+,d0	; Number 4 + D0.B -> D0.B ; A0 + 1 -> A0
			add.b	(a0),d0		; Number 5 + D0.B -> D0.B
			move.b	d0,SUM      ; D0.B -> (SUM)
			
			illegal
			
			org		$550
			
TAB         dc.b	18,3,5,9,14	; Tab containing the 5 numbers.
SUM         ds.b	1			; Reserve 8 bits to store the sum.
