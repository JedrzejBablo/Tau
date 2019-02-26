package pl.edu.pjatk.tau;

public class Add {
    public Double add(Double a, Double b) {
        return a+b;
    }
    public double loopSum(){
        double sum = 0;
        for(int i=0; i<10; i++){
            sum +=0.1;
        }
        return sum;
    }
}