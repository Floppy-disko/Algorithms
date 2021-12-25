package tree.binary.search;

public interface TriFunction<T,U,V,R> {
	public R apply(T first, U second, V third);
}
