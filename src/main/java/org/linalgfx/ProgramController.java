package org.linalgfx;

import canvas2d.CanvasPane2D;
import canvas2d.CanvasRenderer2D;
import canvas3d.CanvasPane3D;
import canvas3d.CanvasRenderer3D;
import graphics.*;
import graphics.ToolBar;
import graphics.textInput.OperatorMaps;
import graphics.textInput.TextInputEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import math.Differentiator;
import regex.RegexUtils;
import write.Writable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProgramController {
    @FXML
    private VBox root;
    @FXML
    private TextField inputField;
    @FXML
    private SplitPane splitPane;
    @FXML
    private VBox toolBar;

    public void initialize() {
        CanvasPane2D canvasPane2D = new CanvasPane2D(16 * 30, 9 * 30);
        Canvas canvas2D = canvasPane2D.getCanvas();
        GraphicsContext graphicsContext2D = canvas2D.getGraphicsContext2D();
        CanvasRenderer2D.setCanvas(canvas2D);
        CanvasRenderer2D.setGraphicsContext(graphicsContext2D);
        CanvasRenderer2D.setUnitSize(40);
        CanvasRenderer2D.start();

        CanvasPane3D canvasPane3D = new CanvasPane3D(16 * 30, 9 * 30);
        Canvas canvas3D = canvasPane3D.getCanvas();
        GraphicsContext graphicsContext3D = canvas3D.getGraphicsContext2D();
        CanvasRenderer3D.setCanvas(canvas3D);
        CanvasRenderer3D.setGraphicsContext(graphicsContext3D);
        CanvasRenderer3D.start();

        toolBar.getChildren().add(new ToolBar());

        DefinedVariables.getScrollPane().getStyleClass().add("variables");
        DefinedVariables.getScrollPane().setMinWidth(150);

        inputField = new TextField();
        inputField.setOnAction(new TextInputEvent(inputField));

        splitPane.getItems().addAll(DefinedVariables.getScrollPane(), canvasPane2D, canvasPane3D);
        splitPane.prefHeightProperty().bind(root.heightProperty());
        splitPane.setDividerPositions(0.3, 0.9);

        OperatorMaps.fillOpMaps();
        Differentiator.fillDerivatives();

        DefinedVariables.getScrollPane().setMinWidth(150);
    }
}
