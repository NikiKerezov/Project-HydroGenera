package Processor.Contracts;

import LocalData.Models.DataPackage;

import java.util.ArrayList;

public interface IProcessPackage {
    DataPackage processPackage(ArrayList<Character> data);
}
