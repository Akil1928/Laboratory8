package domain;

import java.util.List;

public class NumberItem {
    private List<Integer> list;

    public NumberItem(int value) {
        this.list = List.of(value);
    }

    public NumberItem(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }


    public Integer getValue() {
        return list.isEmpty() ? null : list.get(0);
    }

    public void setValue(Integer value) {
        this.list = List.of(value);
    }
}
