package tree.binary.search;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import tree.binary.search.BinaryTree.BTNode;

public class RBTree<T extends Comparable<T>> extends BinaryTree<T> {

	protected RBTNode nil;

	protected RBTree(T... elems) {
		super(elems);
	}

	protected void createTree(T[] elems) { // Qua serve anche creare il nodo nil che sarà ogni foglia
		nil = new RBTNode(null, null, null, null, 0);
		root = nil;
		add(elems);
	}

	protected BTNode newNode(BTNode parent, T key) { // i nuovi nodi li creo rossi e con figli nil
		return new RBTNode(parent, nil, nil, key, 1);
	}

	protected void fixAdd() {
		System.out.println("Nodo (non ancora fixato)");
	}

	protected boolean endOfBranch(BTNode current) { // sovrascrivo perchè in questo caso sono arrivato alla fine se
													// trovoun nodo nil
		return current == nil;
	}

	public boolean respectRBT() { // rispetta le 4 regole degli RBalberi? Utilizzata per scopi di debugging

		boolean flag;

		flag = respect1() && respect2() && respect3() && respect4();
		return flag;
	}
	
	public class BooleanWrapper {  //grazie a questo wrapper posso passare un BooleanWrapper (puntatore all'oggetto) ad un metodo e questo mi modifica il valore di flag
		private boolean flag=true;
		public boolean getFlag() {
			return flag;
		}
		public void setFlag(boolean value) {
			flag=value;
		}
	}
	
	public boolean respect1() {
		
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode,BooleanWrapper> lambda=(current, w) -> {
			if(((RBTNode)current).color < 0 || ((RBTNode)current).color > 1)
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	public boolean respect2() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode,BooleanWrapper> lambda=(current, w) -> {
			if(endOfBranch(current) && ((RBTNode)current).color!=0)
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	public boolean respect3() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode,BooleanWrapper> lambda=(current, w) -> {
			if(((RBTNode)current).color==1 && current.parent!=null && ((RBTNode)current.parent).color==1)  //prima constrollo se il padre esiste, poi controllo il suo colore
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	public boolean respect4() {
		
		return true;
	}

	protected class RBTNode extends BTNode {

		protected int color; // 0 per nero, 1 per rosso

		protected RBTNode(BTNode p, BTNode l, BTNode r, T k, int c) {
			super(p, l, r, k);
			color = c;
		}
	}

	public static void main(String[] args) {
		// BinaryTree<Integer> tree = new BinaryTree<Integer>(2,4,4,5);
		// tree.inVisit(System.out::println);

		RBTree<Integer> tree2 = new RBTree<Integer>(1, 2, 4, 4, 3);
		tree2.inVisit(System.out::println);
		Consumer<BinaryTree<Integer>.BTNode> lambda = (current) -> {
			System.out.println(((RBTree<Integer>.RBTNode) current).color);
		};
		tree2.inVisit(lambda);
		System.out.println(tree2.respectRBT());
	}
}
