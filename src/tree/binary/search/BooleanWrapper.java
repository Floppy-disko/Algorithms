package tree.binary.search;

public class BooleanWrapper {  //grazie a questo wrapper posso passare un BooleanWrapper (puntatore all'oggetto) ad un metodo e questo mi modifica il valore di flag
	private boolean flag=true;
	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean value) {
		flag=value;
	}
}