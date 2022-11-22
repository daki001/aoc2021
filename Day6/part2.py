def readFile(path):
	with open(path) as f:
		input = f.readline()
		erg = list()
		for num in input.split(','):
			erg.append(int(num))
		return erg


def nextDay(input, num):
	return input[num%7]


if __name__ == '__main__':
	input = readFile('resources/input.txt')
	huibu = list()
	for i in range(7):
		huibu.append(0)

	for i in input:
		huibu[i] += 1

	next = 0
	next2 = 0
	print(huibu)
	for i in range(256):
		#print(i)

		erg = nextDay(huibu, i)
		huibu[(i) % 7] += next
		next = next2
		next2 = erg
		#print(huibu)

	print(sum(huibu) + next + next2)

	input = readFile('resources/input.txt')
	for i in range(256):
		input = nextDay(input, i)

	print(len(input))
