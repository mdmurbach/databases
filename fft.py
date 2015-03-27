import numpy as np
import matplotlib.pyplot as plt

# Open File
f = open('250mA.txt','r')

# Read in first 8 lines of file header
    # Voltage(V)
    # Timebase
    # Freq(Hz)
    # Cycles
    # Pts/Cycle
    # Samples/Pt
    # EGain
    # ESUP
name = []
value = []
for i in range(0,8):
    line = f.readline().strip()
    columns = line.split()
    name.append(columns[0])
    value.append(float(columns[1]))

# To Print Header Lines    
#for i in range(len(name)):
#    print name[i] + ' = ' + str(value[i]) + '%d' % (i)

# Read in current and voltage data (skip Current/Voltage Header)
#current, voltage = np.loadtxt(f, dtype={'names': ('current','voltage'),'formats':('f8','f8')},skiprows=1,unpack=True)

data = np.loadtxt(f, dtype={'names': ('current','voltage'),'formats':('f8','f8')},skiprows=1)

# To Print Data Lines
#for lines in range(len(current)):
#  print current[lines],voltage[lines]

# To Plot Raw Data
# plt.plot(current)
# plt.show()

current = data['current']
voltage = data['voltage']
current = current - np.mean(current)
voltage = voltage - np.mean(voltage)

currenthat = np.fft.fft(current)
voltagehat = np.fft.fft(voltage)
freq = np.fft.fftfreq(len(current),1/value[4])

harmonicsI = []
harmonicsV = []
for i in range(1,4):
    harmonicsI.append(currenthat[i*value[3]])
    harmonicsV.append(voltagehat[i*value[3]])
    
print harmonicsI[0],harmonicsI[1],harmonicsI[2]
print harmonicsV[0],harmonicsV[1],harmonicsV[2]

# Plot the time-domain signals
plt.figure(1)
plt.subplot(211)
plt.plot(current)

plt.subplot(212)
plt.plot(voltage)

# Plot the frequency-domain signals
plt.figure(2)
plt.subplot(221)
plt.plot(freq,currenthat.real)
plt.title('Real Current')
plt.xlim(0,5)

plt.subplot(222)
plt.plot(freq,currenthat.imag)
plt.title('Imaginary Current')
plt.xlim(0,5)

plt.subplot(223)
plt.plot(freq,voltagehat.real)
plt.title('Real Voltage')
plt.xlim(0,5)

plt.subplot(224)
plt.plot(freq,voltagehat.imag)
plt.title('Imaginary Voltage')
plt.xlim(0,5)

plt.show()