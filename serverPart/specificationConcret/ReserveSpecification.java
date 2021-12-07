package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Reserve;
import main.java.serverPart.Specification.AbstractSpecification;

public class ReserveSpecification extends AbstractSpecification<Reserve> {
    String date;
    public ReserveSpecification(String date) {
        this.date = date;
	}

	@Override
	public boolean isSatisfiedBy(Reserve t) {
        if (t.date_time.substring(0, 10).equals(date))
            return true;
        return false;
	}
}