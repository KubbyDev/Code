path=/sys/class/gpio

echo 17 > $path/export
echo 27 > $path/export

sleep 1

echo out > $path/gpio17/direction
echo in > $path/gpio27/direction
# Apparently it's complicated to set a pull down with sysfs 
# so I just added a resistor in the circuit

state=1
toggle () {
    state=$(((state+1)%2))
    echo $state > $path/gpio17/value
}
toggle

while true; do
    if [ $(cat $path/gpio27/value) = 1 ]; then
        toggle
        sleep 0.2
        while [ $(cat $path/gpio27/value) = 1 ]; do
            sleep 0.001
        done
    fi        
done

echo 17 > $path/unexport
echo 27 > $path/unexport
