public void isPathClear(Square dest, boolean capture) { //path must be vertical,horizontal,diagonal
  if(dest == location) return true;
  int r = location.getRow(), c = location.getCol();
  int endr = dest.getRow(), endc = dest.getCol();
  int dr = signum(endr - r), dc = signum(endc - c);
  r += dr; c += dc;
  while (r != destr || c != destc) {
    if (squares[r][c].getPiece() != null)
      return false;
      r += dr;
      c += dc;
  }
  if (dest.getPiece() != null)
    return capture;
  return true;
}
//pawn isMoveLegal
public boolean isMoveLegal(Square dest) {
  int dir = (getTeam())? -1 : 1; //move down for black, up for white
  int dr = rowDiff(dest), dc = colDiff(dest);
  
  if (firstMove && dr == dir*2 && dc == 0)
    return isPathClear(dest,false);
  if (dr == -1 && dc == 0) {
    return dest.getPiece() == n
  }
  
  
  
  
}
