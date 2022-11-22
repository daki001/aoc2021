def readInput(path):
	erg = ''
	with open(path) as f:
		input = f.readline()
		for hex in input:
			val = int(hex, 16)
			val = format(val, '04b')
			# val = format(bin(val)[2:])
			erg += val
	return erg


def interpreteNumbers(values):
	erg = ''
	counter = 0
	print(values)
	while counter < len(values) and values[counter] == '1':
		erg += values[counter + 1:counter + 5]

		counter += 5
	print(values)
	print('here')
	erg += values[counter + 1:counter + 5]
	print(values[counter + 1:counter + 5])
	return [int(erg, 2), counter + 5]


def interpretePacket(packet):
	version = int(packet[:3], 2)
	packet = packet[3:]
	#print(packet)
	type = int(packet[:3], 2)
	packet = packet[3:]
	#print(type)
	if type != 4:
		typeLength = int(packet[0])
		packet = packet[1:]
		#print(packet)
		if typeLength == 0:
			length = int(packet[:15], 2)
			print(length)
			packet = packet[15:]
			count = 0
			sum = 0
			while count < length:
				round = interpretePacket(packet)
				sum += round[0]
				packet = packet[round[1]:]
				count += round[1]
			return [sum, count + 7]


		else:
			count = int(packet[:11], 2)
			print(count)
			packet = packet[11:]
			sum = 0
			counter = 0
			for i in range(count):
				round = interpretePacket(packet)
				counter += round[1]
				sum += round[0]
				packet = packet[round[1]:]
			return [sum, counter + 7]
	else:
		erg = interpreteNumbers(packet)
		return [erg[0], erg[1] + 6]



def interpretePacket2(packet):
	version = int(packet[:3], 2)
	packet = packet[3:]
	#print(packet)
	type = int(packet[:3], 2)
	packet = packet[3:]
	#print(type)
	if type != 4:
		typeLength = int(packet[0])
		packet = packet[1:]
		#print(packet)
		if typeLength == 0:
			length = int(packet[:15], 2)
			print(length)
			packet = packet[15:]
			count = 0
			sum = 0
			v = 0
			while count < length:
				round = interpretePacket2(packet)
				v += round[2]
				sum += round[0]
				packet = packet[round[1]:]
				count += round[1]
			return [sum, count + 7 + 15, version + v]


		else:
			count = int(packet[:11], 2)
			print(count)
			packet = packet[11:]
			sum = 0
			counter = 0
			v = 0
			for i in range(count):
				round = interpretePacket2(packet)
				v += round[2]
				counter += round[1]
				sum += round[0]
				packet = packet[round[1]:]
			return [sum, counter + 7 + 11, version + v]
	else:
		erg = interpreteNumbers(packet)
		return [erg[0], erg[1] + 6, version]

if __name__ == '__main__':
	input = readInput('resources/testInput.txt')
	print(interpretePacket2(input))

	input = readInput('resources/input.txt')
	print(interpretePacket2(input))
	#print(interpreteNumbers('101111111000101000'))
