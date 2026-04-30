class WordleResponse {
    char letter;
    int index;
    LetterResponse response;
    WordleResponse(char letter, int index, LetterResponse response) {
        this.letter = letter;
        this.index = index;
        this.response = response;
    }
}