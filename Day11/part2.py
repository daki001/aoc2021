def readFile(path):
	erg = list()
	with open(path) as f:
		for line in f:
			line = line.strip()
			lineList = list()
			for num in line:
				lineList.append(int(num))

			erg.append(lineList)
	return erg


def increaseAdjacent(input, xCord, yCord):
	greaterZeroX = xCord > 0
	greaterZeroY = yCord > 0
	lowerMaxX = xCord < (len(input) - 1)
	lowerMaxY = yCord < (len(input[0]) - 1)

	if greaterZeroX:
		input[xCord-1][yCord] += 1
		if greaterZeroY:
			input[xCord - 1][yCord - 1] += 1
		if lowerMaxY:
			input[xCord - 1][yCord + 1] += 1



	if lowerMaxX:
		input[xCord+1][yCord] += 1
		if greaterZeroY:
			input[xCord + 1][yCord - 1] += 1
		if lowerMaxY:
			input[xCord + 1][yCord + 1] += 1

	if greaterZeroY:
		input[xCord][yCord - 1] += 1
	if lowerMaxY:
		input[xCord][yCord + 1] += 1

	return input


def flashes(input):
	erg = 0
	for line in range(len(input)):
		for octopus in range(len(input[line])):
			if input[line][octopus] > 9:
				input[line][octopus] = -10000
				erg += 1
				input = increaseAdjacent(input, line, octopus)

	return erg


def round(input):
	#print(input)
	for line in input:
		for octopus in range(len(line)):
			line[octopus] += 1

	count = 0
	#print(input)
	roundCount = -1
	#print(input)
	while roundCount != 0:
		roundCount = flashes(input)
		count += roundCount
	#print(input)
	for line in input:
		for octopus in range(len(line)):
			if line[octopus] > 9 or line[octopus] < 0:
				line[octopus] = 0

	return count


def calculateFlashes(input):
	count = 0
	erg = 0
	all = len(input) * len(input[0])
	while erg != all:
		erg = round(input)
		count += 1
	return count



if __name__ == '__main__':
	file = readFile('resources/testInput.txt')
	print(calculateFlashes(file))

	file = readFile('resources/input.txt')
	print(calculateFlashes(file))