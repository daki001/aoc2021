def readInput(path):
	ergList = list()

	with open(path) as f:
		for l in f:
			erg = ''
			input = l
			for hex in input.strip():
				val = int(hex, 16)
				val = format(val, '04b')
				# val = format(bin(val)[2:])
				erg += val
			ergList.append(erg)
	return ergList


def interpreteNumbers(values):
	erg = ''
	counter = 0
	#print(values)
	while counter < len(values) and values[counter] == '1':
		erg += values[counter + 1:counter + 5]

		counter += 5
	#print(values)
	#print('here')
	erg += values[counter + 1:counter + 5]
	#print(values[counter + 1:counter + 5])
	return [int(erg, 2), counter + 5]



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
			#print(length)
			packet = packet[15:]
			count = 0
			part1 = -1
			sum = -1
			v = 0
			while count < length:
				round = interpretePacket2(packet)
				v += round[2]
				if type == 0:
					if sum == -1:
						sum = round[0]
					else:
						sum += round[0]
				elif type == 1:
					if sum == -1:
						sum = round[0]
					else:
						sum *= round[0]
				elif type == 2:
					if sum == -1:
						sum = round[0]
					else:
						sum = min(round[0], sum)
				elif type == 3:
					sum = max(round[0], sum)
				elif type == 5:
					if part1 == -1:
						part1 = round[0]
					else:
						if part1 > round[0]:
							sum = 1
						else:
							sum = 0
				elif type == 6:
					if part1 == -1:
						part1 = round[0]
					else:
						if part1 > round[0]:
							sum = 0
						else:
							sum = 1
				elif type == 7:
					if part1 == -1:
						part1 = round[0]
					else:
						if part1 == round[0]:
							sum = 1
						else:
							sum = 0
				packet = packet[round[1]:]
				count += round[1]
			return [sum, count + 7 + 15, version + v]


		else:
			count = int(packet[:11], 2)
			#print(count)
			packet = packet[11:]
			sum = -1
			counter = 0
			part1 = -1
			v = 0
			for i in range(count):
				round = interpretePacket2(packet)
				v += round[2]
				counter += round[1]
				if type == 0:
					if sum == -1:
						sum = round[0]
					else:
						sum += round[0]
				elif type == 1:
					if sum == -1:
						sum = round[0]
					else:
						sum *= round[0]
				elif type == 2:
					if sum == -1:
						sum = round[0]
					else:
						sum = min(round[0], sum)
				elif type == 3:
					sum = max(round[0], sum)
				elif type == 5:
					if part1 == -1:
						part1 = round[0]
					else:
						if part1 > round[0]:
							sum = 1
						else:
							sum = 0
				elif type == 6:
					if part1 == -1:
						part1 = round[0]
					else:
						if part1 > round[0]:
							sum = 0
						else:
							sum = 1
				elif type == 7:
					if part1 == -1:
						part1 = round[0]
					else:
						if part1 == round[0]:
							sum = 1
						else:
							sum = 0

				packet = packet[round[1]:]
			return [sum, counter + 7 + 11, version + v]
	else:
		erg = interpreteNumbers(packet)
		return [erg[0], erg[1] + 6, version]

if __name__ == '__main__':
	input = readInput('resources/testInput.txt')
	#print(input)
	for i in input:
		print(interpretePacket2(i))

	input = readInput('resources/input.txt')
	#print(input)
	for i in input:
		print(interpretePacket2(i))
	#print(interpreteNumbers('101111111000101000'))
