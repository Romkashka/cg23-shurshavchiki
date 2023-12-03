package ru.shurshavchiki.businessLogic.drawing.lineTipDrawers;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;

import java.util.List;

public class LineTipRepository extends ImplementationRepositoryBase<LineTipDrawer> {
    public LineTipRepository() {
        super(List.of(
                new NoneLineTipDrawer()
        ));
    }
}
