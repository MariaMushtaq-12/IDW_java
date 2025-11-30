package IDW;

public class Main {

	public static int p=2; //power parameter
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      
		double[][] exampleInput = { 
					{1.0, Double.NaN, 3.0}, 
					{Double.NaN, Double.NaN, 2.0}, 
					{4.0, 5.0, Double.NaN} 
				};
	
//		System.out.println(Double.isNaN(exampleInput[0][1])); 
	
	

		
       double[][] result = idw(exampleInput);
//       System.out.println(result);
       for (int i = 0; i < result.length; i++) {
           for (int j = 0; j < result[0].length; j++) {

               System.out.print(result[i][j] + "  ");
           }
           System.out.println();
           
       }
   
	}
	
	static double  euclideanDistance (double i, double j, double x, double y) {
		
		double dx= x-i;
		double dy = y-j;
		double distance = Math.sqrt(dx*dx + dy*dy);
	
		return distance;
	}
	
	static double  weight (double i, double j, double x, double y) {
		
		double distance = euclideanDistance(i,j,x,y);
	
		double weight= 1/Math.pow(distance,p);
		
		return weight;
	}
	

	static double[][] idw(double[][] data) {

        int rows = data.length;
        int cols = data[0].length;

        // Create a new array for output
        double[][] output = new double[rows][cols];

        // Loop over each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                double currentValue = data[i][j];

                // If the cell is NOT NaN, leave it as it is
                if (!Double.isNaN(currentValue)) {
                    output[i][j] = currentValue;
                    continue;
                } else {

	                //compute IDW interpolation, if it is Nan
	                double numerator = 0.0;
	                double denominator = 0.0;
	
	                // Loop over all other cells to find known values
	                for (int x = 0; x < rows; x++ ) {
	                    for (int y = 0; y < cols; y++) {
	
	                        double knownValue = data[x][y];
	
	                        // Only use non-NaN cells to compute weight for formula of idw, unknown=(weightedSum*known)/totalWeightedSum
	                        if (!Double.isNaN(knownValue)) {
	
	                            double w = weight(i, j, x, y);
	
	                            numerator += w * knownValue;
	                            denominator += w;
	                        }
	                    }
	                }
	
	                // IDW output
	                output[i][j] = numerator / denominator;
                }
            }
        }

        return output;
    }
}


