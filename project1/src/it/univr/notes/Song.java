package it.univr.notes;
/**
 * @author Matteo Disconsi
 */

public class Song {
	private String lyrics;
	private Note[] notes;
	/**
	 * @param lyrics è il testo della canzone (una riga)
	 */
	public Song(String lyrics) {
		this.lyrics = lyrics;
		this.notes = new Note[lyrics.length()];
	}

	/**
	 * Posiziona una nota sopra il carattere position dell'unica riga della canzone.
	 * @param note La nota da associare ad una posizione di lyrics
	 * @param position La posizione in cui cominciare ad inserire la nota
	 */
	public void place(Note note, int position) {
		notes[position] = note;
	}

	/**
	 * @return Una stringa di due righe: nella prima riga sono riportate le note posizionate nella canzone,
	 * nella seconda riga è riportato il testo della canzone.
	 */
	public String toString() {
		if(lyrics == null) {
			return null;		
		}
		
		String res = "";
		for(int i = 0; i< lyrics.length(); i+=noteAtPos(i).length()) {
			res+=noteAtPos(i);
		}
		
		return res + "\n" + lyrics;
			
	}
	
	/**
	 * Metodo di supporto per per toString che serve per fornirgli le stringhe da appendere a res
	 * @param pos Posizione in cui verificare se è presente una Note nell'array notes
	 * @return Spazio se non è presente nessuna nota, la prima parola della stringa della nota se la nota è presente in pos
	 */
	private String noteAtPos(int pos) {
		if(notes[pos] == null)
			return " ";
		
		else
			return notes[pos].toString().split(" ")[0];
	}
}
