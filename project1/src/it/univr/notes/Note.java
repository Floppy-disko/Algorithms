package it.univr.notes;

public class Note extends Object {
	private final int semitone;
	
	public Note(int semitone) {
		this.semitone = semitone;
	}
	
	public String toString() {
		return "nota di semitono " + semitone;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Note))
			return false;
		
		// se due note hanno semitoni diversi, non sono equals;
		if (this.semitone != ((Note) other).semitone)
			return false;
		
		// altrimenti se una nota ha una durata e l'altra non ha una durata, non sono equals
		boolean thisHasDuration =
				this instanceof ItalianNoteWithDuration || 	this instanceof EnglishNoteWithDuration;
		boolean otherHasDuration =
				other instanceof ItalianNoteWithDuration || other instanceof EnglishNoteWithDuration;
		if (thisHasDuration != otherHasDuration)
			return false;
		
		// altrimenti se nessuna delle due note ha una durata, sono equals
		if (!thisHasDuration && !otherHasDuration)
			return true;
		
		// altrimenti le due note sono equals se e solo se hanno la stessa durata
		Duration durationOfThis =
				this instanceof ItalianNoteWithDuration ?
				((ItalianNoteWithDuration) this).getDuration() :
				((EnglishNoteWithDuration) this).getDuration();
		Duration durationOfOther =
				other instanceof ItalianNoteWithDuration ?
				((ItalianNoteWithDuration) other).getDuration() :
				((EnglishNoteWithDuration) other).getDuration();
		return durationOfThis == durationOfOther;
	}

	public int compareTo(Note other) {
		// se due note hanno semitoni diversi, non sono equals;
		if (this.semitone != other.semitone)
			return this.semitone - other.semitone;

		// altrimenti se una nota ha una durata e l'altra non ha una durata, non sono equals
		boolean thisHasDuration =
				this instanceof ItalianNoteWithDuration || 	this instanceof EnglishNoteWithDuration;
		boolean otherHasDuration =
				other instanceof ItalianNoteWithDuration || other instanceof EnglishNoteWithDuration;
		if (thisHasDuration && !otherHasDuration)
			return 1;
		if (!thisHasDuration && otherHasDuration)
			return -1;

		// altrimenti se nessuna delle due note ha una durata, sono equals
		if (!thisHasDuration && !otherHasDuration)
			return 0;

		// altrimenti le due note sono equals se e solo se hanno la stessa durata
		Duration durationOfThis =
				this instanceof ItalianNoteWithDuration ?
				((ItalianNoteWithDuration) this).getDuration() :
				((EnglishNoteWithDuration) this).getDuration();
		Duration durationOfOther =
				other instanceof ItalianNoteWithDuration ?
				((ItalianNoteWithDuration) other).getDuration() :
				((EnglishNoteWithDuration) other).getDuration();
		return durationOfThis.compareTo(durationOfOther);
	}

	protected int getSemitone() {
		return semitone;
	}
	
	public Note next() {
		return new Note((semitone + 1) % 12);
	}
}