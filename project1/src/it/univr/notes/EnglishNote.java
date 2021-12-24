package it.univr.notes;

public class EnglishNote extends Note { // EnglishNote <= Note

	public EnglishNote(int semitone) {
		// chiama il costruttore di Note
		// passandogli l'intero semitone
		super(semitone);
	}
	
	private final static String[] notes = {
			"C", "C#", "D", "D#", "E",
			"F", "F#", "G", "G#",
			"A", "A#", "B"
	};

	public String toString() {
		return notes[getSemitone()];
	}
	
	public EnglishNote next() {
		return new EnglishNote((getSemitone() + 1) % 12);
	}
}
