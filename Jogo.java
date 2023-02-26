class Jogo{
  char[][] tab;
  int depth;
  int value;
  Jogo parent;

  Jogo(){
    tab = new char[6][7];
    for(int i=0; i<6; i++){
      for(int j=0; j<7; j++){
        tab[i][j] = '-';
      }
    }
    value = 0;
    depth = 0;
    parent = null;
  }

  public int getDepth(){
    return depth;
  }

  public int getValue(){
    return value;
  }

  public void setDepth(int d){
    depth = d;
  }

  public void setValue(int n){
    value = n;
  }

  public char[][] getTab(){
    return tab;
  }

  public void setTab(char[][] c){
    tab = c;
  }

  public void setParent(Jogo g){
    parent = g;
  }

  public Jogo getParent(){
    return this.parent;
  }

  public void printJogo(){
    System.out.println("0 1 2 3 4 5 6");
    for(int i=0; i<6; i++){
      for(int j=0; j<7; j++){
        System.out.print(tab[i][j] + " ");
      }
      System.out.println();
    }
  }
}
