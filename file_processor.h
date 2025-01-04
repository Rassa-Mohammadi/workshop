char* get_address(char username[MAX_SIZE], char extension[], char directory[]) {
    char *res = malloc(MAX_SIZE * sizeof(char));
    strcpy(res, directory);
    strcat(res, username);
    strcat(res, extension);
    return res;
}

char* get_password(char username[MAX_SIZE]) {
    char *path = get_address(username, ".txt", "users/");
    FILE *fptr = fopen(path, "r");
    char *res = malloc(sizeof(char) * MAX_SIZE);
    fgets(res, MAX_SIZE, fptr);
    fgets(res, MAX_SIZE, fptr);
    return res;
}

int exist_username(char username[MAX_SIZE]) {
    char *path = get_address(username, ".txt", "users/");
    FILE *fptr = fopen(path, "r");
    if (fptr == NULL)
        return 0;
    return 1;
}

int correct_password(char username[], char password[]) {
    char *path = get_address(username, ".txt", "users/");
    FILE *fptr = fopen(path, "r");
    char pass[100];
    fgets(pass, 100, fptr);
    fgets(pass, 100, fptr);
    int n = strlen(pass) - 1;
    if (n != strlen(password))
        return 0;
    for (int i = 0; i < n; i++)
        if (password[i] != pass[i])
            return 0;
    return 1;
}

int valid_password(char password[]) {
    int has_number = 0, has_capital = 0, has_small = 0, len = strlen(password);
    if (len < 7)
        return 0;
    for (int i = 0; i < len; i++) {
        if (password[i] >= '0' && password[i] <= '9')
            has_number |= 1;
        if (password[i] >= 'A' && password[i] <= 'Z')
            has_capital |= 1;
        if (password[i] >= 'a' && password[i] <= 'z')
            has_small |= 1;
    }
    return has_number && has_capital && has_small;
}

char* generate_password() {
    char symbols[] = {'!', '@', '#', '$', '%', '^', '&', '*', '?'};
    char capital[26], small[26];
    for (int i = 0; i < 26; i++) {
        capital[i] = 'A' + i;
        small[i] = 'a' + i;
    }
    int len = 7 + rand() % 4, type = rand() % 4;
    char *res = malloc(sizeof(char) * (len + 1));
    for (int i = 0; i < len; i++) {
        if (type == 0) // number
            res[i] = '0' + rand() % 10; 
        else if (type == 1) { // symbol
            int idx = rand() % 9;
            res[i] = symbols[idx];
        }
        else if (type == 2) { // capital
            int idx = rand() % 26;
            res[i] = capital[idx];
        }
        else { // small
            int idx = rand() % 26;
            res[i] = small[idx];
        }
        type = rand() % 4;
    }
    res[len] = '\0';
    return res;
}

int valid_email(char email[]) {
    int len = strlen(email), at = -1;
    for (int i = 0; i < len; i++)
        if (email[i] == '@') {
            if (at != -1)
                return 0;
            at = i;
        }
    if (at == -1 || at == 0)
        return 0;
    int noghte = -1;
    for (int i = len - 1; i > at; i--)
        if (email[i] == '.') {
            if (noghte != -1)
                return 0;
            noghte = i;
        }
    if (noghte == -1 || noghte == len - 1 || noghte == at + 1)
        return 0;
    return 1;
}

void create_user(struct User *user) {
    user->number_of_games = 0;
    time(&user->first_game);
    user->level = 0;
    char *path = get_address(user->username, ".txt", "users/");
    FILE *fptr = fopen(path, "w");
    fprintf(fptr, "%s\n", user->username);
    fprintf(fptr, "%s\n", user->password);
    fprintf(fptr, "%s\n", user->email);
    fprintf(fptr, "%d\n", user->number_of_games);
    fprintf(fptr, "%ld\n", user->first_game);
    fprintf(fptr, "%d\n", user->level);
    fclose(fptr);
    // add to users.txt
    fptr = fopen("users/users.txt", "a");
    fprintf(fptr, "%s\n", user->username);
    fclose(fptr);
}

void load_user(struct User* user) {
    for (int level = 0; level < 4; level++)
        init_user(user, level);
    char *path = get_address(user->username, ".txt", "users/");
    FILE *fptr = fopen(path, "r");
    char line[MAX_SIZE];
    fgets(line, MAX_SIZE, fptr); // username;
    fgets(line, MAX_SIZE, fptr); // password
    trim(line);
    strcpy(user->password, line);
    fgets(line, MAX_SIZE, fptr); // email
    trim(line);
    strcpy(user->email, line);
    fgets(line, MAX_SIZE, fptr); // number of games
    trim(line);
    user->number_of_games = get_num(line);
    fgets(line, MAX_SIZE, fptr); // first game date
    trim(line);
    user->first_game = get_num(line);
    fgets(line, MAX_SIZE, fptr); // level
    trim(line);
    user->level = line[0] - '0';
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) { // map
            fgets(line, MAX_SIZE, fptr);
            for (int j = 0; j < GAME_Y; j++)
                (user->map)[level][i][j] = line[j];
        }
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) { // mask
            fgets(line, MAX_SIZE, fptr);
            for (int j = 0; j < GAME_Y; j++)
                (user->mask)[level][i][j] = line[j] - '0';
        }
    fgets(line, MAX_SIZE, fptr); // user.pos
    trim(line);
    char *token = strtok(line, " ");
    user->pos.x = get_num(token);
    token = strtok(NULL, " ");
    user->pos.y = get_num(token);
    fgets(line, MAX_SIZE, fptr); // gold
    trim(line);
    user->gold = get_num(line);
    fgets(line, MAX_SIZE, fptr); // health
    trim(line);
    user->health = get_num(line);
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) // traps
            for (int j = 0; j < GAME_Y; j++) {
                fgets(line, MAX_SIZE, fptr);
                trim(line);
                char *token = strtok(line, " ");
                (user->trap)[level][i][j].exist = token[0] - '0';
                token = strtok(NULL, " ");
                (user->trap)[level][i][j].damage = get_num(token);
            }
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) // doors
            for (int j = 0; j < GAME_Y; j++) {
                fgets(line, MAX_SIZE, fptr);
                trim(line);
                char *token = strtok(line, " "); // exist, pos, is_opened, is_old, haspassword, password
                (user->door)[level][i][j].exist = token[0] - '0';
                token = strtok(NULL, " "); // pos.x
                (user->door)[level][i][j].pos.x = get_num(token);
                token = strtok(NULL, " "); // pos.y
                (user->door)[level][i][j].pos.y = get_num(token);
                token = strtok(NULL, " "); // is opened
                (user->door)[level][i][j].is_opened = token[0] - '0';
                token = strtok(NULL, " "); // is old
                (user->door)[level][i][j].is_old = token[0] - '0';
                token = strtok(NULL, " "); // has password
                (user->door)[level][i][j].has_password = token[0] - '0';
                if ((user->door)[level][i][j].has_password) { // password
                    token = strtok(NULL, " ");
                    strcpy((user->door)[level][i][j].password, token);
                }
            }

}

int has_map(struct User* user) {
    char *path = get_address(user->username, ".txt", "users/");
    FILE *fptr = fopen(path, "r");
    char line[MAX_SIZE];
    for (int i = 0; i < 7; i++)
        if (fgets(line, MAX_SIZE, fptr) == NULL) {
            fclose(fptr);
            return 0;
        }
    fclose(fptr);
    return 1;
}

void update_user(struct User* user) {
    char *path = get_address(user->username, ".txt", "users/");
    FILE *fptr = fopen(path, "w");
    fprintf(fptr, "%s\n", user->username);
    fprintf(fptr, "%s\n", user->password);
    fprintf(fptr, "%s\n", user->email);
    fprintf(fptr, "%d\n", user->number_of_games);
    fprintf(fptr, "%ld\n", user->first_game);
    fprintf(fptr, "%d\n", user->level);
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) {
            for (int j = 0; j < GAME_Y; j++)
                fprintf(fptr, "%c", (user->map)[level][i][j]);
            fprintf(fptr, "\n");
        }
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) {
            for (int j = 0; j < GAME_Y; j++)
                fprintf(fptr, "%d", (user->mask)[level][i][j]);
            fprintf(fptr, "\n");
        }
    fprintf(fptr, "%d %d\n", user->pos.x, user->pos.y);
    fprintf(fptr, "%d\n", user->gold);
    fprintf(fptr, "%d\n", user->health);
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) // traps
            for (int j = 0; j < GAME_Y; j++) {
                fprintf(fptr, "%d %d", (user->trap)[level][i][j].exist, (user->trap)[level][i][j].damage);
                fprintf(fptr, "\n");
            }
    for (int level = 0; level < 4; level++)
        for (int i = 0; i < GAME_X; i++) // doors
            for (int j = 0; j < GAME_Y; j++) {
                struct Door *cur = &((user->door)[level][i][j]);
                fprintf(fptr, "%d %d %d %d %d %d", cur->exist, cur->pos.x, cur->pos.y, cur->is_opened, cur->is_old, cur->has_password);
                if (cur->has_password)
                    fprintf(fptr, " %s", cur->password);
                fprintf(fptr, "\n");
            }
    fclose(fptr);
}

void play_song(char song[MAX_SIZE]) {
    char *path = get_address(song, ".wav", "songs/");
    print_message(create_point(0, 0), path);
    SDL_Init(SDL_INIT_AUDIO); // init
    
    SDL_AudioSpec wav_spec; // load
    Uint8* wav_start;
    Uint32 wav_length;
    SDL_LoadWAV(path, &wav_spec, &wav_start, &wav_length);

    SDL_AudioDeviceID id = SDL_OpenAudioDevice(NULL, 0, &wav_spec, NULL, 0); // play
    SDL_QueueAudio(id, wav_start, wav_length);
    SDL_PauseAudioDevice(id, 0);
}
