package main.java.serverPart.specificationConcret;
import main.java.serverPart.Objects.Check;
import main.java.serverPart.Specification.AbstractSpecification;

public class WitersChecksSpecification extends AbstractSpecification<Check> {

    private int id;

    public WitersChecksSpecification(int id) {
        this.id=id;
	}

	@Override
	public boolean isSatisfiedBy(Check t) {
        if (this.id == 1 || t.employee.id == this.id)
            return true;
        return false;
	}
}
