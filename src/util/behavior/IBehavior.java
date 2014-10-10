package util.behavior;

/**
 * Visitor for drawables
 */
public interface IBehavior<T> {

  void visit(T visited);
}