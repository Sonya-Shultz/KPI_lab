package main.java.serverPart.Specification;

public interface Specification<T> {
	public boolean isSatisfiedBy(T t);
}
