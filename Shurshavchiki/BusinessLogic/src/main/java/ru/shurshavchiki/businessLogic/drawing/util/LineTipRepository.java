package ru.shurshavchiki.businessLogic.drawing.util;

import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;

import java.util.List;

public interface LineTipRepository {
    List<String> getAllTips();

    LineTipDrawer getLineTipDrawerByName(String name);
}
