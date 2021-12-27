package tree.binary.search;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import tree.binary.search.BinaryTree.BTNode;

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
			add(null, root, keys[i], 0);
	}
	
	protected void add(BTNode parent, BTNode current, T key, int son) {  //creo un albero di profonditÃ  qualsiasi
		
		if(root==null) {
			root = newNode(null, key);
			fixAdd(root);
		}
		
		else if(endOfBranch(current)) {  //se sono al primo passo e la radice non esiste ancora la creo
			current = newNode(parent, key);
			if(son==0) //se è figlio sinistro
				parent.left=current;
			else
				parent.right=current;
			fixAdd(current);
		}
		
		else if(key.compareTo(current.key)<0)  //se l'elemento è minore va a sinistra
			add(current, current.left, key, 0);
		
		else if(key.compareTo(current.key)>0) //se maggiore va a dx
			add(current, current.right, key, 1);
		
		//se è key è uguale alla key del nodo mi fermo e non aggiungo niente (non ci sono ripetizioni)
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
	
	//Ritorna il un tipo Z e accetta un argomento di tipo S, la funzione elabora i risultati di left e right e può includere anche current nei clacoli
	public <S,Z> Z postVisit(S arg, TriFunction<BTNode,Object[],S,Z> visitClass) {
		return postVisit(root, arg, visitClass);
	}
	
	protected <S,Z> Z postVisit(BTNode current, S arg, TriFunction<BTNode,Object[],S,Z> visitClass) {
		
		if(current==null)
			return visitClass.apply(null, null, arg);  //se arrivo alla fine ritorno l'elemento di default di ritorno di returnClass
		
		Z left = postVisit(current.left, arg, visitClass);
		//System.out.println("l) " + left);
		Z right = postVisit(current.right, arg, visitClass);
		//System.out.println("r) " + right);
		Object[] results = new Object[] {left, right};
		return visitClass.apply(current, results, arg);
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
		
		protected void rightRotate() {
			BTNode y=this.left;
			y.right.parent=this; //il padre di beta diventa x
			this.left=y.right; //il figlio di x diventa beta
			y.right=this; //x diventa figlio di y
			y.parent=this.parent; //il padre di y diventa il vecchio padre di x
			this.parent=y; //il padre di x diventa y
			if(y.parent!=null)
				if(this==y.parent.left) { //se x era figlio sx il nuovo figlio sx del padre diventa y, sennò se x era destro y diventa destro
					y.parent.left=y;
				} else {
					y.parent.right=y;
				}
		}
		
		protected void leftRotate() {
			BTNode y=this.right;
			y.left.parent=this;
			this.right=y.left;
			y.left=this;
			y.parent=this.parent;
			this.parent=y;
			if(y.parent!=null)
				if(this==y.parent.left) { //se x era figlio sx il nuovo figlio sx del padre diventa y, sennò se x era destro y diventa destro
					y.parent.left=y;
				} else {
					y.parent.right=y;
				}
		}
		
		public String toString() {
			if(key==null)
				return null;
			
			return key.toString();
		}
		
	}

}
