import re
def readFile(path):
	points = list()
	with open(path) as f:
		for line in f:
			points.extend(generateLines(line))
	return points


def generateLines(input):
	match = re.match('(\d+),(\d+) -> (\d+),(\d+)', input)
	erg = list()
	startx = int(match.group(1))
	starty = int(match.group(2))
	endx = int(match.group(3))
	endy = int(match.group(4))

	if startx == endx or starty == endy:
		if startx == endx:
			for i in range(min(starty, endy), max(starty, endy) + 1):
				erg.append((startx, i))
		if starty == endy:
			for i in range(min(startx, endx), max(startx, endx) + 1):
				erg.append((i, starty))

	else:
		xmin = min(startx, endx)
		ymin = min(starty, endy)
		for i in range(abs(startx - endx) + 1):
			if starty > endy:
				if startx > endx:
					erg.append((startx - i, starty - i))
				else:
					erg.append((startx + i, starty - i))
			else:
				if startx > endx:
					erg.append((startx - i, starty + i))
				else:
					erg.append((startx + i, starty + i))

	return erg


def reviewErg(input):
	print(len(input))
	print(len(set(input)))
	erg = 0
	alreadyChecked = list()
	doubles = list()
	test = 0
	for i in input:
		test += 1
		if test % 10000 == 0:
			print(test)

		if i not in alreadyChecked:
			alreadyChecked.append(i)
		else:
			if i not in doubles:
				doubles.append(i)
				erg += 1
	return erg

if __name__ == '__main__':
	erg = readFile('resources/testInput.txt')
	print(reviewErg(erg))

	erg = readFile('resources/input.txt')
	print(reviewErg(erg))
