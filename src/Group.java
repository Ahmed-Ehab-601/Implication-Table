import java.util.ArrayList;
import java.awt.*;


public class Group {
    ArrayList<Integer> group = new ArrayList<>();
    public Group(int initalState){
        group.add(initalState);
    }
    public Group(){
    
    }
    public int size (){
        return group.size();
    }
    public boolean contain (Group b){
        if(group.containsAll(b.group)){
            return true;
        }
        return false;
    }
    public int get(int index){
        
        return group.get(index);
    }

    public boolean contain (int b){
        if(group.contains(b)){
            return true;
        }
        return false;
    }
    public static boolean containsPoint(ArrayList<Point> pointList, Point point) {
        for (Point p : pointList) {
            if (p.equals(point)) {
                return true;
            }
        }
        return false;
    }
    public boolean validAdd(int newState,ArrayList<Point> pair){
        if(group.contains(newState)){
            return false;
        }
        for(int state : group){
            Point p;
            if(newState < state){
                p = new Point(state,newState);
            }
            else{
                p = new Point(newState, state);
            }
            boolean pairFound = containsPoint(pair, p);
            if(!pairFound){
                return false;
            }
        }
        group.add(newState);
        return true;
    }

}
