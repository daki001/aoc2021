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
	current_vel = 0
	while current_vel <= max(abs(y_min), abs(y_max)):
		#print(current_vel)
		y_rounds = 0
		y_round_vel = current_vel
		counter = 0
		while y_rounds > y_min:
			y_rounds += y_round_vel
			y_round_vel -= 1
			counter += 1
			if y_rounds <= y_max and y_rounds >= y_min:
				y_vel[current_vel] = counter
				break
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

if __name__ == '__main__':
	input = read_input('resources/testInput.txt')
	print(input)
	for grid in input:
		y_vals = calculate_y_steps(grid[1][0], grid[1][1])
		print(calculate_height(y_vals))

	input = read_input('resources/input.txt')
	print(input)
	for grid in input:
		y_vals = calculate_y_steps(grid[1][0], grid[1][1])
		print(calculate_height(y_vals))


