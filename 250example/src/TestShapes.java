public class TestShapes {
 
 public static void main(String[] args) {
  
  Shape s = new Shape();
  s.setColor("green");
  
  Circle c = new Circle();
  c.setColor("blue");
  
  Triangle t = new Triangle();
  t.setColor("red");

  s.displayInfo();
  c.displayInfo();
  t.displayInfo();
  
  /*
  System.out.println(s.getColor());
  System.out.println(c.getRadius());
  System.out.println(c.getColor());
  */
  
 }
}
