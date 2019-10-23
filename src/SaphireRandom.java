public class SaphireRandom {
		public double Seed;
		
		public double Next(){
			Seed = ((int)Seed) * 16807 + ((int)Seed) / 127773 * -0x7fffffff;
			if (Seed < 0) Seed += Integer.MAX_VALUE;
			return Seed / Integer.MAX_VALUE;
		}	
	}