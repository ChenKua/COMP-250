
public class B extends A{

	public int n;
	
	B(){}
	B(int n){
		System.out.println("B");
		this.n=n;
	}
	
	
	int foo(int n) {
		System.out.println(this.n);
		return n;
	}
}
