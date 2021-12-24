package it.univr.notes;

public class ItalianNoteWithDuration extends ItalianNote {
	private final Duration duration;
	
	public ItalianNoteWithDuration(int semitone, Duration duration) {
		super(semitone);
		this.duration = duration;
	}
	
	public String toString() {
		return super.toString() + " " + duration;
	}
	
	public ItalianNoteWithDuration next() {
		return new ItalianNoteWithDuration
			((getSemitone() + 1) % 12, duration);
	}
	
	public Duration getDuration() {
		return duration;
	}
}
