package com.hbm.inventory.material;

public enum MaterialShapes {
	
	QUANTUM(1), // 1/72 of an ingot, allows the ingot to be divisible through 2, 4, 6, 8, 9, 12, 24 and 36
	NUGGET(8, "nugget"),
	DUSTTINY(NUGGET.quantity, "dustTiny"),
	WIRE(9),
	BILLET(NUGGET.quantity * 6, "billet"),
	INGOT(NUGGET.quantity * 9, "ingot"),
	DUST(INGOT.quantity, "dust"),
	PLATE(INGOT.quantity, "plate"),
	BLOCK(INGOT.quantity * 9, "block");
	
	int quantity;
	String[] prefixes;
	
	private MaterialShapes(int quantity, String... prefixes) {
		this.quantity = quantity;
		this.prefixes = prefixes;
		
		for(String prefix : prefixes) {
			Mats.prefixByName.put(prefix, this);
		}
	}
}