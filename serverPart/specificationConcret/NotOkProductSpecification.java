package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Product;
import main.java.serverPart.Specification.AbstractSpecification;

public class NotOkProductSpecification extends AbstractSpecification<Product> {

    public NotOkProductSpecification() {
	}

	@Override
	public boolean isSatisfiedBy(Product t) {
        if (!t.status.equals("OK") || t.amount<=0)
            return true;
        return false;
	}
}