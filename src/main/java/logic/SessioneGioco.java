package logic;

import domain.Pezzo;
import domain.Scacchiera;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SessioneGioco extends Modalita{



    public static boolean scaccoMatto1 = false;

    public static void setScaccoMatto1(boolean scaccoMatto) {
        scaccoMatto1 = scaccoMatto;
    }


    public SessioneGioco(Giocatore giocatore1, Giocatore giocatore2) {

        super(giocatore1, giocatore2);
    }


    @Override
    public void startGame() throws MossaNonValida {
            String nomeResa = null;
            boolean resa = true;
            boolean mossaFatta = false;
            GestioneInput gestioneInput = GestioneInput.getInstance();
            Scacchiera scacchiera = new Scacchiera();
            MossaServiceImpl p1 = new MossaServiceImpl(scacchiera);
            scacchiera.viewscacchiera();
            System.out.println();
            System.out.println("Inizia il turno " + giocatore1.getNome());
            while (resa && !(scaccoMatto1)) {
                //TURNO GIOCATORE BIANCO
                while (!mossaFatta && resa && !(scaccoMatto1)) {
                        System.out.println("Tocca a " + giocatore1.getNome());
                        System.out.println("Inserisci il pezzo che vuoi spostare o inserisci 'o' per accedere alle opzioni:");
                        GiocatoreService<? extends Giocatore> service = GiocatoreServiceFactory.getGiocatoreService(giocatore1.getClass());
                        String pezzoBianco = service.getPezzo(giocatore1, scacchiera);
                        if (pezzoBianco.equals("o")) {
                            if (this.opzioni().equals("3")) resa = false;
                            nomeResa = giocatore1.getNome();
                            break;
                        }
                        System.out.println("Inserisci mossa: ");
                        String mossaBianco = service.getPosizioneMossa(pezzoBianco, scacchiera);
                    try {
                        scacchiera = p1.move(pezzoBianco, mossaBianco.toUpperCase(), this.giocatore1.getColore());
                        mossaFatta = true;
                    } catch (MossaNonValida m) {
                        System.out.println(m.getMessage());
                        scacchiera.viewscacchiera();
                        System.out.println();
                    }
                }
                if (mossaFatta && !(scaccoMatto1)) {
                    scacchiera.viewscacchiera();
                    System.out.println();
                    mossaFatta = false;
                }

                //TURNO GIOCATORE NERO
                while (!mossaFatta && resa && !(scaccoMatto1)) {
                    System.out.println("Tocca a " + giocatore2.getNome());
                    System.out.println("Inserisci il pezzo che vuoi spostare o inserisci 'o' per accedere alle opzioni:");
                    GiocatoreService<? extends Giocatore> service2 = GiocatoreServiceFactory.getGiocatoreService(giocatore2.getClass());
                    String pezzoNero = service2.getPezzo(giocatore2, scacchiera);
                    if (pezzoNero.equals("o")) {
                        if (this.opzioni().equals("3")) resa = false;
                        nomeResa = giocatore2.getNome();
                        break;
                    }
                    System.out.println("Inserisci mossa: ");
                    String mossaNero = service2.getPosizioneMossa(pezzoNero, scacchiera);
                    System.out.println("mossa nero" + mossaNero);
                    try {
                        scacchiera = p1.move(pezzoNero, mossaNero.toUpperCase(), giocatore2.getColore());
                        mossaFatta = true;
                    } catch (MossaNonValida m) {
                        System.out.println(m.getMessage());
                        scacchiera.viewscacchiera();
                        System.out.println();
                    }
                }
                if (mossaFatta && !(scaccoMatto1)) {
                    scacchiera.viewscacchiera();
                    System.out.println();
                    mossaFatta = false;
                }
            }
            if (!resa) {
                System.out.println("Fine partita!");
                System.out.println(nomeResa + " si è arreso");
                System.out.println("Torna al menù principale (1)");
                System.out.println("Esci (2)");
                String input = gestioneInput.leggiNumeroInput();
                if (input.equals("1")) {
                    ChessGame.newGame();
                }
            }
            if (scaccoMatto1) {
                scaccoMatto1 = false;
                System.out.println("Torna al menù principale (1)");
                System.out.println("Esci (2)");
                String input = gestioneInput.leggiNumeroInput();
                if (input.equals("1")) {
                    ChessGame.newGame();
                }
            }

        }


    @Override
    public String opzioni() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Salva partita (1)");
        System.out.println("Annulla mossa (2)");
        System.out.println("Arrenditi (3)");
        return scanner.nextLine();
    }
}
