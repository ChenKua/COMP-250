public class Triangle extends Shape {
 private double base;
 private double height;
 
 public double getArea() {
  return base*height/2;
 }
 
 public void displayInfo() {
  System.out.println("This is a " + this.getColor() + " triangle.");
 }
 
}
