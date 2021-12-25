package tree.binary.search;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class BinaryTree<T extends Comparable<T>> {
	
	protected BTNode root;
	
	public BinaryTree(T ...elems) {
		createTree(elems);
	}
	
	protected void createTree(T[] elems) { //per BinaryTree basta aggiungere gli elementi uno alla volta
		add(elems);
	}
	
	public void add(T ...keys) {  //interfaccia pubblica di add per aggiungere più elementi all'albero
		
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
		//Non serve fixare negli alberi binari di ricerca semplici
	}
	
	public void inVisit(Consumer<BTNode> visitClass) {
		inVisit(root, visitClass);
	}
	
	protected void inVisit(BTNode current, Consumer<BTNode> visitClass) {
		if(current==null)  //non metto endOfBranch perchè l'invisit voglio che vada anche nei nod nil in RBTree
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
	
	//Ritorna il un tipo Z e accetta un argomento di tipo S, la funzione elabora i risultati di left e right
	public <S,Z> Z postVisit(S arg, BiConsumer<Object[],S> visitClass, BiFunction<BTNode,Object[],Z> returnClass) {
		return postVisit(root, arg, visitClass, returnClass);
	}
	
	protected <S,Z> Z postVisit(BTNode current, S arg, BiConsumer<Object[],S> visitClass, BiFunction<BTNode,Object[],Z> returnClass) {
		
		if(current==null)
			return returnClass.apply(null, null);  //se arrivo alla fine ritorno l'elemento di default di ritorno di returnClass
		
		Z left = postVisit(current.left, arg, visitClass, returnClass);
		//System.out.println("l) " + left);
		Z right = postVisit(current.right, arg, visitClass, returnClass);
		//System.out.println("r) " + right);
		Object[] results = new Object[] {left, right};
		visitClass.accept(results, arg);
		return returnClass.apply(current, results);
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

}
