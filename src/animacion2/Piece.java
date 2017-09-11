package animacion2;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
public class Piece extends Parent{
        public static final int SIZE = 100;
        int n;
        public double ax=0;
        public double ay=0;
        public double axx=0;
        public double ayy=0;

        public double mx=0;
        public double my=0;
        
        private Shape pieceStroke;
        private Shape pieceClip;
        private ImageView imageView = new ImageView();
        private Point2D dragAnchor;
        
                private double startDragX;
        private double startDragY;

        
        public Piece(Image image, final double ax, final double ay,
                     final double deskWidth, final double deskHeight,int n) {
            this.ax = ax;
            this.ay = ay;
            axx=ax;
            ayy=ay;
            this.n=n;
            pieceClip = createPiece();
            pieceClip.setFill(Color.WHITE);
            pieceClip.setStroke(null);
            // add a stroke
            pieceStroke = createPiece();
            pieceStroke.setFill(null);
            pieceStroke.setStroke(Color.BLACK);
            // create image view
            imageView.setImage(image);
            imageView.setClip(pieceClip);
            setFocusTraversable(true);
            
            getChildren().addAll(imageView, pieceStroke);
//            getChildren().addAll(imageView);
            // turn on caching so the jigsaw piece is fasr to draw when dragging
            setCache(true);
            // start in inactive state
            setInactive();
            // add listeners to support dragging
                        setOnMousePressed((MouseEvent me) -> {
                toFront();
                startDragX = getTranslateX();
                startDragY = getTranslateY();
                dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
            });
            setOnMouseReleased((MouseEvent me) -> {
                if (getTranslateX() < (10) && getTranslateX() > (- 10) &&
                        getTranslateY() < (10) && getTranslateY() > (- 10)) {
                    setTranslateX(0);
                    setTranslateY(0);
                    setInactive();
                }
            });
            setOnMouseDragged((MouseEvent me) -> {
                double newTranslateX = startDragX
                        + me.getSceneX() - dragAnchor.getX();
                double newTranslateY = startDragY
                        + me.getSceneY() - dragAnchor.getY();
                double minTranslateX = - 45f - ax;
                double maxTranslateX = (deskWidth - Piece.SIZE + 50f ) - ax;
                double minTranslateY = - 30f - ay;
                double maxTranslateY = (deskHeight - Piece.SIZE + 70f ) - ay;
                if ((newTranslateX> minTranslateX ) &&
                        (newTranslateX< maxTranslateX) &&
                        (newTranslateY> minTranslateY) &&
                        (newTranslateY< maxTranslateY)) {
                    setTranslateX(newTranslateX);
                    setTranslateY(newTranslateY);
                }
            });
        }
        
        public void setPocicion(int a){            
            int px=a%n;
            int py=a/n;        
            mx=px*SIZE-axx;
            my=py*SIZE-ayy;
            ax=px*SIZE;
            ay=py*SIZE;
        }
        public void act(){


        }
        
        private Shape createPiece() {
            Shape shape = createPieceRectangle();
            shape.setTranslateX(ax);
            shape.setTranslateY(ay);
            shape.setLayoutX(50f);
            shape.setLayoutY(50f);
            return shape;
        }

        private Rectangle createPieceRectangle() {
            Rectangle rec = new Rectangle();
            rec.setX(-50);
            rec.setY(-50);
            rec.setWidth(SIZE);
            rec.setHeight(SIZE);
            return rec;
        }
 
        public void setActive() {
            setDisable(false);
            setEffect(new DropShadow());
            toFront();
        }

        public void setInactive() {
            setEffect(null);
            setDisable(true);
            toBack();
            setActive();
        }

        public double getCorrectX() { return ax; }

        public double getCorrectY() { return ay; }
}
