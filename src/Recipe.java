
public class Recipe {
	private String name;
	private double prob;
	private String mat1;
	private String mat2;
	private String mat3;
	private String mat4;
	
	
	public Recipe(String name, double prob, String mat1, String mat2, String mat3, String mat4) {
		super();
		this.name = name;
		this.prob = prob;
		this.mat1 = mat1;
		this.mat2 = mat2;
		this.mat3 = mat3;
		this.mat4 = mat4;
	}
	
	public Recipe(String name, double prob, String mat1, String mat2, String mat3) {
		super();
		this.name = name;
		this.prob = prob;
		this.mat1 = mat1;
		this.mat2 = mat2;
		this.mat3 = mat3;
		this.mat4 = "";
	}

	@Override
	public String toString() {
		if(mat4.equals("")) return name + " " + prob + "%   " + mat1 + "  -  " + mat2 + "  -  " + mat3;
		else return name + " " + prob + "%   " + mat1 + "  -  " + mat2 + "  -  " + mat3 + "  -  " + mat4;
	}
	
	public boolean findMat(String mat) {
		if(this.mat1.equals(mat) || this.mat2.equals(mat) || this.mat3.equals(mat) ||this.mat4.equals(mat)) return true;
		else return false;
	}
	
	public double getProb() {
		return this.prob;
	}
	
	public String getName() {
		return this.name;
	}
	

}
