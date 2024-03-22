package controller.impl;

import controller.MossaNonValida;
import controller.PezzoService;
import domain.Scacchiera;
import domain.Torre;

public class TorreServiceImpl implements PezzoService<Torre> {

    @Override
    public void controlloMossa(int nuovaPosX, int nuovaPosY, int vecchiaPosX, int vecchiaPosY, Scacchiera scacchiera) throws MossaNonValida {
        if (nuovaPosY != vecchiaPosY) {
            if (nuovaPosX != vecchiaPosX) {
                throw new MossaNonValida("Mossa non valida, la torre non può andare in diagonale");
            } else {
                if (vecchiaPosY > nuovaPosY) {
                    for (int i = vecchiaPosY - 1; i > nuovaPosY; i--) {
                        if (scacchiera.casella[vecchiaPosX][i].isOccupata())
                            throw new MossaNonValida("pezzo in mezzo");
                    }
                } else {
                    for (int i = vecchiaPosY + 1; i < nuovaPosY; i++) {
                        if (scacchiera.casella[vecchiaPosX][i].isOccupata())
                            throw new MossaNonValida("pezzo in mezzo");
                    }
                }
            }
        } else {
            if (vecchiaPosX > nuovaPosX) {
                for (int i = vecchiaPosX - 1; i > nuovaPosX; i--) {
                    if (scacchiera.casella[i][vecchiaPosY].isOccupata())
                        throw new MossaNonValida("pezzo in mezzo");
                }
            } else {
                for (int i = vecchiaPosX + 1; i < nuovaPosX; i++) {
                    if (scacchiera.casella[i][vecchiaPosY].isOccupata())
                        throw new MossaNonValida("pezzo in mezzo");
                }
            }
        }
    }
}
