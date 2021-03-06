/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.certicon.routing.utils.collections;

/**
 * Immutable iterator (wraps modification methods and forbids them).
 *
 * @param <T>
 * @author Michael Blaha {@literal <blahami2@gmail.com>}
 */
public class ImmutableIterator<T> implements Iterator<T> {

    private final java.util.Iterator<T> iterator;

    /**
     * Constructor for arrays
     *
     * @param iterator array iterator
     */
    public ImmutableIterator( ArrayIterator<T> iterator ) {
        this.iterator = iterator;
    }

    /**
     * Constructor for regular iterators
     *
     * @param iterator iterator
     */
    public ImmutableIterator( java.util.Iterator<T> iterator ) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return this;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException( "Remove not supported" );
    }

}
