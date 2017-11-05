package lesson3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        Node<T> current = root;              // начинаем с корня

        @SuppressWarnings("unchecked")
        T removableValue = (T) o;

        if (current.value == o){      //если удаляем вершину  /TODO пересмотреть
           if (current.left == null && current.right == null) root = null;         
           else if (current.right == null) root = current.left;
           else if (current.left == null) root = current.right;
           else {
               Node<T> premin = findPremin(current);
               if (premin == null) {
                   current.right.left = current.left;
                   root = current.right;
               }
               else{
                   Node<T> min = premin.left;
                   min.right = current.right;
                   min.left = current.left;
                   root = min;
                   premin.left = null;
               }
           }



        }
        else {
            while ((current.value.compareTo(removableValue) > 0 && current.left != null && current.left.value != removableValue) ||
                    (current.value.compareTo(removableValue) < 0 && current.right != null && current.right.value != removableValue)) {
                if (current.value.compareTo(removableValue) > 0 && current.left != null && current.left.value != o)
                    current = current.left;
                else if (current.value.compareTo(removableValue) < 0 && current.right != null && current.right.value != o)
                    current = current.right;
                else throw new NoSuchElementException();
            }


            // обозначаем удаляемую вершину
            Node<T> removableNode;
            if (current.left != null && current.left.value == o) removableNode = current.left;
            else if (current.right != null && current.right.value == o) removableNode = current.right;
            else throw new NoSuchElementException();

            if (removableNode.right == null && removableNode.left == null) {             //if removable has 0 son
                current.left = (current.left == removableNode) ? null : current.left;
                current.right = (current.right == removableNode) ? null : current.right;
            }

            if ((removableNode.left != null && removableNode.right == null) || (removableNode.left == null && removableNode.right != null)) {    //if removable has one son
                current.left = (current.left == removableNode && removableNode.left != null) ? removableNode.left : current.left;       //if left son and left gSon
                current.left = (current.left == removableNode && removableNode.right != null) ? removableNode.right : current.left;     // if left son and right gSon
                current.right = (current.right == removableNode && removableNode.left != null) ? removableNode.left : current.right;   //if right son and left gSon
                current.right = (current.right == removableNode && removableNode.right != null) ? removableNode.right : current.right;  //if right son and right gSon
            }

            if (removableNode.left != null && removableNode.right != null) {
                Node<T> premin = findPremin(removableNode);
                surrogateRemovable(current, removableNode, premin);

            }
        }

        size --;
        return true;
    }


    private Node<T> findPremin(Node<T> removable){
        Node<T> premin = null;

        if (removable.right.left != null){
            premin = removable.right;
            while(premin.left.left != null) premin = premin.left;
        }

        return premin;
    }

    private void surrogateRemovable(Node<T> current, Node<T> removable, Node<T> premin){

        if (premin == null) {
            removable.right.left = removable.left;
            current.left = current.left == removable? removable.right : current.left;
            current.right = current.right == removable ? removable.right : current.right;
        }
        else {
            Node<T> min = premin.left;

            min.left = removable.left;
            min.right = removable.right;
            current.left = current.left == removable ? min : current.left;
            current.right = current.right == removable ? min : current.right;
            premin.left = null;
        }

    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }


    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {}

        private Node<T> findNext() {
            if (current.right != null){
                return findMin(current);
            }
            else if (current.value.compareTo(findFather(current).value) < 0) return findFather(current);
            return null;
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            BinaryTree.this.remove(current.value);
        }
    }

    private Node<T> findMin(Node<T> current){
        current = current.right;
        while(current.left != null) current = current.left;
        return current;
    }


    private Node<T> findFather(Node<T> son){
        Node<T> current = root;

        if (current.value.compareTo(son.value) > 0 && current.left.value != son.value) current = current.left;
        else if (current.value.compareTo(son.value) < 0 && current.right.value != son.value) current = current.right;

        return current;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
