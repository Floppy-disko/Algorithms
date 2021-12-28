package tree.binary.search;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import tree.binary.search.BinaryTree.BTNode;

public class RBTree<T extends Comparable<T>> extends BinaryTree<T> {

	protected RBTNode nil;
	
	protected final static int BLACK=0;
	protected final static int RED=1;

	protected RBTree(T... elems) {
		super(elems);
	}

	protected void createTree(T[] elems) { // Qua serve anche creare il nodo nil che sarà ogni foglia
		nil = new RBTNode(null, null, null, null, BLACK);//
		root=nil;
		add(elems);
	}
	
	public boolean rootMissing() {
		return root==nil;
	}

	protected boolean endOfBranch(BTNode current) { // sovrascrivo perchè in questo caso sono arrivato alla fine se
		// trovo un nodo nil
		return current == nil;
	}
	
	protected RBTNode newNode(BTNode parent, T key) { // i nuovi nodi li creo rossi e con figli nil
		return new RBTNode(parent, nil, nil, key, RED);
	}
	
	protected void fixAdd(BTNode x) {
		System.out.println("Radice: " + root + " Elemento: " + x);
		BTNode y; //terrà il valore dello zio di x
		while(x.parent!=null && ((RBTNode)x.parent).color==RED) { //se parent non esiste allora sono alla radice e ho terminato, non metto != root perchè quando chaimo fixAdd dopo aver raggiunto la radice root non punta ancora al nodo radice 
			if(x.parent == x.parent.parent.left) {
				y=x.parent.parent.right;
				if(((RBTNode)y).color==RED) {
					((RBTNode)x.parent).color=BLACK;
					((RBTNode)y).color=BLACK;
					((RBTNode)x.parent.parent).color=RED;
					x=x.parent.parent;
				} else {
					if(x==x.parent.right) {
						x=x.parent;
						x.leftRotate();
						if(x==root) //se x era la radice, dopo la leftRotate la nuva radice sarà l'elemento alla che era alla sua dx, ora diventato suo genitore
							root=x.parent;
						x=root;
					}
					
					((RBTNode)x.parent).color=BLACK;
					((RBTNode)x.parent.parent).color=RED;
					x=x.parent.parent;
					x.rightRotate();
					if(x==root) //se x era la radice, dopo la rightRotate la nuva radice sarà l'elemento che era alla sua sx
						root=x.parent;
					x=root;
				}
				
			} else { //se invece x.parent è figlio destro
				y=x.parent.parent.left;
				if(((RBTNode)y).color==RED) {
					((RBTNode)x.parent).color=BLACK;
					((RBTNode)y).color=BLACK;
					((RBTNode)x.parent.parent).color=RED;
					x=x.parent.parent;
				} else {
					if(x==x.parent.left) {
						x=x.parent;
						x.rightRotate();
						if(x==root) //se x era la radice, dopo la rightRotate la nuva radice sarà l'elemento che era alla sua sx
							root=x.parent;
					}
					
					((RBTNode)x.parent).color=BLACK;
					((RBTNode)x.parent.parent).color=RED;
					x=x.parent.parent;
					x.leftRotate();
					if(x==root) //se x era la radice, dopo la leftRotate la nuva radice sarà l'elemento che era alla sua dx
						root=x.parent;
					x=root;
				}
			}
		}
		
		if(x.parent==null) {  //se è la radice rendila nera
			((RBTNode)x).color=BLACK;
		}
	}

	public boolean respectRBT() { // rispetta le 4 regole degli RBalberi? Utilizzata per scopi di debugging

		boolean r1,r2,r3,r4,flag;
		r1=respect1();
		r2=respect2();
		r3=respect3();
		r4=respect4();
		if(!r1)
			System.out.println("Doesn't respect 1");
		if(!r2)
			System.out.println("Doesn't respect 2");
		if(!r3)
			System.out.println("Doesn't respect 3");
		if(!r4)
			System.out.println("Doesn't respect 4");
		
		flag = r1 && r2 && r3 && r4;
		return flag;
	}
	
	public boolean respect1() {
		
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode, BooleanWrapper> lambda=(current, w) -> {
			if(((RBTNode)current).color < BLACK || ((RBTNode)current).color > RED)
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	public boolean respect2() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode, BooleanWrapper> lambda=(current, w) -> {
			if(endOfBranch(current) && ((RBTNode)current).color!=BLACK)
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	public boolean respect3() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode, BooleanWrapper> lambda=(current, w) -> {
			if(((RBTNode)current).color==RED && current.parent!=null && ((RBTNode)current.parent).color==RED)  //prima constrollo se il padre esiste, poi controllo il suo colore
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}
	
	//ad ogni passo constrollo che dimensione nera dei due figli sia uguale e se non è uguale modifico FlagWrapper
	public boolean respect4() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		//controlla che dimensione nera dei rami radicati a sx e a dx sia uguale
		//somma dimensione nera dei due rami della radice del sottoalbero radicato in current e aggiunge 1 se current è nera
		TriFunction<BTNode, Object[], BooleanWrapper, Integer> lambda=(current, childsBHeights, w) -> {
			
			if(current==null)  //non metto ==nil perchè i nil devono ritornare 1 essendo neri e senza figli
				return 0;
			
			Object first = childsBHeights[0];  //faccio il tutto molto generale che funzioni con array di qualsiasi dimensione
			for(Object result: childsBHeights)
				if(result!=first)
					w.setFlag(false);
			
			Integer BHeight;
			
			BHeight=(Integer)first; //Prendo i la black height del figlio sinistro tanto se è diversa da quella del figlio destro ho gia posto a false 
			
			if(((RBTNode)current).color==BLACK)
				BHeight++;
			
			return BHeight;
		};
		
		postVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	protected class RBTNode extends BTNode {

		protected int color; // 0 per nero, 1 per rosso

		protected RBTNode(BTNode p, BTNode l, BTNode r, T k, int c) {
			super(p, l, r, k);
			color = c;
		}
		
		public String toString() {
			
			if(this==nil)
				return "nil";
		
			else
				return super.toString();
		}
	}

	public static void main(String[] args) {
		//BinaryTree<Integer> tree = new BinaryTree<Integer>(2,4,4,5);
		//tree.inVisit(System.out::println);

		RBTree<Integer> tree2 = new RBTree<Integer>(10,11,12,14,9,9,9,3,2,1,0,10);
		//System.out.println(tree2.root.left + " " + tree2.root + " " + tree2.root.right); //Solo per controllare la struttura effettiva
		tree2.inVisit(System.out::println);
		Consumer<BinaryTree<Integer>.BTNode> lambda = (current) -> {
			System.out.println(current.key + ") " + ((RBTree<Integer>.RBTNode) current).color);
		};
		tree2.inVisit(lambda);
		
		System.out.println(tree2.respectRBT());
	}
}
