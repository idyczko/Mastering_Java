import java.io.*;

interface CanClimb { int maxHeight(); }
interface HasClaws { boolean isSharp(); }
public class Koala implements CanClimb, HasClaws {
public boolean isSharp() { return true; }
public int maxHeight() { return 15; }
public static void main(String[] args) {
Object koala = new Object();
CanClimb canClimb = (CanClimb)koala;
HasClaws hasClaws = (HasClaws)canClimb;
Throwable t = (Throwable) hasClaws;
Serializable s = (Serializable) t;
 System.out.print(canClimb.maxHeight());
 System.out.print(hasClaws.isSharp());
 }
 }
