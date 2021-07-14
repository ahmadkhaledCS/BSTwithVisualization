package MainApp.Controllers;


import MainApp.Trees.Tree;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Numbers {

    public Tree tree=new Tree();
    public Button add,delete,clear,inButton,preButton,postButton;
    public TextField addTextField,deleteTextField;
    public TextField preTextField,inTextField,postTextField;
    public AnchorPane pane;
    int counter=0;

    //----------------------
    public void play(Event event){
        String text="";
        String[]numbers;
        int key;
        switch (((Button)event.getSource()).getId()){
            case "preButton":
                disableButtons(false,true,true);
                text=tree.preorder();
                preButton.setText("next");
                changeColor(tree.root.key);
                break;
            case "inButton":
                disableButtons(true,true,false);
                text=tree.inorder();
                inButton.setText("next");
                break;
            case "postButton":
                disableButtons(true,false,true);
                text=tree.postorder();
                postButton.setText("next");
                break;
        }
        numbers=text.split(" ");
        if(counter<numbers.length){
            key=Integer.parseInt(numbers[counter++]);
            changeColor(key);
        }
        else{
            update();
            counter=0;
            preButton.setText("play");
            postButton.setText("play");
            inButton.setText("play");
            disableButtons(false,false,false);
        }
    }
    public void changeColor(int key){
        for(Node node:pane.getChildren()){
            int[]circlePos=new int[]{(int)node.getBoundsInParent().getMinX(),(int)node.getBoundsInParent().getMinY()};
            if(node instanceof Circle && circlePos[0]+30==search(key)[0]&&circlePos[1]+30==search(key)[1]){
                ((Circle) node).setFill(Color.BROWN);
            }
        }
    }
    public void disableButtons(boolean preorder, boolean postorder, boolean inorder){
        inButton.setDisable(inorder);
        preButton.setDisable(preorder);
        postButton.setDisable(postorder);
    }
    public int[]search(int key){
        int shift= Math.abs(tree.minNode(tree.root).pos[0])+100;
        for(Tree.Node[] node :tree.nodesArray())
            for(Tree.Node n:node)
                if(n!=null && n.key==key)
                    return new int[]{n.pos[0]+shift,n.pos[1]};
        return null;
    }
    //-----------------
    public void add(){
        String data=addTextField.getText();
        if(data.matches("\\d+|-\\d+")){
            tree.insert(Integer.parseInt(data));
        }
        update();
    }
    public void delete(){
        String data=deleteTextField.getText();
        if(data.matches("\\d+|-\\d+")){
            try {
                tree.deleteKey(Integer.parseInt(data));
            }catch (Exception ignored){}
        }
        update();
    }
    public void clear(){
        tree=new Tree();
        update();
    }
    public void update(){
        Tree.Node [][]arr= tree.nodesArray();
        Circle circle;
        Label label;
        int shift;
        setLabels();
        pane.getChildren().removeIf(node -> node instanceof Circle || node instanceof Label|| node instanceof Line);
        if(tree.root!=null){
            shift= Math.abs(tree.minNode(tree.root).pos[0])+100;
            for (Tree.Node[] nodes : arr)
                for (int j = 0; j < arr[0].length; j++)
                    if (nodes[j] != null) {
                        nodes[j].setPos(new int[]{nodes[j].pos[0]+shift,nodes[j].pos[1]});

                        circle = new Circle(30);
                        circle.setFill(Color.CYAN);
                        circle.setTranslateX(nodes[j].pos[0]);
                        circle.setTranslateY(nodes[j].pos[1]);
                        label = new Label(nodes[j].key + "");
                        label.setFont(new Font(30));
                        label.setTranslateX(nodes[j].pos[0] - 15);
                        label.setTranslateY(nodes[j].pos[1] - 20);
                        Line line=new Line(0,tree.maxDepth(tree.root)*100,tree.maxDepth(tree.root)*400,tree.maxDepth(tree.root)*100);
                        line.setVisible(false);

                        if(nodes[j].left!=null)
                            pane.getChildren().add(new Line(nodes[j].pos[0],nodes[j].pos[1] , nodes[j].left.pos[0]+shift, nodes[j].left.pos[1]));
                        if(nodes[j].right!=null)
                            pane.getChildren().addAll(new Line(nodes[j].pos[0],nodes[j].pos[1] , nodes[j].right.pos[0]+shift, nodes[j].right.pos[1]));
                        pane.getChildren().addAll(line,circle, label);
                    }
        }
    }
    public void setLabels(){
        addTextField.setText("");
        deleteTextField.setText("");
        preTextField.setText(tree.preorder());
        postTextField.setText(tree.postorder());
        inTextField.setText(tree.inorder());
    }
    public void switchToMenu(Event event) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/MainApp/FXMLFiles/Menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}

