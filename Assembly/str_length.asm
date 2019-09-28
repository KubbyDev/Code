
			org			$4
			
Vector_001  dc.l    	Main

			org			$500
			
Main        movea.l		#STRING,a0  ; A0 points to the string.

StrLen      clr.l 		d0			; Sets the counter to 0
Loop		tst.b		(a0)+		; Sets the N and Z flags for the current character and goes on to the next one
			beq			Done		; If the current character is 0 then the operation is done
			add.l		#1,d0		; Increments the character counter
			bra			Loop		; Loops if the string is not over
			
Done								; The result is in d0

			illegal	
			
			org			$550

STRING      dc.b		"This string is made up of 40 characters.",0
