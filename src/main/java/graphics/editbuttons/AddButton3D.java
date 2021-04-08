package graphics.editbuttons;

import canvas2d.Line2D;
import canvas2d.Point2D;
import canvas2d.Vector2D;
import canvas3d.*;
import graphics.DefinedVariables;
import graphics.DoubleFormatter;
import graphics.Icons;
import graphics.VariableContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import math.Complex;
import math.Matrix;
import math3d.Vector3;
import regex.RegexUtils;
import terraingeneration.InfiniteTerrain;

import java.util.Arrays;
import java.util.stream.Collectors;
/**
 * The button to press for adding new elements in the 3D canvas, fills up the MenuItem list with clickable menuItems
 * for creating different types of 3D elements (vectors, points etc.)
 */
public class AddButton3D extends MenuButton {
    //TODO Add support for more variables (grid, linesegment and more) in the new menu window
    private TextInputDialog dialog;

    public AddButton3D() {
        super("Add 3D shape");
        setGraphic(Icons.of("addnew.png", 30));
        getStyleClass().add("transparent-button");
        getStyleClass().add("new-menu-button");
        clearDialog();

        MenuItem infiniteTerrain = new MenuItem("Mesh");
        infiniteTerrain.getStyleClass().add("new-menu-item");
        infiniteTerrain.setOnAction(actionEvent -> {
            clearDialog();

            dialog.setHeaderText("Choose a mesh to create");
            dialog.setContentText("Enter name:");

            //Creates a set of radiobuttons to pick what mesh to create
            RadioButton terrain = new RadioButton("Infinite Terrain");
            RadioButton chevrolet = new RadioButton("Chevrolet");

            dialog.getDialogPane().setGraphic(new HBox(terrain, chevrolet));

            ToggleGroup toggleGroup = new ToggleGroup();
            terrain.setToggleGroup(toggleGroup);
            terrain.setSelected(true);
            chevrolet.setToggleGroup(toggleGroup);



            dialog.showAndWait().ifPresent(response ->{

                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;
                String selected = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if(selected.equals("Infinite Terrain"))
                    DefinedVariables.add(new InfiniteTerrain(), dialog.getEditor().getText());
                else if(selected.equals("Chevrolet"))
                    DefinedVariables.add(new Mesh("chevrolet.obj", Vector3.ZERO(), 1), dialog.getEditor().getText());
                else
                    System.out.println("Did not understand: "+ selected);
            });
        });


        MenuItem vector = new MenuItem("Vector 3D", Icons.of("vector.png", 20));
        vector.getStyleClass().add("new-menu-item");
        vector.setOnAction(actionEvent -> {
            clearDialog();
            var inputBox = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            inputBox.getChildren().addAll(new Text("Enter x, y, z :   "), xInput, yInput, zInput);
            dialog.setGraphic(inputBox);
            dialog.setHeaderText("Vector 3D");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{

                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Vector3D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem matrix = new MenuItem("Matrix 3x3", Icons.of("matrix2d.png", 20));
        matrix.getStyleClass().add("new-menu-item");
        matrix.setOnAction(actionEvent -> {
            clearDialog();
            var matrixInputRows = new VBox();
            var aRow = new HBox();
            var bRow = new HBox();
            var cRow = new HBox();

            TextField[][] inputs = new TextField[3][3];
            for(int i = 0; i < 3; i++)
                for(int j = 0; j < 3; j++)
                    inputs[i][j] = DoubleFormatter.getTextField();

            aRow.getChildren().addAll(inputs[0]);
            bRow.getChildren().addAll(inputs[1]);
            cRow.getChildren().addAll(inputs[2]);

            matrixInputRows.getChildren().addAll(new Text("Enter values:   "), aRow, bRow, cRow);
            dialog.setGraphic(matrixInputRows);
            dialog.setHeaderText("Matrix 3x3");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;


                double[][] mat = new double[3][3];
                for(int i = 0; i < 3; i++)
                    for(int j = 0; j< 3; j++)
                        mat[i][j] = ((double) inputs[i][j].getTextFormatter().getValue());

                DefinedVariables.add(new VariableContainer<Matrix>(new Matrix(mat), dialog.getEditor().getText()));
            });
        });

//        MenuItem line = new MenuItem("Line", Icons.of("line.png", 20));
//        line.getStyleClass().add("new-menu-item");
//        line.setOnAction(actionEvent -> {
//            clearDialog();
//            var lineInputRows = new VBox();
//            var aRow = new HBox();
//            var bRow = new HBox();
//
//            TextField aInput = DoubleFormatter.getTextField();
//            TextField bInput = DoubleFormatter.getTextField();
//            TextField cInput = DoubleFormatter.getTextField();
//            TextField dInput = DoubleFormatter.getTextField();
//
//            aRow.getChildren().addAll(aInput, bInput);
//            bRow.getChildren().addAll(cInput, dInput);
//
//            lineInputRows.getChildren().addAll(new Text("Enter point:   "), aRow, new Text("Enter directional vector:   "), bRow);
//            dialog.setGraphic(lineInputRows);
//            dialog.setHeaderText("Line");
//            dialog.setContentText("Enter name:");
//            dialog.showAndWait().ifPresent(response ->{
//                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
//                    return;
//
//                DefinedVariables.add(new Line2D(new Point2D((double) aInput.getTextFormatter().getValue(), (double) bInput.getTextFormatter().getValue()), new Vector2D((double) cInput.getTextFormatter().getValue(), (double) dInput.getTextFormatter().getValue())), dialog.getEditor().getText());
//            });
//        });



        MenuItem point = new MenuItem("Point 3D", Icons.of("point.png", 20));
        point.getStyleClass().add("new-menu-item");
        point.setOnAction(actionEvent -> {
            clearDialog();
            var inputBox = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            inputBox.getChildren().addAll(new Text("Enter x, y, z:   "), xInput, yInput, zInput);
            dialog.setGraphic(inputBox);
            dialog.setHeaderText("Point 3D");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Point3D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });


        //Cube
        MenuItem cube = new MenuItem("Cube");
        cube.getStyleClass().add("new-menu-item");
        cube.setOnAction(actionEvent -> {
            clearDialog();
            var inputBox = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            TextField rInput = DoubleFormatter.getTextField(1);


            inputBox.getChildren().addAll(new Text("Enter x, y, z:   "), xInput, yInput, zInput, new Text("Enter size"), rInput);
            dialog.setGraphic(inputBox);
            dialog.setHeaderText("Cube");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Cube(new Vector3((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), ((double) rInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });


        //Sphere
        MenuItem sphere = new MenuItem("Sphere");
        sphere.getStyleClass().add("new-menu-item");
        sphere.setOnAction(actionEvent -> {
            clearDialog();
            var inputBox = new HBox();
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            TextField rInput = DoubleFormatter.getTextField(1);


            inputBox.getChildren().addAll(new Text("Enter x, y, z:   "), xInput, yInput, zInput, new Text("Enter radius"), rInput);
            dialog.setGraphic(inputBox);
            dialog.setHeaderText("Sphere");
            dialog.setContentText("Enter name:");
            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Sphere(new Vector3((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), ((double) rInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });

        getItems().addAll(infiniteTerrain, vector, matrix, point, cube, sphere);
    }


    private void clearDialog(){
        dialog = new TextInputDialog("");
        dialog.initModality(Modality.APPLICATION_MODAL);
    }
}

