package it.univr.notes;

public class MainSong {

	public static void main(String[] args) {
		Song yellowSubmarine = new Song
				("In the town where I was born lived a man who sailed the sea");
		yellowSubmarine.place(new EnglishNote(1), 7);  // C# su "town"
		yellowSubmarine.place(new EnglishNote(1), 56); // C# su "sea"
		yellowSubmarine.place(new EnglishNote(6), 24); // F# su "born"
		yellowSubmarine.place(new EnglishNote(8), 37); // G# su "man"

		System.out.println(yellowSubmarine);
	}
}