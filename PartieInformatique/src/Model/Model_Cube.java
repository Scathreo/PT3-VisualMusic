package Model;

import java.awt.Point;
/*
 * Classe qui permet la cr�ation d'un cube
 */
public class Model_Cube {
		// D�claration des variables
        int x, y, height, width;
        
        Point[] cube_points;
        Point[] getcube;
        /*
         * Constructeur principal
         */
        public Model_Cube(int x, int y, int size, int shift) {
            this.x = x;
            this.y = y;
            this.height = size;
            this.width = shift;
            cube_points = getCubenouveauPoint();
            getcube = getCube();
        }

        /*
         * On cr�er une m�thode qui renvoient un tableau de 
         * 4 points des valeurs pr�d�finis
         */
        private Point[] getCube() {
            Point[] points = new Point[4];
            points[0] = new Point(x, y);
            points[1] = new Point(x + height, y);
            points[2] = new Point(x + height, y + height);
            points[3] = new Point(x, y + height);
            
            return points;
        }

        /*
         * On renvoie un tableau qui permet la fabrication
         * d'une deuxi�me face du cube en modifiant les valeurs donn�es
         */
        private Point[] getCubenouveauPoint() {
            int nouveau_x = x + width;
            int nouveau_y = y + width;
            Point[] points = new Point[4];
            points[0] = new Point(nouveau_x, nouveau_y);
            points[1] = new Point(nouveau_x + height, nouveau_y);
            points[2] = new Point(nouveau_x + height, nouveau_y + height);
            points[3] = new Point(nouveau_x, nouveau_y + height);
            
            return points;
        }

}
