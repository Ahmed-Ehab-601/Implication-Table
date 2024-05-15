import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.awt.Point;
// to run the main is in  app.java

public class ImplicationTable{
    int noStates;
    int noNextStates;
    int noOutput;
    boolean isMoore;
    State [] states;
    Conditions[][] conditions;
    ArrayList<Point> pairs = new ArrayList<>();
    ArrayList<Group> groups = new ArrayList<>();
    ArrayList <State> Final;


    public void scanner(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter The Number Of States : ");
        noStates = Integer.parseInt(in.nextLine());
        states = new State[noStates];
        conditions = new Conditions[noStates][noStates];
        System.out.print("Enter The Number Of Inputs : ");
        noNextStates = (int) Math.pow(2,Integer.parseInt(in.nextLine()));
        System.out.print("Enter The Number Of Outputs : ");
        noOutput = Integer.parseInt(in.nextLine());
        System.out.print("Enter mealy or moore : ");
        String type = in.nextLine();
        type = type.toLowerCase();
        if(type.equals("moore")){
            isMoore = true;
            noOutput = 1;
            
        }
        else{
            isMoore = false;
            noOutput = noNextStates;
        }
        if(!type.equals("mealy") && !type.equals("moore")){
            in.close();
            throw new IllegalArgumentException();
        }
        System.out.println("Enter The States And Next States space Separeted As follow");
        System.out.println("A B C D F 0 1 1 1");
        System.out.println("A is state then the 4 next states then 4 mealy output");
        for(int i = 0; i < noStates;i++){
            String line = in.nextLine();
            String [] input = line.split(" ");
            // after test check for types and value and throw exeptions
            String [] nextStates = new String[noNextStates];
            int j,l;
            for(j = 0,l = 1; l <= noNextStates;l++,j++){
                nextStates[j] = input[l];
            }
            StringBuilder outputs = new StringBuilder();
            int size = input.length;
            for(j = noNextStates+1;j < size;j++){
                outputs.append(input[j]);
            }
            State s = new State(i, noNextStates,input[0], nextStates, outputs.toString());
            states [i] = s;
          
        }

        in.close();
        
    }
    public void conditionsFiller(){
        int i,j;
        for(i = 1; i < noStates;i++){
            for(j = 0;j < i;j++){
                State one = states[j];
                State two = states[i];
                if(one.output.equals(two.output)){
                    Conditions c = new Conditions(0);
                    conditions[i][j] = c;
                    for(int k = 0;k<noNextStates;k++){
                        if(!one.nextStates[k].equals(two.nextStates[k])){
                            String cond = one.nextStates[k]+two.nextStates[k];
                            c.conditions.add(cond);
                            c.n++;
                            conditions[i][j] = c;

                        }
                    }

                }
                else{
                    Conditions c = new Conditions(-1);
                    conditions[i][j] = c;

                }
            }
        }
        boolean isEdited = true;
        while(isEdited){
            isEdited = false;
            for(i = 1; i < noStates;i++){
                for(j = 0;j < i;j++){
                    Conditions c = conditions[i][j];
                    if(c.n > 0){
                        for(int k = 0;k < c.n;k++){
                            String cond = c.conditions.get(k);
                            int r = nameToIndex(cond.substring(0,1));
                            int p = nameToIndex(cond.substring(1, 2));
                            if(r < p){
                                if(conditions[p][r].n == -1){
                                    conditions[i][j].n = -1;
                                    conditions[i][j].conditions.clear();
                                    isEdited = true;
                                    break;
                                }
                            }
                            else{
                                if(conditions[r][p].n == -1){
                                    conditions[i][j].n = -1;
                                    conditions[i][j].conditions.clear();
                                    isEdited = true;
                                    break;
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    
    public int nameToIndex (String s){
        for(int i = 0; i < noStates;i++){
            if(states[i].name.equals(s)){
                return i;
            }
        }
        return -1;
        // after test check for it and throw exp
    
    }
    public void pair(){
        for(int i = 1;i<noStates;i++){
            for(int j = 0;j<i;j++){
                if(conditions[i][j].n >= 0){
                    Point n = new Point(i, j);
                    pairs.add(n);
                }
            }
        }

    }
    public void groupFiller(){
        for(int i = 0;i < noStates;i++){
            Group g = new Group(i);
            groups.add(g);
            ArrayList<Group> t = new ArrayList<>();
            for(int k = 0; k < groups.size();k++){
                Group A = groups.get(k);
                Group B = new Group();
                B.group.addAll(A.group);
                if(A.validAdd(i, pairs)){
                   t.add(B);
                }   
            }
            groups.addAll(t);
        }

    }
    public void groupMinimize(){ // remove the groups which found in larger one
        for(int i = 0;i < groups.size();i++){
            for(int j = 0; j < groups.size();j++){
                Group g = groups.get(i);
                Group p = groups.get(j);
                if(g!=p && g.contain(p)){
                    groups.remove(p);
                    j--; 
                }
            }
        }
        Comparator<Group> comparator = Comparator.comparingInt(Group::size);
        for(int i = 0;i < groups.size();i++){
            comparator = Comparator.comparingInt(Group::size);
            Collections.sort(groups,comparator);
            Collections.reverse(groups);
            for(int j = i+1; j < groups.size();j++){
                Group A = groups.get(i);
                Group B = groups.get(j);
                for(int k = 0; k < A.size();k++){
                    int c = A.get(k);
                    if(B.contain(c)){
                       B.group.remove(Integer.valueOf(c));
                    }
                }
            }
        }
        
    }
    public void stateRuduction(){
        Final = new ArrayList<>(Arrays.asList(states));
        for(Group g : groups){
            if(g.size()==0){
                continue;
            }
            int replaceBy = g.get(0);
            for(int i = 1; i< g.size();i++){
                int replace = g.get(i);
                for(State s : states){
                    replaceState(s, replace, replaceBy);
                }

            }
        }
        for(Group g : groups){
            if(g.size()==0){
                continue;
            }
            for(int i = 1; i< g.size();i++){
                int replace = g.get(i);
                Final.remove(states[replace]);

            }
        }


    }
    public void replaceState(State s,int replace, int replaceBy){
        String nameToReplace = states[replace].name;
        String nameToReplaceBy = states[replaceBy].name;
        for(int i = 0;i <noNextStates;i++){
            if(s.nextStates[i].equals(nameToReplace)){
                s.nextStates[i] = nameToReplaceBy;
            }
        }
    }
    public void print(){
        System.out.println();
        for(State s : Final){
            System.out.print(s.name+" | ");
            for(String name : s.nextStates){
                System.out.print(name+" ");
            }
            System.out.print("| ");
            for(int i = 0;i<s.output.length();i++){
                if(i == s.output.length()-1){
                    System.out.println(s.output.charAt(i)+" ");
                }
                else{
                    System.out.print(s.output.charAt(i)+" ");
                }
                
            }
            
            
        }
        System.out.println();

    }
    public void Reduce(){
        scanner();
        conditionsFiller();
        pair();
        groupFiller();
        groupMinimize();
        stateRuduction();
        print();

    }   
}