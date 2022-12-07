def read_file(path):
	start_pos = list()
	with open(path) as f:
		for line in f:
			start = int(line[line.index(':') + 1:].strip())
			start_pos.append(start)
	return start_pos


def recursive(start_pos, values, current_user, visited):
	#print(values)
	if values[0] >= 21:
		return [1, 0]
	if values[1] >= 21:
		return [0, 1]
	erg = [0, 0]
	current_value = start_pos[current_user]
	if str(start_pos) + ' ' + str(values) + ' ' + str(current_user) in visited.keys():
		return visited[str(start_pos) + ' ' + str(values) + ' ' + str(current_user)]
	for i in range(1, 4):
		for j in range(1,4):
			for k in range(1,4):
				new_value = (((current_value + i + j + k) - 1) % 10) + 1
				start_pos[current_user] = new_value
				values[current_user] += new_value
				if current_user == 0:
					universes = recursive(start_pos, values, 1, visited)
				else:
					universes = recursive(start_pos, values, 0, visited)

				values[current_user] -= new_value
				start_pos[current_user] = current_value
				for ind in range(len(universes)):
					erg[ind] += universes[ind]
	visited[str(start_pos) + ' ' + str(values) + ' ' + str(current_user)] = erg
	return erg



def round(score):
	addition = list()
	for dice in range(1, 4):
		addition.append(((score + dice - 1) % 10) + 1)

	return addition

def full_game(start_pos):
	score = list()
	for _ in range(len(start_pos)):
		score.append(0)
	round_counter = 0
	while True:
		for val_ind in range(len(score)):
			new_position = round(start_pos[val_ind], round_counter * 3 + 1)
			round_counter += 1
			score[val_ind] = score[val_ind] + new_position
			start_pos[val_ind] = new_position
			if score[val_ind] >= 21:
				print(round_counter * 3)
				print(score)
				return (sum(score) - score[val_ind]) * round_counter * 3

if __name__ == '__main__':
	file = read_file('resources/testInput.txt')
	print(file)
	print(recursive(file, [0,0], 0, {}))


	file = read_file('resources/input.txt')
	print(file)
	print(max(recursive(file, [0,0], 0, {})))
