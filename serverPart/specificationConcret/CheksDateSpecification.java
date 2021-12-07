package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Check;
import main.java.serverPart.Specification.AbstractSpecification;

public class CheksDateSpecification extends AbstractSpecification<Check> {
    String date;
    public CheksDateSpecification(String date) {
        this.date = date;
	}

	@Override
	public boolean isSatisfiedBy(Check t) {
        if (t.date_time.substring(0, 10).equals(date))
            return true;
        return false;
	}
}