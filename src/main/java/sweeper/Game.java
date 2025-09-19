package sweeper;

public class Game {

    private final Bomb bomb;
    private Flag flag;

    private boolean firstMove;

    public GameState getState() {
        return state;
    }

    private GameState state;

    public Game (int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        flag.start();
        state = GameState.played;
        firstMove = true;
    }

    public Box getBox (Coord coord) {
        if (flag.get(coord) == Box.opened) {
            return bomb.get(coord);
        } else {
            return flag.get(coord);
        }
    }

    private void checkWinner() {
        if (state != GameState.played) return;

        int totalCells = Ranges.getSize().x * Ranges.getSize().y;
        int openedCells = totalCells - flag.getCountOfClosedBoxes();
        int safeCells = totalCells - bomb.getTotalBombs();

        // Победа наступает, если открыты все безопасные клетки
        if (openedCells == safeCells) {
            state = GameState.winner;
        }
    }



    public void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case opened: setOpenedToClosedBoxesAroundNumber(coord); return;
            case flag: return;
            case closed:
                switch (bomb.get(coord)) {
                    case n0:
                        flag.setOpenedToBox(coord);
                        openBoxAround(coord);
                        break;
                    case mine:
                        openBombs(coord);
                        break;
                    default:
                        flag.setOpenedToBox(coord);
                        break;
                }
        }
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (bomb.get(coord) != Box.mine) {
            if (flag.getCountOfFlagedBoxesAtound(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord)) {
                    if (flag.get(around) == Box.closed) {
                        openBox(around);
                    }
                }
        }
    }

    private void openBombs(Coord bombed) {
        state = GameState.bombed;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.mine) {
                flag.setOpenedToCloseBombMox(coord);
            } else {
                flag.setNoBombToFlagedBox(coord);
            }
    }

    private void openBoxAround(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        flag.toggleFlagedToBox(coord);
        checkWinner();
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;

        if (firstMove) {
            bomb.start(coord); // генерим бомбы, исключая выбранную клетку
            firstMove = false;
        }

        openBox(coord);
        checkWinner();
    }

    private boolean gameOver() {
        if (state == GameState.played) return false;
        start();
        return true;
    }

    public int getCountOfBombsLeft() {
        return bomb.getTotalBombs() - flag.getCountOfFlags();
    }

}
