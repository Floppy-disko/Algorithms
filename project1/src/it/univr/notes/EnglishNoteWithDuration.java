package it.univr.notes;

public class EnglishNoteWithDuration extends EnglishNote {
	private final Duration duration;
	
	public EnglishNoteWithDuration(int semitone, Duration duration) {
		super(semitone);
		this.duration = duration;
	}
	
	public String toString() {
		return super.toString() + " " + duration;
	}
	
	public EnglishNoteWithDuration next() {
		return new EnglishNoteWithDuration
			((getSemitone() + 1) % 12, duration);
	}
	
	public Duration getDuration() {
		return duration;
	}
}
