package tree.binary.search;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> {
	
	protected BTNode root;
	
	public BinaryTree(T ...elems) {
		this.createTree(elems);
	}
	
	protected void createTree(T[] elems) {
		
		System.out.println("Funzione classe esterna");
		add(elems);
	}
	
	public void add(T ...keys) {  //interfaccia pubblica di add per aggiungere un elemento all'albero
		
		for(int i=0; i<keys.length; i++)
			root = add(null, root, keys[i]);
	}
	
	protected BTNode add(BTNode parent, BTNode current, T key) {  //creo un albero di profonditÃ  qualsiasi
		
		if(endOfBranch(current)) {  //se sono al primo passo e la radice non esiste ancora la creo
			current = newNode(parent, key);
			fixAdd(current);
		}
		
		else if(key.compareTo(current.key)<0)  //se l'elemento è minore va a sinistra
			current.left = add(current, current.left, key);
		
		else if(key.compareTo(current.key)>0) //se maggiore va a dx
			current.right = add(current, current.right, key);
		
		//se è key è uguale alla key del nodo mi fermo e non aggiungo niente (non ci sono ripetizioni)
			
		return current;
	}
	
	protected BTNode newNode(BTNode parent, T key) {  //rendo sta parte una funzione così se aumento il numero di campi del nodo non serve modificare tutto add ma ridefinire questa
		return new BTNode(parent, null, null, key);
	}

	protected void fixAdd(BTNode current) {
		//Non serve fixare negli alberi binario
	}
	
	public void inVisit(Consumer<BTNode> visitClass) {
		inVisit(root, visitClass);
	}
	
	protected void inVisit(BTNode current, Consumer<BTNode> visitClass) {
		if(current==null)  //non metto endOfBranch perchè l'invisit voglio che vad anche nei nod nil in RBTree
			return;
		
		inVisit(current.left, visitClass);
		visitClass.accept(current);
		inVisit(current.right, visitClass);
	}
	
	public <S> void inVisit(S arg, BiConsumer<BTNode,S> visitClass) { //versione di invisit che accetta un argomento esterno
		inVisit(root, arg, visitClass);
	}
	
	protected <S> void inVisit(BTNode current, S arg, BiConsumer<BTNode,S> visitClass) {
		if(current==null)
			return;
		
		inVisit(current.left, arg, visitClass);
		visitClass.accept(current, arg);
		inVisit(current.right, arg, visitClass);
	}
	
	protected boolean endOfBranch(BTNode current) {
		return current==null;
	}
	
	protected class BTNode {
		protected BTNode parent;
		protected BTNode left, right;
		protected T key;
		
		protected BTNode(BTNode p, BTNode l, BTNode r, T k) {
			parent=p;
			left=l;
			right=r;
			key=k;
		}
		
		public String toString() {
			if(key==null)
				return null;
			
			return key.toString();
		}
		
	}
	
	public static void main(String[] args) {
		BinaryTree<Integer> tree = new BinaryTree<Integer>(2,4,4,5);
		tree.inVisit(System.out::println);
	}

}
