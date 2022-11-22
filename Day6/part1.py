def readFile(path):
	with open(path) as f:
		input = f.readline()
		erg = list()
		for num in input.split(','):
			erg.append(int(num))
		return erg

def nextDay(input):
	for fish in range(len(input)):
		input[fish] -= 1
		if input[fish] == -1:
			input[fish] = 6
			input.append(8)
	return input




if __name__ == '__main__':
	input = readFile('resources/testInput.txt')
	for i in range(18):
		#print(i)
		#print(input)
		huibu = list()
		for i1 in range(7):
			huibu.append(0)

		for i1 in input:
			if i1 < 7:
				huibu[(i1 + i) % 7] += 1
		print(huibu)
		input = nextDay(input)

	print(len(input))

	input = readFile('resources/input.txt')
	for i in range(256):
		#input = nextDay(input)
		pass

	print(len(input))