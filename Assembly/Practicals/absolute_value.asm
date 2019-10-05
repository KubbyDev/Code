			
			org			$4
			
Vector_001  dc.l    	Main

			org			$500
			
Main        move.l		#-1,d0 		; Initialize D0.

Abs         tst.l 		d0			; Tests sign of d0
			bpl 		Done    	; Skips operation if d0 is positive
			neg 		d0			; negates d0
			
Done								; Operation done

			illegal
