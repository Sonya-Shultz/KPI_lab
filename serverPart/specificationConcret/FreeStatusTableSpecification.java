package main.java.serverPart.specificationConcret;

import main.java.serverPart.Objects.Reserve;
import main.java.serverPart.Objects.Table;
import main.java.serverPart.Specification.AbstractSpecification;

import java.util.ArrayList;

public class FreeStatusTableSpecification extends AbstractSpecification<Table> {

    private String status;

    public FreeStatusTableSpecification(String st) {
        this.status=st;
    }

    @Override
    public boolean isSatisfiedBy(Table t) {
        if (t.status_in_moment.equals(this.status))
            return true;
        return false;
    }
}
