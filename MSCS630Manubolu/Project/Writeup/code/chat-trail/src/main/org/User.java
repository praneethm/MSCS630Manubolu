package main.org;


public class User {

	private boolean logedin;
	private String user;
	private String PublicRSA;
	private String mod;
	private String exponent;

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Boolean getLogedin() {
		return logedin;
	}

	public void setLogedin(Boolean logedin) {
		this.logedin = logedin;
	}

	public String getPublicRSA() {
		 return PublicRSA;
	}

	public void setPublicRSA(String mod, String exponent) {
		System.out.println("saved values in database");
		System.out.println(exponent);
		System.out.println(mod);

		setMod(mod);
		setExponent(exponent);
		

	}

}
