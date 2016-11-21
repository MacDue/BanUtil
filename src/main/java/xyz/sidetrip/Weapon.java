package xyz.sidetrip;

public class Weapon {

	private String name;
	private String hitText;
	private String imageURL;
	private char icon;
	private long price;
	private long damage;
	private float accuracy;
	private boolean melee;

	// weapon ID?
	public Weapon(String name, String imageURL, String hitText, char icon, long damage, float accuracy, boolean melee) {
		this.name = name;
		this.imageURL = imageURL;
		this.hitText = hitText;
		this.icon = icon;
		this.damage = damage;
		this.accuracy = accuracy;
		this.melee = melee;
		this.price = calculatePrice();
	}

	public String toString() {
		return icon + " - " + name + " | DMG: " + damage + " | ACCY: " + UtilDue.formateFloat(accuracy * 100)
				+ "% | Type: " + (melee ? "Melee" : "Ranged") + " | $" + UtilDue.formatNumber(price);
	}

	private long calculatePrice() {
		return (long) ((damage * accuracy) / 0.04375f);
	}

	public long getWeaponPrice() {
		return price;
	}
}
