package org.asdfjkl.jerryfx.gui;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import javafx.scene.image.ImageView;
import org.asdfjkl.jerryfx.SystemInfo;
import org.asdfjkl.jerryfx.lib.Game;
import org.asdfjkl.jerryfx.lib.OptimizedRandomAccessFile;
import org.asdfjkl.jerryfx.lib.PgnReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {


        GameModel gameModel = new GameModel();
        gameModel.getGame().setTreeWasChanged(true);


        // MENU
        MenuBar mnuBar = new MenuBar();

        Menu mnuFile = new Menu("Game");
        Menu mnuEdit = new Menu("Edit");
        Menu mnuMode = new Menu("Mode");
        Menu mnuDatabase = new Menu("Database");
        Menu mnuHelp = new Menu("Help");

        // File Menu
        MenuItem itmNew = new MenuItem("New...");
        MenuItem itmOpenFile = new MenuItem("Open File");
        MenuItem itmSaveCurrentGameAs = new MenuItem("Save Current Game As");
        MenuItem itmPrintGame = new MenuItem("Print Game");
        MenuItem itmPrintPosition = new MenuItem("Print Game");
        MenuItem itmSavePositionAsImage = new MenuItem("Save Position As Image");
        MenuItem itmQuit = new MenuItem("Quit");

        mnuFile.getItems().addAll(itmNew, itmOpenFile, itmSaveCurrentGameAs,
                new SeparatorMenuItem(), itmPrintGame, itmPrintPosition, itmSavePositionAsImage,
                new SeparatorMenuItem(), itmQuit);

        // Edit Menu
        MenuItem itmCopyGame = new MenuItem("Copy Game");
        MenuItem itmCopyPosition = new MenuItem("Copy Position");
        MenuItem itmPaste = new MenuItem("Paste Game/Position");
        MenuItem itmEditGame = new MenuItem("Edit Game Data");
        MenuItem itmEnterPosition = new MenuItem("Enter Position");
        CheckMenuItem itmFlipEval = new CheckMenuItem("Flip Board");
        CheckMenuItem itmShowSearchInfo = new CheckMenuItem("Show Search Info");
        MenuItem itmAppearance = new MenuItem("Appearance");
        MenuItem itmResetLayout = new MenuItem("Reset Layout");

        mnuEdit.getItems().addAll(itmCopyGame, itmCopyPosition, itmPaste,
                new SeparatorMenuItem(), itmEditGame, itmEnterPosition,
                new SeparatorMenuItem(), itmFlipEval, itmShowSearchInfo, itmAppearance, itmResetLayout);

        // Mode Menu
        RadioMenuItem itmAnalysis = new RadioMenuItem("Analysis");
        RadioMenuItem itmPlayAsWhite = new RadioMenuItem("Play as White");
        RadioMenuItem itmPlayAsBlack = new RadioMenuItem("Play as Black");
        RadioMenuItem itmEnterMoves = new RadioMenuItem("Enter Moves");

        ToggleGroup tglMode = new ToggleGroup();
        tglMode.getToggles().add(itmAnalysis);
        tglMode.getToggles().add(itmPlayAsWhite);
        tglMode.getToggles().add(itmPlayAsBlack);
        tglMode.getToggles().add(itmEnterMoves);
        MenuItem itmFullGameAnalysis = new MenuItem("Full Game Analysis");
        MenuItem itmPlayoutPosition = new MenuItem("Play Out Position");
        MenuItem itmEngines = new MenuItem("Engines...");

        mnuMode.getItems().addAll(itmAnalysis, itmPlayAsWhite, itmPlayAsBlack, itmEnterMoves,
                new SeparatorMenuItem(), itmFullGameAnalysis, itmPlayoutPosition,
                new SeparatorMenuItem(), itmEngines);

        // Database Menu
        MenuItem itmBrowseDatabase = new MenuItem("Browse Database");
        MenuItem itmNextGame = new MenuItem("Next Game");
        MenuItem itmPreviousGame = new MenuItem("Previous Game");

        mnuDatabase.getItems().addAll(itmBrowseDatabase, itmNextGame, itmPreviousGame);

        // Help Menu
        MenuItem itmAbout = new MenuItem("About");
        MenuItem itmJerryHomepage = new MenuItem("JerryFX - Homepage");

        mnuHelp.getItems().addAll(itmAbout, itmJerryHomepage);

        mnuBar.getMenus().addAll(mnuFile, mnuEdit, mnuMode, mnuDatabase, mnuHelp);

        // TOOLBAR
        ToolBar tbMainWindow = new ToolBar();
        Button btnNew = new Button("New");
        btnNew.setGraphic(new ImageView( new Image("icons/document-new.png")));
        btnNew.setContentDisplay(ContentDisplay.TOP);

        Button btnOpen = new Button("Open");
        btnOpen.setGraphic(new ImageView( new Image("icons/document-open.png")));
        btnOpen.setContentDisplay(ContentDisplay.TOP);

        Button btnSaveAs = new Button("Save As");
        btnSaveAs.setGraphic(new ImageView( new Image("icons/document-save.png")));
        btnSaveAs.setContentDisplay(ContentDisplay.TOP);

        Button btnPrint = new Button("Print");
        btnPrint.setGraphic(new ImageView( new Image("icons/document-print.png")));
        btnPrint.setContentDisplay(ContentDisplay.TOP);

        Button btnFlipBoard = new Button("Flip Board");
        btnFlipBoard.setGraphic(new ImageView( new Image("icons/view-refresh.png")));
        btnFlipBoard.setContentDisplay(ContentDisplay.TOP);

        Button btnCopyGame = new Button("Copy Game");
        btnCopyGame.setGraphic(new ImageView( new Image("icons/edit-copy-pgn.png")));
        btnCopyGame.setContentDisplay(ContentDisplay.TOP);

        Button btnCopyPosition = new Button("Copy Position");
        btnCopyPosition.setGraphic(new ImageView( new Image("icons/edit-copy-fen.png")));
        btnCopyPosition.setContentDisplay(ContentDisplay.TOP);

        Button btnPaste = new Button("Paste");
        btnPaste.setGraphic(new ImageView( new Image("icons/edit-paste.png")));
        btnPaste.setContentDisplay(ContentDisplay.TOP);

        Button btnEnterPosition = new Button("Enter Position");
        btnEnterPosition.setGraphic(new ImageView( new Image("icons/document-enter-position.png")));
        btnEnterPosition.setContentDisplay(ContentDisplay.TOP);

        Button btnFullAnalysis = new Button("Full Analysis");
        btnFullAnalysis.setGraphic(new ImageView( new Image("icons/emblem-system.png")));
        btnFullAnalysis.setContentDisplay(ContentDisplay.TOP);

        Button btnBrowseGames = new Button("Browse Games");
        btnBrowseGames.setGraphic(new ImageView( new Image("icons/database.png")));
        btnBrowseGames.setContentDisplay(ContentDisplay.TOP);

        Button btnPrevGame = new Button("Prev. Game");
        btnPrevGame.setGraphic(new ImageView( new Image("icons/go-previous.png")));
        btnPrevGame.setContentDisplay(ContentDisplay.TOP);

        Button btnNextGame = new Button("Next Game");
        btnNextGame.setGraphic(new ImageView( new Image("icons/go-next.png")));
        btnNextGame.setContentDisplay(ContentDisplay.TOP);

        Button btnAbout = new Button("About");
        btnAbout.setGraphic(new ImageView( new Image("icons/help-browser.png")));
        btnAbout.setContentDisplay(ContentDisplay.TOP);

        tbMainWindow.getItems().addAll(btnNew, btnOpen, btnSaveAs, btnPrint, new Separator(),
                btnFlipBoard, new Separator(),
                btnCopyGame, btnCopyPosition, btnPaste, btnEnterPosition, new Separator(),
                btnFullAnalysis, new Separator(),
                btnBrowseGames, btnPrevGame, btnNextGame,  btnAbout);


        // Text & Edit Button for Game Data
        Text txtGameData = new Text("Kasparov, G. (Wh) - Kaprov, A. (B)\nSevilla, XX.YY.1993");
        txtGameData.setTextAlignment(TextAlignment.CENTER);
        Button btnEditGameData = new Button();
        btnEditGameData.setGraphic(new ImageView( new Image("icons/document_properties_small.png")));
        Region spcrGameDataLeft = new Region();
        Region spcrGameDataRight = new Region();

        HBox hbGameData = new HBox();
        hbGameData.getChildren().addAll(spcrGameDataLeft, txtGameData, spcrGameDataRight, btnEditGameData);
        hbGameData.setHgrow(spcrGameDataLeft, Priority.ALWAYS);
        hbGameData.setHgrow(spcrGameDataRight, Priority.ALWAYS);

        // Create a WebView
        /*
        WebView viewMoves = new WebView();
        viewMoves.resize(320,200);
        viewMoves.setMinWidth(1);
        viewMoves.setMaxWidth(Double.MAX_VALUE);
        viewMoves.setMaxHeight(Double.MAX_VALUE);
         */
        MoveView moveView = new MoveView(gameModel);

        // Navigation Buttons
        Button btnMoveBegin = new Button();
        btnMoveBegin.setGraphic(new ImageView( new Image("icons/ic_first_page_black.png")));
        Button btnMoveEnd = new Button();
        btnMoveEnd.setGraphic(new ImageView( new Image("icons/ic_last_page_black.png")));
        Button btnMovePrev = new Button();
        btnMovePrev.setGraphic(new ImageView( new Image("icons/ic_chevron_left_black.png")));
        Button btnMoveNext = new Button();
        btnMoveNext.setGraphic(new ImageView( new Image("icons/ic_chevron_right_black.png")));

        HBox hbGameNavigation = new HBox();
        hbGameNavigation.setAlignment(Pos.CENTER);
        hbGameNavigation.getChildren().addAll(btnMoveBegin, btnMovePrev, btnMoveNext, btnMoveEnd);

        VBox vbGameDataMovesNavigation = new VBox();
        vbGameDataMovesNavigation.getChildren().addAll(hbGameData, moveView.getWebView(), hbGameNavigation);
        vbGameDataMovesNavigation.setVgrow(moveView.getWebView(), Priority.ALWAYS);

        // put together  Chessboard | Game Navigation
        Chessboard chessboard = new Chessboard(gameModel);
        chessboard.resize(640,480);
        chessboard.updateCanvas();

        SplitPane spChessboardMoves = new SplitPane();
        spChessboardMoves.getItems().addAll(chessboard, vbGameDataMovesNavigation);
        spChessboardMoves.setDividerPosition(0, 0.5);
        spChessboardMoves.setMaxHeight(Double.MAX_VALUE);

        // Buttons for Engine On/Off and TextFlow for Engine Output
        ToggleButton tglEngineOnOff = new ToggleButton("On");
        Label lblMultiPV = new Label("Lines:");
        ComboBox cmbMultiPV = new ComboBox();
        Button btnSelectEngine = new Button();
        btnSelectEngine.setGraphic(new ImageView( new Image("icons/document_properties_small.png")));
        HBox hbEngineControl = new HBox();
        Region spcrEngineControl = new Region();
        hbEngineControl.getChildren().addAll(tglEngineOnOff, lblMultiPV,
                cmbMultiPV, spcrEngineControl, btnSelectEngine);
        hbEngineControl.setAlignment(Pos.CENTER);
        hbEngineControl.setMargin(lblMultiPV, new Insets(0,5,0,10));
        hbEngineControl.setHgrow(spcrEngineControl, Priority.ALWAYS);
        TextFlow txtEngineOut = new TextFlow();
        txtEngineOut.getChildren().add(new Text("Foo"));
        VBox vbBottom = new VBox();
        vbBottom.getChildren().addAll(hbEngineControl, txtEngineOut);

        // put everything excl. the bottom Engine part into one VBox        
        VBox vbMainUpperPart = new VBox();
        vbMainUpperPart.getChildren().addAll(mnuBar, tbMainWindow, spChessboardMoves);
        vbMainUpperPart.setVgrow(spChessboardMoves, Priority.ALWAYS);

        // add another split pane for main window part and engine output
        SplitPane spMain = new SplitPane();
        spMain.setOrientation(Orientation.VERTICAL);
        spMain.getItems().addAll(vbMainUpperPart, vbBottom);
        spMain.setDividerPosition(0, 0.8);

        // events
        gameModel.addListener(chessboard);
        gameModel.addListener(moveView);

        btnMoveNext.setOnAction(event -> moveView.goForward());
        btnMoveBegin.setOnAction(event -> moveView.seekToRoot());
        btnMovePrev.setOnAction(event -> moveView.goBack());
        btnMoveEnd.setOnAction(event -> moveView.seekToEnd());

        PgnReader reader = new PgnReader();
        try {
            OptimizedRandomAccessFile raf = new OptimizedRandomAccessFile("C:\\Users\\user\\MyFiles\\workspace\\test_databases\\middleg.pgn", "r");
            raf.seek(0);
            Game g = reader.readGame(raf);
            gameModel.game = g;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(spMain, 640, 480);
        //chessboard.updateCanvas();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            System.out.println("Key pressed");
            if (event.getCode() == KeyCode.RIGHT) {
                moveView.goForward();
            }
            if (event.getCode() == KeyCode.LEFT) {
                moveView.goBack();
            }
            if (event.getCode() == KeyCode.HOME) {
                moveView.seekToRoot();
            }
            if (event.getCode() == KeyCode.END) {
                moveView.seekToEnd();
            }
            event.consume();
        });

        JMetro jMetro = new JMetro();
        jMetro.setScene(scene);

        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }

}