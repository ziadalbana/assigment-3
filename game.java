package eg.edu.alexu.csd.datastructure.icehockey;
import java.awt.Point;
public class game implements IPlayerFinder {
    static int  xmin=0,xmax=50,ymin=0,ymax=50,counter=0;
    private Boolean[][] visited;
     Point[] position = new Point[500];
    public void setVisited(Boolean[][] visited) {
        this.visited = visited;
    }
    
   public char[][] convertToChar(String[] photo){
        int i;int y;
        char[][] photochar=new char[photo.length][photo[0].length()];
        for( i=0;i<photo.length;i++){
           for( y=0;y<photo[0].length();y++){
               photochar[i][y]=photo[i].charAt(y);
           }
        }
        return photochar;
    }
    public Point center() {
    int centery;
    int centerx;
    centery = ymin + ymax + 1;
    centerx = xmin + xmax + 1;
    xmin=0;xmax=50;ymin=0;ymax=50;counter=0;
    return new Point(centerx,centery);
  }
     
    public int search(char photochar[][],Boolean[][] visited,char c,int threshold){
        int sizerow=photochar.length;
        int sizecol=photochar[0].length;
        int i,y,counterPoint=0;
        double area=(float)threshold/4;
        area=(int)Math.ceil(area);
        for (i = 0; i < sizerow; i++) {
            for (y = 0; y < sizecol; y++) {
                if (photochar[i][y] == c && visited[i][y] == false) {
                    visited[i][y] = true;
                    setVisited(visited);
                    counter++;
                    xmin = y;
                    xmax = y;
                    ymin = i;
                    ymax = i;

                    counter = dfs(photochar, visited, i, y, c);
                    if (counter >= area) {
                        position[counterPoint] = center();
                       counterPoint++;
                    } else {
                        xmin = 0;
                        xmax = 50;
                        ymin = 0;
                        ymax = 50;
                        counter = 0;
                    }

                }
            }
        }
        return counterPoint;
        
    }
    public int dfs(char photochar[][], Boolean[][] visited, int row, int col, char c) {
        int sizecol = photochar[0].length;
        int sizerow = photochar.length;
        if (col + 1 < sizecol) {
            if (!visited[row][col + 1] && photochar[row][col + 1] == c) {
                counter++;
                if (col + 1 > xmax) {
                    xmax = col + 1;
                }
                visited[row][col + 1] = true;
                setVisited(visited);
                counter = dfs(photochar, visited, row, col + 1, c);
            }
        }
        if (row + 1 < sizerow) {
            if (!visited[row + 1][col] && photochar[row + 1][col] == c) {
                counter++;
                if (row + 1 > ymax) {
                    ymax = row + 1;
                }
                visited[row + 1][col] = true;
                setVisited(visited);
                counter = dfs(photochar, visited, row + 1, col, c);
            }
        }
        if (col - 1 >= 0) {
            if (!visited[row][col - 1] && photochar[row][col - 1] == c) {
                counter++;
                if (col - 1 < xmin) {
                    xmin = col - 1;
                }
                visited[row][col - 1] = true;
                setVisited(visited);
                counter = dfs(photochar, visited, row, col - 1, c);
            }
        }
        if (row - 1 >= 0) {
            if (!visited[row - 1][col] && photochar[row - 1][col] == c) {
                counter++;
                if (row - 1 < ymin) {
                    ymin = row - 1;
                }
                visited[row - 1][col] = true;
                setVisited(visited);
                counter = dfs(photochar, visited, row - 1, col, c);
            }
        }

        return counter;
    }
    public void sort(Point[] position) {
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position.length; j++) {
                if (position[i].x < position[j].x) {
                    Point temp = position[i];
                    position[i] = position[j];
                    position[j] = temp;
                }
                if (position[i].x == position[j].x) {
                    if (position[i].y < position[j].y) {
                        Point temp = position[i];
                        position[i] = position[j];
                        position[j] = temp;
                    }
                }
            }
        }
    }
     
    @Override
    public java.awt.Point[] findPlayers(String[] photo, int team, int threshold) {
        if(photo.length==0){
            return null;
        }
        char[][] photochar = new char[photo.length][photo[0].length()];
        int sizerow = photo.length;
        int sizecol = photo[0].length();
        int i, y;
        photochar = convertToChar(photo);
        Boolean[][] visited = new Boolean[sizerow][sizecol];
        for (i = 0; i < sizerow; i++) {
            for (y = 0; y < sizecol; y++) {
                visited[i][y] = false;
            }
        }
        setVisited(visited);
        char c;
        c = (char) ((char) team + '0');
        int count = search(photochar, visited, c, threshold);
        Point[] holdposition = new Point[count];
        for (i = 0; i < count; i++) {
            holdposition[i] = this.position[i];
        }
        sort(holdposition);
        return holdposition;

    }
}
