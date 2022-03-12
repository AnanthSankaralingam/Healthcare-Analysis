public class Demographics
{
	private double num_physicians, num_primarycare,  proximity_hospital1, proximity_hospital2, diabetes, hypertension;
	public Demographics(double num_physicians, double num_primarycare,double proximity_hospital1, double proximity_hospital2, double diabetes, double hypertension)
	{
		this.num_physicians = num_physicians;
		this.num_primarycare = num_primarycare;
		this.diabetes = diabetes;
		this.hypertension = hypertension;
		this.proximity_hospital1 = proximity_hospital1;
		this.proximity_hospital2 = proximity_hospital2;
	}
	public double getNum_physicians(){ return num_physicians; }
	public double getNum_primarycare(){ return num_primarycare; }
	public double getProximity_hospital1(){ return proximity_hospital1; }
	public double getProximity_hospital2(){ return proximity_hospital2; }
	public double getDiabetes(){ return diabetes; }
	public double getHypertension(){ return hypertension; }

	public String toString()
	{
		return "# of phyisicians/100k: "+num_physicians+ " \n# of primary care physicians/100k: "+num_primarycare+" \n<5 mile from a hospital(%): "+proximity_hospital1+" \n>5 mile from a hospital(%): "+proximity_hospital2+" \ndiabetes(%): "+diabetes+" \nhypertension(%): " +hypertension+"\n";

	}
}