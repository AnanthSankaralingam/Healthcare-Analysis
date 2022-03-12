import java.util.*;
import java.io.*;
import java.awt.*;

//import com.github.sh0nk.matplotlib4j.builder.*;

public class healthcareDisparities
{
	private Scanner data, input, spending_data;
	private Map<String, Demographics> diseaseMap;
	private Map<String, Spending> spendingMap;

	public static void main(String[] args)
	{
		healthcareDisparities app = new healthcareDisparities("medicare_2009.csv","2009_spending_by_race.txt");
	}

	public healthcareDisparities(String demographicsFile, String spendingFile) //main method
	{
		System.out.println("Thesis: African Americans represent a minority population more economically disadvantaged than others, increasing their likelihood of receiving disproportionately worse \nhealthcare services. Being the historically underserved population in the U.S., African Americans bear the brunt of racial disparities in healthcare quality, especially in \ncases of diabetes and hypertension.\n");


		diseaseMap = new HashMap<>();
		spendingMap = new HashMap<>();
		loadData(demographicsFile, spendingFile);
		input = new Scanner(System.in);

		//analysis
		System.out.println("African American statistics:\n"+diseaseMap.get("Black")+"\n"+spendingMap.get("Black")+"\n");
		System.out.println("\nWhite:\n"+diseaseMap.get("White")+"\n"+spendingMap.get("White"));
		System.out.println("\nAsian:\n"+diseaseMap.get("Asian")+"\n"+spendingMap.get("Asian"));
		System.out.println("\nHispanic:\n"+diseaseMap.get("Hispanic")+"\n"+spendingMap.get("Hispanic")+"\n");

		// analysis 1
		System.out.println(getMinMax_diabetes()+"\n"+getMinMax_dbTotal());

		System.out.println("\nWhile the percentage of white Americans who suffer from diabetes is the lowest, government total spending for them is the greatest.");
		System.out.println("While the percentage of African Americans who suffer from diabetes is the highest, government total spending for them is NOT the greatest.");

		System.out.println("\nThe ratio of % of population to spending per capita for African Americans who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("Black").getDiabetes()/spendingMap.get("Black").getDb_total() ) );
		System.out.println("The ratio of % of population to spending per capita for White Americans who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("White").getDiabetes()/spendingMap.get("White").getDb_total() ) );
		System.out.println("The ratio of % of population to spending per capita for Hispanic Americans who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("Hispanic").getDiabetes()/spendingMap.get("Hispanic").getDb_total() ) );
		System.out.println("The ratio of % of population to spending per capita for Asian Americans who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("Asian").getDiabetes()/spendingMap.get("Asian").getDb_total() ) );
		System.out.println("\nHispanics have the lowest ratio of % of population to total spending per capita for diabetes.");

		System.out.println("\n"+getMinMax_hypertension()+"\n"+getMinMax_htTotal() );
		System.out.println("\nWhile the percentage of white Americans who suffer from hypertension is the lowest, government total spending for them is the greatest.");
		System.out.println("While the percentage of African Americans who suffer from hypertension is the highest, government total spending for them is NOT the greatest.");

		System.out.println("\nThe ratio of % of population to spending per capita for African Americans who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("Black").getHypertension()/spendingMap.get("Black").getHt_total() ) );
		System.out.println("The ratio of to % of population to spending per capita for White Americans who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("White").getHypertension()/spendingMap.get("White").getHt_total() ) );
		System.out.println("The ratio of to % of population to spending per capita for Hispanics who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("Hispanic").getHypertension()/spendingMap.get("Hispanic").getHt_total() ) );
		System.out.println("The ratio of to % of population to spending per capita for Asians who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("Asian").getHypertension()/spendingMap.get("Asian").getHt_total() ) );
		System.out.println("\nAfrican Americans have the lowest ratio of % of population to total spending per capita for hypertension.");

		// analysis 2
		System.out.println(getMinMax_proximity1()+"\n"+getMinMax_proximity2()+"\n\n"+getMinMax_numPhysicians()+"\n"+getMinMax_numPrimaryCare());
		System.out.println("\nHispanics have the least proximity to a hospital in the sample, while also having a lesser number of physicians and primary care physicians. Contrastingly, Asians \nhave the greatest proximity to a hospital (<5 mile) as well as a greater access to physicians and primary care physicians. This may be since that Asian immigrants tend to \nrepresent a large portion of wealth in the US, though a minority.\n");

		//analysis 3
		System.out.println(getMinMax_proximity1()+"\n"+getMinMax_proximity2()+"\n\n"+getMinMax_dbAmb()+"\n"+getMinMax_htAmb());
		System.out.println("White Americans have the highest distance from the nearest hospital (>5 mile) and have the highest ambulatory spending for both diabetes and hypertension.");

		System.out.println("\nThe ratio of % of population >5 mile from a hospital to ambulatory spending for African Americans who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("Black").getProximity_hospital2()/spendingMap.get("Black").getDb_a() ) );
		System.out.println("The ratio of to % of population >5 mile from a hospital to ambulatory spending for White Ameticans who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("White").getProximity_hospital2()/spendingMap.get("White").getDb_a() ) );
		System.out.println("The ratio of to % of population >5 mile from a hospital to ambulatory spending for Hispanics who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("Hispanic").getProximity_hospital2()/spendingMap.get("Hispanic").getDb_a() ) );
		System.out.println("The ratio of to % of population  >5 mile from a hospital to ambulatory spending for Asians who suffer from diabetes is "+String.format("%.2f",diseaseMap.get("Asian").getProximity_hospital2()/spendingMap.get("Asian").getDb_a() ) );

		System.out.println("\nThe ratio of % of population >5 mile from a hospital to ambulatory spending for African Americans who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("Black").getProximity_hospital2()/spendingMap.get("Black").getHt_a() ) );
		System.out.println("The ratio of to % of population >5 mile from a hospital to ambulatory spending for White Ameticans who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("White").getProximity_hospital2()/spendingMap.get("White").getHt_a() ) );
		System.out.println("The ratio of to % of population >5 mile from a hospital to ambulatory spending for Hispanics who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("Hispanic").getProximity_hospital2()/spendingMap.get("Hispanic").getHt_a() ) );
		System.out.println("The ratio of to % of population  >5 mile from a hospital to ambulatory spending for Asians who suffer from hypertension is "+String.format("%.2f",diseaseMap.get("Asian").getProximity_hospital2()/spendingMap.get("Asian").getHt_a() ) );
		System.out.println("\nAfrican Americans seemingly have a disproportionate ratio of distance(>5) from a hospital to diabetes and hypertension ambulatory spending, though this may be explained by a large percentage of them being <5 mile to a hospital. However, this also may be explained through cultural differences, as African Americans tend to settle in urban \nareas, where hospitals are more frequent.\n");

		//flaws and conclusion
		System.out.println("A potential flaw I could see is that my data is somewhat outdated, all being isolated from the year 2009. Another potential flaw I can see is that for the proximity to a \nhospital, only 300,000 of each race were in the study. However, the study clearly indicates that they selected a diverse geographic sample of people, including people from \neach region in the US. Also using percentage of population makes sense for analysis in my instance because the dataset surveyed people of equal population sizes, but this \ndoes not hold true for the whole US.");
		System.out.println("\nAfter performing my data analysis, I can conclude with reservations that African Americans have a disproportionate quality of healthcare as compared to other races, \nspecifically in the instances of hypertension and diabetes.\n ");

		//lookup
		boolean exit = true;
		while(exit)
		{
			System.out.println("Do you want to retrieve all data about any race (1), or the lowest/greatest value for any statistic (2)? EXIT(0) ");
			String ans = input.nextLine();
			if(ans.equals("0") )
				exit = false;
			else if(ans.equals("1") ) // tool 1
			{
				Scanner input1 = new Scanner(System.in);
				System.out.print("Input a race: (All races, White, Black, Hispanic, Asian) ");
				String answer = input1.nextLine();
				System.out.println("\n" + diseaseMap.get(answer) +"\n"+spendingMap.get(answer) +"\n"); //tool 1 - getting ALL data
			}
			else if(ans.equals("2") )	// tool 2
			{
				Scanner input2 = new Scanner(System.in);
				System.out.println("Input a demographic statistic OR spending statistic to retrieve the greatest and lowest for the respective statistic \nPossible statistics: % of population with diabetes (1), % of population with hypertension (2), total spending for diabetes (3), total spending for hypertension(4), total \nambulatory spending for diabetes (5), total ambulatory spending for hypertension(6), # of phyisicians(7), # of primary care phyisicians(8), proximity to hospital [<5 miles] (9), proximity to hospital [>5 miles] (10), EXIT(0)");//name statistics and denote by some letter
				String ans2 = input2.nextLine();
				if(ans2.equals("0"))
					exit = false;
				else if(ans2.equals("1"))
					System.out.println(getMinMax_diabetes()+"\n");
				else if(ans2.equals("2"))
					System.out.println(getMinMax_hypertension()+"\n");
				else if(ans2.equals("3"))
					System.out.println(getMinMax_dbTotal()+"\n");
				else if(ans2.equals("4"))
					System.out.println(getMinMax_htTotal()+"\n");
				else if(ans2.equals("5"))
					System.out.println(getMinMax_dbAmb()+"\n");
				else if(ans2.equals("6"))
					System.out.println(getMinMax_htAmb()+"\n");
				else if(ans2.equals("7"))
					System.out.println(getMinMax_numPhysicians()+"\n");
				else if(ans2.equals("8"))
					System.out.println(getMinMax_numPrimaryCare()+"\n");
				else if(ans2.equals("9"))
					System.out.println(getMinMax_proximity1()+"\n");
				else if(ans2.equals("10"))
					System.out.println(getMinMax_proximity1()+"\n");
			}
		}//end while

	}

	public void loadData(String demographicsFile, String spendingFile) //fill maps with data from both sets
	{
		//all_stats = new double[6];//average num physcians, num primary care physicians, <5miles from hospital, >5mile from hospital diabetes, hypertension
		double all_stats[] =new double[6];
		double[] white_stats = new double[6];//not doing all diseases yet
		double[] black_stats = new double[6];
		double[] hispanic_stats = new double[6];
		double[] asian_stats = new double[6]; //arrays for data for demographics object

		double hispanic_spending[] = new double[4];
		double white_spending[] = new double[4];
		double[] black_spending = new double[4];
		double[] asian_spending = new double[4];
		double[] all_spending = new double[4]; //arrays for data for spending object

		try //flip data in csv file to improve efficiency
		{
			data = new Scanner(new File(demographicsFile));
			data.nextLine();
			data.nextLine();
			spending_data = new Scanner(new File(spendingFile));
			spending_data.nextLine();
			while(data.hasNext())
			{
				String[] line = data.nextLine().split(",");
				if(line[1].equals("# of physicians/100k")) //shorten using substring later
				{
					all_stats[0] = Double.parseDouble(line[3]);
					white_stats[0] = Double.parseDouble(line[4]);
					black_stats[0] = Double.parseDouble(line[5]);
					hispanic_stats[0] = Double.parseDouble(line[6]);
					asian_stats[0] = Double.parseDouble(line[7]);
				}
				else if(line[1].equals("# of primary care physician/100k"))
				{
					all_stats[1] = Double.parseDouble(line[3]);
					white_stats[1] = Double.parseDouble(line[4]);
					black_stats[1] = Double.parseDouble(line[5]);
					hispanic_stats[1] = Double.parseDouble(line[6]);
					asian_stats[1] = Double.parseDouble(line[7]);
				}
				else if(line[1].equals("<5 mile from a hospital"))
				{
					all_stats[2] = Double.parseDouble(line[2]);
					white_stats[2] = Double.parseDouble(line[3]);
					black_stats[2] = Double.parseDouble(line[4]);
					hispanic_stats[2] = Double.parseDouble(line[5]);
					asian_stats[2] = Double.parseDouble(line[6]);
				}
				else if(line[1].equals(">5 mile from a hospital"))
				{
					all_stats[3] = Double.parseDouble(line[2]);
					white_stats[3] = Double.parseDouble(line[3]);
					black_stats[3] = Double.parseDouble(line[4]);
					hispanic_stats[3] = Double.parseDouble(line[5]);
					asian_stats[3] = Double.parseDouble(line[6]);
				}
				else if(line[1].equals("Diabetes mellitus"))
				{
 					all_stats[4] = Double.parseDouble(line[2]);
					white_stats[4] = Double.parseDouble(line[3]);
					black_stats[4] = Double.parseDouble(line[4]);
					hispanic_stats[4] = Double.parseDouble(line[5]);
					asian_stats[4] = Double.parseDouble(line[6]);
				}
				else if(line[1].equals("Hypertension"))
				{
 					all_stats[5] = Double.parseDouble(line[2]);
					white_stats[5] = Double.parseDouble(line[3]);
					black_stats[5] = Double.parseDouble(line[4]);
					hispanic_stats[5] = Double.parseDouble(line[5]);
					asian_stats[5] = Double.parseDouble(line[6]);
				}
			}//end while - demographics file
			Demographics dem_all = new Demographics(all_stats[0],all_stats[1],all_stats[2],all_stats[3],all_stats[4],all_stats[5]);
			Demographics dem_white = new Demographics(white_stats[0],white_stats[1],white_stats[2],white_stats[3],white_stats[4],white_stats[5]);
			Demographics dem_black = new Demographics(black_stats[0],black_stats[1],black_stats[2],black_stats[3],black_stats[4],black_stats[5]);
			Demographics dem_hispanic = new Demographics(hispanic_stats[0],hispanic_stats[1],hispanic_stats[2],hispanic_stats[3],hispanic_stats[4],hispanic_stats[5]);
			Demographics dem_asian = new Demographics(asian_stats[0],asian_stats[1],asian_stats[2],asian_stats[3],asian_stats[4],asian_stats[5]);
			diseaseMap.put("All races",dem_all);
			diseaseMap.put("White",dem_white);
			diseaseMap.put("Black",dem_black);
			diseaseMap.put("Hispanic",dem_hispanic);
			diseaseMap.put("Asian",dem_asian);

			boolean female = true;
			//year,age_group_id,age_group_name,sex_id 3,sex,race_ethnicity 5,acause,acause_name,type_care 8,spending_unit,val 10,upper,lower
			while(female)
			{
				String[]line = spending_data.nextLine().split(",");
				if(!line[3].equals("2"))
					female = false;
				if(line[5].substring(0,3).equals("\"As") && asian_spending[3]==0)
				{
					if(line[8].substring(0,1).equals("d") && line[10].substring(0,1).equals("A") ) //db amb
						asian_spending[0] = Double.parseDouble(line[12]);
					else if(line[8].substring(0,1).equals("d") && line[10].substring(0,1).equals("T") ) //db total
						asian_spending[1] = Double.parseDouble(line[12]);
					else if(line[8].substring(0,2).equals("rf") && line[10].substring(0,1).equals("A") ) // rf amb
						asian_spending[2] = Double.parseDouble(line[12]);
					else if(line[8].substring(0,2).equals("rf") && line[10].substring(0,1).equals("T") ) // rf total
						asian_spending[3] = Double.parseDouble(line[12]);
				}
				else if(line[5].substring(0,1).equals("B") && black_spending[3]==0)
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						black_spending[0] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						black_spending[1] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						black_spending[2] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						black_spending[3] = Double.parseDouble(line[10]);
				}
				else if(line[5].substring(0,1).equals("H") && hispanic_spending[3]==0)
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						hispanic_spending[0] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						hispanic_spending[1] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						hispanic_spending[2] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						hispanic_spending[3] = Double.parseDouble(line[10]);
				}
				else if(line[5].substring(0,1).equals("T") && all_spending[3]==0)
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						all_spending[0] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						all_spending[1] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						all_spending[2] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						all_spending[3] = Double.parseDouble(line[10]);
				}
				else if(line[5].substring(0,1).equals("W") )
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						white_spending[0] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						white_spending[1] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						white_spending[2] = Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						white_spending[3] = Double.parseDouble(line[10]);
				}
			}// end female
			while(spending_data.hasNext()) //male
			{
			String[]line = spending_data.nextLine().split(",");
				if(line[5].substring(0,3).equals("\"As") && asian_spending[3]==0)
				{
					if(line[8].substring(0,1).equals("d") && line[10].substring(0,1).equals("A") ) //db amb
						asian_spending[0] += Double.parseDouble(line[12]);
					else if(line[8].substring(0,1).equals("d") && line[10].substring(0,1).equals("T") ) //db total
						asian_spending[1] += Double.parseDouble(line[12]);
					else if(line[8].substring(0,2).equals("rf") && line[10].substring(0,1).equals("A") ) // rf amb
						asian_spending[2] += Double.parseDouble(line[12]);
					else if(line[8].substring(0,2).equals("rf") && line[10].substring(0,1).equals("T") ) // rf total
						asian_spending[3] += Double.parseDouble(line[12]);
				}
				else if(line[5].substring(0,1).equals("B") && black_spending[3]==0)
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						black_spending[0] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						black_spending[1] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						black_spending[2] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						black_spending[3] += Double.parseDouble(line[10]);
				}
				else if(line[5].substring(0,1).equals("H") && hispanic_spending[3]==0)
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						hispanic_spending[0] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						hispanic_spending[1] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						hispanic_spending[2] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						hispanic_spending[3] += Double.parseDouble(line[10]);
				}
				else if(line[5].substring(0,1).equals("T") && all_spending[3]==0)
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						all_spending[0] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						all_spending[1] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						all_spending[2] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						all_spending[3] += Double.parseDouble(line[10]);
				}
				else if(line[5].substring(0,1).equals("W") )
				{
					if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("A") ) //db amb
						white_spending[0] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,1).equals("d") && line[8].substring(0,1).equals("T") ) //db total
						white_spending[1] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("A") ) // rf amb
						white_spending[2] += Double.parseDouble(line[10]);
					else if(line[6].substring(0,2).equals("rf") && line[8].substring(0,1).equals("T") ) // rf total
						white_spending[3] += Double.parseDouble(line[10]);
				}
			} // end while male
			Spending asian_s = new Spending(asian_spending[0],asian_spending[1],asian_spending[2],asian_spending[3]);
			Spending white_s = new Spending(white_spending[0],white_spending[1],white_spending[2],white_spending[3]);
			Spending black_s = new Spending(black_spending[0],black_spending[1],black_spending[2],black_spending[3]);
			Spending all_s = new Spending(all_spending[0],all_spending[1],all_spending[2],all_spending[3]);
			Spending hispanic_s = new Spending(hispanic_spending[0],hispanic_spending[1],hispanic_spending[2],hispanic_spending[3]);
			spendingMap.put("All races", all_s);
			spendingMap.put("White", white_s);
			spendingMap.put("Black", black_s);
			spendingMap.put("Asian", asian_s);
			spendingMap.put("Hispanic", hispanic_s);
			//System.out.println(asian_s);

		}catch(Exception e) {
			e.printStackTrace();
		}




	}
	public String getMinMax_numPhysicians()
	{
		/*int x=10000;
		int y=-1;
		for(Demographics d : diseaseMap.values()
		{
			if(d.getNum_physicians() <x)
				x = d.getNum_physicians;
		}*/
		double x = 100000;
		String low = "";
		double y = -1;
		String high = "";
		for(Map.Entry<String, Demographics> entry : diseaseMap.entrySet() )
		{
			if(entry.getValue().getNum_physicians() <x)
			{
				x = entry.getValue().getNum_physicians();
				low = entry.getKey();
			}
			if(entry.getValue().getNum_physicians() >y)
			{
				y = entry.getValue().getNum_physicians();
				high  = entry.getKey();
			}
		}
		//System.out.println("Low: "+low+"="+x + "\nHigh: "+high+"="+y);
		return "# of phyisicians/100k: Low: "+low+"="+x + "\tHigh: "+high+"="+y;

	}
	public String getMinMax_numPrimaryCare()
	{
		double x = 100000;
		String low = "";
		double y = -1;
		String high = "";
		for(Map.Entry<String, Demographics> entry : diseaseMap.entrySet() )
		{
			if(entry.getValue().getNum_primarycare() <x)
			{
				x = entry.getValue().getNum_primarycare();
				low = entry.getKey();
			}
			if(entry.getValue().getNum_primarycare() >y)
			{
				y = entry.getValue().getNum_primarycare();
				high  = entry.getKey();
			}
		}
		return "# of primary care physicians/100k: Low: "+low+"="+x + "\tHigh: "+high+"="+y;
	}
	public String getMinMax_proximity1()
	{
		double x = 100000;
		String low = "";
		double y = -1;
		String high = "";
		for(Map.Entry<String, Demographics> entry : diseaseMap.entrySet() )
		{
			if(entry.getValue().getProximity_hospital1() <x)
			{
				x = entry.getValue().getProximity_hospital1();
				low = entry.getKey();
			}
			if(entry.getValue().getProximity_hospital1() >y)
			{
				y = entry.getValue().getProximity_hospital1();
				high  = entry.getKey();
			}
		}
		return "<5 mile from a hospital: Low: "+low+"="+x + "%\tHigh: "+high+"="+y+"%";
	}

	public String getMinMax_proximity2()
	{
		double x = 100000;
		String low = "";
		double y = -1;
		String high = "";
		for(Map.Entry<String, Demographics> entry : diseaseMap.entrySet() )
		{
			if(entry.getValue().getProximity_hospital2() <x)
			{
				x = entry.getValue().getProximity_hospital2();
				low = entry.getKey();
			}
			if(entry.getValue().getProximity_hospital2() >y)
			{
				y = entry.getValue().getProximity_hospital2();
				high  = entry.getKey();
			}
		}
		return ">5 mile from a hospital: Low: "+low+"="+x + "%\tHigh: "+high+"="+y+"%";
	}

	public String getMinMax_diabetes()
	{
		double x = 100000;
		String low = "";
		double y = -1;
		String high = "";
		for(Map.Entry<String, Demographics> entry : diseaseMap.entrySet() )
		{
			if(entry.getValue().getDiabetes() <x)
			{
				x = entry.getValue().getDiabetes();
				low = entry.getKey();
			}
			if(entry.getValue().getDiabetes() >y)
			{
				y = entry.getValue().getDiabetes();
				high  = entry.getKey();
			}
		}
		return "Diabetes prevalence: Low: "+low+"="+x + "%\tHigh: "+high+"="+y+"%";
	}

	public String getMinMax_hypertension()
	{
		double x = 100000;
		String low = "";
		double y = -1;
		String high = "";
		for(Map.Entry<String, Demographics> entry : diseaseMap.entrySet() )
		{
			if(entry.getValue().getHypertension() <x)
			{
				x = entry.getValue().getHypertension();
				low = entry.getKey();
			}
			if(entry.getValue().getHypertension() >y)
			{
				y = entry.getValue().getHypertension();
				high  = entry.getKey();
			}
		}
		return "Hypertension prevalence: Low: "+low+"="+x + "%\tHigh: "+high+"="+y+"%";
	}
	public String getMinMax_dbAmb()//spendingMap
	{
		double x = 100000;
		String low = "";
		double y = -1;
		String high = "";
		for(Map.Entry<String, Spending> entry : spendingMap.entrySet() )
		{
			if(entry.getValue().getDb_a() <x)
			{
				x = entry.getValue().getDb_a();
				low = entry.getKey();
			}
			if(entry.getValue().getDb_a() >y)
			{
				y = entry.getValue().getDb_a();
				high  = entry.getKey();
			}
		}
			return "Diabetes Ambulatory Spending: Low per capita: "+low+"= $"+x + "\tHigh: "+high+"= $"+y;
	}
	public String getMinMax_dbTotal()
		{
			double x = 100000;
			String low = "";
			double y = -1;
			String high = "";
			for(Map.Entry<String, Spending> entry : spendingMap.entrySet() )
			{
				if(entry.getValue().getDb_total() <x)
				{
					x = entry.getValue().getDb_total();
					low = entry.getKey();
				}
				if(entry.getValue().getDb_total() >y)
				{
					y = entry.getValue().getDb_total();
					high  = entry.getKey();
				}
			}
			return "Diabetes Total Spending: Low per capita: "+low+"= $"+x + "\tHigh: "+high+"= $"+y;
	}
	public String getMinMax_htAmb()
		{
			double x = 100000;
			String low = "";
			double y = -1;
			String high = "";
			for(Map.Entry<String, Spending> entry : spendingMap.entrySet() )
			{
				if(entry.getValue().getHt_a() <x)
				{
					x = entry.getValue().getHt_a();
					low = entry.getKey();
				}
				if(entry.getValue().getHt_a() >y)
				{
					y = entry.getValue().getHt_a();
					high  = entry.getKey();
				}
			}
			return "Hypertension Ambulatory Spending per capita: Low: "+low+"= $"+x + "\tHigh: "+high+"= $"+y;
	}
	public String getMinMax_htTotal()
		{
			double x = 100000;
			String low = "";
			double y = -1;
			String high = "";
			for(Map.Entry<String, Spending> entry : spendingMap.entrySet() )
			{
				if(entry.getValue().getHt_total() <x)
				{
					x = entry.getValue().getHt_total();
					low = entry.getKey();
				}
				if(entry.getValue().getHt_total() >y)
				{
					y = entry.getValue().getHt_total();
					high  = entry.getKey();
				}
			}
			return "Hypertension Total Spending per capita: Low: "+low+"= $"+x + "\tHigh: "+high+"= $"+y;
	}


}