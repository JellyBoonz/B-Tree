public class BTree {
    private int T; //Minimum degree
    public int loop = 0;
    public class BNode{
        int n;
        int key[] = new int[2 * T - 1];
        BNode child[] = new BNode[2 * T];
        boolean leaf = true;
    }

    //Default to T = tree
    private int minKeySize = 1;
    private int minChildSize = 2;
    private int maxKeySize = 3;
    private int maxChildSize = 4;

    public BNode root = null;
    private int size = 0;


    public BTree() { }

    public BTree(int minDegree) {
        T = minDegree;
        root = new BNode();
        root.n = 0;
        root.leaf = true;
        this.minKeySize = minDegree - 1;
        this.minChildSize = minDegree; //For internal nodes
        this.maxKeySize = 2 * minDegree - 1;
        this.maxChildSize = 2 * minDegree;
    }

    /**
     * Takes the nonfull node and an index of the child
     * such that the index is of a child that is full.
     * @param node
     * @param index
     */
    public void splitChild(BNode node, int index){
        BNode z = new BNode();
        BNode y = node.child[index]; //y is full child node
        z.leaf = y.leaf;
        z.n = T - 1; //number of keys for z

        for(int i = 0; i < T - 1; i++) { //j = 1 to t - 1
            z.key[i] = y.key[i + T];
        }
        if(!y.leaf){
            for(int i = 0; i < T; i++){//j = 1 to t
                z.child[i] = y.child[i + T]; //z now points to y's children with the largest values
            }
        }
        y.n = T - 1; //updating number of y's children

        for(int i = node.n; i >= index + 1; i--){ //for i = x.n + 1 downto index + 1
            node.child[i + 1] = node.child[i]; //moving the node's children to make room for z
        }
        node.child[index + 1] = z; //z is inserted next to y

        for(int i = node.n - 1; i >= index; i--){ //for i = x.n downto index
            node.key[i + 1] = node.key[i]; //moving nodes keys over to making room for promoted key
        }
        node.key[index] = y.key[T - 1]; //promoting median key to parent node
        node.n = node.n + 1; //adjusting node's key count
    }

    /**
     * insert a new key into b-tree based on
     * @param key
     */
    public void bTreeInsert(int key){
        BNode root = this.root;

        if(root.n == (2 * T) - 1){ //if root node is full
            BNode newNode = new BNode();
            this.root = newNode; //creating new root
            newNode.leaf = false;
            newNode.n = 0; //adjust key count
            newNode.child[0] = root; //making original root the first child of the key root
            splitChild(newNode, 0);
            bTreeInsertNonFull(newNode, key);
        }
        else
            bTreeInsertNonFull(root, key); //inserting if root is non-full
    }


    public void bTreeInsertNonFull(BNode node, int key){
        int index = node.n - 1;

        if(node.leaf){
            //Making room for new key by shifting existing keys
            while(index >= 0 && key < node.key[index]){
                node.key[index + 1] = node.key[index];
                index--;
            }

            node.key[index + 1] = key; //inserting new key
            node.n++; //adjusting key count
        }
        else{
            while(index >= 0 && key < node.key[index]){
                index--;
            }
            index++;

            //Splitting node if full
            if(node.child[index].n == (2 * T) - 1){
                splitChild(node, index);

                if(key > node.key[index])
                    index++;
            }

            bTreeInsertNonFull(node.child[index], key); //recursive call to split nodes until key can be inserted
        }
    }

    /**
     * Function prints b-tree contents in order
     * @param node
     * @return BNode
     */
    public BNode printTree(BNode node){
        int i;
        for(i = 0; i < node.n; i++) {
            if(node.leaf) { //Print all the keys in leaf node
                System.out.print((char) node.key[i] + " ");
                continue; //Skip recursion until i = node.n
            }
            printTree(node.child[i]);
            System.out.print((char) node.key[i] + " "); //printing parent node key after recursive call
        }
        if(node.leaf) {
            return null;
        }
        else { //recursive call if there is a child node
            return printTree(node.child[i]);
        }
    }

}
