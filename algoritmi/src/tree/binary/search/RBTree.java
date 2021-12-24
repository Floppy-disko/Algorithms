package tree.binary.search;

import tree.binary.search.BinaryTree.BTNode;

public class RBTree<T extends Comparable<T>> extends BinaryTree<T>{
	
	protected RBTNode nil;
	
	protected RBTree(T ...elems) {
		super(elems);
	}
	
	protected void createTree(T[] elems) { //Qua serve anche creare il nodo nil che sarà ogni foglia
		nil = new RBTNode(null, null, null, null, 0);
		root=nil;
		add(elems);
	}
	
	protected BTNode newNode(BTNode parent, T key) { //i nuovi nodi li creo rossi e con figli nil
		return new RBTNode(parent, nil, nil, key, 1);
	}
	
	protected void fixAdd() {
		System.out.println("Nodo (non ancora fixato)");
	}
	
	protected boolean endOfBranch(BTNode current) {  //sovrascrivo perchè in questo caso sono arrivato alla fine se trovoun nodo nil
		return current==nil;
	}
	
	protected class RBTNode extends BTNode{
		
		protected int color; //0 per nero, 1 per rosso
		
		protected RBTNode(BTNode p, BTNode l, BTNode r, T k, int c) {
			super(p, l, r, k);
			color=c;
		}
	}
	
	public static void main(String[] args) {
		//BinaryTree<Integer> tree = new BinaryTree<Integer>(2,4,4,5);
		//tree.inVisit(System.out::println);
		
		RBTree<Integer> tree2 = new RBTree<Integer>(1,2,4,4,3);
		tree2.inVisit(System.out::println);
	}
}
