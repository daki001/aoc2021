import part1
def get_all_pairs(snailfish_nums):
	result = list()
	for num in snailfish_nums:
		for num2 in snailfish_nums:
			if num != num2:
				result.append((part1.calculate_magnitute(part1.add_snailnums([num, num2]))))
	print(len(result))
	print(result)
	return max(result)





if __name__ == '__main__':
	input = part1.read_file('resources/testInput.txt')
	print(input)
	print(get_all_pairs(input))
	input = part1.read_file('resources/input.txt')
	print(get_all_pairs(input))