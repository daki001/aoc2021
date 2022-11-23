import re


def read_input(path):
	result = list()
	with open(path) as f:
		for line in f:
			print(line)
			matcher = re.match('target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)', line)
			min_x = int(matcher.group(1))
			max_x = int(matcher.group(2))
			min_y = int(matcher.group(3))
			max_y = int(matcher.group(4))

			result.append([[min_x, max_x], [min_y, max_y]])

	return result


def step(x_cord, y_cord, x_vel, y_vel):
	x_cord += x_vel
	y_cord += y_vel
	y_vel -= 1
	if x_vel < 0:
		x_vel += 1
	elif x_vel > 0:
		x_vel -= 1

	return [x_cord, y_cord, x_vel, y_vel]


def calculate_y_steps(y_min, y_max):
	y_vel = dict()
	current_vel = y_min
	while current_vel <= max(abs(y_min), abs(y_max)):
		# print(current_vel)
		y_rounds = 0
		y_round_vel = current_vel
		counter = 0
		while y_rounds > y_min:
			y_rounds += y_round_vel
			y_round_vel -= 1
			counter += 1
			if y_rounds <= y_max and y_rounds >= y_min:
				if current_vel in y_vel.keys():
					y_vel[current_vel].append(counter)
				else:
					y_vel[current_vel] = [counter]
		current_vel += 1
	return y_vel


def calculate_height(y_vels):
	max_height = 0
	for key in y_vels:
		max_height_per_key = 0
		for i in range(key + 1):
			max_height_per_key += i
		max_height = max(max_height, max_height_per_key)

	return max_height


def calculate_x_vels(y_vels, x_min, x_max):
	erg = list()
	for y in y_vels:
		for rounds in y_vels[y]:
			x = 0
			while x < x_max:
				x += 1
				if (x + 1 - rounds >= 0 and x + 1 >= 0) or (x + 1 - rounds <= 0 and x + 1 <= 0):
					move = sum(range(x + 1 - rounds, x + 1))
				else:
					move = sum(range(0, x + 1))
				if move <= x_max and move >= x_min:
					if [x, y] not in erg:
						erg.append([x, y])

	return erg


if __name__ == '__main__':
	input = read_input('resources/testInput.txt')
	print(input)
	for grid in input:
		y_vals = calculate_y_steps(grid[1][0], grid[1][1])
		print(y_vals)
		test = calculate_x_vels(y_vals, grid[0][0], grid[0][1])
		count = 0
		for element in range(len(test)):
			if element < len(test) - 1:
				if not test[element] in test[element + 1:]:
					count += 1
			else:
				count += 1

		print(count)

	print(input)
	for grid in input:
		y_vals = calculate_y_steps(grid[1][0], grid[1][1])
		print(y_vals)
		erg = calculate_x_vels(y_vals, grid[0][0], grid[0][1])
		print(len(calculate_x_vels(y_vals, grid[0][0], grid[0][1])))

	input = read_input('resources/input.txt')
	print(input)
	for grid in input:
		y_vals = calculate_y_steps(grid[1][0], grid[1][1])
		print(y_vals)
		test = calculate_x_vels(y_vals, grid[0][0], grid[0][1])
		count = 0
		for element in range(len(test)):
			if element < len(test) - 1:
				if not test[element] in test[element + 1:]:
					count += 1
			else:
				count += 1
		print(len(test))
		print(count)
