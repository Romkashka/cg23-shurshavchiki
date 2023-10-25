package ru.shurshavchiki.businessLogic.drawing.util;

import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.SolidLineBaseDrawer;
import ru.shurshavchiki.businessLogic.exceptions.LineException;

import java.util.List;

public class LineBaseRepositoryImpl implements LineBaseRepository {
    private final List<LineBaseDrawer> lineBaseDrawers = List.of(
            new SolidLineBaseDrawer()
    );
    @Override
    public List<String> getAllBases() {
        return lineBaseDrawers.stream().map(LineBaseDrawer::getName).toList();
    }

    @Override
    public LineBaseDrawer getLineBaseDrawerByName(String name) {
        var result = lineBaseDrawers.stream().filter(drawer -> drawer.getName().equals(name)).findAny();

        if (result.isEmpty()) {
            throw LineException.NoSuchLineBaseDrawer(name);
        }

        return result.get();
    }
}
