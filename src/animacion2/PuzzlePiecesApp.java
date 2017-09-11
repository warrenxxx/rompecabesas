package animacion2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import rompecabesas.nodo;
import rompecabesas.tu_asterisco;

public class PuzzlePiecesApp extends Application {

    private SimpleStringProperty mensaje;
    final List<Piece> pieces = new ArrayList<Piece>();
    private Timeline timeline;
    private ArrayList<Integer> movimientos[];
    private ArrayList<Integer> ini;
    int p = 0;

    public Parent createContent() {
        mensaje = new SimpleStringProperty();
        Image image = new Image(PuzzlePiecesApp.class.getResourceAsStream("/recursos/a1.jpg"));
        int numOfColumns = (int) (image.getWidth() / Piece.SIZE);
        int numOfRows = (int) (image.getHeight() / Piece.SIZE);
        final Desk desk = new Desk(numOfColumns, numOfRows);
        int k = 0;
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfColumns; col++) {
                int x = col * Piece.SIZE;
                int y = row * Piece.SIZE;
                final Piece piece = new Piece(image, x, y, desk.getWidth(), desk.getHeight(), numOfColumns);
                piece.setPocicion(k++);
                pieces.add(piece);
            }
        }
        desk.getChildren().addAll(pieces);
        Button shuffleButton = new Button("Shuffle");
        shuffleButton.setStyle("-fx-font-size: 2em;");
        shuffleButton.setOnAction((ActionEvent actionEvent) -> {
            SequentialTransition sequence = new SequentialTransition();
            ArrayList<Integer> aux = new ArrayList();
            for (int i = 0; i < numOfColumns * numOfRows; i++) {
                aux.add(i);
            }
            Collections.shuffle(aux);

            ini = aux;
            sequence.getChildren().add(movimiento(ini));
            sequence.play();
            sequence.setOnFinished(e -> {
                pieces.get(0).setVisible(false);
            });
            mensaje.set("");

        });

        Button solveButton = new Button("Solve");
        solveButton.setStyle("-fx-font-size: 2em;");
        solveButton.setOnAction((ActionEvent actionEvent) -> {
            nodo x = new nodo(numOfRows, numOfColumns);
            x.setmatris(ini);
            tu_asterisco asterisco = new tu_asterisco(x);
            boolean esta_ressuelto = false;
            try {
                esta_ressuelto = asterisco.resolver();
            } catch (InterruptedException ex) {
                Logger.getLogger(PuzzlePiecesApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (esta_ressuelto == false) {
                mensaje.set("No se Puede Resolver");
                return;
            }
            mensaje.set("si se pudo Apruebenos");

            ArrayList<nodo> movimientos = asterisco.getMov();
            SequentialTransition seq = new SequentialTransition();
            for (int i = movimientos.size() - 1; i >= 0; i--) {
                nodo g = movimientos.get(i);
                System.out.println(g.costo);
                seq.getChildren().add(movimiento(movimientos.get(i).getMovimiento()));
            }
            seq.play();
            seq.setOnFinished(e -> {
                pieces.get(0).setVisible(true);
                informacion(asterisco.por_procesar.size(), asterisco.nodosprocesados, asterisco.hu1, asterisco.hu2);
            });
        });
        Label t = new Label();
        t.textProperty().bindBidirectional(mensaje);
        HBox buttonBox = new HBox(8);

        buttonBox.getChildren().addAll(shuffleButton, solveButton, t);

        VBox vb = new VBox(10);

        vb.getChildren().addAll(desk, buttonBox);
        vb.setPadding(new Insets(15, 24, 15, 24));
        vb.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
        vb.setMinSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);

        return vb;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private ArrayList getPosision(ArrayList l) {
        ArrayList<par> res = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            res.add(new par(i, (int) l.get(i)));
        }
        res.sort((a, b) -> {
            if (a.b < b.b) {
                return -1;
            }
            if (a.b == b.b) {
                return 0;
            }
            if (a.b > b.b) {
                return 1;
            }
            return -1;
        });
        ArrayList gg = new ArrayList();
        for (int i = 0; i < res.size(); i++) {
            gg.add(res.get(i).a);
        }
        return gg;
    }

    public Timeline movimiento(ArrayList n) {
        ArrayList<Integer> aux = getPosision(n);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).setPocicion(aux.get(i));
        }

        Timeline timel = new Timeline();

        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            piece.setActive();
            timel.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(piece.translateXProperty(), piece.mx),
                            new KeyValue(piece.translateYProperty(), piece.my)));
        }
//            timel.playFromStart();
        return timel;
    }

    public void informacion(int a,int b,String c,String d) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Informacion");
        dialog.setHeaderText("Informacion Obtenida");
        dialog.setGraphic(new ImageView(this.getClass().getResource("../recursos/logo3.png").toString()));
        dialog.setX(0);
        ButtonType loginButtonType = new ButtonType("oks", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextArea t1=new TextArea(c);
        t1.setPrefWidth(60);
        t1.setPrefHeight(40);
        TextArea t2=new TextArea(d);
        t2.setPrefWidth(60);
        t2.setPrefHeight(40);
        grid.add(new Label("Nodos Acumulados en la cola:"), 0, 0);
        grid.add(new TextField(a+""), 1, 0);

        grid.add(new Label("Nodos Procesados:"), 0, 1);
        grid.add(new TextField(b+""), 1, 1);

        grid.add(new Label("Heuristicas Obtenidas:"), 0, 2);
        grid.add(t1, 1, 2);

        grid.add(new Label("Heuristicas acumuladas:"), 0, 3);
        grid.add(t2, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
