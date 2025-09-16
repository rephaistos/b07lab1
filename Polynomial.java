class Polynomial{
    public double[] coefficients;

    public Polynomial(){
        this.coefficients = new double[1];
        this.coefficients[0] = 0.0;
    }

    public Polynomial(double[] coefficients){
        this.coefficients = new double[coefficients.length];
        for(int i=0;i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
    }

    public Polynomial add(Polynomial poly){ 
        if(poly.coefficients.length >= this.coefficients.length){
            Polynomial newpoly = new Polynomial(poly.coefficients);
            // Adding values to new poly
            for(int i = 0; i < this.coefficients.length; i++){
                newpoly.coefficients[i] += this.coefficients[i];
            }
            return newpoly;
        }
        else{
            Polynomial newpoly = new Polynomial(this.coefficients);
            // Adding values to new poly
            for(int i = 0; i < poly.coefficients.length; i++){
                newpoly.coefficients[i] += poly.coefficients[i];
            }
            return newpoly;
        }
    }

    public double evaluate(double num){
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            double tempnum = 1;
            for(int j = 0; j < i; j++){
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

}