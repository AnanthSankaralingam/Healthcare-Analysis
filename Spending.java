public class Spending
{
	private double db_ambulatory_spending, db_total_spending, ht_ambulatory_spending, ht_total_spending;
	public Spending(double db_ambulatory_spending,double db_total_spending,double ht_ambulatory_spending,double ht_total_spending)
	{
		this.db_ambulatory_spending = db_ambulatory_spending;
		this.db_total_spending = db_total_spending;
		this.ht_ambulatory_spending = ht_ambulatory_spending;
		this.ht_total_spending = ht_total_spending;
	}
	public double getDb_a(){ return db_ambulatory_spending; }
	public double getDb_total(){ return db_total_spending; }
	public double getHt_a(){ return ht_ambulatory_spending; }
	public double getHt_total(){ return ht_total_spending; }

	public String toString()
	{
		return "Diabetes ambulatory spending = $"+db_ambulatory_spending+" per capita \nDiabetes total spending = $"+db_total_spending+" per capita  \nHypertension ambulatory spending = $"+ht_ambulatory_spending+ " per capita  \nHypertension total spending = $"+ ht_total_spending+" per capita ";
	}

}