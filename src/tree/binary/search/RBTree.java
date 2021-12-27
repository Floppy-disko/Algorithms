package tree.binary.search;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import tree.binary.search.BinaryTree.BTNode;

public class RBTree<T extends Comparable<T>> extends BinaryTree<T> {

	protected RBTNode nil;

	protected RBTree(T... elems) {
		super(elems);
	}

	protected void createTree(T[] elems) { // Qua serve anche creare il nodo nil che sar� ogni foglia
		nil = new RBTNode(null, null, null, null, 0);
		root=nil;
		add(elems);
	}
	
	public boolean rootMissing() {
		return root==nil;
	}

	protected boolean endOfBranch(BTNode current) { // sovrascrivo perch� in questo caso sono arrivato alla fine se
		// trovo un nodo nil
		return current == nil;
	}
	
	protected RBTNode newNode(BTNode parent, T key) { // i nuovi nodi li creo rossi e con figli nil
		return new RBTNode(parent, nil, nil, key, 1);
	}
	
	protected void fixAdd(BTNode x) {
		System.out.println("Radice: " + root + " Elemento: " + x);
		BTNode y; //terr� il valore dello zio di x
		while(x.parent!=null && ((RBTNode)x.parent).color==1) { //se parent non esiste allora sono alla radice e ho terminato, non metto != root perch� quando chaimo fixAdd dopo aver raggiunto la radice root non punta ancora al nodo radice 
			if(x.parent == x.parent.parent.left) {
				y=x.parent.parent.right;
				if(((RBTNode)y).color==1) {
					((RBTNode)x.parent).color=0;
					((RBTNode)y).color=0;
					((RBTNode)x.parent.parent).color=1;
					x=x.parent.parent;
				} else {
					if(x==x.parent.right) {
						x=x.parent;
						x.leftRotate();
						if(x==root) //se x era la radice, dopo la leftRotate la nuva radice sar� l'elemento alla che era alla sua dx, ora diventato suo genitore
							root=x.parent;
						x=root;
					}
					
					((RBTNode)x.parent).color=0;
					((RBTNode)x.parent.parent).color=1;
					x=x.parent.parent;
					x.rightRotate();
					if(x==root) //se x era la radice, dopo la rightRotate la nuva radice sar� l'elemento che era alla sua sx
						root=x.parent;
					x=root;
				}
				
			} else { //se invece x.parent � figlio destro
				y=x.parent.parent.left;
				if(((RBTNode)y).color==1) {
					((RBTNode)x.parent).color=0;
					((RBTNode)y).color=0;
					((RBTNode)x.parent.parent).color=1;
					x=x.parent.parent;
				} else {
					if(x==x.parent.left) {
						x=x.parent;
						x.rightRotate();
						if(x==root) //se x era la radice, dopo la rightRotate la nuva radice sar� l'elemento che era alla sua sx
							root=x.parent;
					}
					
					((RBTNode)x.parent).color=0;
					((RBTNode)x.parent.parent).color=1;
					x=x.parent.parent;
					x.leftRotate();
					if(x==root) //se x era la radice, dopo la leftRotate la nuva radice sar� l'elemento che era alla sua dx
						root=x.parent;
					x=root;
				}
			}
		}
		
		if(x.parent==null) {  //se � la ardice rendila nera
			((RBTNode)x).color=0;
		}
	}

	public boolean respectRBT() { // rispetta le 4 regole degli RBalberi? Utilizzata per scopi di debugging

		boolean flag;
		if(!respect1())
			System.out.println("Doesn't respect 1");
		if(!respect2())
			System.out.println("Doesn't respect 2");
		if(!respect3())
			System.out.println("Doesn't respect 3");
		if(!respect4())
			System.out.println("Doesn't respect 4");
		
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
		
		BiConsumer<BTNode, BooleanWrapper> lambda=(current, w) -> {
			if(((RBTNode)current).color < 0 || ((RBTNode)current).color > 1)
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	public boolean respect2() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode, BooleanWrapper> lambda=(current, w) -> {
			if(endOfBranch(current) && ((RBTNode)current).color!=0)
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}

	public boolean respect3() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		BiConsumer<BTNode, BooleanWrapper> lambda=(current, w) -> {
			if(((RBTNode)current).color==1 && current.parent!=null && ((RBTNode)current.parent).color==1)  //prima constrollo se il padre esiste, poi controllo il suo colore
				w.setFlag(false);
		};
		
		inVisit(wrapper, lambda);
		
		return wrapper.getFlag();
	}
	
	//ad ogni passo constrollo che dimensione nera dei due figli sia uguale e se non � uguale modifico FlagWrapper
	public boolean respect4() {
		BooleanWrapper wrapper = new BooleanWrapper();
		
		//controlla che dimensione nera di albero sinistro e destro sia uguale
		BiConsumer<Object[], BooleanWrapper> lambda1=(results, w) -> {  //mi tocca passare un arary di object e poi castere ad integer perch� in BinaryTree non mi fa creare un array generico e devo passare da object
			Object first = results[0];  //faccio il tutto molto generale che funzioni con array di qualsiasi dimensione
			for(Object result: results)
				if(result!=first)
					w.setFlag(false);
		};
		
		//somma dimensione nera dei due rami della radice del sottoalbero radicato in current e aggiunge 1 se current � nera
		TriFunction<BTNode, Object[], BooleanWrapper, Integer> lambda=(current, childsBHeights, w) -> {
			
			if(current==null)
				return 0;
			
			Object first = childsBHeights[0];  //faccio il tutto molto generale che funzioni con array di qualsiasi dimensione
			for(Object result: childsBHeights)
				if(result!=first)
					w.setFlag(false);
			
			Integer BHeight;
			
			BHeight=(Integer)first; //Prendo i la black height del figlio sinistro tanto se � diversa da quella del figlio destro ho gia posto a false 
			
			if(((RBTNode)current).color==0)
				BHeight++;
			
			return BHeight;
		};
		
		postVisit(wrapper, lambda);
		
		return wrapper.flag;
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
			
			else if(key==null)
				return "null";
			
			else
				return key.toString();
		}
	}

	public static void main(String[] args) {
		//BinaryTree<Integer> tree = new BinaryTree<Integer>(2,4,4,5);
		//tree.inVisit(System.out::println);

		RBTree<Integer> tree2 = new RBTree<Integer>(10,11,12,14,3,2,1,0);
		//System.out.println(tree2.root.left + " " + tree2.root + " " + tree2.root.right); //Solo per controllare la struttura effettiva
		//tree2.inVisit(System.out::println);
		Consumer<BinaryTree<Integer>.BTNode> lambda = (current) -> {
			System.out.println(current.key + ") " + ((RBTree<Integer>.RBTNode) current).color);
		};
		tree2.inVisit(lambda);
		
		System.out.println(tree2.respectRBT());
	}
}