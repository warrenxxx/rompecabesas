package animacion2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
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
            //for (int i = 0; i < numOfColumns * numOfRows; i++) {
              //  aux.add(i);
            //}
            //Collections.shuffle(aux);
            aux.add(1);
            aux.add(0);
            aux.add(4);
            aux.add(3);
            aux.add(5);
            aux.add(2);
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

    public static void main(String[] args) {
        launch(args);
    }
}
