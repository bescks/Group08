package it.polimi.group08;

/**
 * Created by lucio on 11/21/2016.
 */

public class TestForDeveloper {
    /*
    public static void saveGame(String fileName,Player white, Player black,BoardBasic board){
        String filePath=""+ fileName + ".txt";
        int i=0;
        int tNum = board.getTurnsNum();
        String turnsNum="";
        try{
            FileWriter fw = new FileWriter(filePath);
            while(tNum!=0){
                turnsNum = turnsNum + (tNum%10);
                tNum=tNum/10;
            }
            fw.write(turnsNum);
            fw.write("\r\n"+board.getColor());
            fw.write("\r\n"+board.getTimer1());
            fw.write("\r\n"+board.getTimer2());
            fw.write("\r\n"+board.getUnusedSpells());
            for(i=0;i<white.getPiecesAliveNum();i++){
                fw.write("\r\n"+white.getPiecesAlive()[i].getType());
                fw.write("\r\n"+white.getPiecesAlive()[i].getState());
                fw.write("\r\n"+white.getPiecesAlive()[i].getVitality());
                fw.write("\r\n"+white.getPiecesAlive()[i].getPositionX());
                fw.write("\r\n"+white.getPiecesAlive()[i].getPositionY());
            }
            for(i=0;i<white.getPiecesDeadNum();i++){
                fw.write("\r\n"+white.getPiecesDead()[i].getType());
                fw.write("\r\n"+white.getPiecesDead()[i].getState());
                fw.write("\r\n"+white.getPiecesDead()[i].getVitality());
                fw.write("\r\n"+white.getPiecesDead()[i].getPositionX());
                fw.write("\r\n"+white.getPiecesDead()[i].getPositionY());
            }
            for(i=0;i<black.getPiecesAliveNum();i++){
                fw.write("\r\n"+black.getPiecesAlive()[i].getType());
                fw.write("\r\n"+black.getPiecesAlive()[i].getState());
                fw.write("\r\n"+black.getPiecesAlive()[i].getVitality());
                fw.write("\r\n"+black.getPiecesAlive()[i].getPositionX());
                fw.write("\r\n"+black.getPiecesAlive()[i].getPositionY());
            }
            for(i=0;i<black.getPiecesDeadNum();i++){
                fw.write("\r\n"+black.getPiecesDead()[i].getType());
                fw.write("\r\n"+black.getPiecesDead()[i].getState());
                fw.write("\r\n"+black.getPiecesDead()[i].getVitality());
                fw.write("\r\n"+black.getPiecesDead()[i].getPositionX());
                fw.write("\r\n"+black.getPiecesDead()[i].getPositionY());
            }
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void readGame(String fileName,BoardBasic board,Player white,Player black) {
        String filePath = "" + fileName + ".txt";
        String type;
        char state;
        int vitality;
        int x;
        int y;
        int turnNum=0;
        File file = new File(filePath);
        try {
            Scanner sc = new Scanner(file);
            String s = "";
            String unusedSpellWhite;
            String unusedSpellBlack;

            s=sc.next();
            for(int i=0;i<s.length();i++)turnNum=turnNum*10+s.charAt(i)-48;
            board.setTurnsNum(turnNum);

            sc.nextLine();
            s=sc.nextLine();
            board.setColor(s);

            s=sc.nextLine();
            board.setTimer1(s.charAt(0)-48);
            s=sc.nextLine();
            board.setTimer2(s.charAt(0)-48);

            s=sc.nextLine();
            unusedSpellWhite=s.substring(0,4);
            unusedSpellBlack=s.substring(4,8);

            white.readPlayer();
            black.readPlayer();

            for(int i=0;i<8;i++){
                s=sc.nextLine();
                type = s;
                state =sc.nextLine().charAt(0);
                vitality = sc.nextLine().charAt(0)-48;
                x = sc.nextLine().charAt(0)-48;
                y = sc.nextLine().charAt(0)-48;
                if(type.equals("Archer")){
                    Piece p = new Archer("white",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    white.addPiece(p);
                }
                else if(type.equals("Dragon")){
                    Piece p = new Dragon("white",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    white.addPiece(p);
                }
                else if(type.equals("Giant")){
                    Piece p = new Giant("white",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    white.addPiece(p);
                }
                else if(type.equals("Knight")){
                    Piece p = new Knight("white",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    white.addPiece(p);
                }
                else if(type.equals("Squire")){
                    Piece p = new Squire("white",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    white.addPiece(p);
                }
                else if(type.equals("Mage")){
                    Mage p = new Mage("white",x,y);
                    p.setSpellsUnused(unusedSpellWhite);
                    p.setState(state);
                    p.setVitality(vitality);
                    white.addPiece(p);
                }
            }
            for(int i=0;i<8;i++){
                s=sc.nextLine();
                type = s;
                state =sc.nextLine().charAt(0);
                vitality = sc.nextLine().charAt(0)-48;
                x = sc.nextLine().charAt(0)-48;
                y = sc.nextLine().charAt(0)-48;
                if(type.equals("Archer")){
                    Piece p = new Archer("black",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    black.addPiece(p);
                }
                else if(type.equals("Dragon")){
                    Piece p = new Dragon("black",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    black.addPiece(p);
                }
                else if(type.equals("Giant")){
                    Piece p = new Giant("black",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    black.addPiece(p);
                }
                else if(type.equals("Knight")){
                    Piece p = new Knight("black",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    black.addPiece(p);
                }
                else if(type.equals("Squire")){
                    Piece p = new Squire("black",x,y);
                    p.setState(state);
                    p.setVitality(vitality);
                    black.addPiece(p);
                }
                else if(type.equals("Mage")){
                    Mage p = new Mage("black",x,y);
                    p.setSpellsUnused(unusedSpellBlack);
                    p.setState(state);
                    p.setVitality(vitality);
                    black.addPiece(p);
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        Player white = new Player("white");
        Player black = new Player("black");
        BoardBasic board = new BoardBasic();

//        if(true){
//            readGame("testGame1",board,white,black);
//            board.refreshBoard(white,black);
//        }

        String action;
        char act;
        int x1, y1, x2, y2;
        Piece p1, p2, p3;
        Mage m;
        boolean ctrl;

        while (board.judgeVictory()) {
            do {
                board.printBoard();
                Scanner sc = new Scanner(System.in);
                action = sc.nextLine();
                act = action.charAt(0);
                x1 = action.charAt(1) - 48;
                y1 = action.charAt(2) - 48;
                x2 = action.charAt(3) - 48;
                y2 = action.charAt(4) - 48;
                ctrl = true;

                switch (act) {
                    case 'M': {
                        if(board.getColor().equals("white")){
                            p1=white.choosePieceAlive(x1,y1);
                            p2=black.choosePieceAlive(x2,y2);
                            p3=white.choosePieceAlive(x2,y2);
                            if(board.judgeMove(p1,x2,y2)&&p1.getState()!='f'){
                                if(p1.getType().equals("Castle"))
                                {
                                    if(p3.getPieceEnable()){
                                        Castle c = (Castle)p1;
                                        ctrl=!p3.station(c);
                                        if(!ctrl)p1.moveTo(x2,y2);
                                    }
                                    else {
                                        p1.moveTo(x2,y2);
                                        ctrl = false;
                                    }
                                }
                                else if(p3.getType().equals("Castle"))
                                {
                                    Castle c = (Castle)p3;
                                    ctrl=!p1.station(c);
                                    if(!ctrl)p1.moveTo(x2,y2);
                                }
                                else{
                                    p1.moveTo(x2,y2);
                                    ctrl = false;
                                    if(board.excuteCombat(p1,p2)==1)white.pieceDie(p1);
                                    else if(board.excuteCombat(p1,p2)==2)black.pieceDie(p2);
                                    else if(board.excuteCombat(p1,p2)==3){
                                        white.pieceDie(p1);
                                        black.pieceDie(p2);
                                    }
                                }
                            }
                            else ctrl = true;
                        }
                        else {
                            p1=black.choosePieceAlive(x1,y1);
                            p2=white.choosePieceAlive(x2,y2);
                            p3=black.choosePieceAlive(x2,y2);
                            if(board.judgeMove(p1,x2,y2)&&p1.getState()!='f'){
                                if(p1.getType().equals("Castle"))
                                {
                                    if(p3.getPieceEnable()){
                                        Castle c = (Castle)p1;
                                        ctrl=!p3.station(c);
                                        if(!ctrl)p1.moveTo(x2,y2);
                                    }
                                    else {
                                        p1.moveTo(x2,y2);
                                        ctrl = false;
                                    }
                                }
                                else if(p3.getType().equals("Castle")){
                                    Castle c = (Castle)p3;
                                    ctrl=!p1.station(c);
                                    if(!ctrl)p1.moveTo(x2,y2);
                                }
                                else {
                                    p1.moveTo(x2, y2);
                                    ctrl = false;
                                    if (board.excuteCombat(p1, p2) == 1) black.pieceDie(p1);
                                    else if (board.excuteCombat(p1, p2) == 2) white.pieceDie(p2);
                                    else if (board.excuteCombat(p1, p2) == 3) {
                                        black.pieceDie(p1);
                                        white.pieceDie(p2);
                                    }
                                }
                            }
                            else ctrl =true;
                        }
                        if(ctrl)System.out.println("Move failed!");
                    }
                    break;
                    case 'A': {
                        if(board.getColor().equals("white")){
                            p1=white.choosePieceAlive(x1,y1);
                            p2=black.choosePieceAlive(x2,y2);

                            if(board.judgeAttack(p1,x2,y2)&&p1.getState()!='f'){
                                ctrl = false;
                                if(p1.attack(p2))black.pieceDie(p2);
                            }
                            else ctrl = true;
                        }
                        else {
                            p1=black.choosePieceAlive(x1,y1);
                            p2=white.choosePieceAlive(x2,y2);
                            if(board.judgeAttack(p1,x2,y2)&&p1.getState()!='f'){
                                ctrl = false;
                                if(p1.attack(p2))white.pieceDie(p2);
                            }
                            else ctrl =true;
                        }
                        if(ctrl)System.out.println("Attack failed!");
                    }
                    break;
                    case 'F': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p2=black.choosePieceAlive(x1,y1);
                        }
                        else {
                            m=black.chooseMage();
                            p2=white.choosePieceAlive(x1,y1);
                        }
                        ctrl = !board.excuteFreeze(m,p2);
                    }
                    break;
                    case 'H': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p2=white.choosePieceAlive(x1,y1);
                        }
                        else {
                            m=black.chooseMage();
                            p2=black.choosePieceAlive(x1,y1);
                        }
                        ctrl = !board.excuteHeal(m,p2);
                    }
                    break;
                    case 'R': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p1=white.choosePieceDead(x1,y1);
                            p2=black.choosePieceAlive(x1,y1);
                            ctrl = !board.excuteRevive(m,p1);
                            if(!ctrl) white.pieceRevived(p1);
                            if(p1.getState()=='n') {
                                int result = board.excuteCombat(p1, p2);
                                if (result == 1) white.pieceDie(p1);
                                else if (result == 2) black.pieceDie(p2);
                                else if (result == 3) {
                                    white.pieceDie(p1);
                                    black.pieceDie(p2);
                                }
                            }
                        }
                        else {
                            m=black.chooseMage();
                            p1=black.choosePieceDead(x1,y1);
                            p2=white.choosePieceAlive(x1,y1);
                            ctrl = !board.excuteRevive(m,p1);
                            if(!ctrl)black.pieceRevived(p1);
                            if(p1.getState()=='n') {
                                if (board.excuteCombat(p1, p2) == 1) white.pieceDie(p1);
                                else if (board.excuteCombat(p1, p2) == 2) black.pieceDie(p2);
                                else if (board.excuteCombat(p1, p2) == 3) {
                                    white.pieceDie(p1);
                                    black.pieceDie(p2);
                                }
                            }
                        }
                    }
                    break;
                    case 'T': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p1=white.choosePieceAlive(x1,y1);
                            p2=black.choosePieceAlive(x2,y2);
                            p3=white.choosePieceAlive(x2,y2);
                            if(p2.getType().equals("Castle")){
                                System.out.println("Castle can't be the target of combat!");
                                ctrl = true;
                            }
                            else if(p3.getType().equals("castle")){
                                Castle c = (Castle)p3;
                                ctrl = !p1.station(c);
                                if(!ctrl)ctrl = !board.excuteTeleport(m,p1,x2,y2);
                            }
                            else{
                                ctrl = !board.excuteTeleport(m,p1,x2,y2);
                                if(board.excuteCombat(p1,p2)==1)white.pieceDie(p1);
                                else if(board.excuteCombat(p1,p2)==2)black.pieceDie(p2);
                                else if(board.excuteCombat(p1,p2)==3){
                                    white.pieceDie(p1);
                                    black.pieceDie(p2);
                                }
                            }
                        }
                        else {
                            m=black.chooseMage();
                            p1=black.choosePieceAlive(x1,y1);
                            p2=white.choosePieceAlive(x2,y2);
                            p3=black.choosePieceAlive(x2,y2);
                            if(p2.getType().equals("Castle")){
                                System.out.println("Castle can't be the target of combat!");
                                ctrl = true;
                            }
                            else if(p3.getType().equals("castle"))
                            {
                                Castle c = (Castle)p3;
                                ctrl = !p1.station(c);
                                if(!ctrl)ctrl = !board.excuteTeleport(m,p1,x2,y2);
                            }
                            else{
                                ctrl = !board.excuteTeleport(m,p1,x2,y2);
                                if(board.excuteCombat(p1,p2)==1)black.pieceDie(p1);
                                else if(board.excuteCombat(p1,p2)==2)white.pieceDie(p2);
                                else if(board.excuteCombat(p1,p2)==3){
                                    black.pieceDie(p1);
                                    white.pieceDie(p2);
                                }
                            }
                        }
                    }
                    break;
                    case 'S':{
                        saveGame("testGame1",white,black,board);
                        ctrl = true;
                        System.out.println("******Save Game successful!******");
                    }
                    break;
                    case 'L':{

                            readGame("testGame1",board,white,black);
                            board.refreshBoard(white,black);
                        ctrl = true;
                        System.out.println("******Load Game successful!******");

                    }
                    default: {
                        System.out.println("It's not correct action!");
                    }
                    break;
                }
            } while (ctrl);
            board.refreshBoard(white,black);
            board.turnsOver();
        }

    }
    */
}
