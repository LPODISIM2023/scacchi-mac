package controller;
import domain.*;
import logic.MossaNonValida;

public class PedoneService implements Mossa {

    Scacchiera scacchiera;

    public PedoneService (Scacchiera scacchiera) {
        this.scacchiera = scacchiera;
    }



    public Scacchiera move (String nomePezzo, String new_Posizione, String colore) throws MossaNonValida {
        int vecchiaPosX  = 0 , vecchiaPosY = 0 ;
        int nuovaPosX  = 0 , nuovaPosY = 0;

        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                if (scacchiera.casella[x][y].getNome().equals(nomePezzo) && scacchiera.casella[x][y].getPezzo().getColore().equals(colore)) {
                    vecchiaPosX = x;
                    vecchiaPosY = y;
                }

                if (scacchiera.casella[x][y].getPosizione().equals(new_Posizione)) {
                    nuovaPosX = x;
                    nuovaPosY = y;
                }
            }
        }
        if (nuovaPosX == 0 || nuovaPosY == 0) throw new MossaNonValida("Mossa non valida, fuori scacchiera");
        if (scacchiera.casella[nuovaPosX][nuovaPosY].isOccupata() && scacchiera.casella[vecchiaPosX][vecchiaPosY].getPezzo().getColore().equals(scacchiera.casella[nuovaPosX][nuovaPosY].getPezzo().getColore()))
            throw new MossaNonValida("la casella è gia occupata");
        //questo if qua sopra per ora va bene ma quando implementeremo "mangiare" bisogna gestirlo
        switch (nomePezzo.charAt(0)) {

            case 'p':
                if (nuovaPosY == vecchiaPosY && Math.abs(nuovaPosX - vecchiaPosX) > 1) throw new MossaNonValida("Mossa non valida, il pedone può avanzare una casella alla volta");
                if (nuovaPosY != vecchiaPosY) throw new MossaNonValida("Mossa non valida, il pedone può andare solo dritto");
                break;

            case 'a':
                AlfiereService.controlloAlfiere(nuovaPosX,nuovaPosY,vecchiaPosX,vecchiaPosY,scacchiera);
                break;

                case 't':
                TorreService.controlloTorre(nuovaPosX,nuovaPosY,vecchiaPosX,vecchiaPosY,scacchiera);
                break;


            case 'c':
                /*System.out.println("vecchia posx: "+vecchiaPosX);
                System.out.println("vecchia posy: "+vecchiaPosY);
                System.out.println("nuova posx: "+nuovaPosX);
                System.out.println("nuova posy: "+nuovaPosY);*/
               if(!((nuovaPosX == vecchiaPosX+2 && nuovaPosY == vecchiaPosY+1 )|| (nuovaPosX == vecchiaPosX+2 && nuovaPosY == vecchiaPosY-1)
                       ||(nuovaPosX == vecchiaPosX-2 && nuovaPosY == vecchiaPosY-1 )||(nuovaPosX == vecchiaPosX-2 && nuovaPosY == vecchiaPosY+1 )
                       || (nuovaPosX == vecchiaPosX+1 && nuovaPosY == vecchiaPosY+2 )|| (nuovaPosX == vecchiaPosX+1 && nuovaPosY == vecchiaPosY-2)
                       ||(nuovaPosX == vecchiaPosX-1 && nuovaPosY == vecchiaPosY-2)||(nuovaPosX == vecchiaPosX-1 && nuovaPosY == vecchiaPosY+2 ))){
                   throw new MossaNonValida("Il cavallo può muoversi solo ad L");
                         }


                break;

            case 'q':
                // Verifica se la regina si sta muovendo lungo una linea retta (orizzontalmente, verticalmente o diagonalmente)
                if (!((nuovaPosX == vecchiaPosX || nuovaPosY == vecchiaPosY) ||
                        (Math.abs(nuovaPosX - vecchiaPosX) == Math.abs(nuovaPosY - vecchiaPosY)))) {
                    throw new MossaNonValida("La regina può muoversi solo lungo linee rette");
                }

                // Verifica la presenza di pezzi nel percorso se lo spostamento è lungo una linea retta
                if (nuovaPosX == vecchiaPosX) {
                    // Movimento verticale
                    int inizio = Math.min(vecchiaPosY, nuovaPosY) + 1;
                    int fine = Math.max(vecchiaPosY, nuovaPosY);
                    for (int i = inizio; i < fine; i++) {
                        if (scacchiera.casella[vecchiaPosX][i].isOccupata()) {
                            throw new MossaNonValida("Pezzo in mezzo");
                        }
                    }
                } else if (nuovaPosY == vecchiaPosY) {
                    // Movimento orizzontale
                    int inizio = Math.min(vecchiaPosX, nuovaPosX) + 1;
                    int fine = Math.max(vecchiaPosX, nuovaPosX);
                    for (int i = inizio; i < fine; i++) {
                        if (scacchiera.casella[i][vecchiaPosY].isOccupata()) {
                            throw new MossaNonValida("Pezzo in mezzo");
                        }
                    }
                } else {
                    // Movimento diagonale
                    int startX = Math.min(vecchiaPosX, nuovaPosX) + 1;
                    int startY = Math.min(vecchiaPosY, nuovaPosY) + 1;
                    int endX = Math.max(vecchiaPosX, nuovaPosX);
                    int endY = Math.max(vecchiaPosY, nuovaPosY);
                    for (int i = startX, j = startY; i < endX && j < endY; i++, j++) {
                        if (scacchiera.casella[i][j].isOccupata()) {
                            throw new MossaNonValida("Pezzo in mezzo");
                        }
                    }
                }
                break;
            case 'r':
                ReService.controlloRe(nuovaPosX,nuovaPosY,vecchiaPosX,vecchiaPosY,scacchiera);
                break;
        }



                    for (int i = 1; i < 9; i++) {
                        for (int j = 1; j < 9; j++) {
                    if (scacchiera.casella[i][j].getNome().equals(nomePezzo) && scacchiera.casella[i][j].getPezzo().getColore().equals(colore)) {
                        Pedone p1 = new Pedone(nomePezzo, colore);
                        String pos = scacchiera.casella[i][j].getPosizione();
                        scacchiera.casella[i][j] = new Casella("  ", pos, false);
                        for (int k = 1; k < 9; k++) {
                            for (int z = 1; z < 9; z++) {
                                if (scacchiera.casella[k][z].getPosizione().equals(new_Posizione)) {
                                    scacchiera.casella[k][z] = new Casella(new_Posizione, p1, k, z, true);
                                }
                            }
                        }
                    }
                }
            }

        return scacchiera;
    }
}/*System.out.println("vecchia posx: "+vecchiaPosX);
                System.out.println("vecchia posy: "+vecchiaPosY);
                System.out.println("nuova posx: "+nuovaPosX);
                System.out.println("nuova posy: "+nuovaPosY);*/
