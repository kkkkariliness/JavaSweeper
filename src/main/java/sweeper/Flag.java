package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.closed);
        countOfClosedBoxes = Ranges.getSize().x*Ranges.getSize().y;
    }

    Box get (Coord coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        if (flagMap.get(coord) != Box.opened) {   // <— защита от повторного открытия
            flagMap.set(coord, Box.opened);
            countOfClosedBoxes--;
        }
    }


    public void toggleFlagedToBox(Coord coord) {
        switch(flagMap.get(coord)) {
            case flag : setClosedToBox(coord); break;
            case closed : setFlagetToBox(coord); break;
        }
    }

    public void setFlagetToBox(Coord coord) {
        flagMap.set(coord, Box.flag);
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.closed);
    }

    int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.mine_catch);
    }

    public void setNoBombToFlagedBox(Coord coord) {
        if (flagMap.get(coord) == Box.flag) {
            flagMap.set(coord, Box.none_mine);
        }
    }

    public void setOpenedToCloseBombMox(Coord coord) {
        if (flagMap.get(coord) == Box.closed) {
            flagMap.set(coord, Box.mine);
        }
    }

    int getCountOfFlagedBoxesAtound(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (flagMap.get(around) == Box.flag) {
                count++;
            }
        }
        return count;
    }

    int getCountOfFlags() {
        int count = 0;
        for (Coord coord : Ranges.getAllCoords()) {
            if (flagMap.get(coord) == Box.flag) {
                count++;
            }
        }
        return count;
    }

}
