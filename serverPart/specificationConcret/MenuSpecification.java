package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Menu;
import main.java.serverPart.Specification.AbstractSpecification;

public class MenuSpecification extends AbstractSpecification<Menu> {

    private String date;

    public MenuSpecification(String date) {
        this.date=date;
	}

	@Override
	public boolean isSatisfiedBy(Menu t) {
        if (t.date.substring(0, 10).equals(this.date.substring(0, 10)))
            return true;
        return false;
	}
}
