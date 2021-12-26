package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Table;
import main.java.serverPart.Specification.AbstractSpecification;

public class TableSuplSpecification extends AbstractSpecification<Table> {

    private String name;

    public TableSuplSpecification(String name) {
        this.name=name;
    }

    @Override
    public boolean isSatisfiedBy(Table t) {
        if(name.equals("")) return true;
        if (name.equals(t.from))
            return true;
        return false;
    }
}
