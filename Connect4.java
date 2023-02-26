import java.io.*;
import java.util.*;
import java.lang.Math;

class Connect4{

  static boolean legalMove(int n, Jogo g){
    for(int i=5; i>=0; i--){
      if(g.tab[i][n] == '-') return true;
    }
    return false;
  }

  static boolean win(Jogo g, char in){
    String s = in+""+in+""+in+""+in;

    for(int i=0; i<6; i++){
      if(String.valueOf(g.tab[i]).contains(s)) return true;                     //verificar linhas;
    }

    for(int j=0; j<7; j++){
      String s2 = "";
      for(int i=0; i<6; i++){
        s2 += g.tab[i][j];
      }
      if(s2.contains(s)) return true;                                           //verificar colunas
    }

    int x1 = 2, y1 = 0;
    while(x1 >= 0){
      String s3 = "";
      int xc = x1, yc = y1;
      while(true){
        if(xc >= 6 || yc >= 7) break;
        s3 += g.tab[xc][yc];
        xc++;
        yc++;
      }
      if(s3.contains(s)) return true;
      x1--;
    }

    int x2 = 2, y2 = 6;
    while(x2 >= 0){
      String s4 = "";
      int xc = x2, yc = y2;
      while(true){
        if(xc>=6 || yc>=7 || xc<0 || yc<0) break;
        s4 += g.tab[xc][yc];
        xc++;
        yc--;
      }
      if(s4.contains(s)) return true;
      x2--;
    }

    int x3 = 0, y3 = 1;
    while(y3 <= 3){
      String s5 = "";
      int xc = x3, yc = y3;
      while(true){
        if(xc>=6 || yc>=7 || xc<0 || yc<0) break;
        s5 += g.tab[xc][yc];
        xc++;
        yc++;
      }
      if(s5.contains(s)) return true;
      y3++;
    }

    int x4 = 0, y4 = 5;
    while(y3 >= 3){
      String s6 = "";
      int xc = x4, yc = y4;
      while(true){
        if(xc>=6 || yc>=7 || xc<0 || yc<0) break;
        s6 += g.tab[xc][yc];
        xc++;
        yc--;
      }
      if(s6.contains(s)) return true;
      y3--;
    }

    return false;
  }

  static Jogo copyT (Jogo g){
    Jogo t = new Jogo();
    for(int i=0; i<6; i++){
      for(int j=0; j<7; j++){
        t.tab[i][j] = g.tab[i][j];
      }
    }
    return t;
  }


  static Jogo insert(int n, Jogo g, char c){
    Jogo novo = copyT(g);
      for(int i=5; i>=0; i--){
        if(novo.tab[i][n] == '-'){
          novo.tab[i][n] = c;
          break;
        }
    }
    novo.setValue(evaluationFunction(novo));
    return novo;
  }

  static int scoringSystem(String s){
    int countX=0, countO=0;
    for(int i=0; i<4; i++){
      if(s.charAt(i) == 'X') countX++;
      if(s.charAt(i) == 'O') countO++;
    }

    if      (countO == 4 && countX == 0){
      return -512;
    }else if(countO == 3 && countX == 0){
      return -50;
    }else if(countO == 2 && countX == 0){
      return -10;
    }else if(countO == 1 && countX == 0){
      return -1;
    }else if(countX == 1 && countO == 0){
      return 1;
    }else if(countX == 2 && countO == 0){
      return 10;
    }else if(countX == 3 && countO == 0){
      return 50;
    }else if(countX == 4 && countO == 0){
      return 512;

    }else{
      return 0;
    }
  }

  static int evaluateRows(Jogo g){
    int sum = 0;

    for(int i=0; i<6; i++){
      int x1 = 0, x2 = 3;
      while(x2<7){
        String s = "";
        for(int j=x1; j<=x2; j++){
          s += g.tab[i][j];
        }
        sum += scoringSystem(s);
        x1++;
        x2++;
      }
    }
    return sum;
  }

  static int evaluateColumns(Jogo g){
    int sum = 0;

    for(int j=0; j<7; j++){
      int y1 = 0, y2 = 3;
      while(y2 < 6){
        String s = "";
        for(int i=y1; i<=y2; i++){
          s += g.tab[i][j];
        }
        sum += scoringSystem(s);
        y1++;
        y2++;
      }
    }
    return sum;
  }

  static int evaluateDiagonals(Jogo g){
    int sum = 0;
    for(int i=0; i<3; i++){
      int x = i, y = 0;
      int temp1x = x, temp1y = y;
      while(x+3 < 6){
        String s = "";
        for(int j=0; j<4; j++){
          s += g.tab[x][y];
          x++;
          y++;
        }
        sum += scoringSystem(s);
        temp1x++;
        temp1y++;
        x=temp1x;
        y=temp1y;
      }
    }

    for(int i=1; i<4; i++){
      int x=0, y=i;
      int temp2x = x, temp2y = y;
      while(y+3 < 7){
        String s = "";
        for(int j=0; j<4; j++){
          s += g.tab[x][y];
          x++;
          y++;
        }
        sum += scoringSystem(s);
        temp2x++;
        temp2y++;
        x = temp2x;
        y = temp2y;
      }
    }

    for(int i=0; i<3; i++){
      int x=i, y=6;
      int temp3x = x, temp3y = y;
      while(x+3 < 6){
        String s = "";
        for(int j=0; j<4; j++){
          s += g.tab[x][y];
          x++;
          y--;
        }
        sum += scoringSystem(s);
        temp3x++;
        temp3y--;
        x = temp3x;
        y = temp3y;
      }
    }

    for(int i=5; i>2; i--){
      int x=0, y=i;
      int temp4x = x, temp4y = y;
      while(y-3 >= 0){
        String s = "";
        for(int j=0; j<4; j++){
          s += g.tab[x][y];
          x++;
          y--;
        }
        sum += scoringSystem(s);
        temp4x++;
        temp4y--;
        x = temp4x;
        y = temp4y;
      }
    }
    return sum;
  }

  static int evaluationFunction(Jogo g){
    return evaluateRows(g) + evaluateColumns(g) + evaluateDiagonals(g);
  }

  static void generator(LinkedList<Jogo> l, Jogo g, char c){
    for(int i=0; i<7; i++){
      if(legalMove(i, g)){
        Jogo novo = insert(i, g, c);
        novo.setDepth(g.getDepth()+1);
        novo.setValue(evaluationFunction(novo));
        l.add(novo);
      }
    }
  }

  static Jogo minimax(Jogo g, int n){
    Jogo j = max_value(g, n);
    return j;
  }

  static Jogo max_value(Jogo g, int n){
    if(g.getDepth() == n) return g;
    int v = -9999;
    Jogo j = new Jogo();
    LinkedList<Jogo> l = new LinkedList<>();
    generator(l, g, 'X');
    for(int i=0; i<l.size(); i++){
      if(min_value(l.get(i), n).getValue() > v) {
        j = l.get(i);
        v = j.getValue();
      }
    }
    return j;
  }

  static Jogo min_value(Jogo g, int n){
    if(g.getDepth() == n) return g;
    int v = 9999;
    Jogo j = new Jogo();
    LinkedList<Jogo> l = new LinkedList<>();
    generator(l, g, 'O');
    for(int i=0; i<l.size(); i++){
      if(max_value(l.get(i), n).getValue() < v){
        j = l.get(i);
        v = j.getValue();
      }
    }
    return j;
  }

  static Jogo alpha_beta(Jogo g, int n){
    Jogo j = max_value(g, n, -9999, 9999);
    return j;
  }

  static Jogo max_value(Jogo g, int n, int alfa, int beta){
    if(g.getDepth() == n) return g;
    int v = -9999;
    Jogo j = new Jogo();
    LinkedList<Jogo> l = new LinkedList<>();
    generator(l, g, 'X');
    for(int i=0; i<l.size(); i++){
      if(min_value(l.get(i), n, alfa, beta).getValue() > v) {
        j = l.get(i);
        v = j.getValue();
      }
      if(v >= beta) return j;
      alfa = Math.max(alfa, v);
    }
    return j;
  }

  static Jogo min_value(Jogo g, int n, int alfa, int beta){
    if(g.getDepth() == n) return g;
    int v = 9999;
    Jogo j = new Jogo();
    LinkedList<Jogo> l = new LinkedList<>();
    generator(l, g, 'O');
    for(int i=0; i<l.size(); i++){
      if(max_value(l.get(i), n, alfa, beta).getValue() < v) {
        j = l.get(i);
        v = j.getValue();
      }
      if(v <= alfa) return j;
      beta = Math.min(beta, v);
    }
    return j;
  }

  static Jogo monteCarlo(Jogo g){
    LinkedList<Jogo> l = new LinkedList<>();
    Jogo j = g;
    return j;

  }

  static String play(int alg, int depth, int turn, Scanner in){
    System.out.println();
    int cont=0;
    long startTime = 0, endTime = 0;
    Jogo x = new Jogo();
    while(cont < 42){
      if(turn == 1){
        System.out.println("CPU's turn to play...");
        if(alg == 1){
          startTime = System.nanoTime();
          x = minimax(x,depth);
          endTime = System.nanoTime();
        }else{
          startTime = System.nanoTime();
          x = alpha_beta(x,depth);
          endTime = System.nanoTime();
        }
        long resultTime = (endTime - startTime)/1000000;
        cont++;
        x.printJogo();
        System.out.println();
        if(win(x, 'X')) return "CPU wins!";
        System.out.println("Your turn to play." + "\n");
        int usr = in.nextInt();
        x = insert(usr, x, 'O');
        cont++;
        x.printJogo();
        System.out.println();
        if(win(x, 'O')) return "You win!";
      }else{
        System.out.println("Your turn to play.");
        int usr = in.nextInt();
        x = insert(usr, x, 'O');
        cont++;
        x.printJogo();
        System.out.println();
        if(win(x, 'O')) return "You win!";
        System.out.println("CPU's turn to play.");
        if(alg == 1){
          x = minimax(x, depth);
        }else{
          x = alpha_beta(x, depth);
        }
        cont++;
        x.printJogo();
        System.out.println();
        if(win(x, 'X')) return "CPU wins!";
      }
      if(cont == 42) return "It's a tie!";
    }
    return "Invalid result.";
  }


  public static void main(String[] args){
    int cont = 0;
    Scanner in = new Scanner(System.in);
    System.out.println("Choose algorithm: 1) Minimax 2) Alpha-Beta");
    int alg = in.nextInt();
    System.out.print("Choose depth");
    if(alg == 1){
      System.out.println(" (recommended 5-7):");
    }else{
      System.out.println(" (recommended 9-11):");
    }
    int d = in.nextInt();
    System.out.println("Choose the first play: 1) CPU 2) USER");
    int p = in.nextInt();
    System.out.println(play(alg, d, p, in));

  }
}
