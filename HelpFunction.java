public void isPathClear(Square dest) { //path must be vertical,horizontal,diagonal
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
  return true;
}
