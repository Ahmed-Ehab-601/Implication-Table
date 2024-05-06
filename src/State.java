public class State {
    int index;
    int noNextStates;
    String name;
    String [] nextStates;
    String output;

    public State(int index,int noNextStates ,String name,String[] nextStates,String output){
        this.index = index;
        this.name = name;
        this.noNextStates = noNextStates;
        this.output = output;
        this.nextStates = nextStates;
    }
}
