import java.util.ArrayList;
import java.util.List;

public class RecipesFinder {
	public static SaphireRandom WorldRandom = new SaphireRandom();
	public static double WORLD_SEED;

	public static String[] LiquidMaterials = { 
			"water", 
			"water_ice", 
			"water_swamp", 
			"oil", 
			"alcohol", 
			"swamp", 
			"mud",
			"blood", 
			"blood_fungi", 
			"blood_worm", 
			"radioactive_liquid", 
			"cement", 
			"acid", 
			"lava", 
			"urine", 
			"poison",
			"magic_liquid_teleportation", 
			"magic_liquid_polymorph", 
			"magic_liquid_random_polymorph",
			"magic_liquid_berserk", 
			"magic_liquid_charm", 
			"magic_liquid_invisibility" };

	public static String[] AlchemyMaterials = { 
			"sand", 
			"bone", 
			"soil", 
			"honey", 
			"slime", 
			"snow", 
			"rotten_meat", 
			"wax",
			"gold", 
			"silver", 
			"copper", 
			"brass", 
			"diamond", 
			"coal", 
			"gunpowder", 
			"gunpowder_explosive", 
			"grass",
			"fungi" };

	public static Recipe[] GetRecipes(double seed, boolean full) {
		WORLD_SEED = seed;
		WorldRandom.Seed = ((double) WORLD_SEED) * 0.17127000 + 1323.59030000;
		WorldRandom.Next();

		WorldRandom.Next();
		WorldRandom.Next();
		WorldRandom.Next();
		WorldRandom.Next();
		WorldRandom.Next();

		Recipe[] recipes = new Recipe[2];
		recipes[0] = GetRandomRecipe("Lively Concoction", full);
		recipes[1] = GetRandomRecipe("Alchemic Precursor", full);
		return recipes;
	}
	
	public static Recipe[] GetRecipes(double seed) {
		boolean full = false;
		WORLD_SEED = seed;
		WorldRandom.Seed = ((double) WORLD_SEED) * 0.17127000 + 1323.59030000;
		WorldRandom.Next();

		WorldRandom.Next();
		WorldRandom.Next();
		WorldRandom.Next();
		WorldRandom.Next();
		WorldRandom.Next();

		Recipe[] recipes = new Recipe[2];
		recipes[0] = GetRandomRecipe("Lively Concoction", full);
		recipes[1] = GetRandomRecipe("Alchemic Precursor", full);
		return recipes;
	}

	public static void ChooseRandomMaterials(List<String> target, String[] material_list, int iters) {
		for (int i = 0; i < iters; i++) {
			String pick = material_list[(int) (WorldRandom.Next() * material_list.length)];
			if (target.contains(pick)) {
				i -= 1;
				continue;
			}
			target.add(pick);
		}
	}

	public static Recipe GetRandomRecipe(String name, boolean full) {
			List<String> mats = new ArrayList<String>();

			ChooseRandomMaterials(mats, LiquidMaterials, 3);
			ChooseRandomMaterials(mats, AlchemyMaterials, 1);

            double probability = WorldRandom.Next();
			WorldRandom.Next();

            probability = (10 - (int)(probability * -91.0f));

            Shuffle(mats);
            if (!full && mats.size() == 4) mats.remove(mats.get(mats.size() - 1));

			if (full && mats.size() == 4) return new Recipe(name, probability, mats.get(0), mats.get(1), mats.get(2), mats.get(3));
			else return new Recipe(name, probability, mats.get(0), mats.get(1), mats.get(2));
		}

	public static void Shuffle(List<String> ary) {
		SaphireRandom prng = new SaphireRandom();
		prng.Seed = ((int) WORLD_SEED >> 1) + 0x30f6;
		prng.Next();

		for (int i = ary.size() - 1; i >= 0; i--) {
			int swap_idx = (int) (prng.Next() * (i + 1));
			String elem = ary.get(i);
			
			ary.set(i, ary.get(swap_idx));
			ary.set(swap_idx, elem);
		}
	}
	
	/** return recipes in current seed */
	public static void resultFor(double seed) {
		Recipe[] results = RecipesFinder.GetRecipes(seed);
		
		for(int i = 0; i < results.length; i++) {
			System.out.println(results[i].toString());
		}
	}
	
	/** searching for world seed where exist specific recipe with set materials and probability */
	public static void findSeedBetween(double start, double stop, String mat1, String mat2, String mat3, double prob) {
		double seed;
		int i;
		Recipe[] results;
		
		for(seed = start; seed <= stop; seed++) {
			results = RecipesFinder.GetRecipes(seed);
			
			for(i = 0; i < results.length; i++) {
				if(results[i].getProb() >= prob) {
					if(results[i].findMat(mat1)) {
						if(results[i].findMat(mat2)) {
							if(results[i].findMat(mat3)) {
								System.out.println("WORLD SEED: " + (long)seed);
								System.out.println(results[i].toString());
								System.out.println();					
							}
						}			
					}	
				}		
			}
		}
	}
	
	/** searching for world seed where exist specific recipe with set materials and probability 
	 * this function assume array with materials, so you set more materials than 3
	 * also this function have parameters for finding specific recipe*/
	public static void findSeedBetween(double start, double stop, String[] mat, boolean findLivelyConcoction, boolean findAlchemicPrecursor, boolean mustByBothMaterials, double prob) {
		double seed;
		int i, j;
		Recipe[] results;
		int findMaterals;
		Recipe findLC;
		Recipe findAP;
		
		for(seed = start; seed <= stop; seed++) {
			results = RecipesFinder.GetRecipes(seed);
			findLC = null;
			findAP = null;
			
			for(i = 0; i < results.length; i++) {
				if(results[i].getProb() >= prob) {
					findMaterals = 0;
					
					for(j = 0; j < mat.length; j++) {
						if(results[i].findMat(mat[j])) {
							findMaterals = findMaterals + 1;
						}	
					}
					
					if(findMaterals == 3) {
						String name = results[i].getName();
						if(name.equals("Lively Concoction") && findLivelyConcoction) {
							findLC = results[i];
						}
						
						if(name.equals("Alchemic Precursor") && findAlchemicPrecursor) {
							findAP = results[i];
						}	
					}	
				}	
				
				if((findLC != null) || (findAP != null)) {

					if((findLC != null) && (findAP != null)) {
						System.out.println("WORLD SEED: " + (long)seed);
						System.out.println(findLC.toString());
						System.out.println(findAP.toString());
						System.out.println();	
					}
					
					else if(!mustByBothMaterials) {
						System.out.println("WORLD SEED: " + (long)seed);
						if(findLC != null) {
							System.out.println(findLC.toString());
						}
						else if(findAP != null) {
							System.out.println(findAP.toString());
						}
						System.out.println();	
					}
				}
					
			}	
		}
	}

	
	public static void main(String[] args) {
		System.out.println("Start");
		//Max world seed value 4294967295.0
				
		String[] mat = {
				"water",
				"oil",
				"blood",
				"magic_liquid_teleportation",
				"magic_liquid_polymorph",
				"magic_liquid_random_polymorph",
				"magic_liquid_berserk",
				"magic_liquid_charm",
				"magic_liquid_invisibility",
				"coal",
				"soil",
				"slime",
				"snow",
				};
		
		findSeedBetween(0, 4294967295.0, mat, true, true, true, 100);
			
		System.out.println("End");
	}
}
