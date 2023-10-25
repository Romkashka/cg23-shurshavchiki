package ru.shurshavchiki.businessLogic.drawing.util;

import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;

import java.util.List;

public interface LineBaseRepository {
    List<String> getAllBases();

    LineBaseDrawer getLineBaseDrawerByName(String name);
}
