def readFile(path):
	with open(path) as f:
		firstLine = ''
		rules = dict()
		for line in f:
			if len(firstLine) == 0:
				firstLine = line.strip()
				continue
			line = line.strip().split(' -> ')
			if len(line) == 2:
				#print(line)
				rules[line[0].strip()] = line[1].strip()

	return [firstLine, rules]



def oneRound(input, translation):
	newInput = ''
	#print(translation)
	for letter in range(len(input) - 1):
		key = input[letter:letter + 2]
		#print(key)
		newInput += input[letter]
		if key in translation.keys():
			newInput += translation[key]
	newInput += input[-1]
	return newInput


def calculateErg(input, rounds):
	inputCopy = input[0]
	#print(inputCopy)
	for i in range(rounds):
		print(i)
		inputCopy = oneRound(inputCopy, input[1])

	counter = dict()
	print("here")
	#print(inputCopy)
	for let in inputCopy:
		if let in counter.keys():
			counter[let] += 1
		else:
			counter[let] = 1

	maxCount = 0
	minCount = False
	for key in counter.keys():
		if not minCount:
			minCount = counter[key]
		else:
			minCount = min(minCount, counter[key])

		maxCount = max(maxCount, counter[key])

	return maxCount - minCount

if __name__ == '__main__':
	file = readFile('resources/testInput.txt')
	#print(file)
	erg = calculateErg(file, 40)
	print(erg)

	file = readFile('resources/input.txt')
	print(file)
	erg = calculateErg(file, 10)
	print(erg)