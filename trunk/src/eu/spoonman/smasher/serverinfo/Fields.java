package eu.spoonman.smasher.serverinfo;

public enum Fields {
	
	GAME,
	DEFAULTPORT,
	
	QUERY,
	RESPONSE,
	
	SERVERREGEX,
	
	SERVERSECTION ("server"),
	ORDINALKEY,
	ORDINALVALUE,
	MAPFIELD,
	SERVERNAMEFIELD,
	TIMEFIELD,
	MODFIELD,
	VERSIONFIELD,
	MODVERSIONFIELD,
	
	PLAYERSECTION ("player"),
	PLAYERREGEX,
	ORDINALNAME,
	ORDINALSCORE,
	ORDINALPING,
	ORDINALCLAN,
	NAMEFIELD;
	
	private String name;
	
	Fields (String name) {
		this.name = name;
	}
	
	Fields () {
		this.name = super.toString();
	}
	
	@Override
	public String toString() {
		return this.name.toLowerCase();
	}
}
