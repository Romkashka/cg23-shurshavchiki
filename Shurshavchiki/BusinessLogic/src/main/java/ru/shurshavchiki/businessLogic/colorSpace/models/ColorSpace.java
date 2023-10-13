package ru.shurshavchiki.businessLogic.colorSpace.models;

import lombok.Getter;

import java.util.List;

public record ColorSpace(String Name, List<Channel> Channels) {
}
