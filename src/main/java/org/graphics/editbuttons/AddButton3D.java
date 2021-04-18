package org.graphics.editbuttons;

import org.canvas3d.*;
import org.graphics.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.linalgfx.DefinedVariables;
import org.math.Matrix;
import org.math3d.Vector3;
import org.utils.DoubleFormatter;
import org.utils.RegexUtils;
import org.terraingeneration.InfiniteTerrain;

/**
 * The button to press for adding new elements in the 3D canvas, fills up the MenuItem list with clickable menuItems
 * for creating different types of 3D elements (vectors, points etc.)
 */
public class AddButton3D extends MenuButton {
    //TODO Add support for more variables (grid, linesegment and more) in the new menu window

    public AddButton3D() {
        super("Add 3D shape");
        setGraphic(Icons.of("addnew.png", 30));
        getStyleClass().add("transparent-button");
        getStyleClass().add("new-menu-button");


        MenuItem infiniteTerrain = new MenuItem("Mesh");
        infiniteTerrain.getStyleClass().add("new-menu-item");
        infiniteTerrain.setOnAction(actionEvent -> {


            //Creates a set of radiobuttons to pick what mesh to create
            RadioButton terrain = new RadioButton("Infinite Terrain");
            RadioButton chevrolet = new RadioButton("Chevrolet");

            SimpleDialog dialog = new SimpleDialog("Select Mesh to add to canvas", terrain, chevrolet);

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
                    throw new IllegalArgumentException("Did not understand: "+ selected);
            });
        });


        MenuItem vector = new MenuItem("Vector 3D", Icons.of("vector.png", 20));
        vector.getStyleClass().add("new-menu-item");
        vector.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            SimpleDialog dialog = new SimpleDialog("Vector 3D", new Text("Enter x, y, z :   "), xInput, yInput, zInput);

            dialog.showAndWait().ifPresent(response ->{

                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Vector3D((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), dialog.getEditor().getText());
            });
        });

        MenuItem matrix = new MenuItem("Matrix 3x3", Icons.of("matrix2d.png", 20));
        matrix.getStyleClass().add("new-menu-item");
        matrix.setOnAction(actionEvent -> {
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

            SimpleDialog dialog = new SimpleDialog("Matrix 3x3", matrixInputRows);

            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;


                double[][] mat = new double[3][3];
                for(int i = 0; i < 3; i++)
                    for(int j = 0; j< 3; j++)
                        mat[i][j] = ((double) inputs[i][j].getTextFormatter().getValue());

                DefinedVariables.add(new VariableContainer<>(new Matrix(mat), dialog.getEditor().getText()));
            });
        });



        MenuItem point = new MenuItem("Point 3D", Icons.of("point.png", 20));
        point.getStyleClass().add("new-menu-item");
        point.setOnAction(actionEvent -> {
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

           SimpleDialog dialog = new SimpleDialog("Point 3D", new Text("Enter x, y, z:   "), xInput, yInput, zInput);
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
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            TextField rInput = DoubleFormatter.getTextField(1);


            SimpleDialog dialog = new SimpleDialog("Cube", new Text("Enter x, y, z:   "), xInput, yInput, zInput, new Text("Enter size"), rInput);

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
            TextField xInput = DoubleFormatter.getTextField();
            TextField yInput = DoubleFormatter.getTextField();
            TextField zInput = DoubleFormatter.getTextField();

            TextField rInput = DoubleFormatter.getTextField(1);

            SimpleDialog dialog = new SimpleDialog("Sphere", new Text("Enter x, y, z:   "), xInput, yInput, zInput, new Text("Enter radius"), rInput);

            dialog.showAndWait().ifPresent(response ->{
                if(!RegexUtils.isValidName(dialog.getEditor().getText()))
                    return;

                DefinedVariables.add(new Sphere(new Vector3((double) xInput.getTextFormatter().getValue(), (double) yInput.getTextFormatter().getValue(), (double) zInput.getTextFormatter().getValue()), ((double) rInput.getTextFormatter().getValue())), dialog.getEditor().getText());
            });
        });

        getItems().addAll(infiniteTerrain, vector, matrix, point, cube, sphere);
    }
}
