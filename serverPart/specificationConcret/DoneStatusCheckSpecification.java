package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Check;
import main.java.serverPart.Specification.AbstractSpecification;

public class DoneStatusCheckSpecification extends AbstractSpecification<Check> {
    public DoneStatusCheckSpecification() {
	}

	@Override
	public boolean isSatisfiedBy(Check t) {
        if (t.status.equals("DONE"))
            return true;
        return false;
	}
}