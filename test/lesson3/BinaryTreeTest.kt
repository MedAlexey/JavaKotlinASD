package lesson3

import lesson3.BinaryTree.BinaryTreeIterator
import org.junit.Test

import org.junit.Assert.*

class BinaryTreeTest {
    @Test
    fun add() {
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    fun remove(){
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(3)
        tree.add(1)
        tree.add(4)
        tree.add(8)
        tree.add(15)
        tree.add(20)

        tree.remove(10)                   //вершина
        assertEquals(1,tree.first())
        assertEquals(8,tree.size)
        assertFalse(tree.contains(10))
        assertTrue(tree.checkInvariant())

        tree.remove(1)                       //без детей
        assertEquals(3,tree.first())
        assertEquals(7, tree.size)
        assertFalse(tree.contains(1))
        assertTrue(tree.checkInvariant())

        tree.remove(5)                          // с двумя детьми
        assertEquals(3, tree.first())
        assertEquals(20, tree.last())
        assertEquals(6, tree.size)
        assertFalse(tree.contains(5))
        assertFalse(tree.contains(10))
        assertTrue(tree.contains(7))
        assertTrue(tree.checkInvariant())

        tree.remove(3)                          //с одним ребёнком
        assertEquals(4, tree.first())
        assertEquals(5, tree.size)
        assertTrue(tree.checkInvariant())

    }


    @Test
    fun addKotlin() {
        val tree = KtBinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }
}