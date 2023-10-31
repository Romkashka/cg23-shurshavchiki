package ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;

import java.util.List;

public class LineBaseRepository extends ImplementationRepositoryBase<LineBaseDrawer> {
    public LineBaseRepository() {
        super(List.of(
                new SolidLineBaseDrawer()
        ));
    }
}
