import re

def readFile(path):
	dots = list()
	foldLines = list()
	part1 = True
	with open(path) as f:
		for line in f:
			if len(line.strip()) == 0:
				part1 = False
				continue

			if part1:
				cords = line.strip().split(',')
				dots.append([int(cords[0]), int(cords[1])])
			else:
				match = re.match('fold along ([xy])=(\\d+)', line.strip())
				foldLines.append(match.group(1) + match.group(2))

	return [dots, foldLines]


def foldOneLine(points, foldinstr):
	line = int(foldinstr[1:])
	direction = foldinstr[0]
	deleteIndex = list()
	for point in range(len(points)):
		if direction == 'x':
			if points[point][0] > line:
				newPoint = [line - (points[point][0] - line), points[point][1]]
				if newPoint not in points:
					points[point][0] = line - (points[point][0] - line)
				else:
					deleteIndex.append(point)
		elif direction == 'y':

			if points[point][1] > line:
				newPoint = [points[point][0], line - (points[point][1] - line)]
				if newPoint not in points:
					points[point][1] = line - (points[point][1] - line)
				else:
					deleteIndex.append(point)
		else:
			print('here')

	deleteIndex = sorted(deleteIndex)[::-1]
	for point in deleteIndex:
		points.pop(point)
	return points


def foldAllLines(input):
	for instruction in file[1]:
		foldOneLine(input[0], instruction)

	return input


def printPoints(points):
	quad = list()
	maxX = 0
	maxY = 0
	for i in points:
		maxX = max(maxX, i[0])
		maxY = max(maxY, i[1])

	for i in range(maxY + 1):
		quad.append(list())
		for j in range(maxX + 1):
			quad[i].append(' ')


	for i in points:
		#print(quad)
		#print(i)
		quad[i[1]][i[0]] = '#'

	for i in quad:
		print(''.join(i))

if __name__ == '__main__':
	file = readFile('resources/testInput.txt')
	print(file)
	erg = foldAllLines(file)
	printPoints(erg[0])

	file = readFile('resources/input.txt')
	print(file)
	erg = foldAllLines(file)
	printPoints(erg[0])