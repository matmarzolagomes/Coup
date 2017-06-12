package ssc0103.coup.tests;

public class Teste implements Cloneable {
    private int x;
    private int y;

    public Teste() {
	// TODO Auto-generated constructor stub
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    protected Object clone() {	
	try {
	    return super.clone();
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	}
	return null;
    }
    
    public static void main(String[] args) {
	Teste t = new Teste();
	t.setX(10);
	t.setY(10);
	
	Teste backup = (Teste) t.clone();
	
	t.setX(15);
	t.setY(15);
	
	t = backup;
	
	System.out.println(t.getX());
	System.out.println(t.getY());
    }

}
