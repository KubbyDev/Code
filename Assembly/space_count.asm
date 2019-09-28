			org 		$4
			
Vector_001 	dc.l 		Main

			org 		$500
			
Main 		movea.l 	#STRING,a0 		; A0 points to the string.

SpaceCount 	clr 		d0
Loop		tst.b 		(a0)			; Sets the N and Z flags for the character pointed by a0
			beq			Done			; If char \0 is encountered, the operation is done
			cmp.b		#' ',(a0)+      ; Checks if the character is a space and goes on to the next one
			bne			NotASpace		; If the character is not a space, skips the special treatment
			add.l 		#1,d0			; If the character is a space, increments the counter
NotASpace	bra 		Loop

Done
			
			illegal
			
			org 		$550
			
STRING 		dc.b 		"This string contains 4 spaces.",0
