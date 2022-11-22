

def calculateFuel(input, position):
	fuel = 0
	for i in input:
		fuel += sum(range(abs(i - position) + 1))

	return fuel

def findMin(input):
	minPosition = -1
	minFuel = -1
	for i in range(min(input), max(input) + 1):
		if minFuel == -1:
			minFuel = calculateFuel(input, i)
			minPosition = i
		else:
			testFuel = calculateFuel(input, i)
			if testFuel < minFuel:
				minFuel = testFuel
				minPosition = i

	return [minPosition, minFuel]

def readFile(path):
	with open(path) as f:
		input = f.readline().split(',')
		for i in range(len(input)):
			input[i] = int(input[i])

	return input

if __name__ == '__main__':
	input = readFile('resources/testInput.txt')
	print(findMin(input))

	input2 = readFile('resources/input.txt')
	print(findMin(input2))
