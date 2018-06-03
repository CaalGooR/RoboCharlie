import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args){
		Thread t1;
		try {
			t1 = new Thread (new InterpreteGrafico());
			t1.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
