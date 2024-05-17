package insuranceProduct;

import java.io.Serializable;

public enum InsuranceType implements Serializable {
	life("생명보험"),
	thirdParty("제3보험"),
	indemnity("손해보험")
	;
	private final String name;
	private InsuranceType (String name){
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}