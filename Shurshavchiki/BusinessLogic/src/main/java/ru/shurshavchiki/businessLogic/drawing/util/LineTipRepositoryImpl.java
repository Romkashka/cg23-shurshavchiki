package ru.shurshavchiki.businessLogic.drawing.util;

import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.NoneLineTipDrawer;
import ru.shurshavchiki.businessLogic.exceptions.LineException;

import java.util.List;

public class LineTipRepositoryImpl implements LineTipRepository {
    private final List<LineTipDrawer> lineTipDrawers = List.of(
            new NoneLineTipDrawer()
    );

    @Override
    public List<String> getAllTips() {
        return lineTipDrawers.stream().map(LineTipDrawer::getName).toList();
    }

    @Override
    public LineTipDrawer getLineTipDrawerByName(String name) {
        var result = lineTipDrawers.stream()
                .filter(lineTipDrawer -> lineTipDrawer.getName().equals(name))
                .findAny();
        if (result.isEmpty()) {
            throw LineException.NoSuchLineTipDrawer(name);
        }

        return result.get();
    }
}
