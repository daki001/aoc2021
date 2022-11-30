def read_file(path):
	start_pos = list()
	with open(path) as f:
		for line in f:
			start = int(line[line.index(':') + 1:].strip())
			start_pos.append(start)
	return start_pos

def round(score, current_dice):
	addition = 0
	for dice in range(current_dice, current_dice + 3):
		addition += dice

	return ((score + addition - 1) % 10) + 1

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
			if score[val_ind] >= 1000:
				print(round_counter * 3)
				print(score)
				return (sum(score) - score[val_ind]) * round_counter * 3

if __name__ == '__main__':
	file = read_file('resources/testInput.txt')
	print(file)
	print(full_game(file))


	file = read_file('resources/input.txt')
	print(file)
	print(full_game(file))
