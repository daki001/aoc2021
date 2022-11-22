def readFile(path):
	erg = list()
	with open(path) as f:
		for lines in f:
			lines = lines.strip().split('|')
			erg.append(lines)

	return erg


def deduce(input):
	erg = dict()
	identifiedNumbers = dict()
	numbers = input[0].strip().split(' ')
	for i in numbers:
		if len(i) == 2:
			identifiedNumbers[1] = i
		if len(i) == 3:
			identifiedNumbers[7] = i
		if len(i) == 4:
			identifiedNumbers[4] = i
		if len(i) == 7:
			identifiedNumbers[8] = i

	# a herausfinden durch 1 und 7

	erg[identifiedNumbers[7].replace(identifiedNumbers[1][0], '').replace(identifiedNumbers[1][1], '')] = 'a'


	# 3 mit 7 vergleichen um d und g zu finden und das mit 4 vergleichen um die Werte zu trennen
	for i in numbers:
		if len(i) == 5:
			test = i.replace(identifiedNumbers[7][0], '').replace(identifiedNumbers[7][1], '').replace(identifiedNumbers[7][2], '')
			if len(test) == 2:
				if test[0] in identifiedNumbers[4]:
					identifiedNumbers[3] = i
					erg[test[0]] = 'd'
					erg[test[1]] = 'g'

				if test[1] in identifiedNumbers[4]:
					identifiedNumbers[3] = i
					erg[test[1]] = 'd'
					erg[test[0]] = 'g'

		if len(i) == 6 and not (identifiedNumbers[1][0] in i and identifiedNumbers[1][1] in i):
			identifiedNumbers[6] = i
			if identifiedNumbers[1][0] in i:
				erg[identifiedNumbers[1][0]] = 'f'
				erg[identifiedNumbers[1][1]] = 'c'
			else:
				erg[identifiedNumbers[1][0]] = 'c'
				erg[identifiedNumbers[1][1]] = 'f'

	#print(identifiedNumbers)
	for i in identifiedNumbers[8]:

		if i not in erg.keys():
			if i in identifiedNumbers[4]:
				erg[i] = 'b'
			else:
				erg[i] = 'e'
	return erg



def translate(input, alphabet):
	#print(input)
	encoded = input[1].strip()
	#print(encoded)
	#print(alphabet)
	decoded = ''
	for let in encoded:
		if let in alphabet.values():
			decoded += alphabet[let]
		else:

			decoded += let

	#print(decoded)
	digits = decoded.strip().split(' ')
	#print(digits)
	lib = dict()
	lib['abcefg'] = 0
	lib['cf'] = 1
	lib['acdeg'] = 2
	lib['acdfg'] = 3
	lib['bcdf'] = 4
	lib['abdfg'] = 5
	lib['abdefg'] = 6
	lib['acf'] = 7
	lib['abcdefg'] = 8
	lib['abcdfg'] = 9

	erg = ''
	for d in digits:
		#print(d)
		d = ''.join(sorted(d))
		#print(d)
		erg += str(lib[d])

	return erg



if __name__ == '__main__':
	input = readFile('resources/input.txt')
	erg = 0
	for i in input:
		alph = deduce(i)
		#print(alph)
		erg += int(translate(i, alph))

	#input = readFile('resources/input.txt')
	#print(deduce(input))
	print(erg)