import java.io.*;

public class Driver {
    public static void main(String[] args) throws IOException {
        // Test 1: Test from Lab01
        System.out.println("\nLab 1 Test");
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        
        double[] c1 = {6, 0, 0, 5};
        int[] e1 = {0, 1, 2, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        
        double[] c2 = {0, -2, 0, 0, -9};
        int[] e2 = {0, 1, 2, 3, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        
        // Test 2: Multiplication
        System.out.println("\nMultiplication Test");
        double[] c3 = {1, 2};
        int[] e3 = {0, 1};
        Polynomial p3 = new Polynomial(c3, e3); // 1 + 2x
        
        double[] c4 = {3, 4};
        int[] e4 = {0, 1};
        Polynomial p4 = new Polynomial(c4, e4); // 3 + 4x
        
        Polynomial product = p3.multiply(p4); // (1+2x)(3+4x) = 3 + 10x + 8x^2
        System.out.println("(1+2x) * (3+4x) = 3 + 10x + 8x^2");
        System.out.println("Product at x=2: " + product.evaluate(2)); // 3 + 20 + 32 = 55
        
        // Test 3: File Operations
        System.out.println("\nFile Operations Test");
        
        // Create test file
        PrintWriter writer = new PrintWriter("test_poly.txt");
        writer.println("5-3x2+7x8");
        writer.close();
        
        // Read from file
        File file = new File("test_poly.txt");
        Polynomial filePoly = new Polynomial(file);
        System.out.println("Polynomial from file: evaluates to " + filePoly.evaluate(1) + " at x=1");
        
        // Save to file
        filePoly.saveToFile("saved_poly.txt");
        System.out.println("Polynomial saved to saved_poly.txt");
        
    }
}