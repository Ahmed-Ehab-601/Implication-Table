import java.awt.*;
public class AppTest {
    public static void main(String[] args) {
        ImplicationTable t = new ImplicationTable();
        t.Reduce();

        System.out.println("generated pairs");

        for(Point p : t.pairs){
            System.out.println("["+t.states[p.y].name+", "+t.states[p.x].name+"]");

        }
        System.out.println(" Generated Groups");
        for(Group g : t.groups){
            System.out.print("[");
            for(int i = 0; i<g.size();i++){
                int n = g.get(i);
                if(i==g.size()-1){
                    System.out.print(t.states[n].name);
                }
                else{
                    System.out.print(t.states[n].name+", ");
                }
                
            }
            System.out.println("]");

        }


        /*ImplicationTable t = new ImplicationTable();
        t.noStates = 12;
        t.pairs.add(new Point(2, 1));
        t.pairs.add(new Point(1, 0));
        t.pairs.add(new Point(3, 2));
        t.pairs.add(new Point(3, 1));
        t.pairs.add(new Point(3, 0));
        t.pairs.add(new Point(5, 4));
        t.pairs.add(new Point(6, 5));
        t.pairs.add(new Point(7, 5));
        t.pairs.add(new Point(9, 7));
        t.pairs.add(new Point(10, 9));
        t.pairs.add(new Point(10, 5));
        t.pairs.add(new Point(10, 4));
        t.pairs.add(new Point(11, 5));
        t.pairs.add(new Point(11, 10));
        t.pairs.add(new Point(11, 4));

        t.groupFiller();
        t.groupMinimize();

        for(Group g : t.groups){
            System.out.print("[");
            for(int i = 0; i<g.size();i++){
                int n = g.get(i);
                if(i==g.size()-1){
                    System.out.print(n);
                }
                else{
                    System.out.print(n+", ");
                }
                
            }
            System.out.println("]");

        }*/
        




       
    }
}
