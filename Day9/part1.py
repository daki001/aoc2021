def readFile(path):
	square = list()
	with open(path) as f:
		for line in f:
			line = line.strip()
			line = list(line)
			for i in range(len(line)):
				line[i] = int(line[i])
			square.append(line)

	return square


def checkNeighbors(square, posX, posY):
	lowest = True
	if posX > 0:
		lowest &= square[posX - 1][posY] > square[posX][posY]

	if posX < len(square) - 1:
		lowest &= square[posX + 1][posY] > square[posX][posY]


	if posY > 0:
		lowest &= square[posX][posY - 1] > square[posX][posY]

	if posY < len(square[0]) - 1:
		lowest &= square[posX][posY + 1] > square[posX][posY]

	return lowest


def findLowest(square):
	erg = 0
	#print(square)
	for i in range(len(square)):
		for j in range(len(square[i])):
			if checkNeighbors(square, i, j):
				#print(square[i][j])
				erg += square[i][j] + 1

	return erg


if __name__ == '__main__':
	square = readFile('resources/testInput.txt')
	print(findLowest(square))

	square = readFile('resources/input.txt')
	print(findLowest(square))