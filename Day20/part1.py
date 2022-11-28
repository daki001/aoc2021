def read_file(path):
	image_input = list()
	image_input.append(list())
	image_input.append(list())
	with open(path) as f:
		translation = f.readline()
		counter = 0
		for line in f:
			if len(line.strip()) > 0:
				line_counter = 0
				for character in line:
					if character == '#':
						image_input[0].append([line_counter, counter])
					else:
						image_input[1].append([line_counter, counter])
					line_counter += 1
				counter += 1

	return [translation, image_input]


def print_field(field):
	b = calculate_borders([[],field])
	print(b)
	for y in range(b[1], b[3]):
		line = ''
		for x in range(b[0], b[2]):
			if [x,y] in field[0]:
				line += '#'
			else:
				line += '.'
		print(line)


def translate_pixel(pixel, all_points, background):
	bit = 0
	for i in range(pixel[1] - 1, pixel[1] + 2):
		for j in range(pixel[0] - 1, pixel[0] + 2):
			bit <<= 1
			if [j, i] in all_points[0]:
				bit |= 1
			elif [j, i] in all_points[1]:
				bit |= 0
			else:
				bit |= background

	return bit


def calculate_borders(input):
	min_x = 0
	min_y = 0
	max_x = 0
	max_y = 0
	for pixel in (input[1][0] + input[1][1]):
		min_x = min(pixel[0], min_x)
		min_y = min(pixel[1], min_y)
		max_x = max(pixel[0], max_x)
		max_y = max(pixel[1], max_y)

	return [min_x - 1, min_y - 1, max_x + 2, max_y + 2]


def translate_image(input, background=0):
	translation = input[0]
	output = list()
	output.append(list())
	output.append(list())
	borders = calculate_borders(input)
	for i in range(borders[1], borders[3]):
		for j in range(borders[0], borders[2]):
			index = translate_pixel([j, i], input[1], background)
			new_value = translation[index]
			if new_value == '#':
				output[0].append([j, i])
			else:
				output[1].append([j, i])
	index = 0
	for i in range(9):
		index <<= 1
		index |= background

	if translation[index] == '#':
		new_background = 1
	else:
		new_background = 0

	return [output, new_background]


if __name__ == '__main__':
	file = read_file('resources/testInput.txt')
	print(file)
	print_field(file[1])
	translation = file[0]
	output = translate_image(file)
	print(output[0])
	print_field(output[0])

	output2 = translate_image([translation, output[0]], output[1])
	print_field(output2[0])

	print(len(output2[0][0]))

	file = read_file('resources/input.txt')
	print(file)
	print_field(file[1])
	translation = file[0]
	output = translate_image(file)
	print(output[0])
	print_field(output[0])
	print(len(output[0][0]))

	output = translate_image([translation, output[0]], output[1])
	print_field(output[0])
	print(len(output[0][0]))
	#print(translate_pixel([1, 1], [[1, 2], [2, 2]], 0))
