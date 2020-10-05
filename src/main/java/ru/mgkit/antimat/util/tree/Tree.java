package ru.mgkit.antimat.util.tree;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Tree<T> implements Iterable<TreeNode<T>>{

    private TreeNode<T> root;

    public Tree() {}

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        Iterator<TreeNode<T>> iterator = new Iterator<TreeNode<T>>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public TreeNode<T> next() {
                return null;
            }
        };
        return iterator;
    }

    @Override
    public void forEach(Consumer<? super TreeNode<T>> action) {

    }

    @Override
    public Spliterator<TreeNode<T>> spliterator() {
        return null;
    }
}
