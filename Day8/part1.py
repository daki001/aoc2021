def readFile(path):
	erg = list()
	with open(path) as f:
		for lines in f:
			lines = lines.split('|')[1].strip()
			erg += lines.split(' ')

	return erg


def countSimples(input):
	counter = 0
	for i in input:
		if len(i) == 2 or len(i) == 3 or len(i) == 7 or len(i) == 4:
			print(i)
			counter += 1

	return counter



if __name__ == '__main__':
	input = readFile('resources/testInput.txt')
	print(countSimples(input))

	input = readFile('resources/input.txt')
	print(countSimples(input))