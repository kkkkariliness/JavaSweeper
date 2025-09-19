package sweeper;

public enum Box {

    n0, n1, n2, n3, n4, n5, n6, n7, n8, mine, opened, closed, flag, mine_catch, none_mine;
    public Object image;

    Box getNextNumberBox() {
        if (this.ordinal() < n8.ordinal()) {
            return Box.values()[this.ordinal() + 1];
        }
        return this; // для всего остального не меняем
    }

    int getNumber() {
        return this.ordinal();
    }
}
