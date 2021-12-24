package it.univr.notes;
import it.univr.notes.ItalianNote;
import it.univr.notes.ItalianNoteWithDuration;
import it.univr.notes.Duration;
import it.univr.notes.EnglishNote;
import it.univr.notes.EnglishNoteWithDuration;
import it.univr.notes.Note;

public class Main {
	public static void main(String[] args) {
		ItalianNoteWithDuration n1 = new ItalianNoteWithDuration
			(8, Duration.MINIMA);
		ItalianNote n2 = new ItalianNote(9);
		ItalianNote n3 = new ItalianNoteWithDuration
			(8, Duration.MINIMA);
		System.out.println("n1 ha durata: " + n1.getDuration());
		//System.out.println("n2 ha durata: " + n2.getDuration());
		// sebbene il tipo di n3 sia ItalianNote, fidati,
		// dentro ci troverai una ItalianNoteWithDuration
		System.out.println("n3 ha durata: " +
				((ItalianNoteWithDuration) n3).getDuration());
		
		System.out.println(n3.compareTo(n2));
		System.out.println(n2.toString());
		Song s1 = new Song("Never gona give you up!");
		s1.place(n3, 0);
		s1.place(n3, 4);
		System.out.println(s1.toString());
	}
}
