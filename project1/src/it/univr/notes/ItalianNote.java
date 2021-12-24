package it.univr.notes;

public class ItalianNote extends Note { // ItalianNote <= Note

	public ItalianNote(int semitone) {
		// chiama il costruttore di Note
		// passandogli l'intero semitone
		super(semitone);
	}
	
	private final static String[] notes = {
			"DO", "DO#", "RE", "RE#", "MI",
			"FA", "FA#", "SOL", "SOL#",
			"LA", "LA#", "SI"
	};

	public String toString() {
		return notes[getSemitone()];
	}
	
	public ItalianNote next() {
		return new ItalianNote((getSemitone() + 1) % 12);
	}
}
