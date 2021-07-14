package MainApp.Trees;

public class Tree {
    public static class Node {
        public int key;
        public int []pos;
        public Node left, right;

        public void setPos(int[] pos) {
            this.pos = pos;
        }
        public Node(int data){
            key = data;
            left = right = null;
            pos=new int[]{0,0};
        }
    }
    // BST root node
    public Node root;
    String data="";
    // Constructor for BST =>initial empty tree
    public Tree(){
        root = null;
    }
    public void deleteKey(int key) {
        root = delete_Recursive(root, key);
    }
    Node delete_Recursive(Node root, int key){
        if (root == null)
            return null;
        if (key < root.key)
            root.left = delete_Recursive(root.left, key);
        else if (key > root.key)
            root.right = delete_Recursive(root.right, key);
        else  {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.key = minNode(root.right).key;

            root.right = delete_Recursive(root.right, root.key);
        }
        return root;
    }
    public Node minNode(Node root)  {
        while (root.left != null)  {
            root = root.left;
        }
        return root;
    }
    public void insert(int key)  {
        root = insert_Recursive(root, key);
    }
    Node insert_Recursive(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key < root.key)
            root.left = insert_Recursive(root.left, key);
        else if (key > root.key)
            root.right = insert_Recursive(root.right, key);

        return root;
    }
    public String inorder() {
        data="";
        inorder_Recursive(root);
        return data;
    }
    void inorder_Recursive(Node root) {
        if (root != null) {
            inorder_Recursive(root.left);
            data=data+root.key + " ";
            inorder_Recursive(root.right);
        }
    }
    public String postorder() {
        data="";
        postorder_Recursive(root);
        return data;
    }
    void postorder_Recursive(Node root) {//(Left-right-root)
        if (root != null) {
            postorder_Recursive(root.left);
            postorder_Recursive(root.right);
            data=data+root.key + " ";
        }
    }
    public String preorder() {
        data="";
        preorder_Recursive(root);
        return data;
    }
    void preorder_Recursive(Node root) {//(root-Left-right)
        if (root !=  null) {
            data=data+root.key + " ";
            preorder_Recursive(root.left);
            preorder_Recursive(root.right);
        }
    }
    public Node[][] nodesArray(){
        int rows=maxDepth(root);
        int columns=(int)Math.pow(2,rows)-1;
        Node[][]arr=new Node[rows+1][columns];
        Node temp;
        if(root!=null){
            root.pos=new int[]{100,50};
            arr[0][columns/2]=root;
        }
        for(int i=0;i<rows;i++)
            for(int j=1;j<columns;j++)
                if((temp=arr[i][j])!=null){
                    if(temp.left!=null)
                    temp.left.setPos(new int[]{temp.pos[0]-maxDepth(temp.left)*50,temp.pos[1]+100});
                    if(temp.right!=null)
                    temp.right.setPos(new int[]{temp.pos[0]+maxDepth(temp.right)*50,temp.pos[1]+100});
                    arr[i+1][j-maxDepth(temp.left)]=temp.left;
                    arr[i+1][j+maxDepth(temp.right)]=temp.right;
                }
        return arr;
    }
    public int maxDepth(Node node) {
        if (node == null)
            return 0;
        else {
            int lDepth = maxDepth(node.left);
            int rDepth = maxDepth(node.right);
            return (lDepth > rDepth)?(lDepth + 1):(rDepth + 1);
        }
    }
}