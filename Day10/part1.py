def readFile(path):
	erg = list()
	with open(path) as f:
		for line in f:
			erg.append(line.strip())

	return erg


def findAllCorrupted(input):
	erg = 0
	for line in input:
		erg += findCorrupted(line)

	return erg


def findCorrupted(line):
	closers = list()
	brackets = dict()
	brackets['('] = ')'
	brackets['{'] = '}'
	brackets['['] = ']'
	brackets['<'] = '>'

	points = dict()
	points[')'] = 3
	points[']'] = 57
	points['}'] = 1197
	points['>'] = 25137
	for char in line:
		if char in ['(', '[', '{', '<']:
			closers.insert(0, char)
		else:
			if len(closers) > 0 and char == brackets[closers[0]]:
				closers.pop(0)
			else:
				#print(char)
				#print(closers)
				print(line)
				return points[char]

	return 0


if __name__ == '__main__':
	lines = readFile('resources/testInput.txt')
	print(findAllCorrupted(lines))

	lines = readFile('resources/input.txt')
	print(findAllCorrupted(lines))
