def readFile(path):
	erg = dict()
	with open(path) as f:
		for line in f:
			line = line.strip()
			line = line.split('-')
			if line[0] not in erg.keys():
				erg[line[0]] = list()
			erg[line[0]].append(line[1])


			if line[1] not in erg.keys():
				erg[line[1]] = list()
			erg[line[1]].append(line[0])

	return erg


def calculatePaths(input, current, path):
	erg = list()
	if current == 'end':
		return [path + current]

	path += current + ','
	for cave in input[current]:
		if cave.upper() != cave:
			if cave not in path.split(','):
				erg += calculatePaths(input, cave, path)
		else:
			erg += calculatePaths(input, cave, path)

	return erg



if __name__ == '__main__':
	input = readFile('resources/testInput.txt')
	print(len(calculatePaths(input, 'start', '')))
	input = readFile('resources/testInput2.txt')
	print(len(calculatePaths(input, 'start', '')))
	input = readFile('resources/testInput3.txt')
	print(len(calculatePaths(input, 'start', '')))
	input = readFile('resources/input.txt')
	print(len(calculatePaths(input, 'start', '')))