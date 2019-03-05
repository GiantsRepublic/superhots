# Simple example of reading the MCP3008 analog input channels and printing
# them all out.
# Author: Tony DiCola
# License: Public Domain
import time

# Import SPI library (for hardware SPI) and MCP3008 library.
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008


# Software SPI configuration:
# CLK  = 23
# MISO = 21
# MOSI = 19
# CS   = 24
# mcp = Adafruit_MCP3008.MCP3008(clk=CLK, cs=CS, miso=MISO, mosi=MOSI)

# Hardware SPI configuration:
SPI_PORT   = 0
SPI_DEVICE = 0
mcp = Adafruit_MCP3008.MCP3008(spi=SPI.SpiDev(SPI_PORT, SPI_DEVICE))


print('Reading MCP3008 values, press Ctrl-C to quit...')

print ('Channel 1')
print ('---------')

# Main program loop.
while True:
    # Read ADC Channel 7 value.
    value = mcp.read_adc(0)
    # Print ADC Channel 7 value
    print('| {0:>4} |'.format(value))
    # Pause for half a second.
    time.sleep(0.5)
