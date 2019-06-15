package vcoty.vainglory.go.utils;

import java.util.Map;
import java.util.HashMap;

public class ActorHelper {
	
	private static Map<String, String> cleanActorNames = new HashMap<>();
    static {
        cleanActorNames.put("Sayoc", "Taka");
        cleanActorNames.put("Hero009", "Krul");
        cleanActorNames.put("Hero010", "Skaarf");
        cleanActorNames.put("Hero016", "Rona");
    }
	
	public static String cleanActorName(final String dirtyActor) {
        String cleanActor = dirtyActor.replace("*", "");
        if (cleanActorNames.containsKey(cleanActor)) {
            return cleanActorNames.get(cleanActor);
        }
        return cleanActor;
    }
}
