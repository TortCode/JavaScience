public boolean isPathClear(Square dest, boolean captureAllowed) { //path must be vertical,horizontal,diagonal
	  if(dest == location) return false;
	  int r = location.getRow(), c = location.getCol();
	  int endr = dest.getRow(), endc = dest.getCol();
	  int dr = signum(endr - r), dc = signum(endc - c);
	  r += dr; c += dc;
	  while (r != endr || c != endc) {
	    if (GameBoard.squares[r][c].getPiece() != null)
	      return false;
	    r += dr;
	    c += dc;
	  }
	  if (dest.getPiece() != null)
	    return captureAllowed && dest.getPiece().team != this.team;
	  return true;
	}
//pawn isMoveLegal
public boolean isMoveLegal(Square dest) {
  int dir = (getTeam())? 1 : -1; //move down for black, up for white
  int dr = rowDiff(dest), dc = colDiff(dest);
  
  if (dr == dir*1 && dc == 0) {
    return dest.getPiece() == null;
  }
  if (dr == dir*1 && Math.abs(dc)==1)
    return dest.getPiece() != null;
  if (firstMove && dr == dir*2 && dc == 0)
    return isPathClear(dest,false);
  
  return false;
}
