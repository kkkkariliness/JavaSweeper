package sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombCount();
    }
    public int getTotalBombs() {
        return totalBombs;
    }
    public void setTotalBombs(int totalBombs) {
        this.totalBombs = totalBombs;
    }

    void start(Coord safeCoord) {
        bombMap = new Matrix(Box.n0);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb(safeCoord);
        }
    }


    Box get (Coord coord) {
        return bombMap.get(coord);
    }

    private void fixBombCount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs) totalBombs = maxBombs;
    }

    private void placeBomb(Coord safeCoord) {
        while (true) {
            Coord coord = Ranges.getRandomCoord();

            // не ставим бомбу в безопасную клетку и вокруг неё
            if (coord.equals(safeCoord)) continue;
            if (Ranges.getCoordsAround(safeCoord).contains(coord)) continue;

            if (Box.mine == bombMap.get(coord)) continue;

            bombMap.set(coord, Box.mine);
            incNumberAroundBomb(coord);
            break;
        }
    }


    private void incNumberAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (Box.mine != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
        }
    }

}
