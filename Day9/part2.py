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

def findBasin(square, xCord, yCord):
	if square[xCord][yCord] != 9:

		if checkNeighbors(square, xCord, yCord):
			return [1, xCord, yCord]



def findLowest(square):
	erg = 0
	#print(square)
	basin = dict()
	for i in range(len(square)):
		for j in range(len(square[i])):
			if square[i][j] != 9:
				xCord = i
				yCord = j

				while not checkNeighbors(square, xCord, yCord):
					#print(xCord)
					if len(square) - 1 > xCord:
						if square[xCord + 1][yCord] < square[xCord][yCord]:
							xCord += 1
							continue

					if xCord > 0:
						if square[xCord - 1][yCord] < square[xCord][yCord]:
							xCord -= 1
							continue

					if len(square[xCord]) - 1 > yCord:
						if square[xCord][yCord + 1] < square[xCord][yCord]:
							yCord += 1
							continue

					if yCord > 0:
						if square[xCord][yCord - 1] < square[xCord][yCord]:
							yCord -= 1
							continue

				if str(xCord) + '/' + str(yCord) in basin.keys():
					basin[str(xCord) + '/' + str(yCord)] += 1
				else:
					basin[str(xCord) + '/' + str(yCord)] = 1


			#if checkNeighbors(square, i, j):
				#print(square[i][j])
			#	erg += square[i][j] + 1
	print(basin)
	values = basin.values()
	values = sorted(values)
	values = values[::-1]
	print(values[0] * values[1] * values[2])
	return basin


if __name__ == '__main__':
	square = readFile('resources/testInput.txt')
	print(findLowest(square))

	square = readFile('resources/input.txt')
	print(findLowest(square))