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

        tree.remove(10)                   //вершина с двумя детьми без premin
        assertEquals(1,tree.first())
        assertEquals(8,tree.size)
        assertFalse(tree.contains(10))
        assertTrue(tree.checkInvariant())

        tree.remove(20)                    //без детей
        assertEquals(15, tree.last())
        assertEquals(7, tree.size)
        assertTrue(tree.checkInvariant())
        assertFalse(tree.contains(20))

        tree.remove(15)              //вершина только с левым ребёнком
        assertFalse(tree.contains(15))
        assertEquals(8, tree.last())
        assertEquals(6, tree.size)

        tree.add(6)
        tree.remove(5)              // вершина с 2умя детьми с premin и min
        assertEquals(6, tree.size)
        assertEquals(8, tree.last())

        tree.remove(3)                    // с двумя детьми
        assertEquals(1, tree.first())
        assertEquals(8, tree.last())
        assertTrue(tree.checkInvariant())
        assertTrue(tree.contains(4))
        assertTrue(tree.contains(1))
        assertEquals(5, tree.size)

        tree.remove(4)                   //только с левым ребёнком
        assertEquals(1, tree.first())
        assertEquals(8, tree.last())
        assertFalse(tree.contains(4))

        tree.remove(7)                       //только с правым ребёнком
        assertFalse(tree.contains(7))
        assertEquals(3, tree.size)
        assertEquals(1, tree.first())
        assertEquals(8, tree.last())

        tree.remove(1)
        tree.remove(6)                //вершина только с правым сыном
        assertEquals(8, tree.first())
        assertEquals(8, tree.last())
        assertFalse(tree.contains(6))
        assertEquals(1, tree.size)

        tree.remove(8)                //вершина без детей
        assertEquals(0, tree.size)

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