package ru.mgkit.antimat.util.tree;

import java.util.List;

public class TreeNode<T> {

    private T value;
    private List<TreeNode<T>> nodes;


    public TreeNode() {
    }

    public TreeNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void addNode(TreeNode<T> node) {
        this.nodes.add(node);
    }

    public void removeNode(TreeNode<T> node) {
        this.nodes.remove(node);
    }

    public boolean containsInChild(T value) {
        return nodes.parallelStream().anyMatch(node -> node.getValue().equals(value));
    }

    public boolean isLeaf() {
        return nodes != null && nodes.size() == 0;
    }

    public boolean isBranch() {
        return nodes.size() > 0;
    }
}
