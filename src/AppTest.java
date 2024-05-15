import java.awt.*;
 /*
     if multible output mealy or moore circuit is entered enter output space separed
     ex: 
        mealy 2 input 2 output 
        out : 1 2 1 2 1 2 1 2
    a a b c d 0 1 1 1 1 0 1 1
         
     
 */
public class AppTest {
    public static void main(String[] args) {
        ImplicationTable t = new ImplicationTable();
        t.Reduce();

        System.out.println("generated pairs");

        for(Point p : t.pairs){
            System.out.println("["+t.states[p.y].name+", "+t.states[p.x].name+"]");

        }
        System.out.println();
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


       
    }
}
