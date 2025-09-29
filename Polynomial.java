import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Polynomial{
    public double[] coefficients;
    public int[] exponents;

    public Polynomial(){
        this.coefficients = new double[1];
        this.coefficients[0] = 0.0;

        this.exponents = new int[1];
        this.exponents[0] = 0;    
    }

    public Polynomial(double[] coefficients, int[] exponents){
        int nonZeroCount = 0;
        for(int i =0; i < coefficients.length;i++){
            if(coefficients[i] != 0){
                nonZeroCount++;
            } 
        }
        
        this.coefficients = new double[nonZeroCount];
        this.exponents = new int[nonZeroCount];

        int index = 0;
        for(int i=0;i < coefficients.length; i++){
            if(coefficients[i] == 0) continue;
            this.coefficients[index] = coefficients[i];
            this.exponents[index] = exponents[i];
            index++;
        }

    }

    public Polynomial(File file) throws IOException {
        //Scanner scanner = new Scanner(file);
        
        Scanner scanner = new Scanner(file);
        String polynomial = scanner.nextLine().replaceAll("\\s", "");
        scanner.close();

        List<Double> coefficientList = new ArrayList<>();
        List<Integer> exponentList = new ArrayList<>();

        //String[] terms = polynomial.split("(?=[+-])"); also this one works I guess
        String spacedPoly = polynomial.replace("+", " +").replace("-", " -").trim();
        String[] terms = spacedPoly.split("\\s");

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            if (term.isEmpty() == false) {
                if (term.contains("x") == false) {// its just number
                    coefficientList.add(Double.parseDouble(term));
                    exponentList.add(0);
                }
                else {
                    // x includes "5x2", "+3x5", "-x", "x8"
                    int IndexOfX = term.indexOf('x');

                    //coefficient
                    String coefficientPart = term.substring(0, IndexOfX);
                    double coefficient;
                    if(coefficientPart.isEmpty() == true || coefficientPart.equals("+") == true){ // x5 == (1)x^5 or +x5 == +(1)x^5
                        coefficient = 1.0;
                    }
                    else if(coefficientPart.equals("-") == true){
                        coefficient = -1.0;
                    }
                    else{
                        coefficient = Double.parseDouble(coefficientPart);
                    }

                    // exponent
                    String expPart = term.substring(IndexOfX + 1);
                    int exponent = 0;
                    if (expPart.isEmpty() == true) {// it has a structure of 5x which ic 5x^1
                        exponent = 1;
                    }
                    else {  // it has a structure like 5x2 which is 5x^2
                        exponent = Integer.parseInt(expPart);
                    }

                    coefficientList.add(coefficient);
                    exponentList.add(exponent);
                }
            }
        }
    
        // Convert list to array
        this.coefficients = new double[coefficientList.size()];
        this.exponents = new int[exponentList.size()];
        for(int i = 0; i < coefficientList.size(); i++){
            this.coefficients[i] = coefficientList.get(i);
            this.exponents[i] = exponentList.get(i);
        }
    
    }

    public Polynomial add(Polynomial poly) {
        List<Double> finalCoeficients = new ArrayList<>();
        List<Integer> resultExponents = new ArrayList<>();
        
        for(int i = 0; i <this.coefficients.length; i++){
            finalCoeficients.add(this.coefficients[i]);
            resultExponents.add(this.exponents[i]);
        }
        
        for(int i = 0; i < poly.coefficients.length; i++){
            boolean finded = false;
            for (int j = 0; j < resultExponents.size(); j++){
                if(resultExponents.get(j) == poly.exponents[i]){
                    double tempsum = finalCoeficients.get(j) + poly.coefficients[i];
                    finalCoeficients.set(j, tempsum);
                    finded = true;
                    break;
                }
            }
            if (finded == false) {
                finalCoeficients.add(poly.coefficients[i]);
                resultExponents.add(poly.exponents[i]);
            }
        }
        
        return ListToPolynomial(finalCoeficients, resultExponents);
    }

    public Polynomial multiply(Polynomial other) {
        //List<data type> name = new ArrayList<>();
        List<Double> finalCoefficients = new ArrayList<>();
        List<Integer> finalExponents = new ArrayList<>();
        
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double new_coefficient = this.coefficients[i] * other.coefficients[j];
                int new_exponent = this.exponents[i] + other.exponents[j];
                
                boolean finded = false;
                for (int k = 0; k < finalExponents.size(); k++) {
                    if (finalExponents.get(k) == new_exponent) {
                        finalCoefficients.set(k,finalCoefficients.get(k) +new_coefficient);
                        finded = true;
                        break;
                    }
                }
                
                if(finded == false){
                    finalCoefficients.add(new_coefficient);
                    finalExponents.add(new_exponent);
                }
            }
        }
        
        return ListToPolynomial(finalCoefficients, finalExponents);
    }

    private Polynomial ListToPolynomial(List<Double> coefficients, List<Integer> exponents) {
        //List<data type> name = new ArrayList<>();
        List<Double> finalCoefficients = new ArrayList<>();
        List<Integer> finalExponents = new ArrayList<>();
        
        for(int i = 0; i < coefficients.size(); i++){
            if (coefficients.get(i) != 0.0) {
                finalCoefficients.add(coefficients.get(i));
                finalExponents.add(exponents.get(i));
            }
        }        
        if(finalCoefficients.isEmpty()){
            return new Polynomial();
        }
        
        double[] coefficientArray = new double[finalCoefficients.size()];
        int[] exponentArray = new int[finalExponents.size()];        
        for (int i = 0; i < finalCoefficients.size(); i++) {
            coefficientArray[i] = finalCoefficients.get(i);
            exponentArray[i] = finalExponents.get(i);
        }
        
        Polynomial newpoly = new Polynomial(coefficientArray, exponentArray);
        
        return newpoly;
    }

    public double evaluate(double num){
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            double tempnum = 1;
            for(int j = 0; j < this.exponents[i]; j++){
                tempnum *= num;
            }
            result += coefficients[i] * tempnum;
        }
        return result;
    }

    public boolean hasRoot(double num){
        if(evaluate(num) == 0){
            return true;
        }
        return false;
    }


    public void saveToFile(String fileName) throws IOException {
        //FileWriter output = new FileWriter(“myfile.txt”, false); //use true for appending
        FileWriter writer = new FileWriter(fileName);

        if(this.coefficients.length == 0 || (this.coefficients.length == 1 && coefficients[0] == 0.0)) {
            writer.write("0");
            writer.close();
            return;
        }

        boolean first = true;
        for(int i = 0; i < this.coefficients.length; i++){
            double coefficient = this.coefficients[i];
            int exponent = exponents[i];

            if (coefficient == 0.0) continue;

            // writing + -
            if (first == true) {
                if(coefficient < 0){
                    writer.write("-");
                    first = false;
                }
            }
            else {
                if(coefficient > 0){
                    writer.write("+");
                } else {
                    writer.write("-");
                }
            }

            // writing coefficient
            double neutralCoefficient = Math.abs(coefficient); // get rid of + -
            if(exponent == 0) {
                // just coefficient
                writer.write(String.valueOf(neutralCoefficient));
            }
            else{
                if (neutralCoefficient != 1.0) {
                    writer.write(String.valueOf(neutralCoefficient));
                }

                writer.write("x");

                if (exponent != 1) {
                    writer.write(String.valueOf(exponent));
                }
            }
            first = false;
        }
        writer.close();
        }

    }