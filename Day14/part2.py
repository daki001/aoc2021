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
				# print(line)
				rules[line[0].strip()] = line[1].strip()

	return [firstLine, rules]


def oneRound(input, translation):
	newInput = dict()
	# print(translation)
	for letter in input.keys():
		# print(key)
		if letter in translation.keys():
			if letter[0] + translation[letter] in newInput.keys():
				newInput[letter[0] + translation[letter]] += input[letter]
			else:
				newInput[letter[0] + translation[letter]] = input[letter]
			if translation[letter] + letter[1] in newInput.keys():
				newInput[translation[letter] + letter[1]] += input[letter]
			else:
				newInput[translation[letter] + letter[1]] = input[letter]
		else:
			if letter in newInput.keys():
				newInput[letter] += input[letter]
			else:
				newInput[letter] = input[letter]
	return newInput


def calculateErg(input, rounds):
	inputCopy = dict()
	for i in range(len(input[0]) - 1):
		if input[0][i:i + 2] in inputCopy.keys():
			inputCopy[input[0][i:i + 2]] += 1
		else:
			inputCopy[input[0][i:i + 2]] = 1
	print(inputCopy)

	for i in range(rounds):
		print(i)
		inputCopy = oneRound(inputCopy, input[1])
	print(inputCopy)
	counter = dict()
	print("here")
	# print(inputCopy)
	for let in inputCopy.keys():
		if let[0] in counter.keys():
			counter[let[0]] += inputCopy[let]
		else:
			counter[let[0]] = inputCopy[let]

		if let[1] in counter.keys():
			counter[let[1]] += inputCopy[let]
		else:
			counter[let[1]] = inputCopy[let]
	print(counter)
	maxCount = 0
	minCount = False
	for key in counter.keys():
		if not minCount:
			minCount = counter[key]
		else:
			minCount = min(minCount, counter[key])

		maxCount = max(maxCount, counter[key])

	return (maxCount + 1) // 2 - (minCount + 1)// 2


if __name__ == '__main__':
	file = readFile('resources/testInput.txt')
	# print(file)
	erg = calculateErg(file, 40)
	print(erg)

	file = readFile('resources/input.txt')
	print(file)
	erg = calculateErg(file, 40)
	print(erg)
