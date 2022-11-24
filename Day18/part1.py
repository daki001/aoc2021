import re


def read_file(path):
	snailnumbers = list()
	with open(path) as f:
		for line in f:
			snailnumbers.append(line.strip())

	return snailnumbers


def reduce_snailnumber(number):
	level = 0
	regex = '(\\[(\\d+),(\\d+)\\]).*'
	#print(number)
	for character_index in range(len(number)):
		character = number[character_index]

		if character == '[':
			level += 1
			if level >= 5:
				matcher = re.match(regex, number[character_index:])
				if matcher:
					#print(number)
					number = explode(number, character_index, matcher)
					return reduce_snailnumber(number)
		elif character == ']':
			level -= 1

	for character_index in range(len(number)):
		character = number[character_index]
		if character.isdigit():
			split_number = ''
			n = character
			next_index = 0
			while n.isdigit():
				split_number += n
				next_index += 1
				n = number[character_index + next_index]
			split_number = int(split_number)
			if split_number >= 10:
				#print(number)
				number = split(number, character_index, split_number)
				return reduce_snailnumber(number)
	return number


def calculate_magnitute(number):
	newnumber = number
	matches = re.findall('\\[\\d+,\\d+\\]', newnumber)
	# print(newnumber)
	while len(matches) > 0:
		for element in matches:
			# print(element)
			m = re.match('\\[(\\d+),(\\d+)\\]', element)
			# print(element)
			# print(str(int(m.group(1)) * 3 + int(m.group(2)) * 2))

			# print(newnumber)
			# print(element)
			newnumber = newnumber.replace(element, str(int(m.group(1)) * 3 + int(m.group(2)) * 2))
		# print(newnumber)
		matches = re.findall('\\[\\d+,\\d+\\]', newnumber)
	# print(matches)
	return newnumber


def split(number, index, split_number):
	num_first_part = number[:index]
	splitted = [split_number // 2, (split_number + 1) // 2]
	num_second_part = number[index:].replace(str(split_number), str(splitted), 1).replace(' ', '')
	return num_first_part + num_second_part


def explode(number: str, index, matcher):
	num_left = int(matcher.group(2))
	num_right = int(matcher.group(3))
	num_first_part = number[:index]
	last_num = re.match('(.*[^\\d]+)(\\d+)([^\\d]*)', num_first_part)
	if last_num:
		num_first_part = last_num.group(1) + str(int(last_num.group(2)) + num_left) + last_num.group(3)
	num_second_part = number[index:].replace(matcher.group(1), '0', 1)
	first_num = re.match('([^\\d]*)(\\d+)([^\\d]+.*)', num_second_part[1:])
	if first_num:
		num_second_part = num_second_part[0] + first_num.group(1) + str(
			int(first_num.group(2)) + num_right) + first_num.group(3)
	return num_first_part + num_second_part



def add_snailnums(snailnums):
	snail_sum = snailnums[0]
	for num in snailnums[1:]:
		snail_sum = '[' + snail_sum + ',' + num + ']'
		snail_sum = reduce_snailnumber(snail_sum)
	return snail_sum


if __name__ == '__main__':
	input = read_file('resources/testInput.txt')
	input2 = read_file('resources/input.txt')
	print(calculate_magnitute(add_snailnums(input)))
	print(calculate_magnitute(add_snailnums(input2)))
	#print(reduce_snailnumber('[[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]],[2,9]]'))
# print(reduce_snailnumber('[[[[[9,8],1],2],3],4]'))
# print(reduce_snailnumber('[7,[6,[5,[4,[3,2]]]]]'))
# print(reduce_snailnumber('[[6,[5,[4,[3,2]]]],1]'))
# print(reduce_snailnumber('[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]'))
# print(reduce_snailnumber('[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]'))
# print(split('[[13,13],[13,13]', 2, 13))
# print(calculate_magnitute('[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]'))
# print(explode('[[[[[9,8],1],2],3],4]', ))
