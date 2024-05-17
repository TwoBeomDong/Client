package insuranceProduct;

import java.io.Serializable;

public enum TermPeriod implements Serializable {
	month_6("6개월"),
	month_1("1개월")
	;
	private final String name;
	private TermPeriod (String name){
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}