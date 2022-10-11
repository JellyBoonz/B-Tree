import org.w3c.dom.Node;

public class Main {
    public static void main(String[] args) {
        BTree myTree = new BTree(2); //Initializing BTree of min degree 2
        BTree myOtherTree = new BTree(3); //Initializing BTree of min degree 2
        final int START_OF_ALPHA = 65;
        final int END_OF_ALPHA = 91; //is 91
        //inserting ascii equivalent of alphabet
        for(int i = START_OF_ALPHA; i < END_OF_ALPHA; i++)
            myTree.bTreeInsert(i);

        for(int i = START_OF_ALPHA; i < END_OF_ALPHA; i++)
            myOtherTree.bTreeInsert(i);

        BTree.BNode temp = myTree.root;
        BTree.BNode currentNode = myTree.root;
        //Printing tree with T = 2
        System.out.println("***** T = 2 Tree *****");
        myTree.printTree(myTree.root);

        //Printing tree with T = 3
        System.out.println();
        System.out.println();
        System.out.println("***** T = 3 Tree *****");
        myOtherTree.printTree(myOtherTree.root);

    }
}