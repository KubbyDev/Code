
			org			$4
			
Vector_001  dc.l    	Main
			
			org			$500
			
Main      	move.l  	#$FFFFFFFF,d0
			move.l  	#$FFFFFFFF,d1
			move.l  	#$00000000,d2
			move.l  	#$00000000,d3			
			
			move.l  	#$FFFFFFFF,d4	
			move.l  	#$FFFFFFFF,d5	
			move.l  	#$00000000,d6	
			move.l  	#$00000000,d7	
			
			add.l 		d4,d0			
			addx.l 		d5,d1			
			addx.l 		d6,d2			
			addx.l 		d7,d3
			
			illegal
			
