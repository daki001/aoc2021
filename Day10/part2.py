def readFile(path):
	erg = list()
	with open(path) as f:
		for line in f:
			erg.append(line.strip())

	return erg


def calculateIncompletes(input):
	erg = list()
	for line in input:
		calc = findCorrupted(line)
		if not calc[0]:
			if len(calc[1]) > 0:
				erg.append(calculateIncompleteLine(calc[1]))
				erg = sorted(erg)


	return erg[len(erg)//2]

def calculateIncompleteLine(chars):
	erg = 0
	values = dict()
	values['('] = 1
	values['['] = 2
	values['{'] = 3
	values['<'] = 4
	print(chars)
	for i in chars:
		erg *= 5
		erg += values[i]

	return erg

def findCorrupted(line):
	closers = list()
	brackets = dict()
	brackets['('] = ')'
	brackets['{'] = '}'
	brackets['['] = ']'
	brackets['<'] = '>'

	for char in line:
		if char in ['(', '[', '{', '<']:
			closers.insert(0, char)
		else:
			if len(closers) > 0 and char == brackets[closers[0]]:
				closers.pop(0)
			else:
				return [True]

	return [False, closers]


if __name__ == '__main__':
	lines = readFile('resources/testInput.txt')
	print(calculateIncompletes(lines))

	lines = readFile('resources/input.txt')
	print(calculateIncompletes(lines))
