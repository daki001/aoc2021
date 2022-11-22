
def readFile(path):
	erg = list()
	with open(path) as f:
		for line in f:
			lineList = list()
			for i in line.strip():
				lineList.append(int(i))

			erg.append(lineList)

	return erg

def printList(square):
	for i in square:
		print(i)

def graphentheorie(square, xCord, yCord):
	erg = list()
	for i in range(len(square)):
		erg.append(list())
		for _ in range(len(square[0])):
			erg[i].append(-1)
	next = dict()
	erg[0][0] = 0
	next[square[xCord + 1][yCord]] = [[xCord + 1, yCord]]
	#print(next.keys())
	if square[xCord][yCord + 1] in next:
		next[square[xCord][yCord + 1]].append([xCord, yCord + 1])
	else:
		next[square[xCord][yCord + 1]] = [[xCord, yCord + 1]]
	while len(next.keys()) > 0:
		lowest = sorted(next)[0]
		#print(lowest)
		#print(next)
		#print(lowest)
		#print(next)
		n = next.get(lowest)[0]
		if n == [2, 1]:
			print(lowest)
			print(next)
		#print(n)
		#print(next[lowest])
		next[lowest].pop(0)
		if len(next[lowest]) == 0:
			next.pop(lowest)
		if erg[n[0]][n[1]] != -1:
			continue
		erg[n[0]][n[1]] = lowest

		if n[0] + 1 < len(erg):
			costs = lowest + square[n[0] + 1][n[1]]
			if costs in next:
				next[costs].append([n[0] + 1, n[1]])
			else:
				next[costs] = [[n[0] + 1, n[1]]]

		if n[0] - 1 >= 0:
			costs = lowest + square[n[0] - 1][n[1]]
			if costs in next:
				next[costs].append([n[0] - 1, n[1]])
			else:
				next[costs] = [[n[0] - 1, n[1]]]

		if n[1] + 1 < len(erg[0]):
			costs = lowest + square[n[0]][n[1] + 1]
			if costs in next:
				next[costs].append([n[0], n[1] + 1])
			else:
				next[costs] = [[n[0], n[1] + 1]]

		if n[1] - 1 >= 0:
			costs = lowest + square[n[0]][n[1] - 1]
			if costs in next:
				next[costs].append([n[0], n[1] - 1])
			else:
				next[costs] = [[n[0], n[1] - 1]]

	return erg


if __name__ == '__main__':
	file = readFile('resources/testInput.txt')
	print(file)
	test = dict()
	test[5] = 'test'
	test[2] = 'hui'
	test = sorted(test)
	print(test)
	#print(calculatePath(file, 0, 0, list(), 0))
	printList(graphentheorie(file, 0, 0))


	file = readFile('resources/input.txt')
	print()
	printList(graphentheorie(file, 0, 0))


