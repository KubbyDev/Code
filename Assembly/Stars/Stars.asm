					org		$0

vector_000			dc.l	$ff6a00
vector_001			dc.l	main

					org		$500

main				incbin	"Stars.bin"
