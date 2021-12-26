package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Table;
import main.java.serverPart.Specification.AbstractSpecification;

public class TableSitSpaceSpecification extends AbstractSpecification<Table> {

    private int max_a;
    private int min_a;

    public TableSitSpaceSpecification(int max, int min) {
        this.max_a=max; this.min_a = min;
    }

    @Override
    public boolean isSatisfiedBy(Table t) {
        if (this.min_a <= t.sit && t.sit <= this.max_a)
            return true;
        return false;
    }
}