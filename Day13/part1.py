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


if __name__ == '__main__':
	file = readFile('resources/testInput.txt')
	print(file)
	print(foldOneLine(file[0], file[1][0]))
	print(len(file[0]))

	file = readFile('resources/input.txt')
	print(file)
	print(foldOneLine(file[0], file[1][0]))
	print(len(file[0]))