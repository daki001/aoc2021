import re

global nums

def readFile(path):
	erg = list()
	with open(path) as f:
		for line in f:
			erg.append(line)

	return erg


def interpreteFile(input):
	#print(input)
	nums = re.split(',', input[0][:len(input[0])-1])
	for num in range(len(nums)):
		nums[num] = int(nums[num])

	bingos = list()
	bingo = list()
	for i in range(1, len(input)):
		if input[i] == '\n':
			if len(bingo) > 0:
				bingos.append(newBingo(bingo))
			bingo = list()
		else:
			bingo.append(input[i])

	#print(bingo)
	bingos.append(newBingo(bingo))
	return [nums, bingos]


def newBingo(bingo):
	erg = list()
	for l in bingo:
		line = list()
		match = re.match('\s?(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s?', l)
		#print(match)
		for i in range(5):
			line.append(int(match.group(i + 1)))
		erg.append(line)

	return erg

def runNumbers(input):
	for i in input[0]:
		for bingo in input[1]:
			for line in bingo:
				if line.count(i) > 0:
					line[line.index(i)] = '.'

		#print(input[1])
		erg = calculateWinning(input[1], i)
		#print(erg)
		if type(erg) != list():
			#if len(input[1]) == 1:
			#	return erg.
			for gen in erg:
				#print(gen[0])

				#print(input[1])
				if input[1].count(gen[0]) > 0 and len(input[1]) >= 1:
					if len(input[1]) == 1:
						#print(i)
						#print(input[1])
						print(gen[1])
						return
					input[1].pop(input[1].index(gen[0]))
					#print(input[1])


def calculateWinning(input, lastNum):
	for i in input:
		for num in range(5):
			line = True
			col = True
			for num2 in range(5):
				line = line & (i[num][num2] == '.')
				col = col & (i[num2][num] == '.')

			if line | col:
				sum = 0
				for rows in i:
					for values in rows:
						if values != '.':
							sum += values

				yield [i, sum * lastNum]
	return [0, 0]

if __name__ == '__main__':
	cool = readFile('resources/testInput.txt')
	bingos = interpreteFile(cool)
	runNumbers(bingos)

	cool = readFile('resources/input.txt')
	bingos = interpreteFile(cool)
	runNumbers(bingos)

