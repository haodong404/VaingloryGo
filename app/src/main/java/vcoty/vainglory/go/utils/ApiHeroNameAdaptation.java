package vcoty.vainglory.go.utils;

public class ApiHeroNameAdaptation {
	
	
	public static String adapt(String name){
		String corrected = name;
		switch(name){
			case "San Feng":
				corrected = "Sanfeng";
				break;
		}
		return corrected;
	}
}
