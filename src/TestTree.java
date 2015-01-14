// Gregory Halverson
// Pierce College
// CS 532
// Spring 2014

public class TestTree
{
    public static void main(String[] args)
    {
        UnionFindTree test1 = new UnionFindTree(4);
        test1.wunion(0, 1);
        test1.wunion(2, 0);
        test1.wunion(3, 0);
        System.out.println(test1.printArray());

        UnionFindTree test2 = new UnionFindTree(12);
        test2.wunion(0, 1);
        test2.wunion(2, 3);
        test2.wunion(4, 5);
        test2.wunion(6, 7);
        test2.wunion(8, 9);
        test2.wunion(10, 11);
        System.out.println(test2.printArray());

        test2.wunion(0, 3);
        test2.wunion(5, 0);
        test2.wunion(6, 9);
        test2.wunion(11, 6);
        System.out.println(test2.printArray());

        test2.wunion(5, 11);
        test2.pcfind(9);
        System.out.println(test2.printArray());
    }
}
