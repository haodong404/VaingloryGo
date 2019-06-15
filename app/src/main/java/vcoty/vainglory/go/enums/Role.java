package vcoty.vainglory.go.enums;

public enum Role {
	
	CAPTAIN("Captain"),
	
	CARRY("Carry"),
	
	JUNGLER("Jungler");
	
	private String roleName;
	
	public Role(String roleName){
		this.roleName = roleName;
	}
	
	public static Role parse(String string){
		for(Role role : values()){
			if(role.getRoleName().equals(string)){
				return role;
			}
		}
		return CAPTAIN;
	}
	
	public String getRoleName(){
		return roleName;
	}

	@Override
	public String toString(){
		return "name;" + name() + "value:" + getRoleName();
	}
}
